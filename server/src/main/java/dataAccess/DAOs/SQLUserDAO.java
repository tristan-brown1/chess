package dataAccess.DAOs;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.UserData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public UserData getUser(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username,password,email FROM user WHERE username = ?;";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if(rs.next()){
                        return readUserData(rs);
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
    public UserData createUser(String username, String password, String email) throws DataAccessException {
        if(username == null || password == null || email == null){
            return null;
        }
        else{
            UserData newUser = new UserData(username,password,email);
            String[] createUserStatements = {
                    "INSERT INTO user (username, password, email) VALUES ('" + username + "', '" + password + "', '" + email + "');"
            };

            executeStatement(createUserStatements);
            return newUser;
        }
    }

    private UserData readUserData(ResultSet rs) throws SQLException {
        if(rs != null){
            var username = rs.getString("username");
            var password = rs.getString("password");
            var email = rs.getString("email");
            return new UserData(username,password,email);
        }
        else {
            return null;
        }
    }
}
