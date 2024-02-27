package chess.pieceMovesCalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class KnightMovesCalculator {

    private final ChessBoard board;
    private final ChessPosition myPosition;
    HashSet<ChessMove> hashValidMoves = new HashSet<ChessMove>();

    public KnightMovesCalculator(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
    }

    public Collection<ChessMove> getValidMoves() {
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();
        int columnNum = myPosition.getColumn();
        int rowNum = myPosition.getRow();
//        up left
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();
        rowNum = rowNum + 2;
        columnNum = columnNum - 1;
        if((columnNum >= 1) && rowNum <= 8) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
//        up right
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();
        rowNum = rowNum + 2;
        columnNum = columnNum + 1;
        if((columnNum <= 8) && rowNum <= 8) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
//        left up
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();
        rowNum = rowNum + 1;
        columnNum = columnNum - 2;
        if((columnNum >= 1) && rowNum <= 8) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
//        left down
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();
        rowNum = rowNum - 1;
        columnNum = columnNum - 2;
        if((columnNum >= 1) && rowNum >= 1) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
//        right up
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();
        rowNum = rowNum + 1;
        columnNum = columnNum + 2;
        if((columnNum <= 8) && rowNum <= 8) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
//        right down
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();
        rowNum = rowNum - 1;
        columnNum = columnNum + 2;
        if((columnNum <= 8) && rowNum >= 1) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
//        down left
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();
        rowNum = rowNum - 2;
        columnNum = columnNum - 1;
        if((columnNum >= 1) && rowNum >= 1) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
//        down right
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();
        rowNum = rowNum - 2;
        columnNum = columnNum + 1;
        if((columnNum <= 8) && rowNum >= 1) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
        hashValidMoves.addAll(validMoves);
        return hashValidMoves;
    }

    private void checkForValidMove(int rowNum, int columnNum, ArrayList<ChessMove> validMoves) {
        ChessMove newMove = createNewMove(rowNum, columnNum);
        if (board.getPiece(newMove.getEndPosition()) == null) {
            validMoves.add(newMove);
        } else if (board.getPiece(newMove.getEndPosition()).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            validMoves.add(newMove);
        }
    }

    public ChessMove createNewMove(int rowNum, int columnNum){
        ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
        return new ChessMove(myPosition, newPosition);
    }

}
