package dataAccess.DAOs;

import dataAccess.DataAccessException;
import server.AuthData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{

    final private HashMap<String,AuthData> auth = new HashMap<>();

    public void clearAll() {
        auth.clear();
    }

    public AuthData createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        AuthData newData = new AuthData(authToken,username);
        auth.put(authToken, newData);
        return newData;
    }


}
