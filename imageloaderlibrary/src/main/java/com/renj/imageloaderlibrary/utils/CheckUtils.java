package com.renj.imageloaderlibrary.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Collection;
import java.util.Map;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-01-22   16:27
 * <p>
 * 描述：检查工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CheckUtils {
    private CheckUtils() {
        // Utility class.
    }

    public static void checkArgument(boolean expression, @NonNull String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    @NonNull
    public static <T> T checkNotNull(@Nullable T arg) {
        return checkNotNull(arg, "Argument must not be null");
    }

    @NonNull
    public static <T> T checkNotNull(@Nullable T arg, @NonNull String message) {
        if (arg == null) {
            throw new NullPointerException(message);
        }
        return arg;
    }

    @NonNull
    public static <T> boolean checkNotNull(@Nullable T... args) {
        return checkNotNull("Argument must not be null", args);
    }

    @NonNull
    public static <T> boolean checkNotNull(@NonNull String message, @Nullable T... args) {
        if (args == null)
            throw new NullPointerException(message);
        for (T arg : args) {
            if (arg == null)
                throw new NullPointerException(message);
        }
        return false;
    }

    @NonNull
    public static String checkNotEmpty(@Nullable String string) {
        if (TextUtils.isEmpty(string)) {
            throw new IllegalArgumentException("Must not be null or empty");
        }
        return string;
    }

    @NonNull
    public static <K, T> Map<K, T> checkNotEmpty(@Nullable Map<K, T> map) {
        return checkNotEmpty(map, "Must not be empty.");
    }

    @NonNull
    public static <K, T> Map<K, T> checkNotEmpty(@Nullable Map<K, T> map, @NonNull String message) {
        if (map == null || map.isEmpty())
            throw new IllegalArgumentException(message);
        return map;
    }

    @NonNull
    public static <T extends Collection<Y>, Y> T checkNotEmpty(@NonNull T collection) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("Must not be empty.");
        }
        return collection;
    }
}
