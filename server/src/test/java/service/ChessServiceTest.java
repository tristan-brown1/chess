package service;

import dataAccess.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ResultData;

import static org.junit.jupiter.api.Assertions.*;

class ChessServiceTest {
    private String username = "newUser";
    private String password = "newPassword";
    private String email = "newemail@mail.com";
    private String authToken;
    private String gameName = "newGamePlus";
    private ChessService myService = new ChessService();


    @BeforeEach
    public void init(){
        String username = "newUser";
        String password = "newPassword";
        String email = "newemail@mail.com";
        var myService = new ChessService();
    }

    @Test
    void login() throws DataAccessException {

        ResultData resultData = myService.login(username,password);
        int expected = 401;
        int actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);

        myService.register(username,password,email);
        resultData = myService.login(username,password);
        expected = 200;
        actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void register() throws DataAccessException {

        ResultData resultData = myService.register(username,password,email);
        int expected = 200;
        int actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);

        resultData = myService.register(username,password,email);
        expected = 403;
        actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void logout() throws DataAccessException {

        ResultData resultData2 = myService.logout(authToken);
        int expected = 401;
        int actual = resultData2.getStatus();
        Assertions.assertEquals(expected,actual);

        ResultData resultData = myService.register(username,password,email);
        authToken = resultData.getAuthData().getAuthToken();
        resultData2 = myService.logout(authToken);
        expected = 200;
        actual = resultData2.getStatus();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void createGame() throws DataAccessException {

        authToken = "urmom";
        ResultData resultData = myService.createGame(authToken,gameName);
        int expected = 401;
        int actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);


        resultData = myService.register(username,password,email);
        authToken = resultData.getAuthData().getAuthToken();
        resultData = myService.createGame(authToken,gameName);
        expected = 200;
        actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);

        resultData = myService.createGame(authToken,null);
        expected = 400;
        actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);

    }

    @Test
    void listGames() throws DataAccessException {

        ResultData resultData = myService.register(username,password,email);
        authToken = resultData.getAuthData().getAuthToken();
        resultData = myService.createGame(authToken,gameName);
        int expected = 200;
        int actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);

        resultData = myService.createGame(authToken,null);
        expected = 400;
        actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);

    }


    @Test
    void clear() throws DataAccessException {

    }


}