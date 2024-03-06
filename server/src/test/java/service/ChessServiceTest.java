package service;

import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestException;
import passoffTests.testClasses.TestModels;
import server.ResultData;
import server.Server;

class ChessServiceTest {
    private String username = "newUser";
    private String password = "newPassword";
    private String email = "newemail@mail.com";
    private String authToken;
    private String gameName = "newGamePlus";
    private ChessService myService = new ChessService();
    private static TestModels.TestUser existingUser;

    private static TestModels.TestUser newUser;

    private static TestModels.TestCreateRequest createRequest;

    private static TestServerFacade serverFacade;
    private static Server server;

    private String existingAuth;

    ChessServiceTest() throws DataAccessException {
    }


    @BeforeEach
    public void init(){

        String username = "newUser";
        String password = "newPassword";
        String email = "newemail@mail.com";
        var myService = new ChessService();

    }
    @BeforeAll
    public static void init2() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);

        serverFacade = new TestServerFacade("localhost", Integer.toString(port));

        existingUser = new TestModels.TestUser();
        existingUser.username = "ExistingUser";
        existingUser.password = "existingUserPassword";
        existingUser.email = "eu@mail.com";

        newUser = new TestModels.TestUser();
        newUser.username = "NewUser";
        newUser.password = "newUserPassword";
        newUser.email = "nu@mail.com";

        createRequest = new TestModels.TestCreateRequest();
        createRequest.gameName = "testGame";
    }
    @BeforeEach
    public void setup() throws TestException {
        serverFacade.clear();

        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = existingUser.username;
        registerRequest.password = existingUser.password;
        registerRequest.email = existingUser.email;

        //one user already logged in
        TestModels.TestLoginRegisterResult regResult = serverFacade.register(registerRequest);
        existingAuth = regResult.authToken;
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    void loginTest() throws DataAccessException {

//        test invalid login credentials
        ResultData resultData = myService.login(username,password);
        int expected = 401;
        int actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);

    }

    @Test
    void loginTestPositive() throws DataAccessException{
        //        test valid
        ResultData resultData = myService.login(username,password);
        myService.register(username,password,email);
        resultData = myService.login(username,password);
        int expected = 200;
        int actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void registerTest() throws DataAccessException {


//        test invalid re re-register
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
    void registerTestPositive() throws DataAccessException{
        //        test valid
        ResultData resultData = myService.register(username,password,email);
        int expected = 200;
        int actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);

    }

    @Test
    void logoutTest() throws DataAccessException {

//        test invalid token
        ResultData resultData2 = myService.logout(authToken);
        int expected = 401;
        int actual = resultData2.getStatus();
        Assertions.assertEquals(expected,actual);

//        test valid
        ResultData resultData = myService.register(username,password,email);
        authToken = resultData.getAuthData().getAuthToken();
        resultData2 = myService.logout(authToken);
        expected = 200;
        actual = resultData2.getStatus();
        Assertions.assertEquals(expected,actual);

    }

    @Test
    void logoutTestPositive() throws DataAccessException{
//        test valid
        ResultData resultData = myService.register(username,password,email);
        authToken = resultData.getAuthData().getAuthToken();
        ResultData resultData2 = myService.logout(authToken);
        int expected = 200;
        int actual = resultData2.getStatus();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void createGameTest() throws DataAccessException {

//        test invalid game name
        ResultData resultData = myService.createGame(authToken,null);
        int expected = 400;
        int actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);

    }

    @Test
    void createGamePositive() throws DataAccessException{
        //        test valid
        ResultData resultData = myService.register(username,password,email);
        authToken = resultData.getAuthData().getAuthToken();
        resultData = myService.createGame(authToken,gameName);
        int expected = 200;
        int actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);

    }



    @Test
    void listGamesTest() throws DataAccessException {
//        test invalid game name
        ResultData resultData = myService.createGame(authToken,null);
        int expected = 400;
        int actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);

    }

    @Test
    void listGamePositive() throws DataAccessException{

        //        test valid
        ResultData resultData = myService.register(username,password,email);
        authToken = resultData.getAuthData().getAuthToken();
        resultData = myService.createGame(authToken,gameName);
        int expected = 200;
        int actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);

    }


    @Test
    void clearTest() throws DataAccessException {

        ResultData resultData = myService.register(username,password,email);
        myService.clearAll(authToken);
        int expected = 200;
        int actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);

    }


    @Test
    void joinGameTest() throws DataAccessException {

//        test wrong team color
        ResultData resultData = myService.register(username,password,email);
        authToken = resultData.getAuthData().getAuthToken();
        resultData = myService.createGame(authToken,gameName);
        int gameID = resultData.getGameData().getGameID();
        resultData = myService.joinGame(authToken,"purple",gameID);
        int expected = 500;
        int actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);

    }

    @Test
    void joinGamePositive() throws DataAccessException{

        //        test watcher ability
        ResultData resultData = myService.register(username,password,email);
        authToken = resultData.getAuthData().getAuthToken();
        resultData = myService.createGame(authToken,gameName);
        int gameID = resultData.getGameData().getGameID();
        resultData = myService.joinGame(authToken,null,gameID);
        int expected = 200;
        int actual = resultData.getStatus();
        Assertions.assertEquals(expected,actual);

    }

    @Test
    void connectionTest() throws DataAccessException {

        myService.configureDatabase();

    }

    @Test
    void clearingTableTest() throws DataAccessException {

        myService.clearAll(authToken);

    }

}