package dataAccess.DAOs;

import exception.DataAccessException;
import sharedDataClasses.AuthData;
import sharedDataClasses.GameData;
import sharedDataClasses.UserData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class MemoryAuthDAO implements DAO{

    final private HashMap<String,AuthData> auth;

    public MemoryAuthDAO(){
        auth = new HashMap<String, AuthData>();
    }

    public void clearAll() {
        auth.clear();
    }

    public AuthData createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        AuthData newData = new AuthData(authToken,username);
        auth.put(authToken, newData);
        return newData;
    }

    public AuthData getAuth(String authToken) {
        return auth.getOrDefault(authToken, null);
    }

    public void deleteAuth(String authToken){
        auth.remove(authToken);
    }

}
