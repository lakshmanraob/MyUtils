package sheet.bottom.com.networklib.serviceLayer.loaders;

import android.content.Context;

import retrofit2.Call;
import sheet.bottom.com.networklib.models.global.MyLoaderException;
import sheet.bottom.com.networklib.models.global.MyLoaderResponse;
import sheet.bottom.com.networklib.models.global.MyUtilModel;
import sheet.bottom.com.networklib.models.stackexchange.StackResponse;
import sheet.bottom.com.networklib.serviceLayer.HttpTaskLoader;
import sheet.bottom.com.networklib.serviceLayer.apicalls.StackOverFlowApi;
import sheet.bottom.com.networklib.serviceLayer.myRetrofit.MyRetroFitLib;

/**
 * Created by labattula on 22/09/16.
 */

public class StackItemsLoader extends HttpTaskLoader<MyLoaderResponse<StackResponse>> {

    private String queryParam;

    private final String BASE_URL = "https://api.stackexchange.com";

    private Context mContext;

    public StackItemsLoader(Context context, String query) {
        super(context);
        this.queryParam = query;
        this.mContext = context;
    }

    @Override
    protected MyLoaderResponse<StackResponse> loadDataInBackground() throws MyLoaderException {

        Call<StackResponse> stackCall = MyRetroFitLib.getAuthRetrofit(BASE_URL).create(StackOverFlowApi.class).loadQuestions(queryParam);

        return MyRetroFitLib.buildReponse(stackCall);

    }

    @Override
    protected MyLoaderResponse<StackResponse> buildEmptyResult() {
        MyLoaderException.Builder errorBuilder = new MyLoaderException.Builder();
        MyLoaderResponse response = new MyLoaderResponse();
        response.setData(null);
        errorBuilder.setErrorCode(200);
        errorBuilder.setDetailMessage("EmptyMessage");
        response.setException(errorBuilder.build());
        return response;
    }



}
