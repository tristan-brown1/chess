package ui.RequestClasses;

public class JoinGameRequest {

    int gameID;
    String playerColor;

    public JoinGameRequest(String playerColor, int gameID) {
        this.playerColor = playerColor;
        this.gameID = gameID;
    }


}
