package com.renj.mvp.mode.http.exception;

import android.support.annotation.NonNull;

import com.renj.mvp.mode.bean.BaseResponseBean;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-08-17   10:07
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NullDataException extends Exception {
    private Object baseResponseBean;

    public <T extends BaseResponseBean> NullDataException(@NonNull T baseResponseBean) {
        this.baseResponseBean = baseResponseBean;
    }

    public <T extends BaseResponseBean> T getBaseResponseBean() {
        return (T) baseResponseBean;
    }
}
