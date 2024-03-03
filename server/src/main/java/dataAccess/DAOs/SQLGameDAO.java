package dataAccess.DAOs;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.GameData;

import java.sql.SQLException;
import java.util.HashSet;

public class SQLGameDAO implements GameDAO{


    public void clearAll() throws DataAccessException {
        String[] createStatements = {
                """
            DROP TABLE IF EXISTS game;
            """
        };


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

    @Override
    public void updateGame(GameData gameData, String playerColor, String username) {

    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public HashSet getGames() {
        return null;
    }

    @Override
    public GameData createGame(String gameName) {
        return null;
    }

}
