package dataAccess.DAOs;

import server.AuthData;

public interface AuthDAO{


    public void clearAll();

    public AuthData createAuth(String username);
}
