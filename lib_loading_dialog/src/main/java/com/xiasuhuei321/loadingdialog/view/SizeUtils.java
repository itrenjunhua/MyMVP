package com.xiasuhuei321.loadingdialog.view;

import android.content.Context;

/**
 * Created by Luo_xiasuhuei321@163.com on 2016/11/6.
 * desc:
 */

public class SizeUtils {
    /**
     * dp转px
     */
    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }
}
