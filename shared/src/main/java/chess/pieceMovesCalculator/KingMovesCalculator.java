package chess.pieceMovesCalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class KingMovesCalculator {

    private final ChessBoard board;
    private final ChessPosition myPosition;
    HashSet<ChessMove> hashValidMoves = new HashSet<ChessMove>();

    public KingMovesCalculator(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
    }

    public Collection<ChessMove> getValidMoves() {
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();
        int columnNum = myPosition.getColumn();
        int rowNum = myPosition.getRow();
//        up
        rowNum = rowNum + 1;
        if(rowNum <= 8) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
//        down
        rowNum = myPosition.getRow();
        rowNum = rowNum - 1;
        if(rowNum >= 1) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
//        left
        rowNum = myPosition.getRow();
        columnNum = columnNum - 1;
        if(columnNum >= 1) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
//        right
        columnNum = myPosition.getColumn();
        columnNum = columnNum + 1;
        if(columnNum <= 8) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
//        top left
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();
        rowNum = rowNum + 1;
        columnNum = columnNum + 1;
        if((columnNum <= 8) && rowNum <= 8) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
//        top right
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();
        rowNum = rowNum + 1;
        columnNum = columnNum - 1;
        if((columnNum >= 1) && rowNum <= 8) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
//        bot right
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();
        rowNum = rowNum - 1;
        columnNum = columnNum + 1;
        if((columnNum <= 8) && rowNum >= 1) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
//        bot left
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();
        rowNum = rowNum - 1;
        columnNum = columnNum - 1;
        if((columnNum >= 1) && rowNum >= 1) {
            checkForValidMove(rowNum, columnNum, validMoves);
        }
        hashValidMoves.addAll(validMoves);
        return hashValidMoves;
    }

    private void checkForValidMove(int rowNum, int columnNum, ArrayList<ChessMove> validMoves) {
        ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
        ChessMove newMove = new ChessMove(myPosition, newPosition);

        if (board.getPiece(newPosition) == null) {
            validMoves.add(newMove);
        } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            validMoves.add(newMove);
        }
    }

    public ChessMove createNewMove(int rowNum, int columnNum){
        ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
        return new ChessMove(myPosition, newPosition);
    }





}