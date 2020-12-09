package com.renj.utils.common;

import com.renj.utils.res.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-06-12   20:27
 * <p>
 * 描述：MD5 工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MD5Utils {
    private static final String ENCODING_ALGORITHM = "MD5";

    /**
     * 获取字符串的MD5值
     */
    public static String getMD5Value(String str) {
        if (str != null && str.length() > 0) {
            byte[] data = md5sum(str.getBytes());
            if (data != null) {
                return convertToHexString(data);
            }
        }
        return "";
    }

    /**
     * 获取文件的MD5值
     */
    public static String getMD5Value(File file) {
        if (file != null && file.exists()) {
            byte[] data = md5sum(file);
            if (data != null) {
                return convertToHexString(data);
            }
        }
        return "";
    }

    private static byte[] md5sum(byte[] data) {
        try {
            MessageDigest mdTemp = MessageDigest.getInstance(ENCODING_ALGORITHM);
            mdTemp.update(data);
            return mdTemp.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] md5sum(File file) {
        InputStream fis = null;
        byte[] buffer = new byte[1024];
        int numRead = 0;
        MessageDigest md5;
        try {
            fis = new FileInputStream(file);
            md5 = MessageDigest.getInstance(ENCODING_ALGORITHM);
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            return md5.digest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将data数组转换为16进制字符串
     *
     * @param data
     * @return
     */
    private static String convertToHexString(byte data[]) {
        StringBuilder strBuffer = new StringBuilder();
        for (byte aData : data) {
            String str = Integer.toHexString(0xFF & aData);
            if (StringUtils.notEmpty(str)) {
                if (str.length() == 1)
                    str = "0" + str;
                strBuffer.append(str);
            }
        }
        return strBuffer.toString().toUpperCase();
    }
}
