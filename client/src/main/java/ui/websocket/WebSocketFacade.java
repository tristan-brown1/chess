package ui.websocket;

import com.google.gson.Gson;
import webSocketMessages.serverMessages.ServerMessage;
import dataAccess.ResponseException;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.Action;
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
//    NotificationHandler notificationHandler;


    public WebSocketFacade(String url) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
//            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    System.out.print(message);
                    System.out.print("\n");
                    System.out.print("got a message from the server to the client");
//                    Notification notification = new Gson().fromJson(message, Notification.class);
//                    notificationHandler.notify(notification);
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


    public void joinGame(String visitorName) throws ResponseException {
        try {
            var newMessage = new UserGameCommand(visitorName);
            newMessage.setCommandType(UserGameCommand.CommandType.JOIN_PLAYER);
            this.session.getBasicRemote().sendText(new Gson().toJson(newMessage));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void redraw() throws ResponseException {

    }

    public void leave(String visitorName) throws ResponseException {

    }

    public void resign() throws ResponseException {

    }

    public void makeMove() throws ResponseException {

    }

}

