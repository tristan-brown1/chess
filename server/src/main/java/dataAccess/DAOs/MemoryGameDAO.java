package dataAccess.DAOs;

import dataAccess.DataAccessException;
import server.GameData;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<String, GameData> game = new HashMap<>();


    public void clearAll(){
        game.clear();
    }

    public GameData createGame(String gameName){
        GameData newGame = new GameData();
        Random random = new Random();
        newGame.setGameID(random.nextInt(10,10000));
        newGame.setGameName(gameName);
        game.put(gameName, newGame);
        return newGame;
    }

}
