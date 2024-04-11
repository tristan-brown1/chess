package dataAccess.DAOs;

import exception.DataAccessException;
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
        DAO.executeStatement(createStatements);
    }


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

    public void deleteAuth(String authToken) throws DataAccessException {
        String createUserStatements = "DELETE FROM auth WHERE authToken = ?";
        executeUpdate(createUserStatements,authToken);
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
