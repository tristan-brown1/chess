package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import server.DAOs.DAO;
import service.ChessService;
import spark.*;

public class Server {
    private final ChessService service;

//    public Server() {
//        this.service = null;
//    }

    public Server(DAO dataAccess) {
        service = new ChessService(dataAccess);
    }
    public Server() {
        service = null;
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("/resources");

        // Register your endpoints and handle exceptions here.
//        register handler
        Spark.get("/", this::test);

        Spark.post("/user", this::registerHandler);

//        clear handler
        Spark.delete("/db", this::clearAppHandler);

//        login handler

//        logout handler

//        list games handler

//        create game handler

//        join game handler

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
        if (service != null){
        service.clearAll();
        res.status(200);
        }
        else {
            res.status(200);
//            throw new DataAccessException("Error: description");
        }
        return "";
    }

    private Object registerHandler(Request req, Response res) throws DataAccessException {
        var username = req.params(":username");
        var password = req.params(":password");
        var email = req.params(":email");
        String authToken = null;

        if (service != null){
            authToken = service.register(username,password,email);
            res.status(200);
        }
        else {
//            throw new DataAccessException("Service was null");
            res.status(400);
        }

        return new Gson().toJson(authToken);
    }

}
