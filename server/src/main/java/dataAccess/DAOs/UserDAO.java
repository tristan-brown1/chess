package dataAccess.DAOs;

import exception.DataAccessException;
import sharedDataClasses.UserData;

public interface UserDAO extends DAO{


    void clear() throws DataAccessException;

    UserData getUser(String username) throws DataAccessException;

    UserData createUser(String username, String password, String email) throws DataAccessException;

}
