package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.ServerMessage;
import dataAccess.ResponseException;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.UserGameCommand;


import java.io.IOException;
import java.util.Timer;


@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, ResponseException {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(session,message);
            case JOIN_OBSERVER -> joinObserver(session);
            case MAKE_MOVE -> makeMove(session);
            case LEAVE -> leave(session);
            case RESIGN -> resign(session);
        }
    }

    private void joinPlayer(Session session, String message) throws IOException, ResponseException {
//        session.getRemote().sendString("got from client to the server!");
        JoinPlayer joinPlayer = new Gson().fromJson(message, JoinPlayer.class);
        int tempGameID = joinPlayer.getGameID();
        String playerColor = joinPlayer.getPlayerColor();
        ChessGame newGame = new ChessGame();
        try {
            var newMessage = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME,newGame);
            session.getRemote().sendString(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
//        ServerMessage serverMessage = new Gson().toJson(newMessage,ServerMessage.class);
    }

    private void joinObserver(Session session) throws IOException, ResponseException {
        try {
            ChessGame newGame = new ChessGame();
            var newMessage = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME,newGame);
            session.getRemote().sendString(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private void makeMove(Session session) throws IOException {

    }

    private void leave(Session session) throws IOException {

    }

    private void resign(Session session) throws IOException {

    }
}
