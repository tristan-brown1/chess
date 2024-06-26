package ui;

import com.google.gson.Gson;
import exception.ResponseException;
import sharedDataClasses.UserData;
import sharedDataClasses.ResultData;
import ui.RequestClasses.CreateGameRequest;
import ui.RequestClasses.JoinGameRequest;
import ui.RequestClasses.LoginRequest;
import ui.RequestClasses.LogoutRequest;
//import exception.DataAccessException;
//import model.Pet;

import java.io.*;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;
    private String authToken = null;

    public ServerFacade(String url) {
        serverUrl = url;
    }


    public void clear(String authToken) throws ResponseException, IOException {
        String path = "/db";
        LogoutRequest logoutRequest = new LogoutRequest(authToken);
        this.makeRequest("DELETE", path, logoutRequest);

    }

    public ResultData register(String username, String password, String email) throws ResponseException, IOException {
        String path = "/user";
        UserData userData = new UserData(username,password,email);
        return this.makeRequest("POST", path, userData);

    }

    public ResultData login(String username, String password) throws ResponseException, IOException {
        String path = "/session";
        LoginRequest loginRequest = new LoginRequest(username,password);
        return this.makeRequest("POST", path, loginRequest);
    }

    public ResultData logout(String authToken) throws ResponseException, IOException {
        String path = "/session";
        this.authToken = authToken;
        LogoutRequest logoutRequest = new LogoutRequest(authToken);
        return this.makeRequest("DELETE", path, logoutRequest);
    }

    public ResultData createGame(String authToken,String newGameName) throws ResponseException, IOException {
        String path = "/game";
        this.authToken = authToken;
        CreateGameRequest createGameRequest = new CreateGameRequest(newGameName);
        return this.makeRequest("POST", path, createGameRequest);
    }

    public ResultData listGames(String authToken) throws ResponseException, IOException {
        String path = "/game";
        this.authToken = authToken;
        return this.makeRequest("GET", path, null);
    }

    public ResultData joinGame(String authToken,String playerColor,int gameID) throws ResponseException, IOException {
        String path = "/game";
        this.authToken = authToken;
        JoinGameRequest joinGameRequest = new JoinGameRequest(playerColor,gameID);
        return this.makeRequest("PUT", path, joinGameRequest);
    }

    private <T> T makeRequest(String method, String path, Object request) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            http.addRequestProperty("authorization", authToken);

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, (Class<T>)ResultData.class);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());

        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}

