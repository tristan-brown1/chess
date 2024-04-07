package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;

import dataAccess.DAOs.SQLAuthDAO;
import dataAccess.DAOs.SQLGameDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import dataAccess.ResponseException;
import webSocketMessages.userCommands.JoinObserver;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.Redraw;
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
            case JOIN_OBSERVER -> joinObserver(session,message);
            case MAKE_MOVE -> makeMove(session);
            case LEAVE -> leave(session);
            case RESIGN -> resign(session, message);
            case REDRAW -> redraw(session,message);
        }
    }

    private void joinPlayer(Session session, String message) throws IOException, ResponseException {
//        session.getRemote().sendString("got from client to the server!");
        JoinPlayer joinPlayer = new Gson().fromJson(message, JoinPlayer.class);
        int tempGameID = joinPlayer.getGameID();
        String playerColor = joinPlayer.getPlayerColor();
        String authToken = joinPlayer.getAuthString();

        try {
            SQLGameDAO gameDAO = new SQLGameDAO();
            SQLAuthDAO authDAO = new SQLAuthDAO();
            GameData gameData = gameDAO.getGame(tempGameID);
            String username = authDAO.getAuth(authToken).getUsername();
            var newMessage = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME,gameData.getGame());
            session.getRemote().sendString(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private void joinObserver(Session session,String message) throws IOException, ResponseException {
        JoinObserver joinObserver = new Gson().fromJson(message, JoinObserver.class);
        int tempGameID = joinObserver.getGameID();
        String authToken = joinObserver.getAuthString();
        try {
            SQLGameDAO gameDAO = new SQLGameDAO();
            SQLAuthDAO authDAO = new SQLAuthDAO();
            GameData gameData = gameDAO.getGame(tempGameID);
            String username = authDAO.getAuth(authToken).getUsername();
            var newMessage = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME,gameData.getGame());
            session.getRemote().sendString(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private void makeMove(Session session) throws IOException, ResponseException {
        try {
            ChessGame newGame = new ChessGame();
            String message = "";
            var newMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            session.getRemote().sendString(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private void leave(Session session) throws IOException, ResponseException {
        //        need to actually leave game and then distribute responses correctly




        try {
            String message = "";
            var newMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            session.getRemote().sendString(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private void resign(Session session, String message) throws IOException, ResponseException {
//        need to actually resign game and then distribute responses correctly



        try {
            var newMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            session.getRemote().sendString(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private void redraw(Session session,String message) throws IOException, ResponseException {
        Redraw redraw = new Gson().fromJson(message, Redraw.class);
        int tempGameID = redraw.getGameID();
//        String authToken = redraw.getAuthString();
        try {
            SQLGameDAO gameDAO = new SQLGameDAO();
            SQLAuthDAO authDAO = new SQLAuthDAO();
            GameData gameData = gameDAO.getGame(tempGameID);
//            String username = authDAO.getAuth(authToken).getUsername();
            var newMessage = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME,gameData.getGame());
            session.getRemote().sendString(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


}
