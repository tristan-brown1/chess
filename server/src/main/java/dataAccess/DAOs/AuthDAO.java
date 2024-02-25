package dataAccess.DAOs;

import model.AuthData;

public interface AuthDAO{


    public void clearAll();

    public AuthData createAuth(String username);
}
