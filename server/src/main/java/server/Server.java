package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.ChessService;
import spark.*;

import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class Server {
    private final ChessService service;


    public Server(){
        this.service = new ChessService();
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("/resources/web");

        // Register your endpoints and handle exceptions here.
        Spark.get("/", this::openTest);

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

    private Object openTest(Request request, Response response) {
        response.status(200);
        return "CS 240 Chess Server Web API";
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object clearAppHandler(Request req, Response res) throws DataAccessException {
        ResultData statusData = service.clearAll();
        res.status(statusData.getStatus());
        return new Gson().toJson(statusData);
    }

    private Object registerHandler(Request req, Response res) throws DataAccessException {
        HashMap<String, Object> yourHashMap = new Gson().fromJson(req.body(), HashMap.class);
        if ((yourHashMap.containsKey("username") && yourHashMap.containsKey("password") && yourHashMap.containsKey("email"))){
            String username = yourHashMap.get("username").toString();
            String password = yourHashMap.get("password").toString();
            String email = yourHashMap.get("email").toString();

            ResultData statusData = service.register(username,password,email);
            res.status(statusData.getStatus());
            return new Gson().toJson(statusData);
        }
        else {
            ResultData statusData = new ResultData();
            statusData.setStatus(400);
            statusData.setMessage("Error:Bad Request");
            res.status(statusData.getStatus());
            return new Gson().toJson(statusData);
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
        HashMap<String, Object> tempHashMap = new Gson().fromJson(req.body(), HashMap.class);
        String playerColor = null;
        String gameIDString = null;
        int gameID = 0;

        try{
            if(tempHashMap.size() == 2){
                playerColor = tempHashMap.get("playerColor").toString();
                gameIDString = tempHashMap.get("gameID").toString();
                String gameIDString2 = gameIDString.substring(0,gameIDString.length() - 2);
                gameID = parseInt(gameIDString2);

                ResultData statusData = service.joinGame(authToken,playerColor,gameID);
                res.status(statusData.getStatus());
                return new Gson().toJson(statusData);
            }
            else{
                gameIDString = tempHashMap.get("gameID").toString();
                String gameIDString2 = gameIDString.substring(0,gameIDString.length() - 2);
                gameID = parseInt(gameIDString2);

                ResultData statusData = service.joinGame(authToken,null,gameID);
                res.status(statusData.getStatus());
                return new Gson().toJson(statusData);
            }
        }catch(Exception badRequest){
            ResultData statusData = new ResultData();
            statusData.setStatus(400);
            statusData.setMessage("Error Bad Request");
            res.status(statusData.getStatus());
            return new Gson().toJson(statusData);
        }
    }
}