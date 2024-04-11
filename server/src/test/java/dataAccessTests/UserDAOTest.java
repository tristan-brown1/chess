package dataAccessTests;

import dataAccess.DAOs.SQLUserDAO;
import dataAccess.DAOs.UserDAO;
import dataAccess.DataAccessException;
import sharedDataClasses.UserData;
import org.junit.jupiter.api.*;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private String username = "newUser";
    private String password = "newPassword";
    private String email = "newemail@mail.com";
    private static Server server;

    private UserDAO userDAO;

    UserDAOTest() throws DataAccessException {
        this.userDAO = new SQLUserDAO();
    }

    @BeforeEach
    public void init2() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);

        TestServerFacade serverFacade = new TestServerFacade("localhost", Integer.toString(port));

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
    void clear() throws DataAccessException {
        UserData user = this.userDAO.createUser(username, password, email);
        UserData xUser = this.userDAO.createUser("xxxx","yyyyy","zzzz");
        UserData aUser = this.userDAO.createUser("aaaa","bbbb","cccc");
        this.userDAO.clear();
        assertNull(this.userDAO.getUser(username));

    }

    @Test
    void getUserPositive() throws DataAccessException {

        UserData user = this.userDAO.createUser(username, password, email);
        assertEquals(username,this.userDAO.getUser(username).getUsername());
    }

    @Test
    void getUserNegative() throws DataAccessException {

        UserData user = this.userDAO.createUser(username, password, email);
        UserData xUser = this.userDAO.createUser("xxxx","yyyyy","zzzz");
        UserData aUser = this.userDAO.createUser("aaaa","bbbb","cccc");
        assertNull(this.userDAO.getUser("thisisnotthere"));

    }

    @Test
    void createUserPositive() throws DataAccessException {

        UserData user = this.userDAO.createUser(username, password, email);
        UserData userTest = new UserData(username,password,email);
        assertEquals(userTest.getUsername(),user.getUsername());
        assertEquals(userTest.getPassword(),user.getPassword());
        assertEquals(userTest.getEmail(),user.getEmail());

    }

    @Test
    void createUserNegative() throws DataAccessException {

        assertNull(this.userDAO.createUser(null,null,null));

    }
}