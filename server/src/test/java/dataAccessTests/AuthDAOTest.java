package dataAccessTests;

import dataAccess.DAOs.AuthDAO;
import dataAccess.DAOs.SQLAuthDAO;
import dataAccess.DAOs.SQLUserDAO;
import dataAccess.DAOs.UserDAO;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestModels;
import server.ResultData;
import server.Server;
import service.ChessService;

import static org.junit.jupiter.api.Assertions.*;

class AuthDAOTest {

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
    private AuthDAO authDAO;


    AuthDAOTest() throws DataAccessException {
        this.authDAO = new SQLAuthDAO();
    }

    @BeforeEach
    public void init2() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);

        serverFacade = new TestServerFacade("localhost", Integer.toString(port));

        String username = "newUser";
        String password = "newPassword";
        String email = "newemail@mail.com";
        serverFacade.clear();
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @Test
    void clearAll() throws DataAccessException {

        AuthData auth = this.authDAO.createAuth(username);
        this.authDAO.clearAll();
        assertNull(this.authDAO.getAuth(auth.getAuthToken()));

    }

    @Test
    void createAuthPositive() throws DataAccessException {

        assertNotNull(this.authDAO.createAuth(username));

    }

    @Test
    void createAuthNegative() throws DataAccessException {

        assertNull(this.authDAO.createAuth(null));

    }

    @Test
    void getAuthPositive() throws DataAccessException {
        AuthData authData = this.authDAO.createAuth(username);
        String setToken = authData.getAuthToken();
        assertEquals(authData.getUsername(),this.authDAO.getAuth(setToken).getUsername());
        assertEquals(authData.getAuthToken(),this.authDAO.getAuth(setToken).getAuthToken());
    }

    @Test
    void getAuthNegative() throws DataAccessException {

        assertNull(this.authDAO.getAuth("thisisnotthere"));

    }

    @Test
    void deleteAuthPositive() throws DataAccessException {

        AuthData authData = this.authDAO.createAuth(username);
        this.authDAO.deleteAuth(authData.getAuthToken());
        assertNull(this.authDAO.getAuth(authData.getAuthToken()));
    }

    @Test
    void deleteAuthNegative() throws DataAccessException {

        AuthData authData = this.authDAO.createAuth(username);
        this.authDAO.deleteAuth("thisisnotinthere");
        assertNotNull(this.authDAO.getAuth(authData.getAuthToken()));

    }

}