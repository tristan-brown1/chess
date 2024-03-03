package dataAccess.DAOs;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthData;
import model.UserData;

import java.sql.SQLException;
import java.util.HashMap;

public class SQLAuthDAO implements AuthDAO{


    public void clearAll() throws DataAccessException {
        String[] createStatements = {
                """
            DROP TABLE IF EXISTS auth;
            """
        };


        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException | DataAccessException ex) {
            throw new DataAccessException(String.format("Unable to clear database: %s", ex.getMessage()));
        }
    }

    @Override
    public AuthData createAuth(String username) {
        return null;
    }

    @Override
    public AuthData getAuth(String authToken) {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {

    }


}
