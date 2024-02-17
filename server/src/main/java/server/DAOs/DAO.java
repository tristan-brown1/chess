package server.DAOs;

import dataAccess.DataAccessException;
import server.UserData;

public interface DAO {

    void clearAll()throws DataAccessException;

    UserData getUser(String username) throws DataAccessException ;

    void createUser(String username, String password, String email) throws DataAccessException;

    String createAuth(String username) throws DataAccessException;
}
