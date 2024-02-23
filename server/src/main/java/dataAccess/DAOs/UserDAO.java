package dataAccess.DAOs;

import server.UserData;

public interface UserDAO {


    void clearAll();

    UserData getUser(String username);

    UserData createUser(String username, String password, String email);

}
