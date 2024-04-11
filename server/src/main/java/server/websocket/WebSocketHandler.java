package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;

import dataAccess.DAOs.SQLAuthDAO;
import dataAccess.DAOs.SQLGameDAO;
import dataAccess.DataAccessException;
import sharedDataClasses.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.Error;
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
    public void onMessage(Session session, String message) throws IOException, ResponseException, DataAccessException {
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
            if(gameData != null){
                if(authDAO.getAuth(authToken) != null){
                    String username = authDAO.getAuth(authToken).getUsername();
                    if(playerColor.equalsIgnoreCase("white")){
                        if(gameData.getWhiteUsername() != null){
                            if(gameData.getWhiteUsername().equalsIgnoreCase(username)){
                                var newMessage = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME,gameData.getGame());
                                session.getRemote().sendString(new Gson().toJson(newMessage));
                                String responseMessage =  username + " has entered the game as a player \n";
                                connections.broadcastToGame(authToken,tempGameID,responseMessage, ServerMessage.ServerMessageType.NOTIFICATION,null);
                            }
                            else{
                                var newMessage = new Error(ServerMessage.ServerMessageType.ERROR,"Invalid join request: team is already taken");
                                session.getRemote().sendString(new Gson().toJson(newMessage));
                            }
                        }
                        else{
                            var newMessage = new Error(ServerMessage.ServerMessageType.ERROR,"Invalid join request: Empty Game");
                            session.getRemote().sendString(new Gson().toJson(newMessage));
                        }
                    }
                    else {
                        if(gameData.getBlackUsername() != null){
                            if(gameData.getBlackUsername().equalsIgnoreCase(username)){
                                var newMessage = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME,gameData.getGame());
                                session.getRemote().sendString(new Gson().toJson(newMessage));
                                String responseMessage =  username + " has entered the game as a player \n";
                                connections.broadcastToGame(authToken,tempGameID,responseMessage, ServerMessage.ServerMessageType.NOTIFICATION,null);
                            }
                            else{
                                var newMessage = new Error(ServerMessage.ServerMessageType.ERROR,"Invalid join request: team is already taken");
                                session.getRemote().sendString(new Gson().toJson(newMessage));
                            }
                        }
                        else{
                            var newMessage = new Error(ServerMessage.ServerMessageType.ERROR,"Invalid join request: Empty Game");
                            session.getRemote().sendString(new Gson().toJson(newMessage));
                        }
                    }


                }
                else{
                    var newMessage = new Error(ServerMessage.ServerMessageType.ERROR,"Invalid join request: authentication is invalid");
                    session.getRemote().sendString(new Gson().toJson(newMessage));
                }

            }
            else{
                var newMessage = new Error(ServerMessage.ServerMessageType.ERROR,"Invalid join request: game id number is incorrect");
                session.getRemote().sendString(new Gson().toJson(newMessage));
            }



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
            if(gameData != null){
                if(authDAO.getAuth(authToken) != null){

                    String username = authDAO.getAuth(authToken).getUsername();
                    var newMessage = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME,gameData.getGame());
                    session.getRemote().sendString(new Gson().toJson(newMessage));
                    String responseMessage =  username + " has entered the game as a watcher\n";
                    connections.broadcastToGame(authToken,tempGameID,responseMessage, ServerMessage.ServerMessageType.NOTIFICATION,null);

                }
                else{
                    var newMessage = new Error(ServerMessage.ServerMessageType.ERROR,"Invalid join request: authentication is invalid");
                    session.getRemote().sendString(new Gson().toJson(newMessage));
                }
            }
            else{
                var newMessage = new Error(ServerMessage.ServerMessageType.ERROR,"Invalid join request: game id number is incorrect");
                session.getRemote().sendString(new Gson().toJson(newMessage));
            }

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

//            try this

