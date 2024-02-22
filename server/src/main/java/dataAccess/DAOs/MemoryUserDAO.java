package dataAccess.DAOs;

import dataAccess.DataAccessException;
import server.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO{

    final private HashMap<String, UserData> user = new HashMap<>();

    public void clearAll() {
        user.clear();
    }

    public UserData getUser(String username) {
        return user.getOrDefault(username, null);
    }
    public void createUser(String username, String password, String email){
        UserData newUser = new UserData(username,password,email);
        user.put(username,newUser);
    }






}
