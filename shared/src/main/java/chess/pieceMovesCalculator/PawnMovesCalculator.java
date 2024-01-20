package chess.pieceMovesCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class PawnMovesCalculator {

    private ChessBoard board;
    private ChessPosition myPosition;

    ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();

    public PawnMovesCalculator(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
    }

    public ArrayList<ChessMove> getValidMoves() {
//        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();
        int columnNum = myPosition.getColumn();
        int rowNum = myPosition.getRow();
        int blackStartRow = 7;
        int whiteStartRow = 2;
        ChessPosition botRight = new ChessPosition(rowNum - 1,columnNum + 1);
        ChessPosition botLeft = new ChessPosition(rowNum - 1,columnNum - 1);
        ChessPosition topRight = new ChessPosition(rowNum + 1,columnNum + 1);
        ChessPosition topLeft = new ChessPosition(rowNum + 1,columnNum - 1);
        ChessPosition straightDown = new ChessPosition(rowNum - 1,columnNum);
        ChessPosition straightUp = new ChessPosition(rowNum + 1,columnNum);


//        These chess moves have the potential to also have promotion piece info!!
//        first move special case


//        black

        if (board.getPiece(myPosition).getTeamColor().equals(ChessGame.TeamColor.BLACK)){
            if(myPosition.getRow() == 7){
                rowNum = rowNum - 2;
                ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
                ChessMove newMove = new ChessMove(myPosition, newPosition);
                validMoves.add(newMove);

            }

            if(board.getPiece(botRight) != null){
                if(board.getPiece(botRight).getTeamColor().equals(ChessGame.TeamColor.WHITE)){
                    ChessMove newMove = new ChessMove(myPosition, botRight);
                    validMoves.add(newMove);
//                    System.out.printf("(%d, %d)", rowNum - 1,columnNum + 1);
                }
            }

            if(board.getPiece(botLeft) != null) {
                if(board.getPiece(botLeft).getTeamColor().equals(ChessGame.TeamColor.WHITE)){
                    ChessMove newMove = new ChessMove(myPosition, botLeft);
                    validMoves.add(newMove);
                }
            }

            if(board.getPiece(straightDown) == null){
                columnNum = myPosition.getColumn();
                rowNum = myPosition.getRow();
                rowNum = rowNum - 1;

                ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
                ChessMove newMove = new ChessMove(myPosition, newPosition);
                validMoves.add(newMove);
            }

        }


//        white
        if (board.getPiece(myPosition).getTeamColor().equals(ChessGame.TeamColor.WHITE)){
            if(myPosition.getRow() == 2){
                rowNum = rowNum + 2;
                ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
                ChessMove newMove = new ChessMove(myPosition, newPosition);
                validMoves.add(newMove);
            }

            if(board.getPiece(topRight) != null) {
                if(board.getPiece(topRight).getTeamColor().equals(ChessGame.TeamColor.BLACK)){
                    ChessMove newMove = new ChessMove(myPosition, topRight);
                    validMoves.add(newMove);
                }
            }

            if(board.getPiece(topLeft) != null) {
                if(board.getPiece(topLeft).getTeamColor().equals(ChessGame.TeamColor.BLACK)){
                    ChessMove newMove = new ChessMove(myPosition, topLeft);
                    validMoves.add(newMove);
                }
            }

            if(board.getPiece(straightUp) == null){
                columnNum = myPosition.getColumn();
                rowNum = myPosition.getRow();
                rowNum = rowNum + 1;

                ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
                ChessMove newMove = new ChessMove(myPosition, newPosition);
                validMoves.add(newMove);
            }


        }

//        capture special case




        return validMoves;
    }

//    public ChessMove goodMove(int rowNum, int columnNum){
//        ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
//        ChessMove newMove = new ChessMove(myPosition, newPosition);
//
//        if (board.getPiece(newPosition) == null) {
//            return newMove;
//
//        } else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
//            return newMove;
//
//        }
//
//        return null;
//    }


}
