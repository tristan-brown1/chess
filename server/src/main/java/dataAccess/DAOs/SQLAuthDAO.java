package dataAccess.DAOs;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import sharedDataClasses.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLAuthDAO implements AuthDAO{


    public void clearAll() throws DataAccessException {
        String[] createStatements = {
                """
            DROP TABLE IF EXISTS auth;
            """
        };
        executeStatement(createStatements);
    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        if(username == null){
            return null;
        }
        else{
            String authToken = UUID.randomUUID().toString();
            AuthData newData = new AuthData(authToken,username);

            String createAuthStatement = "INSERT INTO auth (authToken, username) VALUES (?,?)";

            executeUpdate(createAuthStatement,newData.getAuthToken(),username);

            return newData;
        }
    }

    @Override
    public AuthData getAuth(String authToken) {

        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT username,authToken FROM auth WHERE authToken=? ";
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
        String createUserStatements = "DELETE FROM auth WHERE authToken = ?";
        executeUpdate(createUserStatements,authToken);
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


}
