package com.renj.mvp.mode.http.utils;

import com.renj.common.utils.Logger;
import com.renj.common.utils.UIUtils;
import com.renj.mvp.mode.bean.BaseResponseBean;
import com.renj.mvp.mode.http.exception.NullDataException;
import com.renj.mvp.mode.http.exception.TokenException;
import com.renj.mvpbase.mode.MvpBaseRB;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;
import retrofit2.HttpException;
import retrofit2.Response;

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
public class ResponseTransformer<T extends BaseResponseBean> implements FlowableTransformer<Response<T>, T> {
    @Override
    public Flowable<T> apply(Flowable upstream) {
        return upstream.flatMap((Function<Response<T>, Flowable<T>>) tResponse -> createFlowable(tResponse));
    }

    private Flowable<T> createFlowable(Response<T> tResponse) {
        // 处理正确响应，访问正确，获取到了服务器返回的数据
        if (tResponse != null && tResponse.isSuccessful() && tResponse.code() == 200) {
            return responseResult(tResponse);
        } else {
            // 处理错误响应，没有获取到服务器返回的数据
            // return responseError(tResponse);
            return Flowable.error(new HttpException(tResponse));
        }
    }

    /**
     * 处理正确响应，访问正确，获取到了服务器返回的数据
     */
    private Flowable<T> responseResult(Response<T> tResponse) {
        final T body = tResponse.body();
        if (body == null) {
            // 整个响应体为 null，连 code 和 message 字段都没有，表示出错
            Logger.e("Response Body is null Exception(响应体为空异常) => 服务器没有返回任何数据，包括状态信息。");
            return Flowable.error(new IllegalStateException("Response Body is null Exception(响应体为空异常)"));
        }
        /**
         * 如果有其他情况需要处理，可以在这里进行统一处理，比如服务器端自定义的错误码处理等
         * 同时，如果在这里定义了 自定义的异常类，那么需要在 {@link CustomSubscriber#onError(Throwable)} 方法中进行处理
         */
        else if (body.code != 200) {
            UIUtils.showToastSafe(body.message);
            Logger.e("Error ResponseBody Exception(APP请求参数错误服务器返回异常信息) => status: " + body.code + " ; message: " + body.data);
            return Flowable.error(new Exception("status: " + body.code + " ; message: " + body.message));
        } else if (body.code == 503) {
            Logger.e("Token Exception(Token 异常) => status: " + body.code + " ; message: " + body.data);
            return Flowable.error(new TokenException("status: " + body.code + " ; message: " + body.message));

        } else {
            // 响应体不为 null，进一步判断响应结果，使用RxJava发送事件
            return Flowable.create((emitter) -> {
                // 先对null进行判断
                onNullDataJudge(body);
                try {
                    emitter.onNext(body);
                    emitter.onComplete();
                } catch (Exception e) {
                    Logger.e("RxJava Send Data Exception(RxJava发送数据异常) => " + e);
                    emitter.onError(e);
                }
            }, BackpressureStrategy.BUFFER);
        }
    }

    /**
     * 处理错误响应，没有获取到服务器返回的数据
     */
    private Flowable<T> responseError(Response<T> tResponse) {
        // 当服务器返回错误时进行统一错误处理
        return Flowable.error(new HttpException(tResponse));
    }

    /**
     * 做页面是否显示空状态判断。<br/>
     * 这个方法主要作用是判断页面是否显示空状态，如果页面不需要对 {@link com.renj.mvpbase.view.IBaseView#showEmptyDataPage(int, int, MvpBaseRB)} 做处理，可以不用重写
     *
     * @param t 响应数据
     * @throws NullDataException 当需要显示空页面时，抛出该异常，如果不抛出该异常，那么在 {@link CustomSubscriber} 类中不会处理
     */
    protected void onNullDataJudge(T t) throws NullDataException {
    }
}
