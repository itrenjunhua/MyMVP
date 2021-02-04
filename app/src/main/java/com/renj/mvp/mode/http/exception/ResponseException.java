package com.renj.mvp.mode.http.exception;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2021-02-04   17:08
 * <p>
 * 描述：没有获取到服务器响应
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ResponseException extends Exception {
    public ResponseException() {
        super();
    }

    public ResponseException(String message) {
        super(message);
    }

    public ResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected ResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
