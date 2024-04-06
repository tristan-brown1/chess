package ui.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import webSocketMessages.serverMessages.ServerMessage;
import dataAccess.ResponseException;
import webSocketMessages.userCommands.JoinObserver;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.UserGameCommand;
//import exception.ResponseException;

import javax.swing.*;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {

    Session session;


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
//                    System.out.print(message);
//                    System.out.print("\n");
//                    System.out.print("got a message from the server to the client\n");
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch (serverMessage.getServerMessageType()) {
                        case LOAD_GAME -> loadGame(serverMessage);
                        case ERROR -> error(serverMessage);
                        case NOTIFICATION -> notification(serverMessage);
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

    public void redraw() throws ResponseException {
        System.out.print("redrawing board");
    }

    public void leave(String visitorName) throws ResponseException {
        System.out.print("leaving");
    }

    public void resign() throws ResponseException {
        System.out.print("resigning");
    }

    public void makeMove() throws ResponseException {
        System.out.print("making a move");
    }

    public void highlightMoves() throws ResponseException {
        System.out.print("highlighting moves");
    }

    private void loadGame(ServerMessage serverMessage){
        System.out.print("got to the load board method\n");
    }

    private void notification(ServerMessage serverMessage){
        System.out.print("got to notification method\n");
    }

    private void error(ServerMessage serverMessage){
        System.out.print("got to the error method\n");
    }
}
