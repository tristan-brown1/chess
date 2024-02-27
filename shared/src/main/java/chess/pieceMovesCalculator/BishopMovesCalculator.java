package chess.pieceMovesCalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class BishopMovesCalculator {
    private final ChessBoard board;
    private final ChessPosition myPosition;
    HashSet<ChessMove> hashValidMoves = new HashSet<ChessMove>();

    public BishopMovesCalculator(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
    }

    public Collection<ChessMove> getValidMoves(){
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();

//        right up
        int columnNum = myPosition.getColumn();
        for (int i = (myPosition.getRow() + 1); i <= 8; i++){
            if(columnNum < 8) {
                columnNum = columnNum + 1;
                if (checkForValidMove(i, columnNum, validMoves)) break;
                if (columnNum == 8) {
                    break;
                }
            }

        }

//        right down
        columnNum = myPosition.getColumn();
        for (int i = (myPosition.getRow() - 1); i >= 1; i--){
            if(columnNum < 8){
                columnNum = columnNum + 1;
                if (checkForValidMove(i, columnNum, validMoves)) break;
                if (columnNum == 8){
                    break;
                }
            }
        }

//        left up
        columnNum = myPosition.getColumn();
        for (int i = (myPosition.getRow() + 1); i <= 8; i++){

            if(columnNum > 1){
                columnNum = columnNum - 1;
                if (checkForValidMove(i, columnNum, validMoves)) break;
                if(columnNum == 1){
                    break;
                }
            }
        }

//        left down
        columnNum = myPosition.getColumn();
        for (int i = (myPosition.getRow() - 1); i >= 1; i--){
            if(columnNum > 1){
                columnNum = columnNum - 1;
                if (checkForValidMove(i, columnNum, validMoves)) break;
                if (columnNum == 1){
                    break;
                }
            }
        }
        hashValidMoves.addAll(validMoves);
        return hashValidMoves;
    }

    private boolean checkForValidMove(int i, int columnNum, ArrayList<ChessMove> validMoves) {

        ChessMove newMove = createNewMove(i,columnNum);

        if (board.getPiece(newMove.getEndPosition()) == null) {
            validMoves.add(newMove);
        } else if (board.getPiece(newMove.getEndPosition()).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            validMoves.add(newMove);
            return true;
        } else if (board.getPiece(newMove.getEndPosition()).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
            return true;
        }
        return false;
    }


    public ChessMove createNewMove(int rowNum, int columnNum){
        ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
        return new ChessMove(myPosition, newPosition);
    }
}
