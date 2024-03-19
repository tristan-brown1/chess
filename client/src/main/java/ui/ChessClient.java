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
//                case "list" -> listGames();
                case "logout" -> logOut();
                case "quit" -> "quit";
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

//    public String listGames() throws ResponseException {
//        assertLoggedIn();
//        var pets = server.listPets();
//        var result = new StringBuilder();
//        var gson = new Gson();
//        for (var pet : pets) {
//            result.append(gson.toJson(pet)).append('\n');
//        }
//        return result.toString();
//    }

    public String logOut() throws ResponseException, IOException {
        assertLoggedIn();
        server.logout(this.visitorAuthToken);
        state = State.LOGGEDOUT;
        return String.format("%s has been logged out, have a nice day!", visitorName);
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
