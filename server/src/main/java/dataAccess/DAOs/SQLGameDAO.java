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

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

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
            String createGameStatement = "UPDATE game SET whiteUsername = ? + WHERE gameID = ? );";
            executeUpdate(createGameStatement,currentGame.getWhiteUsername() ,currentGame.getGameID());
        }
        else if (playerColor.equalsIgnoreCase("black")){
            currentGame.setBlackUsername(username);
            currentGame.setWhiteUsername(username);
            String createGameStatement = "UPDATE game SET blackUsername = ? + WHERE gameID = ? );";
            executeUpdate(createGameStatement,currentGame.getBlackUsername() ,currentGame.getGameID());
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
        String gameJson = new Gson().toJson(newGame.getGame());

        String createGameStatement =
            "INSERT INTO game (gameName, whiteUsername, blackUsername, game, gameID) " +
            "VALUES (?,?,?,?,?);";
        executeUpdate(createGameStatement,gameName,newGame.getWhiteUsername(),newGame.getBlackUsername(),gameJson,newGame.getGameID());
        return newGame;

    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
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
