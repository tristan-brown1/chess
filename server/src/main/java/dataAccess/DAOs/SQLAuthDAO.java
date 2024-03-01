package dataAccess.DAOs;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthData;
import model.UserData;

import java.sql.SQLException;
import java.util.HashMap;

public class SQLAuthDAO implements AuthDAO{

    public SQLAuthDAO() throws DataAccessException {

    }
    public void clearAll() {

    }

    @Override
    public AuthData createAuth(String username) {
        return null;
    }

    @Override
    public AuthData getAuth(String authToken) {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {

    }


}
