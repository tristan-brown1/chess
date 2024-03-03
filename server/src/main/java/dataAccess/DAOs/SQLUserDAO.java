package dataAccess.DAOs;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.UserData;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO{



    public void clear() throws DataAccessException {

        String[] createStatements = {
                """
            DROP TABLE IF EXISTS user;
            """
        };
        executeStatement(createStatements);
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

    @Override
    public UserData getUser(String username) {
        return null;
    }

    @Override
    public UserData createUser(String username, String password, String email) throws DataAccessException {
        UserData newUser = new UserData(username,password,email);
        String[] createUserStatements = {
           "INSERT INTO user (username, password, email) VALUES ('" + username + "', '" + password + "', '" + email + "');"
        };

        executeStatement(createUserStatements);
        return newUser;
    }
}
