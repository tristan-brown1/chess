package webSocketMessages.userCommands;

public class Leave extends UserGameCommand{
    int gameID;
    String playerColor;
    public Leave(String authToken, int gameID, String playerColor) {
        super(authToken);
        this.gameID = gameID;
        this.playerColor = playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    public String getPlayerColor() {
        return playerColor;
    }
}

