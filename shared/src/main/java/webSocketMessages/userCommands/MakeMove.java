package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

public class MakeMove extends UserGameCommand{
    int gameID;
    ChessMove move;
    ChessGame.TeamColor playerColor;
    public MakeMove(String authToken, int gameId, ChessMove move, ChessGame.TeamColor playerColor) {
        super(authToken);
        this.gameID = gameId;
        this.move = move;
        this.playerColor = playerColor;

    }

    public int getGameID() {
        return gameID;
    }

    public ChessMove getMove() {
        return move;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }
}
