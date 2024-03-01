package service;

import dataAccess.DAOs.*;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthData;
import model.GameData;
import server.ResultData;
import model.UserData;

import java.sql.SQLException;

public class ChessService {

    private  AuthDAO authDAO;
    private  UserDAO userDAO;
    private  GameDAO gameDAO;


    public ChessService() {

        try{
            configureDatabase();
            this.authDAO = new SQLAuthDAO();
            this.userDAO = new SQLUserDAO();
            this.gameDAO = new SQLGameDAO();
        }catch(DataAccessException e){
            this.authDAO = new MemoryAuthDAO();
            this.userDAO = new MemoryUserDAO();
            this.gameDAO = new MemoryGameDAO();
        }
    }

    public void clearAll() throws DataAccessException {
        this.authDAO.clearAll();
        this.userDAO.clearAll();
        this.gameDAO.clearAll();
    }

    public ResultData register(String username, String password, String email) throws DataAccessException {
        ResultData resultData = new ResultData();
        UserData userData = userDAO.getUser(username);
        if(userData == null) {
            resultData.setUserData(this.userDAO.createUser(username, password, email));
            resultData.setAuthData(this.authDAO.createAuth(username));
            resultData.setStatus(200);
        }
        else{
            resultData.setStatus(403);
            resultData.setMessage("Error: Already Taken");
        }
        return resultData;
    }

    public ResultData login(String username, String password) throws DataAccessException {
        ResultData resultData = new ResultData();
        UserData userData = userDAO.getUser(username);
        if(userData == null) {
            resultData.setStatus(401);
            resultData.setMessage("Error: Unauthorized, User has not yet been created");
            return resultData;
        }
        else{
            if(password.equals(userData.getPassword())){
                AuthData authData = authDAO.createAuth(username);
                resultData.setAuthData(authData);
                resultData.setUserData(userData);
                resultData.setStatus(200);
                return resultData;
            }
            else {
                resultData.setStatus(401);
                resultData.setMessage("Error: Unauthorized, Incorrect Password");
                return resultData;
            }
        }
    }

    public ResultData logout(String authToken) throws DataAccessException {
        ResultData resultData = new ResultData();
        if(authDAO.getAuth(authToken) != null){
            authDAO.deleteAuth(authToken);
            resultData.setStatus(200);
        }
        else {
            resultData.setStatus(401);
            resultData.setMessage("Error: Unauthorized");
        }
        return resultData;
    }

    public ResultData createGame(String authToken, String gameName) throws DataAccessException {
        ResultData resultData = new ResultData();
        if(authToken == null || gameName == null){
            resultData.setStatus(400);
            resultData.setMessage("Error: bad request");
        }
        else{
            if(authDAO.getAuth(authToken) != null){
                resultData.setGameData(gameDAO.createGame(gameName));
                resultData.setStatus(200);
            }
            else {
                resultData.setStatus(401);
                resultData.setMessage("Error: Unauthorized");
            }
        }
        return resultData;
    }

    public ResultData listGames(String authToken) throws DataAccessException {
        ResultData resultData = new ResultData();
        if(authToken == null){
            resultData.setStatus(400);
            resultData.setMessage("Error: bad request");
        }
        else{
            if(authDAO.getAuth(authToken) != null){
                resultData.setGameSet(gameDAO.getGames());
                resultData.setStatus(200);
            }
            else {
                resultData.setStatus(401);
                resultData.setMessage("Error: Unauthorized");
            }
        }
        return resultData;
    }

    public ResultData joinGame(String authToken, String playerColor, int gameID) throws DataAccessException {
        ResultData resultData = new ResultData();
        AuthData authData = authDAO.getAuth(authToken);

//        check authorization
        if(authData == null){
            resultData.setStatus(401);
            resultData.setMessage("Error: Unauthorized");
            return resultData;
        }

//        check for watcher status
        if(playerColor == null){
            GameData gameData = gameDAO.getGame(gameID);
            resultData.setStatus(200);
            resultData.setGameData(gameData);
            return resultData;
        }
        else{
//            set username
            if(playerColor.equalsIgnoreCase("black") || playerColor.equalsIgnoreCase("white")){
                GameData gameData = gameDAO.getGame(gameID);
                if (playerColor.equalsIgnoreCase("black")) {
                    if(gameData.getBlackUsername() != null){
                        resultData.setStatus(403);
                        resultData.setMessage("Error: Already Taken");
                        return resultData;
                    }
                }
                else if(gameData.getWhiteUsername() != null){
                    resultData.setStatus(403);
                    resultData.setMessage("Error: Already Taken");
                    return resultData;
                }
                gameDAO.updateGame(gameData, playerColor, authData.getUsername());
                resultData.setStatus(200);
                resultData.setGameData(gameData);
                return resultData;
            }
            else{
                resultData.setStatus(500);
                resultData.setMessage("Error: description");
                return resultData;
            }
        }
    }

    private final String[] createStatements = {

            """
            CREATE TABLE IF NOT EXISTS  user (
              `user_id_num` int NOT NULL AUTO_INCREMENT,
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`user_id_num`),
              CONSTRAINT `usernamer` FOREIGN KEY (`username`),
              INDEX(username)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
            ,
            """
            CREATE TABLE IF NOT EXISTS  game (
              `game_id_num` int NOT NULL AUTO_INCREMENT,
              `username` varchar(256) NOT NULL,
              `gameName` varchar(256) NOT NULL,
              `gameData` varchar(256) NOT NULL,
              `gameID` int NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`game_id_num`),
              INDEX(username)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
            ,
            """
            CREATE TABLE IF NOT EXISTS  auth (
              `auth_id_num` int NOT NULL AUTO_INCREMENT,
              `username` varchar(256) NOT NULL,
              `authToken` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`auth_id_num`),
              INDEX(username)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """

    };

    public void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
}
