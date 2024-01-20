package chess;

import chess.pieceMovesCalculator.BishopMovesCalculator;
import chess.pieceMovesCalculator.RookMovesCalculator;
import chess.pieceMovesCalculator.QueenMovesCalculator;
import chess.pieceMovesCalculator.KingMovesCalculator;
import chess.pieceMovesCalculator.KnightMovesCalculator;
import chess.pieceMovesCalculator.PawnMovesCalculator;


import java.util.ArrayList;
import java.util.Collection;

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
        ArrayList<ChessMove> moveList = new ArrayList<ChessMove>();

        if (this.pieceType.equals(PieceType.BISHOP)) {
            BishopMovesCalculator bishopMoves = new BishopMovesCalculator(board, myPosition);
            moveList = bishopMoves.getValidMoves();
        }

        if (this.pieceType.equals(PieceType.ROOK)){
            RookMovesCalculator rookMoves = new RookMovesCalculator(board, myPosition);
            moveList = rookMoves.getValidMoves();
        }

        if (this.pieceType.equals(PieceType.QUEEN)){
            QueenMovesCalculator queenMoves = new QueenMovesCalculator(board, myPosition);
            moveList = queenMoves.getValidMoves();
        }

        if (this.pieceType.equals(PieceType.KING)){
            KingMovesCalculator kingMoves = new KingMovesCalculator(board, myPosition);
            moveList = kingMoves.getValidMoves();
        }

        if (this.pieceType.equals(PieceType.KNIGHT)){
            KnightMovesCalculator knightMoves = new KnightMovesCalculator(board, myPosition);
            moveList = knightMoves.getValidMoves();
        }
//
//        if (this.pieceType.equals(PieceType.PAWN)){
//            QueenMovesCalculator pawnMoves = new PawnMovesCalculator(board, myPosition);
//            moveList = pawnMoves.getValidMoves();
//        }

        return moveList;
    }
}
