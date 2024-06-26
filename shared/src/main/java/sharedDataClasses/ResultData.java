package sharedDataClasses;

import sharedDataClasses.AuthData;
import sharedDataClasses.GameData;
import sharedDataClasses.UserData;

import java.util.HashSet;

public class ResultData {

    private String message;
    private int status;

    private UserData userData;
    private AuthData authData;
    private GameData gameData;
    private String username;
    private String authToken;
    private String password;
    private String email;
    private Integer gameID;
    private HashSet<GameData> games;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;

    public ResultData(){
        this.status = 200;
        this.message = null;
        this.authData = null;
        this.gameData = null;
        this.userData = null;
        this.username = null;
        this.password = null;
        this.authToken = null;
        this.email = null;
        this.games = null;

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
        this.username = this.userData.getUsername();
        this.password = this.userData.getPassword();
        this.email = this.userData.getEmail();
    }

    public void setAuthData(AuthData authData) {
        this.authData = authData;
        this.authToken = authData.getAuthToken();
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
        this.gameID = gameData.getGameID();
        this.gameName = gameData.getGameName();
        this.whiteUsername = gameData.getWhiteUsername();
        this.blackUsername = gameData.getBlackUsername();
    }

    public int getStatus() {
        return status;
    }

    public AuthData getAuthData() {
        return authData;
    }

    public GameData getGameData() {
        return gameData;
    }

    public void setGameSet(HashSet<GameData> games) {
        this.games = games;
    }

    public HashSet<GameData> getGames(){
        return this.games;
    }
}
