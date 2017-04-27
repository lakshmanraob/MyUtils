package sheet.bottom.com.networklib.models.global;

/**
 * Created by labattula on 22/09/16.
 */

public class MyLoaderResponse<T extends MyUtilModel> {

    private T mData;
    private MyLoaderException mException;

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