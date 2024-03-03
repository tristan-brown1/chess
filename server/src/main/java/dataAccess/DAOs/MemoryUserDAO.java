package dataAccess.DAOs;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO{

    final private HashMap<String, UserData> user;

    public MemoryUserDAO(){
        user = new HashMap<String, UserData>();
    }

    public void clear() {
        user.clear();
    }

    public UserData getUser(String username) {
        return user.getOrDefault(username, null);
    }
    public UserData createUser(String username, String password, String email){
        UserData newUser = new UserData(username,password,email);
        user.put(username,newUser);
        return newUser;
    }
}
