package server;

import server.AuthData;
import server.GameData;
import server.UserData;

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
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public UserData getUserData() {
        return userData;
    }

    public AuthData getAuthData() {
        return authData;
    }

    public GameData getGameData() {
        return gameData;
    }
}
