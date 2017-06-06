package sheet.bottom.com.networklib.models.tecoutil;

import sheet.bottom.com.networklib.models.global.MyUtilModel;

/**
 * Created by labattula on 30/05/17.
 */

public class Results extends MyUtilModel {

    LoginResult[] results;

    public LoginResult[] getResults() {
        return results;
    }

    public void setResults(LoginResult[] results) {
        this.results = results;
    }
}
