package dataAccess.DAOs;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthData;
import model.UserData;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO{


    public void clearAll() throws DataAccessException {
        String[] createStatements = {
                """
            DROP TABLE IF EXISTS user;
            """
        };
        executeStatement(createStatements);
    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData newData = new AuthData(authToken,username);

        String[] createAuthStatement = {
                "INSERT INTO auth (authToken, username) VALUES ('" + authToken + "', '" + username + "');"
        };

        executeStatement(createAuthStatement);

        return newData;
    }

    @Override
    public AuthData getAuth(String authToken) {

//        String[] createUserStatements = {
//                "INSERT INTO user (username, password, email) VALUES ('" + username + "', '" + password + "', '" + email + "');"
//        };
//
//        executeStatement(createUserStatements);


        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

        String[] createUserStatements = {
                "DELETE FROM auth " +
                        "WHERE + '" + authToken + "'"

        };

        executeStatement(createUserStatements);

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
