package server.DAOs;

import dataAccess.DataAccessException;
import server.AuthData;
import server.GameData;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<String, GameData> game = new HashMap<>();


    public void clearAll() throws DataAccessException {
        game.clear();
    }
}
