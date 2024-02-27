package chess.pieceMovesCalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class RookMovesCalculator {

    private ChessBoard board;
    private ChessPosition myPosition;
    HashSet<ChessMove> hashValidMoves = new HashSet<ChessMove>();

    public RookMovesCalculator(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
    }

    public Collection<ChessMove> getValidMoves(){
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();
        int columnNum = myPosition.getColumn();
        int rowNum = myPosition.getRow();

//        right
        for (int i = (myPosition.getRow() + 1); i <= 8; i++) {

            if (checkForValidMove(i, columnNum, validMoves)) break;

        }

//        left
        for (int i = (myPosition.getRow() - 1); i >= 1; i--) {

            if (checkForValidMove(i, columnNum, validMoves)) break;

        }

//        up
        for (int i = (myPosition.getColumn() + 1); i <= 8; i++) {

            if (checkForValidMove(rowNum, i, validMoves)) break;

        }

//        down
        for (int i = (myPosition.getColumn() - 1); i >= 1; i--) {

            if (checkForValidMove(rowNum, i, validMoves)) break;

        }
        hashValidMoves.addAll(validMoves);
        return hashValidMoves;
    }

    private boolean checkForValidMove(int i, int columnNum, ArrayList<ChessMove> validMoves) {
        ChessPosition newPosition = new ChessPosition(i, columnNum);
        ChessMove newMove = new ChessMove(myPosition, newPosition);

        if (board.getPiece(newPosition) == null) {
            validMoves.add(newMove);
        } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            validMoves.add(newMove);
            return true;
        } else if (board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
            return true;
        }
        return false;
    }


}


