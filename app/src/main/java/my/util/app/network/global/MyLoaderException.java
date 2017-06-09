package my.util.app.network.global;

public class MyLoaderException extends RuntimeException {

    private static final int DEFAULT_CODE = 100;
    private int mCode;
    private String detailMessage;

    private MyLoaderException(int code, String detailMessage) {
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

        public MyLoaderException build() {
            return new MyLoaderException(code == -1 ? DEFAULT_CODE : code, detailMessage);
        }
    }

}
