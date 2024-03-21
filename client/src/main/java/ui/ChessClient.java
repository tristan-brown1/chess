package ui;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import dataAccess.DataAccessException;
import dataAccess.ResponseException;


public class ChessClient {

    private String visitorName = null;
    private String visitorPassword = null;
    private String visitorEmail = null;
    private String visitorAuthToken = null;
    private String newGameName = null;

    private final ServerFacade server;
    private final String serverUrl;

    private State state = State.LOGGEDOUT;

    public ChessClient(String serverUrl) {
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
                case "join" -> joinGame(params);
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
        return String.format("%s has been logged out, have a nice day!", visitorName);
    }

    public String listGames() throws ResponseException, IOException {
        assertLoggedIn();
        var games = server.listGames(this.visitorAuthToken).getGames();
        var result = new StringBuilder();
        var gson = new Gson();
        for (var game : games) {
            result.append(gson.toJson(game)).append('\n');
        }
        return result.toString();
    }

    public String joinGame(String... params) throws ResponseException, IOException {
        assertLoggedIn();
        int gameID = Integer.parseInt(params[0]);
        String playerColor = params[1];
        state = State.GAMEPLAY;
        server.joinGame(this.visitorAuthToken,playerColor,gameID);
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
            return "congrats big dog, you made it to gameplay";
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
