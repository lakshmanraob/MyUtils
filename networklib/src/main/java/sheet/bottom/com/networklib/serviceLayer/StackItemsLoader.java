package sheet.bottom.com.networklib.serviceLayer;

import android.content.Context;

import retrofit2.Call;
import sheet.bottom.com.networklib.models.global.StLoaderException;
import sheet.bottom.com.networklib.models.global.StLoaderResponse;
import sheet.bottom.com.networklib.models.stackexchange.StackResponse;
import sheet.bottom.com.networklib.serviceLayer.myRetrofit.MyRetroFitLib;

/**
 * Created by labattula on 22/09/16.
 */

public class StackItemsLoader extends HttpTaskLoader<StLoaderResponse<StackResponse>> {

    private String queryParam;

    private final String BASE_URL = "https://api.stackexchange.com";

    private Context mContext;

    public StackItemsLoader(Context context, String query) {
        super(context);
        this.queryParam = query;
        this.mContext = context;
    }

    @Override
    protected StLoaderResponse<StackResponse> loadDataInBackground() throws StLoaderException {

        Call<StackResponse> stackCall = MyRetroFitLib.getAuthRetrofit(BASE_URL).create(StackOverFlowApi.class).loadQuestions(queryParam);

        return MyRetroFitLib.buildReponse(stackCall);

    }

    @Override
    protected StLoaderResponse<StackResponse> buildEmptyResult() {
        StLoaderException.Builder errorBuilder = new StLoaderException.Builder();
        StLoaderResponse response = new StLoaderResponse();
        response.setData(null);
        errorBuilder.setErrorCode(200);
        errorBuilder.setDetailMessage("EmptyMessage");
        response.setException(errorBuilder.build());
        return response;
    }



}
