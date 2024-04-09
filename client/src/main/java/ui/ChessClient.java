package ui;

import chess.ChessGame;
import chess.ChessPosition;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import dataAccess.DataAccessException;
import dataAccess.ResponseException;
import model.GameData;
import server.ResultData;
import ui.REPLs.GameplayRepl;
//import ui.websocket.NotificationHandler;
import ui.websocket.WebSocketFacade;


public class ChessClient {

    private String visitorName = null;
    private String visitorPassword = null;
    private String visitorEmail = null;
    private String visitorAuthToken = null;
    private String newGameName = null;

//    private final NotificationHandler notificationHandler;
    private WebSocketFacade ws;
    private final ServerFacade server;
    private final String serverUrl;

    private State state = State.LOGGEDOUT;
    private int gameID;

    public ChessClient( String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(params);
                case "login" -> login(params);
                case "logout" -> logOut();
                case "create" -> createGame(params);
                case "list" -> listGames();
                case "join", "observe" -> joinGame(params);
                case "redraw" -> redraw();
                case "leave" -> leaveGame();
                case "move" -> makeMove(params);
                case "resign" -> resignGame();
                case "highlight" -> highlightMoves();
                case "quit" -> quit();
                default -> help();
            };
        } catch (ResponseException | IOException ex) {
            return ex.getMessage();
        }
    }

    public String register(String... params) throws ResponseException, IOException {
        if (params.length >= 3) {
            state = State.LOGGEDIN;
            visitorName = params[0];
            visitorPassword = params[1];
            visitorEmail = params[2];
            this.visitorAuthToken = server.register(visitorName,visitorPassword,visitorEmail).getAuthData().getAuthToken();
            return String.format("Thanks for registering a new player! \n" +
                    " You have been logged in as %s.", visitorName);
        }
        throw new ResponseException(400, "Expected: <username> <password> <email>");
    }

    public String login(String... params) throws ResponseException, IOException {
        if (params.length >= 2) {
            state = State.LOGGEDIN;
            visitorName = params[0];
            visitorPassword = params[1];
            this.visitorAuthToken = server.login(visitorName,visitorPassword).getAuthData().getAuthToken();
            return String.format("You logged in as %s.", visitorName);
        }
        throw new ResponseException(400, "Expected: <username> <password>");
    }

    public String logOut() throws ResponseException, IOException {
        assertLoggedIn();
        server.logout(this.visitorAuthToken);
        state = State.LOGGEDOUT;
        return String.format("%s has been logged out, have a nice day!", visitorName);
    }

    public String createGame(String... params) throws ResponseException, IOException {
        assertLoggedIn();
        newGameName = params[0];
        server.createGame(this.visitorAuthToken,newGameName);
        return String.format("%s has been created! Join now to start playing!", newGameName);
    }

    public String listGames() throws ResponseException, IOException {
        assertLoggedIn();
        HashSet<GameData> games = server.listGames(this.visitorAuthToken).getGames();
        var result = new StringBuilder();
        var gson = new Gson();
        int counter = 1;
        for (GameData game : games) {
            result.append(counter);
            result.append(": ");
            result.append(game.getGameName()).append("  ");
            result.append("Game ID: ").append(game.getGameID()).append("  ");
            result.append("White Player: ").append(game.getWhiteUsername()).append("  ");
            result.append("Black Player: ").append(game.getBlackUsername()).append("\n");
//            result.append(gson.toJson(game)).append('\n');

            counter++;
        }
        return result.toString();
    }

    public String joinGame(String... params) throws ResponseException, IOException {
        assertLoggedIn();
        this.gameID = Integer.parseInt(params[0]);
        state = State.GAMEPLAY;
        if(params.length > 1){
            String playerColor = params[1];
            ResultData resultData = server.joinGame(this.visitorAuthToken,playerColor,gameID);
            ChessGame chessGame = resultData.getGameData().getGame();
            ws = new WebSocketFacade(serverUrl);
            ws.joinGamePlayer(visitorAuthToken,playerColor,gameID);
            new GameplayRepl(this);
        }
        else{
            ResultData resultData = server.joinGame(this.visitorAuthToken,null,gameID);
            ChessGame chessGame = resultData.getGameData().getGame();
            ws = new WebSocketFacade(serverUrl);
            ws.joinGameObserver(visitorAuthToken,gameID);
            new GameplayRepl(this);
        }
        return "\njoined game\n";
    }

    public String redraw() throws ResponseException, IOException {
        ws = new WebSocketFacade(serverUrl);
        ws.redraw(visitorAuthToken,gameID);
        return "here is the updated board!";
    }

    public String leaveGame() throws ResponseException, IOException {
        ws = new WebSocketFacade(serverUrl);
        ws.leave(visitorAuthToken,gameID);
        return String.format("%s has left the game", visitorName);
    }

    public String makeMove(String... params) throws ResponseException, IOException {

        String start1 = params[0];
        int start1Int = decipherLetter(start1);
        int start2 = Integer.parseInt(params[1]);

        String end1 = params[3];
        int end1Int = decipherLetter(end1);
        int end2 = Integer.parseInt(params[4]);



        ChessPosition startPosition = new ChessPosition(start1Int,start2);
        ChessPosition endPosition = new ChessPosition(end1Int,end2);


        ws = new WebSocketFacade(serverUrl);
        ws.makeMove(visitorAuthToken,gameID,startPosition,endPosition);
        return String.format("%s has made their move!", visitorName);
    }

    private static int decipherLetter(String start1) {
        int start1Int = 0;
        if(start1.equalsIgnoreCase("a")){
            start1Int = 1;
        }
        else if(start1.equalsIgnoreCase("b")){
            start1Int = 2;
        }
        else if(start1.equalsIgnoreCase("c")){
            start1Int = 3;
        }
        else if(start1.equalsIgnoreCase("d")){
            start1Int = 4;
        }
        else if(start1.equalsIgnoreCase("e")){
            start1Int = 5;
        }
        else if(start1.equalsIgnoreCase("f")){
            start1Int = 6;
        }
        else if(start1.equalsIgnoreCase("g")){
            start1Int = 7;
        }
        else if(start1.equalsIgnoreCase("h")){
            start1Int = 8;
        }
        return start1Int;
    }

    public String resignGame() throws ResponseException, IOException {
        ws = new WebSocketFacade(serverUrl);
        ws.resign(visitorAuthToken,gameID);
        return String.format("%s has resigned. GG!", visitorName);
    }

    public String highlightMoves() throws ResponseException, IOException {
        ws = new WebSocketFacade(serverUrl);
        ws.highlightMoves();
        return "here are the potential moves!";
    }

    public String quit(){
        state = State.QUIT;
        return "quit";
    }

    public String help() {
        if (state == State.LOGGEDOUT) {
            return """
                    - register <username> <password> <email> - to create an account
                    - login <username> <password> - to play chess
                    - quit - playing chess
                    - help - with possible commands
                    """;
        }
        if (state == State.GAMEPLAY){
            return """
                - move <LETTER,NUMBER> to <LETTER,NUMBER>
                - redraw - redraws the chess board
                - leave - return to logged-in menu
                - highlight legal moves - highlights possible legal moves
                - help - with possible commands
                """;
        }
        return """
                - create <NAME> - a game
                - list - games
                - join <ID> [WHITE|BLACK|<EMPTY>] - a game
                - observe <ID> - a game
                - logout - when you are done
                - quit - playing chess
                - help - with possible commands
                """;
    }

    private void assertLoggedIn() throws ResponseException {
        if (state == State.LOGGEDOUT) {
            throw new ResponseException(400, "You must sign in");
        }
    }

    public State getState(){
        return state;
    }

}