//            ChessGame.TeamColor playerColor = makeMove.getPlayerColor();

            if(gameDAO.getGame(gameID) != null){
                ChessGame.TeamColor playerColor = null;
                if(gameDAO.getGame(gameID).getWhiteUsername().equals(authDAO.getAuth(authToken).getUsername())){
                    playerColor = ChessGame.TeamColor.WHITE;
                }
                else if(gameDAO.getGame(gameID).getBlackUsername().equals(authDAO.getAuth(authToken).getUsername())){
                    playerColor = ChessGame.TeamColor.BLACK;
                }


                ChessGame updatedGame = gameDAO.getGame(gameID).getGame().setUserTeamColor(playerColor);
                updatedGame = updatedGame.makeMove(newMove);
                GameData gameData = gameDAO.getGame(gameID);
                int tempGameID = gameData.getGameID();
                gameDAO.updateGameStatus(tempGameID, updatedGame);
                gameData = gameDAO.getGame(gameID);

                var newMessage = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME,gameData.getGame());
                session.getRemote().sendString(new Gson().toJson(newMessage));

                String responseMessage = "move has been made!";
                connections.broadcastToGame(authToken,gameID,null,ServerMessage.ServerMessageType.LOAD_GAME,gameData.getGame());
                connections.broadcastToGame(authToken,gameID,responseMessage,ServerMessage.ServerMessageType.NOTIFICATION,gameData.getGame());

            }
            else{
                var newMessage = new Error(ServerMessage.ServerMessageType.ERROR,"Error: Game is not currently in progress");
                session.getRemote().sendString(new Gson().toJson(newMessage));
            }

//            probably need to update these catch blocks to send error messages xd

        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        } catch (InvalidMoveException | DataAccessException e) {
            var newMessage = new Error(ServerMessage.ServerMessageType.ERROR,"Error: move is invalid");
            session.getRemote().sendString(new Gson().toJson(newMessage));
        }
    }

    private void leave(Session session, String message) throws IOException, ResponseException, DataAccessException {
        Leave leave = new Gson().fromJson(message, Leave.class);
        String playerColor = leave.getPlayerColor();
        String authToken = leave.getAuthString();
        int gameID = leave.getGameID();

        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();
        String username = authDAO.getAuth(authToken).getUsername();
        if(playerColor != null){
            gameDAO.removePlayer(username,gameID,playerColor);
        }
        try {



            String notiMessage = username + " has left the game";
            connections.remove(authToken);
            connections.broadcastToGame(authToken,gameID,notiMessage, ServerMessage.ServerMessageType.NOTIFICATION,null);


        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private void resign(Session session, String message) throws IOException, ResponseException {
//        need to actually resign game and then distribute responses correctly
        try {
            Resign resign = new Gson().fromJson(message, Resign.class);
            String authToken = resign.getAuthString();
            int gameID = resign.getGameID();
            SQLAuthDAO authDAO = new SQLAuthDAO();
            SQLGameDAO gameDAO = new SQLGameDAO();
            String username = authDAO.getAuth(authToken).getUsername();
            if(gameDAO.getGame(gameID) != null){
                if(gameDAO.getGame(gameID).getBlackUsername().equalsIgnoreCase(username) || gameDAO.getGame(gameID).getWhiteUsername().equalsIgnoreCase(username)){
                    gameDAO.clearGame(gameID);

                    String responseMessage = username + " has resigned the game! GG";
                    var newMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, responseMessage);
                    session.getRemote().sendString(new Gson().toJson(newMessage));
                    connections.broadcastToGame(authToken, gameID,responseMessage, ServerMessage.ServerMessageType.NOTIFICATION,null);
                }
                else{
                    var newMessage = new Error(ServerMessage.ServerMessageType.ERROR,"Error: You are not authorized to resign");
                    session.getRemote().sendString(new Gson().toJson(newMessage));
                }
            }
            else{
                var newMessage = new Error(ServerMessage.ServerMessageType.ERROR,"Error: Game has already concluded");
                session.getRemote().sendString(new Gson().toJson(newMessage));
            }

//            need to remove the game from connections and from the database

        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
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
