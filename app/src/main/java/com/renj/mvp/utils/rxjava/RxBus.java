package com.renj.mvp.utils.rxjava;

import android.support.annotation.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-06-02   11:33
 * <p>
 * 描述：RxBus<br/>
 * 使用：<br/>
 * <pre>
 *  发送端：
 *  <code>RxBus.newInstance().post("aaa");</code><br/>
 *  接受端：
 *  <code>Disposable subscribe = RxBus.newInstance().toFlowable(String.class).subscribe(new Consumer<String>() {</code>
 *      <code>@Override</code>
 *      <code>public void accept(String s) throws Exception {</code>
 *          <code>// 处理结果</code>
 *          <code>// ...</code>
 *      <code>}</code>
 *  <code>});</code><br/>
 *  另外需要注意在<code>onDestroy()</code>方法中取消订阅：
 *  <code>@Override</code>
 *  <code>protected void onDestroy() {</code>
 *      <code>super.onDestroy();</code>
 *      <code>if (subscribe.isDisposed()) {</code>
 *          <code>subscribe.dispose();</code>
 *      <code>}</code>
 *      <code>// 如果发送了粘性事件，需要清除所有的粘性事件</code>
 *      <code>// RxBus.newInstance().clearStickyEvent();</code>
 *  <code>}</code>
 *  </pre>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RxBus {
    // RxBus实例，使用单例，并且对所有线程可见
    private static volatile RxBus instance;
    private final Subject<Object> mBus;
    // 保存所有的粘性事件的集合，线程安全的Map集合 ConcurrentHashMap
    private final Map<Class<?>, Object> mStickyEventMap;

    private RxBus() {
        mBus = PublishSubject.create().toSerialized();
        // 创建一个线程安全的HashMap
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    /**
     * 获取RxBus实例
     *
     * @return
     */
    public static RxBus newInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    /**
     * 发送普通事件
     *
     * @param obj
     */
    public void post(@NonNull Object obj) {
        mBus.onNext(obj);
    }

    /**
     * 订阅普通事件
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> Observable<T> toFlowable(@NonNull Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    /**
     * 发送粘性事件
     *
     * @param obj
     */
    public void postSticky(@NonNull Object obj) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(obj.getClass(), obj);
        }
        post(obj);
    }

    /**
     * 订阅粘性事件
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> Observable<T> toFlowableSticky(@NonNull final Class<T> tClass) {
        synchronized (mStickyEventMap) {
            Observable<T> flowable = mBus.ofType(tClass);
            if (tClass != null) {
                final Object obj = mStickyEventMap.get(tClass);
                return flowable.mergeWith(new Observable<T>() {
                    @Override
                    protected void subscribeActual(Observer<? super T> observer) {
                        observer.onNext(tClass.cast(obj));
                    }
                });
            } else {
                return flowable;
            }
        }
    }

    /**
     * 判断是否有订阅者
     *
     * @return
     */
    public boolean hasSubscribers() {
        return mBus.hasObservers();
    }

    /**
     * 根据事件类型获取一个粘性事件
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getStickyEvent(@NonNull Class<T> tClass) {
        return tClass.cast(mStickyEventMap.get(tClass));
    }

    /**
     * 根据事件类型移除一个粘性事件
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T removetStickyEvent(@NonNull Class<T> tClass) {
        synchronized (mStickyEventMap) {
            return tClass.cast(mStickyEventMap.remove(tClass));
        }
    }

    /**
     * 移除所有粘性事件
     */
    public void clearStickyEvent() {
        mStickyEventMap.clear();
    }

    /**
     * 封装默认订阅
     *
     * @param tClass   事件类型
     * @param consumer 观察者
     * @param <T>      泛型
     * @return {@link Disposable} 对象
     */
    public <T> Disposable toDefaultFlowable(Class<T> tClass, Consumer<T> consumer) {
        return mBus.ofType(tClass)
                .subscribe(consumer);
    }
}
