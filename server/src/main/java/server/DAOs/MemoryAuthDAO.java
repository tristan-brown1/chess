package server.DAOs;

import dataAccess.DataAccessException;
import server.AuthData;
import server.UserData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public abstract class MemoryAuthDAO implements AuthDAO{

    final private HashMap<String,AuthData> auth = new HashMap<>();

    public void clearAll() throws DataAccessException {
        auth.clear();
    }

    public String createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData newData = new AuthData(authToken,username);
        auth.put(authToken, newData);
        return authToken;
    }


}
