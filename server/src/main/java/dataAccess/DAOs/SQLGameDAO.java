package dataAccess.DAOs;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.GameData;

import java.sql.SQLException;
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
    public GameData createGame(String gameName) throws DataAccessException {

        GameData newGame = new GameData();
        Random random = new Random();
        newGame.setGameID(random.nextInt(10,10000));
        newGame.setGameName(gameName);

        String[] createAuthStatement = {
                "INSERT INTO game (gameID, gameName, gameData) VALUES ('" + newGame.getGameID() + "', '" + gameName + "', '" +"', '" + newGame + "');"
        };

        executeStatement(createAuthStatement);

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


}
