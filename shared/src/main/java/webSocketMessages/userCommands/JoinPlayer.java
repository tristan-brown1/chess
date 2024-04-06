package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand {
    int gameID;
    ChessGame.TeamColor playerColor;
    public JoinPlayer(String authToken, int gameID, ChessGame.TeamColor playerColor) {
        super(authToken);
        this.playerColor = playerColor;
        this.gameID = gameID;
    }
}
