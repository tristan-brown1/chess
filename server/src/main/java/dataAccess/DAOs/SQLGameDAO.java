package dataAccess.DAOs;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthData;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class SQLGameDAO implements GameDAO{


    public void clearAll() throws DataAccessException {
        String[] createStatements = {
                """
            DROP TABLE IF EXISTS game;
            """
        };

        executeStatement(createStatements);
    }

    @Override
    public void updateGame(GameData gameData, String playerColor, String username) throws DataAccessException {

        GameData currentGame = getGame(gameData.getGameID());
        if(playerColor.equalsIgnoreCase("white")){
            currentGame.setWhiteUsername(username);
            String[] createGameStatement = {
                    "UPDATE game" +
                    "SET whiteUsername = '" + currentGame.getWhiteUsername() +
                    "WHERE gameID = '" + currentGame.getGameID() +  "');"
            };
            executeStatement(createGameStatement);
        }
        else if (playerColor.equalsIgnoreCase("black")){
            currentGame.setBlackUsername(username);
            String[] createGameStatement = {
                    "UPDATE game" +
                            "SET blackUsername = '" + currentGame.getBlackUsername() +
                            "WHERE gameID = '" + currentGame.getGameID() +  "');"
            };
            executeStatement(createGameStatement);
        }
    }

    @Override
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

    @Override
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

    @Override
    public GameData createGame(String gameName) throws DataAccessException {

        GameData newGame = new GameData();
        Random random = new Random();
        newGame.setGameID(random.nextInt(10,10000));
        newGame.setGameName(gameName);

        String[] createGameStatement = {
                "INSERT INTO game (gameName, whiteUsername, blackUsername, game, gameID) " +
                "VALUES ('" + gameName + "', '" + newGame.getWhiteUsername() + "', '" + newGame.getBlackUsername() + "', '" + newGame.getGame() + "', '" + newGame.getGameID() + "');"
        };
        executeStatement(createGameStatement);
        return newGame;

    }

    private static void executeStatement(String[] createStatements) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to clear database: %s", ex.getMessage()));
        }
    }


    private GameData readGameData(ResultSet rs) throws SQLException {
        if(rs != null){
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
