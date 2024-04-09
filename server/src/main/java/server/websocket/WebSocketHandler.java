package server.websocket;

import chess.ChessMove;
import chess.InvalidMoveException;
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
import webSocketMessages.userCommands.*;


import java.io.IOException;


@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, ResponseException {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(session,message);
            case JOIN_OBSERVER -> joinObserver(session,message);
            case MAKE_MOVE -> makeMove(session, message);
            case LEAVE -> leave(session,message);
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
        connections.add(session,authToken,tempGameID);

        try {
            SQLGameDAO gameDAO = new SQLGameDAO();
            SQLAuthDAO authDAO = new SQLAuthDAO();
            GameData gameData = gameDAO.getGame(tempGameID);
            String username = authDAO.getAuth(authToken).getUsername();
            var newMessage = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME,gameData.getGame());
            session.getRemote().sendString(new Gson().toJson(newMessage));
            connections.broadcastToGame(authToken,tempGameID,"THE GUY has entered the game as a player \n");
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private void joinObserver(Session session,String message) throws IOException, ResponseException {

        JoinObserver joinObserver = new Gson().fromJson(message, JoinObserver.class);
        int tempGameID = joinObserver.getGameID();
        String authToken = joinObserver.getAuthString();
        connections.add(session,authToken,tempGameID);
        try {
            SQLGameDAO gameDAO = new SQLGameDAO();
            SQLAuthDAO authDAO = new SQLAuthDAO();
            GameData gameData = gameDAO.getGame(tempGameID);
            String username = authDAO.getAuth(authToken).getUsername();
            var newMessage = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME,gameData.getGame());
            session.getRemote().sendString(new Gson().toJson(newMessage));
            connections.broadcastToGame(authToken,tempGameID,"THE GUY has entered the game as a watcher\n");
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private void makeMove(Session session, String message) throws IOException, ResponseException {
        try {
            MakeMove makeMove = new Gson().fromJson(message, MakeMove.class);
            String authToken = makeMove.getAuthString();
            int gameID = makeMove.getGameID();
            ChessMove newMove = makeMove.getMove();
            SQLGameDAO gameDAO = new SQLGameDAO();
            SQLAuthDAO authDAO = new SQLAuthDAO();

            gameDAO.getGame(gameID).getGame().makeMove(newMove);

            String responseMessage = "move has been made!";
            var newMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, responseMessage);
            session.getRemote().sendString(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
    }

    private void leave(Session session, String message) throws IOException, ResponseException {
        //        need to actually leave game and then distribute responses correctly



        try {
            String notiMessage = "someone has left the game";
            var newMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, notiMessage);
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
