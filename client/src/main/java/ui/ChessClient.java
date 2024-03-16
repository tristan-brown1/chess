package ui;

import com.google.gson.Gson;

import java.util.Arrays;
import dataAccess.DataAccessException;
import dataAccess.ResponseException;


public class ChessClient {

    private String visitorName = null;
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
                case "login" -> logIn(params);
//                case "list" -> listGames();
                case "logout" -> logOut();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String logIn(String... params) throws ResponseException {
        if (params.length >= 1) {
            state = State.LOGGEDIN;
            visitorName = String.join("-", params);
            return String.format("You signed in as %s.", visitorName);
        }
        throw new ResponseException(400, "Expected: <yourname>");
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

    public String logOut() throws ResponseException {
        assertLoggedIn();
        state = State.LOGGEDOUT;
        return String.format("%s has logged out, Have a nice day!", visitorName);
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
                - list
                - adopt <pet id>
                - rescue <name> <CAT|DOG|FROG|FISH>
                - adoptAll
                - signOut
                - quit
                """;
    }

    private void assertLoggedIn() throws ResponseException {
        if (state == State.LOGGEDOUT) {
            throw new ResponseException(400, "You must sign in");
        }
    }

}
