package ui;

public class ChessClient {

    private String visitorName = null;
    private final ServerFacade server;
    private final String serverUrl;

    private State state = State.LOGGEDOUT;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

}
