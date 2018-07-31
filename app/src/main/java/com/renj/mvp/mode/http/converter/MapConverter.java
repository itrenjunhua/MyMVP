package com.renj.mvp.mode.http.converter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-12   16:46
 * <p>
 * 描述：用于将后台返回的json数据解析成Map集合的Converter
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MapConverter implements Converter<ResponseBody, Map<String, Object>> {
    public static final MapConverter INSTANCE = new MapConverter();

    @Override
    public Map<String, Object> convert(ResponseBody value) throws IOException {
        String string = value.string();
        try {
            JSONObject jsonObject = new JSONObject(string);
            Iterator<String> iterator = jsonObject.keys();
            Map<String, Object> result = new HashMap<>();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object keyValue = jsonObject.get(key);
                result.put(key, keyValue);
                return result;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
