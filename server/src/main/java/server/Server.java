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

        Spark.staticFiles.location("/resources");

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
        String username = yourHashMap.get("username").toString();
        String password = yourHashMap.get("password").toString();
        String email = yourHashMap.get("email").toString();
        UserData newUser = new UserData(username,password,email);
        AuthData authData = null;

        if (service != null){
            authData = service.register(username,password,email);
            res.status(200);
        }
        else if(username != null){
//            authToken = service.register(username,password,email);
            res.status(200);
        }
        else {
//            throw new DataAccessException("Service was null");
            res.status(400);
        }

        return new Gson().toJson(authData);
    }

    private Object loginHandler(Request req, Response res) throws DataAccessException {
        HashMap<String, Object> yourHashMap = new Gson().fromJson(req.body(), HashMap.class);
        String username = yourHashMap.get("username").toString();
        String password = yourHashMap.get("password").toString();
        ResultData statusData = service.login(username,password);
        AuthData authData = statusData.getAuthData();
        if(authData == null){
            res.status(statusData.getStatus());
            return new Gson().toJson(statusData);
        }

        else {
            res.status(200);

            return new Gson().toJson(authData);
        }
    }
    private Object logoutHandler(Request req, Response res) throws DataAccessException {

        return "";
    }
    private Object listGameHandler(Request req, Response res) throws DataAccessException {

        return "";
    }
    private Object createGameHandler(Request req, Response res) throws DataAccessException {

        return "";
    }
    private Object joinGameHandler(Request req, Response res) throws DataAccessException {

        return "";
    }


}
