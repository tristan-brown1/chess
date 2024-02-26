package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];
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

    public void removePiece(ChessPosition position) {
        squares[position.getRow()-1][position.getColumn()-1] = null;
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

//        pawns
//        white pawns
        for(int i = 1; i<=8; i++){
            ChessPosition newPosition = new ChessPosition(2,i);
            ChessPiece newPiece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            addPiece(newPosition,newPiece);
        }

//        black pawns
        for(int i = 1; i<=8; i++){
            ChessPosition newPosition = new ChessPosition(7,i);
            ChessPiece newPiece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            addPiece(newPosition,newPiece);
        }

//        white pieces
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

//        black pieces
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.squares);
    }
}
