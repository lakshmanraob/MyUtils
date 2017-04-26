package sheet.bottom.com.networklib.models.global;

/**
 * Created by labattula on 22/09/16.
 */

public class StLoaderResponse<T> {

    private T mData;
    private StLoaderException mException;

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        this.mData = data;
    }

    public StLoaderException getException() {
        return mException;
    }

    public void setException(StLoaderException exception) {
        this.mException = exception;
    }
}
