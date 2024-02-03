package chess.pieceMovesCalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class QueenMovesCalculator {

    private ChessBoard board;
    private ChessPosition myPosition;
    HashSet<ChessMove> hashValidMoves = new HashSet<ChessMove>();

    public QueenMovesCalculator(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
    }

    public Collection<ChessMove> getValidMoves(){
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();

        BishopMovesCalculator bishopMoves = new BishopMovesCalculator(board, myPosition);
        RookMovesCalculator rookMoves = new RookMovesCalculator(board, myPosition);

        validMoves.addAll(bishopMoves.getValidMoves());
        validMoves.addAll(rookMoves.getValidMoves());

        hashValidMoves.addAll(validMoves);
        return hashValidMoves;
    }


}
