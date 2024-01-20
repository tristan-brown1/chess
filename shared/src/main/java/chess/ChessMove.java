package chess;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    private ChessPosition startPosition;
    private ChessPosition endPosition;
    private ChessPiece.PieceType promotionPiece;


    public ChessMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return this.startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return this.endPosition;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return this.promotionPiece;
    }


    @Override
    public boolean equals(Object o){
        if(o == null){return false;}
        if(o == this){return true;}

        if(this.getClass() != o.getClass()){
            return false;
        }

        if(this.getPromotionPiece() != ((ChessMove) o).getPromotionPiece()){
            return false;
        }

        ChessMove other = (ChessMove)o;

        return (this.startPosition.equals(other.startPosition) && (this.endPosition.equals(other.endPosition)));
    }

    @Override
    public int hashCode() {
        if(this.getPromotionPiece() != null){
            return (this.startPosition.hashCode() * this.endPosition.hashCode() * this.promotionPiece.hashCode()) + 1 ;
        }
        else{
            return this.startPosition.hashCode() * this.endPosition.hashCode();
        }

    }
}
