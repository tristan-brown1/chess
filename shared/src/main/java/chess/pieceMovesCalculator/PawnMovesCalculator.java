package chess.pieceMovesCalculator;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class PawnMovesCalculator {

    private ChessBoard board;
    private ChessPosition myPosition;

    ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();
    HashSet<ChessMove> hashValidMoves = new HashSet<ChessMove>();

    public PawnMovesCalculator(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
    }

    public Collection<ChessMove> getValidMoves() {
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
                if(board.getPiece(newPosition) == null && board.getPiece(straightDown) == null){
                    ChessMove newMove = new ChessMove(myPosition, newPosition);
                    validMoves.add(newMove);
                    System.out.printf("(%d, %d)", rowNum,columnNum);
                }
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
                if(board.getPiece(newPosition) == null){
                    ChessMove newMove = new ChessMove(myPosition, newPosition);
                    validMoves.add(newMove);
                }
            }
//      check to see if the valid move gets promotion piece

            ArrayList<ChessMove> copyMoves = new ArrayList<ChessMove>(validMoves);
            int counter = 0;
            for (ChessMove move : copyMoves){
                    if(move.getEndPosition().getRow() == 1){
                        ChessMove replacementMove = new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.QUEEN);
                        validMoves.set(counter,replacementMove);
                        ChessMove rookPromo = new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.ROOK);
                        ChessMove bishopPromo = new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.BISHOP);
                        ChessMove knightPromo = new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.KNIGHT);
                        validMoves.add(rookPromo);
                        validMoves.add(bishopPromo);
                        validMoves.add(knightPromo);
                    }
                    counter++;
            }

//            ig we gotta turn it into a hash set or sum
            hashValidMoves.addAll(validMoves);


        }


//        white
        if (board.getPiece(myPosition).getTeamColor().equals(ChessGame.TeamColor.WHITE)){
            if(myPosition.getRow() == 2){
                rowNum = rowNum + 2;
                ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
                if(board.getPiece(newPosition) == null && (board.getPiece(straightUp) == null)){
                    ChessMove newMove = new ChessMove(myPosition, newPosition);
                    validMoves.add(newMove);
                }
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
                if(board.getPiece(newPosition) == null){
                    ChessMove newMove = new ChessMove(myPosition, newPosition);
                    validMoves.add(newMove);
                }
            }
            //            check to see if the valid move gets promotion piece
            ArrayList<ChessMove> copyMoves = new ArrayList<ChessMove>(validMoves);
            int counter = 0;
            for (ChessMove move : copyMoves){
                if(move.getEndPosition().getRow() == 1){
                    ChessMove replacementMove = new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.QUEEN);
                    validMoves.set(counter,replacementMove);
                    ChessMove rookPromo = new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.ROOK);
                    ChessMove bishopPromo = new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.BISHOP);
                    ChessMove knightPromo = new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.KNIGHT);
                    validMoves.add(rookPromo);
                    validMoves.add(bishopPromo);
                    validMoves.add(knightPromo);
                }
                counter++;
            }

//            ig we gotta turn it into a hash set or sum
            hashValidMoves.addAll(validMoves);


        }

//        capture special case




        return hashValidMoves;
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
