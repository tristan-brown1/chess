package model;

import chess.ChessBoard;
import chess.ChessGame;

public class GameData {

    private int gameID;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;
    private ChessGame game;

    public GameData(int gameID,String whiteUsername, String blackUsername,String gameName, ChessGame game) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
//        createNewGameBoard();
//        this.game = game;
        if(game == null){
            createNewGameBoard();
        }
        else{
            this.game = game;
        }

    }

    public GameData() {
        this.whiteUsername = null;
        this.blackUsername = null;
        this.gameName = null;
        this.game = null;
        createNewGameBoard();
    }

    private void createNewGameBoard() {
        ChessBoard newBoard = new ChessBoard();
        newBoard.resetBoard();
        ChessGame newGame = new ChessGame();
        newGame.setBoard(newBoard);
        this.game = newGame;
    }



    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getGameID() {
        return gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public ChessGame getGame() {
        return game;
    }
    public void setGame(ChessGame game) {
        this.game = game;
    }
}
