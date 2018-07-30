package com.renj.mvp.mode.retrofit.exception;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-04-02   11:27
 * <p>
 * 描述：用于在 网络连接异常时抛出
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NetworkException extends IllegalStateException {
    public NetworkException() {
    }

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetworkException(Throwable cause) {
        super(cause);
    }
}
