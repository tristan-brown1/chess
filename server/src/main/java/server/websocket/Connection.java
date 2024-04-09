package server.websocket;

import org.eclipse.jetty.websocket.api.Session;
import java.io.IOException;

public class Connection {
    public String visitorAuth;
    public Session session;
    public int gameID;

    public Connection(Session session, String visitorAuth, int gameID) {
        this.visitorAuth = visitorAuth;
        this.session = session;
        this.gameID = gameID;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }

    public int getGameID() {
        return gameID;
    }
}
