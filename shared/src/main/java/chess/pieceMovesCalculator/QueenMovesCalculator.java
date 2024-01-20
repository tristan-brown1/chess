package chess.pieceMovesCalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class QueenMovesCalculator {

    private ChessBoard board;
    private ChessPosition myPosition;

    public QueenMovesCalculator(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
    }

    public ArrayList<ChessMove> getValidMoves(){
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();

        BishopMovesCalculator bishopMoves = new BishopMovesCalculator(board, myPosition);
        RookMovesCalculator rookMoves = new RookMovesCalculator(board, myPosition);

        validMoves.addAll(bishopMoves.getValidMoves());
        validMoves.addAll(rookMoves.getValidMoves());

        return validMoves;
    }


}
