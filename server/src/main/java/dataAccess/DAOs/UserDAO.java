package dataAccess.DAOs;

import model.UserData;

public interface UserDAO {


    void clearAll();

    UserData getUser(String username);

    UserData createUser(String username, String password, String email);

}
