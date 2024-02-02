package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard gameBoard = new ChessBoard();
    private TeamColor TeamColor;


    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return TeamColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.TeamColor = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> validMoves = new HashSet<ChessMove>();
        ChessPiece currentPiece = gameBoard.getPiece(startPosition);
        validMoves = currentPiece.pieceMoves(gameBoard,startPosition);
        Collection<ChessMove> newValidMoves = new HashSet<>();

        for (ChessMove move : validMoves) {
//            simulate the board
            ChessGame tempGame = new ChessGame();
            tempGame.setBoard(this.gameBoard);
            try{
                tempGame.makeMove(move);
            }
            catch (Exception e){
                System.out.println("whoopsidaisies");
            }


//            check if the move puts them in check or checkmate or stalemate
            if (!(tempGame.isInCheck(this.TeamColor) && tempGame.isInCheckmate(this.TeamColor) && tempGame.isInStalemate(this.TeamColor))){
                newValidMoves.add(move);
            }
//            return the updates list of valid moves that avoids those scenarios
        }

        return newValidMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece currentPiece = this.gameBoard.getPiece(move.getStartPosition());
        Collection<ChessMove> validMoves;
        validMoves = currentPiece.pieceMoves(this.gameBoard,move.getStartPosition());
//        if(!validMoves.contains(move)){
//            throw new InvalidMoveException();
//        }

//        ChessGame tempGame = new ChessGame();
//        tempGame.setBoard(this.gameBoard);
//        tempGame.getBoard().addPiece(move.getEndPosition(),tempGame.gameBoard.getPiece(move.getStartPosition()));
//        tempGame.getBoard().removePiece(move.getStartPosition());
//
//        if(tempGame.isInCheck(this.TeamColor)){
//            throw new InvalidMoveException();
//        }
        if(!validMoves.contains(move)){
            throw new InvalidMoveException();
        }
//        if(this.TeamColor != this.gameBoard.getPiece(move.getStartPosition()).getTeamColor()){
//            throw new InvalidMoveException();
//        }
        if(move.getPromotionPiece() != null){
            ChessPiece replacementPiece = new ChessPiece(this.gameBoard.getPiece(move.getStartPosition()).getTeamColor(),move.getPromotionPiece());
            this.gameBoard.addPiece(move.getEndPosition(),replacementPiece);
            this.gameBoard.removePiece(move.getStartPosition());
        }
        else{
            this.gameBoard.addPiece(move.getEndPosition(),this.gameBoard.getPiece(move.getStartPosition()));
            this.gameBoard.removePiece(move.getStartPosition());
        }


    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
//        check the current board to see if the passed in color is in check
        ChessPosition kingPosition = kingPosition(teamColor);
        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8; j++){
                ChessPosition targetPosition = new ChessPosition(i,j);
                if(this.gameBoard.getPiece(targetPosition) != null){
                    ChessPiece targetPiece = this.gameBoard.getPiece((targetPosition));
                    for(ChessMove  move : targetPiece.pieceMoves(this.gameBoard,targetPosition)){
                        if(move.getEndPosition().equals(kingPosition)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public ChessPosition kingPosition(TeamColor teamColor) {
        ChessPosition kingPosition = null;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition targetPosition = new ChessPosition(i, j);
                if(this.gameBoard.getPiece(targetPosition) != null) {
                    ChessPiece targetPiece = this.gameBoard.getPiece((targetPosition));
                    if(targetPiece.getPieceType() == ChessPiece.PieceType.KING && targetPiece.getTeamColor() == teamColor){
                        kingPosition = new ChessPosition(i,j);
                    }
                }
            }
        }
        return kingPosition;
    }

    public Collection<ChessPosition> getTeamPositions(TeamColor teamColor) {
        HashSet<ChessPosition> teamPositions = new HashSet<>();
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition targetPosition = new ChessPosition(i, j);
                if(this.gameBoard.getPiece(targetPosition) != null) {
                    ChessPiece targetPiece = this.gameBoard.getPiece((targetPosition));
                    if(targetPiece.getTeamColor().equals(teamColor)){
                        teamPositions.add(targetPosition);
                    }
                }
            }
        }
        return teamPositions;
    }


    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        ChessPosition kingPosition = kingPosition(teamColor);
        Collection<ChessPosition> teamPositions = getTeamPositions(teamColor);
        for(ChessPosition position : teamPositions){
            for(ChessMove move : this.gameBoard.getPiece(position).pieceMoves(this.gameBoard,position)){
                if(isInCheck(this.gameBoard.getPiece(position).getTeamColor())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        Collection<ChessPosition> positionList = this.getTeamPositions(teamColor);
        boolean answer = false;
        for (ChessPosition position : positionList){
            if(validMoves(position) == null){
                answer = true;
            }
            else{
                answer = false;
                break;
            }
        }
        return answer;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.gameBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.gameBoard;
    }
}
