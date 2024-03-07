package dataAccessTests;

import dataAccess.DAOs.AuthDAO;
import dataAccess.DAOs.GameDAO;
import dataAccess.DAOs.SQLAuthDAO;
import dataAccess.DAOs.SQLGameDAO;
import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestModels;
import server.Server;
import service.ChessService;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class GameDAOTest {

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
    private GameDAO gameDAO;


    GameDAOTest() throws DataAccessException {
        this.gameDAO = new SQLGameDAO();
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

        GameData game = this.gameDAO.createGame(username);
        this.gameDAO.clearAll();
        assertNull(this.gameDAO.getGame(game.getGameID()));

    }

    @Test
    void updateGamePositive() throws DataAccessException {

        GameData game = this.gameDAO.createGame(username);
        assertNotNull(this.gameDAO.updateGame(game, "white", username));

    }

    @Test
    void updateGameNegative() throws DataAccessException {

        assertNull(this.gameDAO.updateGame(null, "white", username));

    }

    @Test
    void getGamePositive() throws DataAccessException {

        GameData game = this.gameDAO.createGame(username);
        assertEquals(game.getGameName(),this.gameDAO.getGame(game.getGameID()).getGameName());
        assertEquals(game.getBlackUsername(),this.gameDAO.getGame(game.getGameID()).getBlackUsername());
        assertEquals(game.getWhiteUsername(),this.gameDAO.getGame(game.getGameID()).getWhiteUsername());
        assertEquals(game.getGame(),this.gameDAO.getGame(game.getGameID()).getGame());

    }

    @Test
    void getGameNegative() {

        assertNull(this.gameDAO.getGame(0));

    }

    @Test
    void getGamesPositive() throws DataAccessException {
        HashSet<GameData> gameList = new HashSet<GameData>();
        GameData game = this.gameDAO.createGame(username);
        GameData game2 = this.gameDAO.createGame(username);
        gameList.add(game);
        gameList.add(game2);
        HashSet dbGameList = this.gameDAO.getGames();
        assertEquals(gameList.size(),dbGameList.size());

    }

    @Test
    void getGamesNegative() throws DataAccessException {

        assertTrue(this.gameDAO.getGames().isEmpty());

    }

    @Test
    void createGamePositive() throws DataAccessException {

        assertNotNull(this.gameDAO.createGame("gameName"));

    }

    @Test
    void createGameNegative() throws DataAccessException {

        assertNull(this.gameDAO.createGame(null));

    }

}