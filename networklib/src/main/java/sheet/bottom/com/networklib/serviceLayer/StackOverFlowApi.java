package sheet.bottom.com.networklib.serviceLayer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sheet.bottom.com.networklib.models.stackexchange.StackResponse;

/**
 * Created by labattula on 22/09/16.
 */

public interface StackOverFlowApi {

    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
    Call<StackResponse> loadQuestions(@Query("tagged") String tags);
}
