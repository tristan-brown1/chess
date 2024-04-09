package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand{
    int gameID;
    ChessMove move;
    public MakeMove(String authToken, int gameId, ChessMove move) {
        super(authToken);
        this.gameID = gameId;
        this.move = move;

    }

    public int getGameID() {
        return gameID;
    }

    public ChessMove getMove() {
        return move;
    }
}
