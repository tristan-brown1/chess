package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(Session session, String visitorAuth, int gameID) {
        var connection = new Connection(session, visitorAuth, gameID);
        connections.put(visitorAuth, connection);
    }

    public void remove(String visitorName) {
        connections.remove(visitorName);
    }

    public void broadcastToGame(String excludeVisitorAuth, int visitorGameID, String responseMessage, ServerMessage.ServerMessageType messageType, ChessGame game) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.visitorAuth.equals(excludeVisitorAuth)) {
                    if(c.gameID == visitorGameID){
                        if(messageType == ServerMessage.ServerMessageType.NOTIFICATION){
                            var newMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, responseMessage);
                            c.send(new Gson().toJson(newMessage));
                        }
                        else if (messageType == ServerMessage.ServerMessageType.LOAD_GAME){
                            var newMessage = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME,game);
                            c.send(new Gson().toJson(newMessage));
                        }
                    }
                }
            } else {
                removeList.add(c);
            }
        }
        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.visitorAuth);
        }
    }
}

