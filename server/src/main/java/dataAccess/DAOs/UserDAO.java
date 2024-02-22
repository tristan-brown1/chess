package dataAccess.DAOs;

import server.UserData;

public interface UserDAO {


    public void clearAll();

    public UserData getUser(String username);

    public void createUser(String username, String password, String email);

}
