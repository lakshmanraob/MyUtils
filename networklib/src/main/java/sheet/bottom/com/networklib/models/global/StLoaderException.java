package sheet.bottom.com.networklib.models.global;

/**
 * Created by labattula on 22/09/16.
 */

public class StLoaderException extends RuntimeException {

    private static final int DEFAULT_CODE = 100;
    private int mCode;
    private String detailMessage;

    private StLoaderException(int code, String detailMessage) {
        super(detailMessage);
        mCode = code;
    }

    public static class Builder {

        private int code = -1;
        private String detailMessage;

        public Builder setErrorCode(int errorCode) {
            this.code = errorCode;
            return this;
        }

        public Builder setDetailMessage(String detailMessage) {
            this.detailMessage = detailMessage;
            return this;
        }

        public StLoaderException build() {
            return new StLoaderException(code == -1 ? DEFAULT_CODE : code, detailMessage);
        }
    }

}
