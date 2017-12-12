package com.renj.mvp.retrofit.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-12   16:50
 * <p>
 * 描述：用于创建 MapConverter 的工厂类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MapConverterFactory extends Converter.Factory {
    public static final MapConverterFactory INSTANCE = new MapConverterFactory();

    public static MapConverterFactory create() {
        return INSTANCE;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == Map.class)
            return MapConverter.INSTANCE;
        return super.responseBodyConverter(type, annotations, retrofit);
    }
}
