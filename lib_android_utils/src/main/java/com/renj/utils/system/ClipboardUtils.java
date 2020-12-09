package com.renj.utils.system;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.text.TextUtils;

import com.renj.utils.common.UIUtils;
import com.renj.utils.res.StringUtils;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-06-15   19:39
 * <p>
 * 描述：剪贴板工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ClipboardUtils {
    /**
     * 将内容保存到剪贴板，并且提示 复制成功、复制失败
     *
     * @param textCase 需要复制的内容
     */
    public static void copyText(String textCase) {
        copyText(textCase, "复制成功", "复制失败");
    }

    /**
     * 将内容保存到剪贴板，并且提示 自定义复制成功提示、复制失败
     *
     * @param textCase   需要复制的内容
     * @param successMsg 成功提示
     */
    public static void copyText(String textCase, String successMsg) {
        copyText(textCase, successMsg, "复制失败");
    }

    /**
     * 将内容保存到剪贴板，并且自定义复制成功、失败提示，都为空时不提示
     *
     * @param textCase   需要复制的内容
     * @param successMsg 成功提示
     * @param failMsg    失败提示
     */
    public static void copyText(String textCase, String successMsg, String failMsg) {
        if (TextUtils.isEmpty(textCase)) {
            return;
        }
        ClipboardManager cmb = (ClipboardManager) UIUtils.getContext().getSystemService(CLIPBOARD_SERVICE);
        if (cmb != null) {
            cmb.setPrimaryClip(ClipData.newPlainText("text", textCase));
        }
        // 不为null时提示
        if (StringUtils.notEmpty(successMsg, failMsg)) {
            if (getClipboardFirstData().equals(textCase)) {
                UIUtils.showToast(successMsg);
            } else {
                UIUtils.showToast(failMsg);
            }
        }
    }

    /**
     * 获取剪贴板内容
     *
     * @return
     */
    public static String getClipboardFirstData() {
        ClipboardManager manager = (ClipboardManager) UIUtils.getContext().getSystemService(CLIPBOARD_SERVICE);
        if (manager == null) {
            return "";
        }
        ClipData primaryClip = manager.getPrimaryClip();
        if (primaryClip == null) {
            return "";
        }
        int clipDataSize = primaryClip.getItemCount();
        if (clipDataSize == 0) {
            return "";
        }
        ClipData.Item firstClipItem = primaryClip.getItemAt(0);
        if (firstClipItem == null) {
            return "";
        }
        CharSequence clipCharSequence = firstClipItem.getText();
        if (clipCharSequence == null) {
            return "";
        }
        return clipCharSequence.toString();
    }

    /**
     * 清除剪贴板内容
     */
    public static void clearClipboardFirstData() {
        ClipboardManager manager = (ClipboardManager) UIUtils.getContext().getSystemService(CLIPBOARD_SERVICE);
        if (manager != null) {
            manager.setPrimaryClip(ClipData.newPlainText("", ""));
        }
    }
}
