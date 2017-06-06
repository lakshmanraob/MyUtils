package sheet.bottom.com.networklib.models.tecoutil;

/**
 * Created by labattula on 30/05/17.
 */

public class User {

    String userName;
    String password;
    String authToken;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
