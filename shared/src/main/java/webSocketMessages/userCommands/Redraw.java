package webSocketMessages.userCommands;

public class Redraw extends UserGameCommand{
    int gameID;

    public Redraw(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }
}
