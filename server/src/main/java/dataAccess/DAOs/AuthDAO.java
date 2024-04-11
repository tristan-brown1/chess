package dataAccess.DAOs;

import exception.DataAccessException;
import sharedDataClasses.AuthData;

public interface AuthDAO extends DAO {


    public void clearAll() throws DataAccessException;

    public AuthData createAuth(String username) throws DataAccessException;

    public AuthData getAuth(String authToken);

    public void deleteAuth(String authToken) throws DataAccessException;


}
