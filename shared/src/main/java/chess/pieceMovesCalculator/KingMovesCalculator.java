package chess.pieceMovesCalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class KingMovesCalculator {

    private ChessBoard board;
    private ChessPosition myPosition;

    public KingMovesCalculator(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
    }

    public ArrayList<ChessMove> getValidMoves() {
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();
        int columnNum = myPosition.getColumn();
        int rowNum = myPosition.getRow();

//        up
        rowNum = rowNum + 1;

        if(rowNum <= 8) {
            ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
            ChessMove newMove = new ChessMove(myPosition, newPosition);

            if (board.getPiece(newPosition) == null) {
                validMoves.add(newMove);
            } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMoves.add(newMove);
            }
        }

//        down
        rowNum = myPosition.getRow();
        rowNum = rowNum - 1;

        if(rowNum >= 1) {
            ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
            ChessMove newMove = new ChessMove(myPosition, newPosition);

            if (board.getPiece(newPosition) == null) {
                validMoves.add(newMove);
            } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMoves.add(newMove);
            }
        }

//        left
        rowNum = myPosition.getRow();
        columnNum = columnNum - 1;

        if(columnNum >= 1) {
            ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
            ChessMove newMove = new ChessMove(myPosition, newPosition);

            if (board.getPiece(newPosition) == null) {
                validMoves.add(newMove);
            } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMoves.add(newMove);
            }
        }


//        right
        columnNum = myPosition.getColumn();
        columnNum = columnNum + 1;

        if(columnNum <= 8) {
            ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
            ChessMove newMove = new ChessMove(myPosition, newPosition);

            if (board.getPiece(newPosition) == null) {
                validMoves.add(newMove);
            } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMoves.add(newMove);
            }
        }


//        top left
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();

        rowNum = rowNum + 1;
        columnNum = columnNum + 1;

        if((columnNum <= 8) && rowNum <= 8) {
            ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
            ChessMove newMove = new ChessMove(myPosition, newPosition);

            if (board.getPiece(newPosition) == null) {
                validMoves.add(newMove);
            } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMoves.add(newMove);
            }
        }


//        top right
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();

        rowNum = rowNum + 1;
        columnNum = columnNum - 1;

        if((columnNum >= 1) && rowNum <= 8) {
            ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
            ChessMove newMove = new ChessMove(myPosition, newPosition);

            if (board.getPiece(newPosition) == null) {
                validMoves.add(newMove);
            } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMoves.add(newMove);
            }
        }

//        bot right
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();

        rowNum = rowNum - 1;
        columnNum = columnNum + 1;

        if((columnNum <= 8) && rowNum >= 1) {
            ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
            ChessMove newMove = new ChessMove(myPosition, newPosition);

            if (board.getPiece(newPosition) == null) {
                validMoves.add(newMove);
            } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMoves.add(newMove);
            }
        }

//        bot left
        columnNum = myPosition.getColumn();
        rowNum = myPosition.getRow();

        rowNum = rowNum - 1;
        columnNum = columnNum - 1;

        if((columnNum >= 1) && rowNum >= 1) {
            ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
            ChessMove newMove = new ChessMove(myPosition, newPosition);

            if (board.getPiece(newPosition) == null) {
                validMoves.add(newMove);
            } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                validMoves.add(newMove);
            }
        }



        return validMoves;
    }
}