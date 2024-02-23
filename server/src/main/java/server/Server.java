package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.DAOs.DAO;
import service.ChessService;
import spark.*;

import java.util.HashMap;

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
        return "";
    }
    private Object createGameHandler(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("authorization");
        HashMap<String, Object> yourHashMap = new Gson().fromJson(req.body(), HashMap.class);
        String gameName = yourHashMap.get("gameName").toString();
        ResultData statusData = service.createGame(authToken,gameName);
        res.status(statusData.getStatus());
        return new Gson().toJson(statusData);
    }
    private Object joinGameHandler(Request req, Response res) throws DataAccessException {

        return "";
    }
}