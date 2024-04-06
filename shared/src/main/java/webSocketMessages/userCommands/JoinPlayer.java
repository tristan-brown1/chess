package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand {
    int gameID;
    String playerColor;
    public JoinPlayer(String authToken, int gameID, String playerColor) {
        super(authToken);
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }

    public String getPlayerColor() {
        return playerColor;
    }
}
