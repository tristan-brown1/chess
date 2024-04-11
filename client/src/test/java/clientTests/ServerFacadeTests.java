package clientTests;

import exception.ResponseException;
import org.junit.jupiter.api.*;
import sharedDataClasses.ResultData;
import server.Server;
import ui.ServerFacade;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;



    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        var serverUrl = "http://localhost:" + port;
        serverFacade = new ServerFacade(serverUrl);
    }

    @BeforeEach
    public void clearServerFacade() throws ResponseException, IOException {
        serverFacade.clear("");
    }


    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void clearTest() throws ResponseException, IOException {


    }

    @Test
    public void registerTestPos() throws ResponseException, IOException {

        ResultData resultData = serverFacade.register("u","p","e");

        Assertions.assertEquals(200,resultData.getStatus());
    }
    @Test
    public void registerTestNeg() throws ResponseException, IOException {

        serverFacade.register("u","p","e");
        try {
            serverFacade.register("u","p","e");
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertNotNull(e);
        }
//        ResultData resultData = serverFacade.register("u","p","e");
//        Assertions.assertThrows(ResponseException.class, serverFacade.register("WRONG","f","f"));
//        Assertions.assertThrows(ResponseException.class,serverFacade.register(""));

    }

    @Test
    public void loginTestPos() throws ResponseException, IOException {
        serverFacade.register("u","p","e");
        ResultData resultData = serverFacade.login("u","p");
        Assertions.assertEquals(200,resultData.getStatus());

    }
    @Test
    public void loginTestNeg() throws ResponseException, IOException {

        try {
            serverFacade.login("u","p");
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void logoutTestPos() throws ResponseException, IOException {
        serverFacade.register("u","p","e");
        ResultData tempData = serverFacade.login("u","p");
        ResultData resultData = serverFacade.logout(tempData.getAuthData().getAuthToken());
        Assertions.assertEquals(200,resultData.getStatus());
    }
    @Test
    public void logoutTestNeg() {

        try {
            serverFacade.logout("wrongauth");
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void createGameTestPos() throws ResponseException, IOException {
        serverFacade.register("u","p","e");
        ResultData tempData = serverFacade.login("u","p");
        ResultData resultData = serverFacade.createGame(tempData.getAuthData().getAuthToken(),"gamename");
        Assertions.assertEquals(200,resultData.getStatus());
    }

    @Test
    public void createGameTestNeg() throws ResponseException, IOException {
        try {
            serverFacade.createGame("wrongauth","newGame");
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void listGamesTestPos() throws ResponseException, IOException {
        serverFacade.register("u","p","e");
        ResultData tempData = serverFacade.login("u","p");
        serverFacade.createGame(tempData.getAuthData().getAuthToken(),"gamename");
        ResultData resultData = serverFacade.listGames(tempData.getAuthData().getAuthToken());
        Assertions.assertEquals(200,resultData.getStatus());
    }
    @Test
    public void listGamesTestNeg() {
        try {
            serverFacade.listGames("wrongauth");
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }


    @Test
    public void joinGameTestPos() throws ResponseException, IOException {
        serverFacade.register("u","p","e");
        ResultData tempData = serverFacade.login("u","p");
        ResultData gameInfo = serverFacade.createGame(tempData.getAuthData().getAuthToken(),"gamename");
        ResultData resultData = serverFacade.joinGame(tempData.getAuthData().getAuthToken(),"white",gameInfo.getGameData().getGameID());
        Assertions.assertEquals(200,resultData.getStatus());
    }
    @Test
    public void joinGameTestNeg() throws ResponseException, IOException {
        serverFacade.register("u","p","e");
        ResultData tempData = serverFacade.login("u","p");
        ResultData gameInfo = serverFacade.createGame(tempData.getAuthData().getAuthToken(),"gamename");
        try {
            serverFacade.joinGame("wrongauth","white",44444);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }


}

