package server.DAOs;

import dataAccess.DataAccessException;
import server.AuthData;
import server.GameData;
import server.UserData;

import java.util.HashMap;
import java.util.HashSet;

public abstract class MemoryGameDAO implements GameDAO{

    final private HashMap<String, GameData> game = new HashMap<>();


    public void clearAll() throws DataAccessException {
        game.clear();
    }
}
