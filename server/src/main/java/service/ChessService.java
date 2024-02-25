package service;

import dataAccess.DAOs.*;
import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import server.ResultData;
import model.UserData;

public class ChessService {

    //    private final DAO dataAccess;
    private final MemoryAuthDAO authDAO;
    private final MemoryUserDAO userDAO;
    private final MemoryGameDAO gameDAO;


    //    public ChessService() {
//        this.dataAccess = dataAccess;
//    }
    public ChessService() {
        this.authDAO = new MemoryAuthDAO();
        this.userDAO = new MemoryUserDAO();
        this.gameDAO = new MemoryGameDAO();

    }

    public void clearAll() throws DataAccessException {
//        if(this.dataAccess != null){
        this.authDAO.clearAll();
        this.userDAO.clearAll();
        this.gameDAO.clearAll();
//        }
//        else{
//            throw new DataAccessException("dataAccess was null");
//        }
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
                resultData.setMessage("Error: Incorrect Password");
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
        if(authData == null){
            resultData.setStatus(401);
            resultData.setMessage("Error: bad request");
            return resultData;
        }
        if(playerColor == null){
            GameData gameData = gameDAO.getGame(gameID);
            resultData.setStatus(200);
            resultData.setGameData(gameData);
            return resultData;
        }
        else{
            if(playerColor.equals("BLACK") || playerColor.equals("WHITE")){
                GameData gameData = gameDAO.getGame(gameID);
                if (playerColor.equals("BLACK")) {
                    if(gameData.getBlackUsername() != null){
                        resultData.setStatus(403);
                        resultData.setMessage("Error Forbidden");
                        return resultData;
                    }
                }
                else if(gameData.getWhiteUsername() != null){
                    resultData.setStatus(403);
                    resultData.setMessage("Error Forbidden");
                    return resultData;
                }
                gameDAO.updateGame(gameData, playerColor, authData.getUsername());
                resultData.setStatus(200);
                resultData.setGameData(gameData);
                return resultData;
            }
            else{
                resultData.setStatus(500);
                resultData.setMessage("Error Wrong Color");
                return resultData;
            }
        }



    }

//    public ResultData joinGame(String authToken, String playerColor, int gameID) throws DataAccessException {
//        ResultData resultData = new ResultData();
//        if(authToken == null){
//            resultData.setStatus(400);
//            resultData.setMessage("Error: bad request");
//            return resultData;
//        }
//        else{
//            AuthData authData = authDAO.getAuth(authToken);
//            if(authData != null){
//                GameData gameData = gameDAO.getGame(gameID);
//                if((gameData != null) && (playerColor.toLowerCase().contains("white") || playerColor.toLowerCase().contains("black"))){
//                    if(gameData.getBlackUsername() == null){
//                        if(playerColor.toLowerCase().contains("black")){
//                            gameDAO.updateGame(gameData, playerColor, authData.getUsername());
//                            resultData.setStatus(200);
//                            resultData.setGameData(gameData);
//                            return resultData;
//                        }
////                        else{
////                            resultData.setStatus(403);
////                            resultData.setMessage("Error Forbidden");
////                            return resultData;
////                        }
//                    }
//                    else if(gameData.getWhiteUsername() == null){
//                        if((playerColor.toLowerCase().contains("white"))){
//                            gameDAO.updateGame(gameData, playerColor, authData.getUsername());
//                            resultData.setStatus(200);
//                            resultData.setGameData(gameData);
//                            return resultData;
//                        }
////                        else{
////                            resultData.setStatus(403);
////                            resultData.setMessage("Error Forbidden");
////                            return resultData;
////                        }
//                    }
//                    else{
//                        resultData.setStatus(403);
//                        resultData.setMessage("Error Forbidden");
//                        return resultData;
//                    }
//                }
//                else {
//                    resultData.setStatus(400);
//                    resultData.setMessage("Error Forbidden");
//                    return resultData;
//                }
//            }
//            else {
//                resultData.setStatus(401);
//                resultData.setMessage("Error: Unauthorized");
//                return resultData;
//            }
//        }
//        resultData.setStatus(403);
//        resultData.setMessage("Error Forbidden");
//        return resultData;
//    }



}
