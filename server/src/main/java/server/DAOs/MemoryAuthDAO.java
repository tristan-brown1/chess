package server.DAOs;

import dataAccess.DataAccessException;
import server.AuthData;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryAuthDAO implements AuthDAO{

    final private HashMap<String,AuthData> auth = new HashMap<>();

    public void clearAll() throws DataAccessException {
        auth.clear();
    }
}
