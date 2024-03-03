package dataAccess.DAOs;

import dataAccess.DataAccessException;
import model.AuthData;

public interface AuthDAO{


    public void clearAll() throws DataAccessException;

    public AuthData createAuth(String username);

    public AuthData getAuth(String authToken);

    public void deleteAuth(String authToken);


}
