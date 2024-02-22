package dataAccess.DAOs;

import dataAccess.DataAccessException;
import server.GameData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<String, GameData> game = new HashMap<>();


    public void clearAll(){
        game.clear();
    }
}
