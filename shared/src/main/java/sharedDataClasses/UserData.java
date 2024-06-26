package sharedDataClasses;

public class UserData {

    private String username;
    private String password;
    private String email;

    public UserData(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
