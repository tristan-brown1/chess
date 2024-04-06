package chess;


import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;


/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard gameBoard = new ChessBoard();
    private static TeamColor teamColor;


    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamColor = team;
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
            ChessBoard tempBoard = createDeepCopy(this.gameBoard);
            tempGame.setBoard(tempBoard);

            tempBoard.addPiece(move.getEndPosition(),tempBoard.getPiece(move.getStartPosition()));
            tempBoard.removePiece(move.getStartPosition());

//            check if the move puts them in check or checkmate or stalemate

            if (!(tempGame.isInCheck(tempBoard.getPiece(move.getEndPosition()).getTeamColor()) && tempGame.isInCheckmate(tempBoard.getPiece(move.getEndPosition()).getTeamColor()))){
                newValidMoves.add(move);
            }
//            return the updated list of valid moves that avoids those scenarios
        }

        return newValidMoves;
    }

    public ChessBoard createDeepCopy(ChessBoard board){
        ChessBoard copyBoard = new ChessBoard();

        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8; j++) {
                copyBoard.addPiece(new ChessPosition(i,j),board.getPiece(new ChessPosition(i,j)));
            }
        }
        return copyBoard;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> validMoves;
        validMoves = validMoves(move.getStartPosition());
        if(!validMoves.contains(move)){
            throw new InvalidMoveException("move is not contained in validMoves");
        }
        else if(this.teamColor != this.gameBoard.getPiece(move.getStartPosition()).getTeamColor()){
            throw new InvalidMoveException("move is of the wrong color");
        }
        else if(move.getPromotionPiece() != null){
            ChessPiece replacementPiece = new ChessPiece(this.gameBoard.getPiece(move.getStartPosition()).getTeamColor(),move.getPromotionPiece());
            this.gameBoard.addPiece(move.getEndPosition(),replacementPiece);
            this.gameBoard.removePiece(move.getStartPosition());
            if(teamColor == teamColor.WHITE){
                setTeamTurn(teamColor.BLACK);
            }
            else{
                setTeamTurn(teamColor.WHITE);
            }
        }
        else {
            this.gameBoard.addPiece(move.getEndPosition(),this.gameBoard.getPiece(move.getStartPosition()));
            this.gameBoard.removePiece(move.getStartPosition());
            if(teamColor == teamColor.WHITE){
                setTeamTurn(teamColor.BLACK);
            }
            else{
                setTeamTurn(teamColor.WHITE);
            }

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
        ChessGame.TeamColor otherTeam;
        if(teamColor == ChessGame.teamColor.WHITE){
            otherTeam = ChessGame.teamColor.BLACK;
        }
        else{
            otherTeam = ChessGame.teamColor.WHITE;
        }
        ChessPosition kingPosition = kingPosition(teamColor);
        Collection<ChessPosition> teamPositions = getTeamPositions(otherTeam);
        for (ChessPosition position : teamPositions){
            for(ChessMove move : this.gameBoard.getPiece(position).pieceMoves(this.gameBoard,position)){
                if(move.getEndPosition().equals(kingPosition)){
                    return true;
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
                        kingPosition = targetPosition;
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
                    if(targetPiece.getTeamColor() == teamColor){
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
        for (ChessPosition position : positionList){
            if(validMoves(position).isEmpty()){
                return true;
            }
        }
        return false;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return Objects.deepEquals(gameBoard, chessGame.getBoard()) && teamColor == chessGame.teamColor;
    }

    @Override
    public int hashCode() {
        return this.gameBoard.hashCode() + this.teamColor.hashCode();
    }
}
