package my.util.app.models;

import my.util.app.network.global.MyUtilModel;

public class DJavaClass extends MyUtilModel {

    LoginResult[] results;

    public LoginResult[] getResults() {
        return results;
    }

    public void setResults(LoginResult[] results) {
        this.results = results;
    }
}
