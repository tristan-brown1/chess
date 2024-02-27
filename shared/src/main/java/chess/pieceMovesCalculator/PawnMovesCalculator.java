package chess.pieceMovesCalculator;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class PawnMovesCalculator {

    private final ChessBoard board;
    private final ChessPosition myPosition;

    ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();
    HashSet<ChessMove> hashValidMoves = new HashSet<ChessMove>();

    public PawnMovesCalculator(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
    }

    public Collection<ChessMove> getValidMoves() {
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

//        black
        if (board.getPiece(myPosition).getTeamColor().equals(ChessGame.TeamColor.BLACK)){
            if(myPosition.getRow() == blackStartRow){
                rowNum = rowNum - 2;
                ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
                if(board.getPiece(newPosition) == null && board.getPiece(straightDown) == null){
                    addMove(newPosition);
                }
            }

            if(isNotOutOfBounds(botRight)) {
                if (board.getPiece(botRight) != null) {
                    if (board.getPiece(botRight).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
                        addMove(botRight);
                    }
                }
            }

            if(isNotOutOfBounds(botLeft)) {
                if (board.getPiece(botLeft) != null) {
                    if (board.getPiece(botLeft).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
                        addMove(botLeft);
                    }
                }
            }

            if(isNotOutOfBounds(straightDown)) {
                if (board.getPiece(straightDown) == null) {
                    columnNum = myPosition.getColumn();
                    rowNum = myPosition.getRow();
                    rowNum = rowNum - 1;
                    ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
                    if (board.getPiece(newPosition) == null) {
                        addMove(newPosition);
                    }
                }
            }
//      check to see if the valid move gets promotion piece
            checkForPromo(1);
        }
//        white
        if (board.getPiece(myPosition).getTeamColor().equals(ChessGame.TeamColor.WHITE)){
            if(myPosition.getRow() == whiteStartRow){
                rowNum = rowNum + 2;
                ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
                if(board.getPiece(newPosition) == null && (board.getPiece(straightUp) == null)){
                    addMove(newPosition);
                }
            }

            if(isNotOutOfBounds(topRight)) {
                if (board.getPiece(topRight) != null) {
                    if (board.getPiece(topRight).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
                        addMove(topRight);
                    }
                }
            }


            if(isNotOutOfBounds(topLeft)){
                if (board.getPiece(topLeft) != null) {
                    if (board.getPiece(topLeft).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
                        addMove(topLeft);
                    }
                }
            }

            if(isNotOutOfBounds(straightUp)){
                if(board.getPiece(straightUp) == null){
                    columnNum = myPosition.getColumn();
                    rowNum = myPosition.getRow();
                    rowNum = rowNum + 1;

                    ChessPosition newPosition = new ChessPosition(rowNum, columnNum);
                    if(board.getPiece(newPosition) == null){
                        addMove(newPosition);
                    }
                }
            }
            //            check to see if the valid move gets promotion piece
            checkForPromo(8);
        }
        //            turn into hashset in order to avoid compatibility problems
        hashValidMoves.addAll(validMoves);
        return hashValidMoves;
    }

    private void addMove(ChessPosition newPosition) {
        ChessMove newMove = new ChessMove(myPosition, newPosition);
        validMoves.add(newMove);
    }

    private void checkForPromo(int x) {
        ArrayList<ChessMove> copyMoves = new ArrayList<ChessMove>(validMoves);
        int counter = 0;
        for (ChessMove move : copyMoves) {
            if (move.getEndPosition().getRow() == x) {
                ChessMove replacementMove = new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.QUEEN);
                validMoves.set(counter, replacementMove);
                ChessMove rookPromo = new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.ROOK);
                ChessMove bishopPromo = new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.BISHOP);
                ChessMove knightPromo = new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.KNIGHT);
                validMoves.add(rookPromo);
                validMoves.add(bishopPromo);
                validMoves.add(knightPromo);
            }
            counter++;
        }
    }

    public boolean isNotOutOfBounds(ChessPosition position){
        return (position.getRow() >= 1 && position.getRow() <= 8) && (position.getColumn() >= 1 && position.getColumn() <= 8);
    }

}
