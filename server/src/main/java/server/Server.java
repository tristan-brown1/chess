package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.ChessService;
import spark.*;

public class Server {
    private final ChessService service;


    public Server(ChessService service) {
        this.service = service;
    }
    public Server() {
        this.service = null;
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.


//        register handler


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

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object clearAppHandler(Request req, Response res) throws DataAccessException {
        if (service != null){
            service.clearAll();
        }
        else {
            throw new DataAccessException("Service was null");
        }
        res.status(204);
        return "";
    }

}
