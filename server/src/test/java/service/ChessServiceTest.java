package service;

import dataAccess.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.ResultData;

import static org.junit.jupiter.api.Assertions.*;

class ChessServiceTest {

    @Test
    void login() throws DataAccessException {
        String username = "newUser";
        String password = "newPassword";
        String email = "newemail@mail.com";
        var myService = new ChessService();
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

}