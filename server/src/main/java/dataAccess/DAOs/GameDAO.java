package dataAccess.DAOs;

import model.GameData;

import java.util.HashSet;

public interface GameDAO{

    public void clearAll();

    public void updateGame(GameData gameData, String playerColor, String username);

    public GameData getGame(int gameID);

    public HashSet getGames();

    public GameData createGame(String gameName);

}
