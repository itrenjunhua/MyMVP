package com.renj.common.utils;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
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
     * json字符串转换成JavaBean
     *
     * @param jsonString json 字符串
     * @param clazz      Bean
     * @param <T>
     * @return
     */
    @Nullable
    public static <T> T jsonStringToBean(String jsonString, Class<T> clazz) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, clazz);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json 字符串 变为 {@link Map<String,Object>}
     *
     * @param jsonString json 字符串
     * @return 转变后的 {@link Map<String,Object>} 对象
     */
    @NonNull
    @CheckResult(suggest = "结果从未使用过")
    public static Map<String, Object> jsonStringToMap(String jsonString) {
        if (StringUtils.isEmpty(jsonString)) return new HashMap<>();

        if (!jsonString.startsWith("{") || !jsonString.endsWith("}")) return new HashMap<>();

        Map<String, Object> result = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
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
    @CheckResult(suggest = "结果从未使用过")
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
    @CheckResult(suggest = "结果从未使用过")
    public static String mapToJsonString(Map<String, Object> map) {
        return mapToJsonObject(map).toString();
    }

    /**
     * json 字符串 变为 {@link List<Map<String,Object>>}
     *
     * @param jsonString json 字符串
     * @return 转变后的 {@link List<Map<String,Object>>} 对象
     */
    @NonNull
    @CheckResult(suggest = "结果从未使用过")
    public static ArrayList<Map<String, Object>> jsonStringToList(String jsonString) {
        ArrayList<Map<String, Object>> result = new ArrayList<>();

        if (StringUtils.isEmpty(jsonString)) {
            return result;
        }
        if (!jsonString.startsWith("[") && !jsonString.endsWith("]")) {
            return result;
        }
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.add(jsonStringToMap(jsonObject.toString()));
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
    @CheckResult(suggest = "结果从未使用过")
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
    @CheckResult(suggest = "结果从未使用过")
    public static String listToJsonString(List<Map<String, Object>> list) {
        return listToJsonArray(list).toString();
    }
}
