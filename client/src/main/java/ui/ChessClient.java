package ui;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import dataAccess.DataAccessException;
import dataAccess.ResponseException;
import model.GameData;
import ui.REPLs.GameplayRepl;
import ui.websocket.NotificationHandler;


public class ChessClient {

    private String visitorName = null;
    private String visitorPassword = null;
    private String visitorEmail = null;
    private String visitorAuthToken = null;
    private String newGameName = null;

//    private final NotificationHandler notificationHandler;

    private final ServerFacade server;
    private final String serverUrl;

    private State state = State.LOGGEDOUT;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
//        this.notificationHandler = notificationHandler;

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
                case "join" -> joinGame(params);
                case "observe" -> joinGame(params);
                case "redraw" -> redraw();
                case "leave" -> leaveGame();
                case "make" -> makeMove(params);
                case "resign" -> resignGame();
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
        int gameID = Integer.parseInt(params[0]);
        state = State.GAMEPLAY;
        if(params.length > 1){
            String playerColor = params[1];
            server.joinGame(this.visitorAuthToken,playerColor,gameID);
            new GameplayRepl(this);
        }
        else{
            server.joinGame(this.visitorAuthToken,null,gameID);
//            new GameplayRepl(this);
        }
        return "\njoined game\n";
    }

    public String redraw() throws ResponseException, IOException {
//        assertLoggedIn();
//        server.logout(this.visitorAuthToken);
//        state = State.LOGGEDOUT;
        return String.format("%s has been logged out, have a nice day!", visitorName);
    }

    public String leaveGame() throws ResponseException, IOException {
//        assertLoggedIn();
//        server.logout(this.visitorAuthToken);
//        state = State.LOGGEDOUT;
        return String.format("%s has been logged out, have a nice day!", visitorName);
    }

    public String makeMove(String... params) throws ResponseException, IOException {
//        assertLoggedIn();
//        server.logout(this.visitorAuthToken);
//        state = State.LOGGEDOUT;
        return String.format("%s has been logged out, have a nice day!", visitorName);
    }

    public String resignGame() throws ResponseException, IOException {
//        assertLoggedIn();
//        server.logout(this.visitorAuthToken);
//        state = State.LOGGEDOUT;
        return String.format("%s has been logged out, have a nice day!", visitorName);
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
                - make move <LETTER,NUMBER> to <LETTER,NUMBER>
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
