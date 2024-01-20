package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private final ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {

    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
       return squares[position.getRow()-1][position.getColumn()-1];
    }

    public ChessPiece[][] getBoard(){
        return this.squares;
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
//        for(int i = 1; i<=8; i++){
//            System.out.print("ChessPosition newPosition = new ChessPosition(2,i);\n");
//            System.out.print("ChessPiece newPiece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);\n");
//            System.out.print("addPiece(newPosition,newPiece);\n");
//        }
//        for(int i = 1; i<=8; i++){
//            ChessPosition newPosition = new ChessPosition(7,i);
//            ChessPiece newPiece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
//            addPiece(newPosition,newPiece);
//        }

//        pawns
        ChessPosition pW1Position = new ChessPosition(2,1);
        ChessPiece pW1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        addPiece(pW1Position,pW1);

        ChessPosition pW2Position = new ChessPosition(2,2);
        ChessPiece pW2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        addPiece(pW2Position,pW2);
        ChessPosition pW3Position = new ChessPosition(2,3);
        ChessPiece pW3 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        addPiece(pW3Position,pW3);
        ChessPosition pW4Position = new ChessPosition(2,4);
        ChessPiece pW4 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        addPiece(pW4Position,pW4);
        ChessPosition pW5Position = new ChessPosition(2,5);
        ChessPiece pW5 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        addPiece(pW5Position,pW5);
        ChessPosition pW6Position = new ChessPosition(2,6);
        ChessPiece pW6 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        addPiece(pW6Position,pW6);
        ChessPosition pW7Position = new ChessPosition(2,7);
        ChessPiece pW7 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        addPiece(pW7Position,pW7);
        ChessPosition pW8Position = new ChessPosition(2,8);
        ChessPiece pW8 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        addPiece(pW8Position,pW8);

        ChessPosition pB1Position = new ChessPosition(7,1);
        ChessPiece pB1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        addPiece(pB1Position,pB1);
        ChessPosition pB2Position = new ChessPosition(7,2);
        ChessPiece pB2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        addPiece(pB2Position,pB2);
        ChessPosition pB3Position = new ChessPosition(7,3);
        ChessPiece pB3 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        addPiece(pB3Position,pB3);
        ChessPosition pB4Position = new ChessPosition(7,4);
        ChessPiece pB4 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        addPiece(pB4Position,pB4);
        ChessPosition pB5Position = new ChessPosition(7,5);
        ChessPiece pB5 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        addPiece(pB5Position,pB5);
        ChessPosition pB6Position = new ChessPosition(7,6);
        ChessPiece pB6 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        addPiece(pB6Position,pB6);
        ChessPosition pB7Position = new ChessPosition(7,7);
        ChessPiece pB7 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        addPiece(pB7Position,pB7);
        ChessPosition pB8Position = new ChessPosition(7,8);
        ChessPiece pB8 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        addPiece(pB8Position,pB8);

//        rook
        ChessPosition rookAWPosition = new ChessPosition(1,1);
        ChessPiece rookAW = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        addPiece(rookAWPosition,rookAW);
//        knight
        ChessPosition knightAWPosition = new ChessPosition(1,2);
        ChessPiece knightAW = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        addPiece(knightAWPosition,knightAW);
//        bishop
        ChessPosition bishopAWPosition = new ChessPosition(1,3);
        ChessPiece bishopAW = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        addPiece(bishopAWPosition,bishopAW);
//        queen
        ChessPosition queenAWPosition = new ChessPosition(1,4);
        ChessPiece queenAW = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        addPiece(queenAWPosition,queenAW);
//        king
        ChessPosition kingAWPosition = new ChessPosition(1,5);
        ChessPiece kingAW = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        addPiece(kingAWPosition,kingAW);
//        rook
        ChessPosition rookBWPosition = new ChessPosition(1,8);
        ChessPiece rookBW = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        addPiece(rookBWPosition,rookBW);
//        knight
        ChessPosition knightBWPosition = new ChessPosition(1,7);
        ChessPiece knightBW = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        addPiece(knightBWPosition,knightBW);
//        bishop
        ChessPosition bishopBWPosition = new ChessPosition(1,6);
        ChessPiece bishopBW = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        addPiece(bishopBWPosition,bishopBW);


        //        rook
        ChessPosition rookABPosition = new ChessPosition(8,1);
        ChessPiece rookAB = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        addPiece(rookABPosition,rookAB);
//        knight
        ChessPosition knightABPosition = new ChessPosition(8,2);
        ChessPiece knightAB = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        addPiece(knightABPosition,knightAB);
//        bishop
        ChessPosition bishopABPosition = new ChessPosition(8,3);
        ChessPiece bishopAB = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        addPiece(bishopABPosition,bishopAB);
//        queen
        ChessPosition queenBPosition = new ChessPosition(8,4);
        ChessPiece queenB = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        addPiece(queenBPosition,queenB);
//        king
        ChessPosition kingBPosition = new ChessPosition(8,5);
        ChessPiece kingB = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        addPiece(kingBPosition,kingB);
//        rook
        ChessPosition rookBBPosition = new ChessPosition(8,8);
        ChessPiece rookBB = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        addPiece(rookBBPosition,rookBB);
//        knight
        ChessPosition knightBBPosition = new ChessPosition(8,7);
        ChessPiece knightBB = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        addPiece(knightBBPosition,knightBB);
//        bishop
        ChessPosition bishopBBPosition = new ChessPosition(8,6);
        ChessPiece bishopBB = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        addPiece(bishopBBPosition,bishopBB);

    }

    @Override
    public boolean equals(Object o){
        if(o == null){return false;}
        if(o == this){return true;}

        if(this.getClass() != o.getClass()){
            return false;
        }

        ChessBoard other = (ChessBoard) o;

        return (Arrays.deepEquals(this.squares,other.getBoard()));
    }
}
