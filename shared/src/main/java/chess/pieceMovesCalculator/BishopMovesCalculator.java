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

        for (int i = (myPosition.getRow()); i <= 8; i++){
            for (int j = (myPosition.getColumn()); j <= 8; j++){
                ChessPosition newPosition = new ChessPosition(i,j);
                ChessMove newMove = new ChessMove(myPosition,newPosition);
                validMoves.add(newMove);
            }
        }

        for (int i = (myPosition.getRow()); i <= 8; i++){
            for (int j = (myPosition.getColumn()); j > 0; j--){
                ChessPosition newPosition = new ChessPosition(i,j);
                ChessMove newMove = new ChessMove(myPosition,newPosition);
                validMoves.add(newMove);
            }
        }

        for (int i = (myPosition.getRow()); i > 0; i--){
            for (int j = (myPosition.getColumn()); j <= 8; j++){
                ChessPosition newPosition = new ChessPosition(i,j);
                ChessMove newMove = new ChessMove(myPosition,newPosition);
                validMoves.add(newMove);
            }
        }

        for (int i = (myPosition.getRow()); i > 0; i--){
            for (int j = (myPosition.getColumn()); j > 0 ; j--){
                ChessPosition newPosition = new ChessPosition(i,j);
                ChessMove newMove = new ChessMove(myPosition,newPosition);
                validMoves.add(newMove);
            }
        }


        return validMoves;
    }
}
