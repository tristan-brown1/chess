package service;

import dataAccess.DAOs.*;
import dataAccess.DataAccessException;
import org.eclipse.jetty.server.Authentication;
import server.AuthData;
import server.ResultData;
import server.UserData;

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




}
