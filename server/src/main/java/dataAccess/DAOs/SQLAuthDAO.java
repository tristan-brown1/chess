package dataAccess.DAOs;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthData;
import model.UserData;

import java.sql.ResultSet;
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

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username,authToken FROM auth WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if(rs.next()){
                        return readAuthData(rs);
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
    public void deleteAuth(String authToken) throws DataAccessException {

        String[] createUserStatements = {
                "DELETE FROM auth " +
                "WHERE authToken = '" + authToken + "'"
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



    private AuthData readAuthData(ResultSet rs) throws SQLException {
        if(rs != null){
            var username = rs.getString("username");
            var authToken = rs.getString("authToken");
            return new AuthData(authToken,username);
        }
        else {
            return null;
        }
    }


}
