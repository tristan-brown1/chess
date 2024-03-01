package dataAccess.DAOs;

import dataAccess.DataAccessException;
import model.GameData;

import java.util.HashSet;

public class SQLGameDAO implements GameDAO{


    public void clearAll() {

    }

    @Override
    public void updateGame(GameData gameData, String playerColor, String username) {

    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public HashSet getGames() {
        return null;
    }

    @Override
    public GameData createGame(String gameName) {
        return null;
    }

}
