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

        for (int i = (myPosition.getRow() + 1); i <= 8; i++) {

            ChessPosition newPosition = new ChessPosition(i, columnNum);
            ChessMove newMove = new ChessMove(myPosition, newPosition);

            if (board.getPiece(newPosition) == null) {
                validMoves.add(newMove);
            } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMoves.add(newMove);
                break;
            } else if (board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                break;
            }

        }

        for (int i = (myPosition.getRow() - 1); i >= 1; i--) {

            ChessPosition newPosition = new ChessPosition(i, columnNum);
            ChessMove newMove = new ChessMove(myPosition, newPosition);

            if (board.getPiece(newPosition) == null) {
                validMoves.add(newMove);
            } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMoves.add(newMove);
                break;
            } else if (board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                break;
            }

        }

        for (int i = (myPosition.getColumn() + 1); i <= 8; i++) {

            ChessPosition newPosition = new ChessPosition(rowNum,i);
            ChessMove newMove = new ChessMove(myPosition, newPosition);

            if (board.getPiece(newPosition) == null) {
                validMoves.add(newMove);
            } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMoves.add(newMove);
                break;
            } else if (board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                break;
            }

        }

        for (int i = (myPosition.getColumn() - 1); i >= 1; i--) {

            ChessPosition newPosition = new ChessPosition(rowNum,i);
            ChessMove newMove = new ChessMove(myPosition, newPosition);

            if (board.getPiece(newPosition) == null) {
                validMoves.add(newMove);
            } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMoves.add(newMove);
                break;
            } else if (board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                break;
            }

        }
        hashValidMoves.addAll(validMoves);
        return hashValidMoves;
    }

}


