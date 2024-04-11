package dataAccess.DAOs;

import exception.DataAccessException;
import sharedDataClasses.GameData;

import java.util.HashSet;

public interface GameDAO{

    public void clearAll() throws DataAccessException;

    public Object updateGame(GameData gameData, String playerColor, String username) throws DataAccessException;

    public GameData getGame(int gameID);

    public HashSet getGames() throws DataAccessException;

    public GameData createGame(String gameName) throws DataAccessException;

}
