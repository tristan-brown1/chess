package service;

import dataAccess.DAOs.*;
import dataAccess.DataAccessException;
import org.eclipse.jetty.server.Authentication;
import server.AuthData;
import server.ResultData;
import server.UserData;

public class ChessService {

    //    private final DAO dataAccess;
    private final AuthDAO authDAO;
    private final UserDAO userDAO;
    private final GameDAO gameDAO;


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

    public AuthData register(String username, String password, String email) throws DataAccessException {
//        if(this.dataAccess != null){
        if (this.userDAO.getUser(username) == null) {
            this.userDAO.createUser(username, password, email);
            return authDAO.createAuth(username);
        }
//        }
        else {
            return null;
//            throw new DataAccessException("dataAccess was null");
        }

    }

    public ResultData login(String username, String password) throws DataAccessException {
        ResultData resultData = new ResultData();
        AuthData authData = authDAO.createAuth(username);
        UserData userData = userDAO.getUser(username);
        if (userData == null) {
            resultData.setStatus(401);
            resultData.setMessage("Error: Unauthorized, User has not yet been created");
            return resultData;
        }
        else {
            if(password.equals(userData.getPassword())){
                resultData.setAuthData(authData);
                resultData.setUserData(userData);
                return resultData;
            }
            else {
                resultData.setStatus(401);
                resultData.setMessage("Error: Incorrect Password");
            }
            return resultData;
        }
    }




}
