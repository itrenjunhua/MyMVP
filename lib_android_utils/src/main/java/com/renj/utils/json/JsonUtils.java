package com.renj.utils.json;

import android.support.annotation.NonNull;

import com.renj.utils.res.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2018-04-20   16:29
 * <p>
 * 描述：Json 操作类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class JsonUtils {

    /**
     * json 字符串 变为 {@link Map<String,Object>}
     *
     * @param json json 字符串
     * @return 转变后的 {@link Map<String,Object>} 对象
     */
    @NonNull
    public static Map<String, Object> jsonToMap(String json) {
        if (StringUtils.isEmpty(json)) return new HashMap<>();

        if (!json.startsWith("{") || !json.endsWith("}")) return new HashMap<>();

        Map<String, Object> result = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                result.put(next, jsonObject.get(next));
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return result;
        }
    }

    /**
     * {@link Map<String,Object>} 变为 {@link JSONObject}
     *
     * @param map {@link Map<String,Object>} 对象
     * @return 转变后的 {@link JSONObject} 对象
     */
    @NonNull
    public static JSONObject mapToJsonObject(Map<String, Object> map) {
        JSONObject result = new JSONObject();

        if (map == null) return result;

        Set<String> strings = map.keySet();
        try {
            for (String string : strings) {
                result.put(string, map.get(string));
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * {@link Map<String,Object>} 变为 {@link String}
     *
     * @param map {@link Map<String,Object>} 对象
     * @return 转变后的 {@link String}
     */
    @NonNull
    public static String mapToJson(Map<String, Object> map) {
        return mapToJsonObject(map).toString();
    }

    /**
     * json 字符串 变为 {@link List<Map<String,Object>>}
     *
     * @param json json 字符串
     * @return 转变后的 {@link List<Map<String,Object>>} 对象
     */
    @NonNull
    public static ArrayList<Map<String, Object>> jsonToList(String json) {
        ArrayList<Map<String, Object>> result = new ArrayList<>();

        if (StringUtils.isEmpty(json)) {
            return result;
        }
        if (!json.startsWith("[") && !json.endsWith("]")) {
            return result;
        }
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.add(jsonToMap(jsonObject.toString()));
            }
            return result;
        } catch (JSONException e) {
            return result;
        }
    }

    /**
     * {@link List<Map<String,Object>>} 变为 {@link JSONArray}
     *
     * @param list {@link List<Map<String,Object>>} 对象
     * @return 转变后的 {@link JSONArray} 对象
     */
    @NonNull
    public static JSONArray listToJsonArray(List<Map<String, Object>> list) {
        JSONArray result = new JSONArray();

        if (list == null || list.isEmpty()) return result;

        for (Map<String, Object> objectMap : list) {
            result.put(mapToJsonObject(objectMap));
        }
        return result;
    }

    /**
     * {@link List<Map<String,Object>>} 变为 {@link String}
     *
     * @param list {@link List<Map<String,Object>>} 对象
     * @return 转变后的 {@link String}
     */
    @NonNull
    public static String listToJson(List<Map<String, Object>> list) {
        return listToJsonArray(list).toString();
    }
}
