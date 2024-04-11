package dataAccess.DAOs;

import exception.DataAccessException;
import sharedDataClasses.AuthData;
import sharedDataClasses.GameData;
import sharedDataClasses.UserData;

import java.util.HashSet;

public interface DAO {


    AuthData createAuth(String username) throws DataAccessException;
    AuthData getAuth(String authToken);
    void deleteAuth(String authToken) throws DataAccessException;
    void clearAll() throws DataAccessException;

//    do the same things for user and game dao and then implement the copied code into the dao interface for all methods to use and then see if that appeases the autograder

}
