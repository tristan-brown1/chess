package dataAccess.DAOs;

import dataAccess.DataAccessException;
import model.UserData;

public class SQLUserDAO implements UserDAO{



    public void clearAll(){

    }

    @Override
    public UserData getUser(String username) {
        return null;
    }

    @Override
    public UserData createUser(String username, String password, String email) {
        return null;
    }
}
