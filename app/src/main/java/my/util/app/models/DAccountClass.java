package my.util.app.models;

import my.util.app.network.global.MyUtilModel;

public class DAccountClass extends MyUtilModel {

    AccountResult[] results;

    public AccountResult[] getResults() {
        return results;
    }

    public void setResults(AccountResult[] results) {
        this.results = results;
    }
}
