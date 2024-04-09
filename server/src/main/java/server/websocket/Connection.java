package server.websocket;

import org.eclipse.jetty.websocket.api.Session;
import java.io.IOException;

public class Connection {
    public String visitorAuth;
    public Session session;

    public Connection(Session session, String visitorAuth) {
        this.visitorAuth = visitorAuth;
        this.session = session;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
}
