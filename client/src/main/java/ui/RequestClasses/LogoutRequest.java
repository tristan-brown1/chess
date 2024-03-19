package ui.RequestClasses;

public class LogoutRequest {

    String authorization;

    public LogoutRequest(String authToken) {
        this.authorization = authToken;
    }

}
