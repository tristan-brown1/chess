package dataAccess.DAOs;

import exception.DataAccessException;
import dataAccess.DatabaseManager;
import sharedDataClasses.UserData;

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
        DAO.executeStatement(createStatements);
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

            DAO.executeStatement(createUserStatements);
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
