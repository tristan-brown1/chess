package server.DAOs;

import dataAccess.DataAccessException;
import org.eclipse.jetty.server.Authentication;
import server.AuthData;
import server.UserData;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryUserDAO implements UserDAO{

    final private HashMap<String, UserData> user = new HashMap<>();

    public void clearAll() throws DataAccessException {
        user.clear();
    }

    public UserData getUser(String username) throws DataAccessException {
        return user.getOrDefault(username, null);
    }
    public void createUser(String username, String password, String email) throws DataAccessException {
        UserData newUser = new UserData(username,password,email);
        user.put(username,newUser);
    }

    @Override
    public String createAuth(String username) throws DataAccessException {
        return null;
    }


}
