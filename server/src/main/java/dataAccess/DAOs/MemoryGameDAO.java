package dataAccess.DAOs;

import model.GameData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<Integer, GameData> game = new HashMap<>();


    public void clearAll(){
        game.clear();
    }

    public GameData createGame(String gameName){
        GameData newGame = new GameData();
        Random random = new Random();
        newGame.setGameID(random.nextInt(10,10000));
        newGame.setGameName(gameName);
        game.put(newGame.getGameID(), newGame);
        return newGame;
    }

    public HashSet getGames() {
        HashSet<GameData> gameSet = new HashSet<>();
        this.game.forEach((key,value) -> gameSet.add(value));
        return gameSet;
    }

    public GameData getGame(int gameID){
        GameData currentGame = game.get(gameID);
        return currentGame;
    }

    public void updateGame(GameData gameData, String playerColor, String username){

        GameData currentGame = game.get(gameData.getGameID());
        if(playerColor.toLowerCase().contains("white")){
            currentGame.setWhiteUsername(username);
        }
        else if (playerColor.toLowerCase().contains("black")){
            currentGame.setBlackUsername(username);
        }

    }


}
