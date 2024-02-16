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
}
