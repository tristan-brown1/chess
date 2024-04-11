package dataAccess.DAOs;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.DataAccessException;
import dataAccess.DatabaseManager;
import sharedDataClasses.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Random;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLGameDAO implements GameDAO{


    public void clearAll() throws DataAccessException {
        String[] createStatements = {
                """
            DROP TABLE IF EXISTS game;
            """
        };

        DAO.executeStatement(createStatements);
    }

    public void clearGame(int gameID) throws DataAccessException {
        String createGameStatement = "DELETE FROM game WHERE gameID = ?";
        executeUpdate(createGameStatement,gameID);
    }

    public void updateGameStatus(int gameID, ChessGame game) throws DataAccessException {
        String createGameStatement = "UPDATE game SET game = ? WHERE gameID = ?";
        executeUpdate(createGameStatement,new Gson().toJson(game),gameID);
    }

    public void removePlayer(String username, int gameID,String playerColor) throws DataAccessException {

        if(playerColor.equalsIgnoreCase("white")){
            String createGameStatement = "UPDATE game SET whiteUsername = NULL WHERE gameID = ? AND whiteUsername = ?";
            executeUpdate(createGameStatement,gameID,username);
        }
        else{
            String createGameStatement = "UPDATE game SET blackUsername = NULL WHERE gameID = ? AND blackUsername = ?";
            executeUpdate(createGameStatement,gameID,username);
        }
    }


    public Object updateGame(GameData gameData, String playerColor, String username) throws DataAccessException {

        if(gameData == null || playerColor == null || username == null){
            return null;
        }
        else{
            GameData currentGame = getGame(gameData.getGameID());
            if(playerColor.equalsIgnoreCase("white")){
                currentGame.setWhiteUsername(username);
                String createGameStatement = "UPDATE game SET whiteUsername = ? WHERE gameID = ?";
                executeUpdate(createGameStatement,currentGame.getWhiteUsername() ,currentGame.getGameID());
            }
            else if (playerColor.equalsIgnoreCase("black")){
                currentGame.setBlackUsername(username);
//            currentGame.setWhiteUsername(username);
                String createGameStatement = "UPDATE game SET blackUsername = ? WHERE gameID = ?";
                executeUpdate(createGameStatement,currentGame.getBlackUsername() ,currentGame.getGameID());
            }
            return 1;
        }
        
    }


    public GameData getGame(int gameID) {

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameName,whiteUsername,blackUsername,game,gameID FROM game WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if(rs.next()){
                        return readGameData(rs);
                    }
                    else {
                        return null;
                    }
                }
            }
        } catch (Exception e) {
//            throw new DataAccessException( String.format("Unable to read data: %s", e.getMessage()));
            return null;
        }

    }

    public HashSet getGames() throws DataAccessException {
        var result = new HashSet<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameName,whiteUsername,blackUsername,game,gameID FROM game";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGameData(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return result;
    }

    public GameData createGame(String gameName) throws DataAccessException {

        if(gameName == null){
            return null;
        }
        else{
            GameData newGame = new GameData();
            Random random = new Random();
            newGame.setGameID(random.nextInt(10,10000));
            newGame.setGameName(gameName);
            String gameJson = new Gson().toJson(newGame.getGame());

            String createGameStatement =
                    "INSERT INTO game (gameName, whiteUsername, blackUsername, game, gameID) " +
                            "VALUES (?,?,?,?,?);";
            executeUpdate(createGameStatement,gameName,newGame.getWhiteUsername(),newGame.getBlackUsername(),gameJson,newGame.getGameID());
            return newGame;
        }

    }


    private GameData readGameData(ResultSet rs) throws SQLException {
        if(rs!= null){
            var whiteUsername = rs.getString("whiteUsername");
            var blackUsername = rs.getString("blackUsername");
            var gameName = rs.getString("gameName");
            var game = new Gson().fromJson(rs.getString("game"), ChessGame.class);
            var gameID = rs.getInt("gameID");
            return new GameData(gameID,whiteUsername,blackUsername,gameName,game);
        }
        else {
            return null;
        }
    }

}
