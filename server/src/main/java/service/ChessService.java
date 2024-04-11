package service;

import dataAccess.DAOs.*;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import sharedDataClasses.AuthData;
import sharedDataClasses.GameData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sharedDataClasses.ResultData;
import sharedDataClasses.UserData;

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

    public ResultData clearAll(String authToken) throws DataAccessException {
            this.authDAO.clearAll();
            this.userDAO.clear();
            this.gameDAO.clearAll();
            configureDatabase();
            ResultData resultData = new ResultData();
            resultData.setStatus(200);
            return resultData;
    }

    public ResultData register(String username, String password, String email) throws DataAccessException {
        String hashedPassword = encodePassword(password);
        ResultData resultData = new ResultData();
        UserData userData = userDAO.getUser(username);
        if(userData == null) {
            resultData.setUserData(this.userDAO.createUser(username, hashedPassword, email));
            resultData.setAuthData(this.authDAO.createAuth(username));
            resultData.setStatus(200);
        }
        else{
            resultData.setStatus(403);
            resultData.setMessage("Error: Already Taken");
        }
        return resultData;
    }

    private static String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
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
            if(verifyUser(userData,password)){
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
            CREATE TABLE IF NOT EXISTS `user` (
              `username` VARCHAR(256) NOT NULL,
              `password` VARCHAR(256) NOT NULL,
              `email` VARCHAR(256) NOT NULL,
              PRIMARY KEY (`username`));
            """
            ,
            """
            CREATE TABLE IF NOT EXISTS `game` (
              `gameName` VARCHAR(256) NOT NULL,
              `whiteUsername` VARCHAR(256) NULL,
              `blackUsername` VARCHAR(256) NULL,
              `game` LONGTEXT NOT NULL,
              `gameID` INT NOT NULL,
              PRIMARY KEY (`gameID`));
            """
            ,
            """
            CREATE TABLE IF NOT EXISTS `auth` (
              `username` VARCHAR(256) NOT NULL,
              `authToken` VARCHAR(256) NOT NULL,
              PRIMARY KEY (`authToken`));
            
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

    boolean verifyUser(UserData userData, String providedClearTextPassword) {
        // read the previously hashed password from the database
        var hashedPassword = userData.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(providedClearTextPassword, hashedPassword);
    }
}
