package ui.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.Gson;
import dataAccess.DAOs.SQLGameDAO;
import ui.ChessImage;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import dataAccess.ResponseException;
import webSocketMessages.userCommands.*;
//import exception.ResponseException;

import javax.swing.*;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static ui.EscapeSequences.GREEN;
import static ui.EscapeSequences.RESET;

//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {

    Session session;
    ChessGame game;


    public WebSocketFacade(String url) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch (serverMessage.getServerMessageType()) {
                        case LOAD_GAME -> loadGame(message);
                        case ERROR -> error(message);
                        case NOTIFICATION -> notification(message);
                    }

                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }


    public void joinGamePlayer(String authToken, String playerColor, int gameID) throws ResponseException {
        try {
            var newMessage = new JoinPlayer(authToken,gameID,playerColor);
            newMessage.setCommandType(UserGameCommand.CommandType.JOIN_PLAYER);
            this.session.getBasicRemote().sendText(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void joinGameObserver(String authToken, int gameID) throws ResponseException {
        try {
            var newMessage = new JoinObserver(authToken,gameID);
            newMessage.setCommandType(UserGameCommand.CommandType.JOIN_OBSERVER);
            this.session.getBasicRemote().sendText(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void redraw(String authToken, int gameID) throws ResponseException {
//        System.out.print("redrawing board");

        try {
            var newMessage = new JoinObserver(authToken,gameID);
            newMessage.setCommandType(UserGameCommand.CommandType.REDRAW);
            this.session.getBasicRemote().sendText(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void leave(String authToken, int gameID, String playerColor) throws ResponseException {
        try {
            var newMessage = new Leave(authToken,gameID,playerColor);
            newMessage.setCommandType(UserGameCommand.CommandType.LEAVE);
            this.session.getBasicRemote().sendText(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
        System.out.print("leaving game");
    }

    public void resign(String authToken, int gameID) throws ResponseException {
//        need to print out confirmation message

        try {
            var newMessage = new Resign(authToken,gameID);
            newMessage.setCommandType(UserGameCommand.CommandType.RESIGN);
            this.session.getBasicRemote().sendText(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }

        System.out.print("resigning game");
    }

    public void makeMove(String authToken, int gameID, ChessMove newMove) throws ResponseException {

        try {
            var newMessage = new MakeMove(authToken,gameID,newMove);
            newMessage.setCommandType(UserGameCommand.CommandType.MAKE_MOVE);
            this.session.getBasicRemote().sendText(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
        System.out.print("making a move");
    }

    public void highlightMoves() throws ResponseException {
        System.out.print("highlighting moves");
    }

    private void loadGame(String message){
        LoadGame loadGame = new Gson().fromJson(message, LoadGame.class);
        ChessImage.printCurrentBoard(loadGame.getGame().getBoard());
        System.out.print("\n" + RESET + ">>> " + GREEN);
    }

    private void notification(String message){
        Notification notification = new Gson().fromJson(message, Notification.class);
        System.out.print(notification.getMessage());
    }

    private void error(String message){
        Error error = new Gson().fromJson(message, Error.class);
        System.out.print(error.getMessage());
    }
}
