package sheet.bottom.com.networklib.models.global;

import okhttp3.Headers;

public class MyLoaderResponse<T extends MyUtilModel> {

    private T mData;
    private MyLoaderException mException;
    private Headers mHeaders;

    public Headers getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Headers mHeaders) {
        this.mHeaders = mHeaders;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        this.mData = data;
    }

    public MyLoaderException getException() {
        return mException;
    }

    public void setException(MyLoaderException exception) {
        this.mException = exception;
    }
}
