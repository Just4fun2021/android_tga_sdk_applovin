package sg.just4fun.tgasdk.adsdk;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class TgaSdkException extends Exception {

    public TgaSdkException() {
    }

    public TgaSdkException(String message) {
        super(message);
    }

    public TgaSdkException(String message, Throwable cause) {
        super(message, cause);
    }

    public TgaSdkException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public TgaSdkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
