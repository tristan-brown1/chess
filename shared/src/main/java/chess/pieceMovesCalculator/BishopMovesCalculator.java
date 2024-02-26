package chess.pieceMovesCalculator;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class BishopMovesCalculator {
    private ChessBoard board;
    private ChessPosition myPosition;
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
            if(columnNum < 8){
                columnNum = columnNum + 1;
            }
            ChessPosition newPosition = new ChessPosition(i,columnNum);
            ChessMove newMove = new ChessMove(myPosition,newPosition);

            if (board.getPiece(newPosition) == null){
                validMoves.add(newMove);
            }
            else if(board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                validMoves.add(newMove);
                break;
            }
            else if(board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()){
                break;
            }

        }

//        right down
        columnNum = myPosition.getColumn();
        for (int i = (myPosition.getRow() - 1); i >= 1; i--){
            if(columnNum < 8){
                columnNum = columnNum + 1;
                ChessPosition newPosition = new ChessPosition(i,columnNum);
                ChessMove newMove = new ChessMove(myPosition,newPosition);

                if (board.getPiece(newPosition) == null){
                    validMoves.add(newMove);
                }
                else if(board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    validMoves.add(newMove);
                    break;
                }
                else if(board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()){
                    break;
                }
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
                ChessPosition newPosition = new ChessPosition(i,columnNum);
                ChessMove newMove = new ChessMove(myPosition,newPosition);

                if (board.getPiece(newPosition) == null){
                    validMoves.add(newMove);
                }
                else if(board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    validMoves.add(newMove);
                    break;
                }
                else if(board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()){
                    break;
                }
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
                ChessPosition newPosition = new ChessPosition(i,columnNum);
                ChessMove newMove = new ChessMove(myPosition,newPosition);

                if (board.getPiece(newPosition) == null){
                    validMoves.add(newMove);
                }
                else if(board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    validMoves.add(newMove);
                    break;
                }
                else if(board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()){
                    break;
                }
                if (columnNum == 1){
                    break;
                }
            }
        }
        hashValidMoves.addAll(validMoves);
        return hashValidMoves;
    }
}
