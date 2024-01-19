package chess.pieceMovesCalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalculator {
    private ChessBoard board;
    private ChessPosition myPosition;

    public BishopMovesCalculator(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
    }

    public ArrayList<ChessMove> getValidMoves(){
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();

        int columnNum = myPosition.getColumn();
        for (int i = (myPosition.getRow() + 1); i <= 8; i++){
            if(columnNum <= 8){
                columnNum = columnNum + 1;
            }
            ChessPosition newPosition = new ChessPosition(i,columnNum);
            ChessMove newMove = new ChessMove(myPosition,newPosition);

            if (board.getPiece(newPosition) == null){
                validMoves.add(newMove);
                System.out.printf("(%d, %d)", i, columnNum);
            }
            else if(board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                validMoves.add(newMove);
                System.out.printf("(%d, %d)", i, columnNum);
                break;
            }
            else if(board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()){
                break;
            }

//            System.out.printf("(%d, %d)", i, columnNum);
        }

        columnNum = myPosition.getColumn();
        for (int i = (myPosition.getRow() - 1); i >= 1; i--){
            if(columnNum <= 8){
                columnNum = columnNum + 1;
                ChessPosition newPosition = new ChessPosition(i,columnNum);
                ChessMove newMove = new ChessMove(myPosition,newPosition);

                if (board.getPiece(newPosition) == null){
                    validMoves.add(newMove);
                    System.out.printf("(%d, %d)", i, columnNum);
                }
                else if(board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    validMoves.add(newMove);
                    System.out.printf("(%d, %d)", i, columnNum);
                    break;
                }
                else if(board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()){
                    break;
                }
//                System.out.printf("(%d, %d)", i, columnNum);
                if (columnNum == 8){
                    break;
                }
            }
        }

        columnNum = myPosition.getColumn();
        for (int i = (myPosition.getRow() + 1); i <= 8; i++){

            if(columnNum >= 1){
                columnNum = columnNum - 1;
                ChessPosition newPosition = new ChessPosition(i,columnNum);
                ChessMove newMove = new ChessMove(myPosition,newPosition);

                if (board.getPiece(newPosition) == null){
                    validMoves.add(newMove);
                    System.out.printf("(%d, %d)", i, columnNum);
                }
                else if(board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    validMoves.add(newMove);
                    System.out.printf("(%d, %d)", i, columnNum);
                    break;
                }
                else if(board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()){
                    break;
                }
//                System.out.printf("(%d, %d)", i, columnNum);
                if(columnNum == 1){
                    break;
                }
            }
        }

        columnNum = myPosition.getColumn();
        for (int i = (myPosition.getRow() - 1); i >= 1; i--){
            if(columnNum >= 1){
                columnNum = columnNum - 1;
                ChessPosition newPosition = new ChessPosition(i,columnNum);
                ChessMove newMove = new ChessMove(myPosition,newPosition);

                if (board.getPiece(newPosition) == null){
                    validMoves.add(newMove);
                    System.out.printf("(%d, %d)", i, columnNum);
                }
                else if(board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    validMoves.add(newMove);
                    System.out.printf("(%d, %d)", i, columnNum);
                    break;
                }
                else if(board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()){
                    break;
                }
//                System.out.printf("(%d, %d)", i, columnNum);
                if (columnNum == 1){
                    break;
                }
            }
        }

        return validMoves;
    }
}
