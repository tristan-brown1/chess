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

}
