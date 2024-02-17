package service;

import dataAccess.DataAccessException;
import server.DAOs.DAO;

public class ChessService {

    private final DAO dataAccess;


    public ChessService(DAO dataAccess) {
        this.dataAccess = dataAccess;
    }
    public ChessService() {
        this.dataAccess = null;
    }

    public void clearAll() throws DataAccessException {
        if(this.dataAccess != null){
            dataAccess.clearAll();
        }
        else{
            throw new DataAccessException("dataAccess was null");
        }
    }

    public String register(String username,String password,String email) throws DataAccessException {
        if(this.dataAccess != null){
            if(dataAccess.getUser(username) == null){
                dataAccess.createUser(username,password,email);
                return dataAccess.createAuth(username);
            }
        }
        else{
            throw new DataAccessException("dataAccess was null");
        }
        return null;
    }

}
