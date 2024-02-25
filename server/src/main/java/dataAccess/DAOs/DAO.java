package dataAccess.DAOs;

import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;

public interface DAO {

    void clearAll()throws DataAccessException;

    UserData getUser(String username) throws DataAccessException ;

    void createUser(String username, String password, String email) throws DataAccessException;

    AuthData createAuth(String username) throws DataAccessException;
}
