package com.renj.mvp.mode.http.utils;

import com.renj.mvp.mode.bean.response.BaseResponseBean;
import com.renj.mvp.mode.http.exception.NullDataException;
import com.renj.mvp.mode.http.exception.ResponseException;
import com.renj.mvp.mode.http.exception.TokenException;
import com.renj.utils.common.Logger;
import com.renj.utils.common.UIUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-08-17   10:01
 * <p>
 * 描述：响应统一处理<br/><br/>
 * <b>说明：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 如果需要判断数据决定是否显示空状态页面时需要重写 {@link ResponseTransformer#onNullDataJudge(BaseResponseBean)} 方法并抛出 {@link NullDataException} 异常</b>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ResponseTransformer<T extends BaseResponseBean> implements ObservableTransformer<T, T> {

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.flatMap((Function<T, ObservableSource<T>>) t -> createFlowable(t));
    }

    private ObservableSource<T> createFlowable(T tResponse) {
        // 处理正确响应，访问正确，获取到了服务器返回的数据
        if (tResponse != null) {
            return responseResult(tResponse);
        } else {
            // 处理错误响应，没有获取到服务器返回的数据
            // return responseError(tResponse);
            return Observable.error(new ResponseException());
        }
    }

    /**
     * 处理正确响应，访问正确，获取到了服务器返回的数据
     */
    private ObservableSource<T> responseResult(T tResponse) {
        /**
         * 如果有其他情况需要处理，可以在这里进行统一处理，比如服务器端自定义的错误码处理等
         * 同时，如果在这里定义了 自定义的异常类，那么需要在 {@link CustomSubscriber#onError(Throwable)} 方法中进行处理
         */
        if (tResponse.code != 200) {
            UIUtils.showToast(tResponse.message);
            Logger.e("Error ResponseBody Exception(APP请求参数错误服务器返回异常信息) => status: " + tResponse.code + " ; message: " + tResponse.data);
            return Observable.error(new Exception("status: " + tResponse.code + " ; message: " + tResponse.message));
        } else if (tResponse.code == 503) {
            Logger.e("Token Exception(Token 异常) => status: " + tResponse.code + " ; message: " + tResponse.data);
            return Observable.error(new TokenException("status: " + tResponse.code + " ; message: " + tResponse.message));
        } else {
            // 响应体不为 null，进一步判断响应结果，使用RxJava发送事件
            return Observable.create(emitter -> {
                // 先对null进行判断
                onNullDataJudge(tResponse);
                try {
                    emitter.onNext(tResponse);
                    emitter.onComplete();
                } catch (Exception e) {
                    Logger.e("RxJava Send Data Exception(RxJava发送数据异常) => " + e);
                    emitter.onError(e);
                }
            });
        }
    }

    /**
     * 做页面是否显示空状态判断。<br/>
     * 这个方法主要作用是判断页面是否显示空状态，如果页面不需要对 {@link com.renj.mvpbase.view.IBaseView#showEmptyDataPage(int, Object)} 做处理，可以不用重写
     *
     * @param t 响应数据
     * @throws NullDataException 当需要显示空页面时，抛出该异常，如果不抛出该异常，那么在 {@link ResponseSubscriber} 类中不会处理
     */
    protected void onNullDataJudge(T t) throws NullDataException {
    }
}
