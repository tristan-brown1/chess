package chess;

import chess.pieceMovesCalculator.BishopMovesCalculator;
import chess.pieceMovesCalculator.RookMovesCalculator;
import chess.pieceMovesCalculator.KingMovesCalculator;
import chess.pieceMovesCalculator.KnightMovesCalculator;
import chess.pieceMovesCalculator.PawnMovesCalculator;


import java.util.Collection;
import java.util.HashSet;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessPiece.PieceType pieceType;
    private ChessGame.TeamColor teamColor;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceType = type;
        this.teamColor = pieceColor;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.teamColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moveList = new HashSet<>();

        if (this.pieceType.equals(PieceType.BISHOP)) {
            BishopMovesCalculator bishopMoves = new BishopMovesCalculator(board, myPosition);
            moveList = bishopMoves.getValidMoves();
        }

        if (this.pieceType.equals(PieceType.ROOK)){
            RookMovesCalculator rookMoves = new RookMovesCalculator(board, myPosition);
            moveList = rookMoves.getValidMoves();
        }

        if (this.pieceType.equals(PieceType.QUEEN)){
            RookMovesCalculator rookMoves = new RookMovesCalculator(board, myPosition);
            BishopMovesCalculator bishopMoves = new BishopMovesCalculator(board, myPosition);
            moveList = rookMoves.getValidMoves();
            moveList.addAll(bishopMoves.getValidMoves());
        }

        if (this.pieceType.equals(PieceType.KING)){
            KingMovesCalculator kingMoves = new KingMovesCalculator(board, myPosition);
            moveList = kingMoves.getValidMoves();
        }

        if (this.pieceType.equals(PieceType.KNIGHT)){
            KnightMovesCalculator knightMoves = new KnightMovesCalculator(board, myPosition);
            moveList = knightMoves.getValidMoves();
        }

        if (this.pieceType.equals(PieceType.PAWN)){
            PawnMovesCalculator pawnMoves = new PawnMovesCalculator(board, myPosition);
            moveList = pawnMoves.getValidMoves();
        }

        return moveList;
    }

    @Override
    public int hashCode() {
            return this.pieceType.hashCode() - this.teamColor.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece piece = (ChessPiece) o;
        return pieceType == piece.pieceType && teamColor == piece.teamColor;
    }

}
