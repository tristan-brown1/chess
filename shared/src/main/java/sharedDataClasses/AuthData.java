package sharedDataClasses;

public class AuthData {

    private String authToken;
    private String username;

    public AuthData(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    public String getAuthToken(){
        return this.authToken;
    }

    public String getUsername() {
        return username;
    }
}
