package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import service.ChessService;
import spark.*;

import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class Server {
    private final ChessService service;

//    public Server(DAO dataAccess) {
//        service = new ChessService(dataAccess);
//    }
    public Server() {
        this.service = new ChessService();
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("/resources/web");

        // Register your endpoints and handle exceptions here.
        Spark.get("/", this::test);
//        register handler
        Spark.post("/user", this::registerHandler);
//        clear handler
        Spark.delete("/db", this::clearAppHandler);
//        login handler
        Spark.post("/session", this::loginHandler);
//        logout handler
        Spark.delete("/session", this::logoutHandler);
//        list games handler
        Spark.get("/game", this::listGameHandler);
//        create game handler
        Spark.post("/game", this::createGameHandler);
//        join game handler
        Spark.put("/game", this::joinGameHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    private Object test(Request request, Response response) {
        response.status(200);
        return "CS 240 Chess Server Web API";
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object clearAppHandler(Request req, Response res) throws DataAccessException {
        service.clearAll();
        res.status(200);
        return "";
    }

    private Object registerHandler(Request req, Response res) throws DataAccessException {
        HashMap<String, Object> yourHashMap = new Gson().fromJson(req.body(), HashMap.class);
        if ((yourHashMap.containsKey("username") && yourHashMap.containsKey("password") && yourHashMap.containsKey("email"))){
            String username = yourHashMap.get("username").toString();
            String password = yourHashMap.get("password").toString();
            String email = yourHashMap.get("email").toString();

            UserData newUser = new UserData(username,password,email);
            ResultData statusData = service.register(username,password,email);
            AuthData authData = statusData.getAuthData();
            res.status(statusData.getStatus());
            return new Gson().toJson(statusData);
        }
        else {
            ResultData resultData = new ResultData();
            resultData.setStatus(400);
            resultData.setMessage("Error:Bad Request");
            res.status(resultData.getStatus());
            return new Gson().toJson(resultData);
        }
    }

    private Object loginHandler(Request req, Response res) throws DataAccessException {
        HashMap<String, Object> yourHashMap = new Gson().fromJson(req.body(), HashMap.class);
        String username = yourHashMap.get("username").toString();
        String password = yourHashMap.get("password").toString();
        ResultData statusData = service.login(username,password);
        res.status(statusData.getStatus());
        return new Gson().toJson(statusData);
    }
    private Object logoutHandler(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("authorization");
        ResultData statusData = service.logout(authToken);
        res.status(statusData.getStatus());
        return new Gson().toJson(statusData);
    }
    private Object listGameHandler(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("authorization");
        ResultData statusData = service.listGames(authToken);
        res.status(statusData.getStatus());
        return new Gson().toJson(statusData);
    }
    private Object createGameHandler(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("authorization");
        String gameName = "";
        HashMap<String, Object> yourHashMap = new Gson().fromJson(req.body(), HashMap.class);
        if(yourHashMap.get("gameName") != null){
            gameName = yourHashMap.get("gameName").toString();
        }
        ResultData statusData = service.createGame(authToken,gameName);
        res.status(statusData.getStatus());
        return new Gson().toJson(statusData);
    }
    private Object joinGameHandler(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("authorization");
        HashMap<String, Object> yourHashMap = new Gson().fromJson(req.body(), HashMap.class);
        String playerColor = null;
        String gameIDString = null;
        int gameID = 0;
        ResultData statusData = null;

        if(yourHashMap.get("playerColor") != null && yourHashMap.get("gameID") != null){
            playerColor = yourHashMap.get("playerColor").toString();
            gameIDString = yourHashMap.get("gameID").toString().substring(0,gameIDString.length() - 2);
            gameID = parseInt(gameIDString);
            if(gameID != 0){
                statusData = service.joinGame(authToken,playerColor,gameID);
            }
        }
        else if(yourHashMap.get("gameID") != null){
            gameIDString = yourHashMap.get("gameID").toString().substring(0,3);
            gameID = parseInt(gameIDString);
            statusData = service.joinGame(authToken,null,gameID);
        }
        else{

        }

        res.status(statusData.getStatus());
        return new Gson().toJson(statusData);
    }
}