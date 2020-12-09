package com.renj.utils.system;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.renj.utils.common.Logger;

import java.io.File;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2018-04-03   13:43
 * <p>
 * 描述：操作系统相机和图库、调用系统裁剪图片功能等相关工具类<br/>
 * <b>注意需要申请权限</b>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SystemPhotoUtils {

    /**
     * 调用系统相机，需要申请权限；不指定图片保存位置，<br/>
     * 通过 {@code Bitmap bm = (Bitmap) data.getExtras().get("data")} 或 {@code Bitmap bm = (Bitmap)data.getExtras().getParcelable("data")} 的形式获取
     *
     * @param activity    当前 {@link Activity}
     * @param requestCode 调用系统相机请求码
     * @see #takePicture(Activity, Uri, int)
     */
    public static void takePicture(@NonNull Activity activity, int requestCode) {
        takePicture(activity, null, requestCode);
    }

    /**
     * 调用系统相机，需要申请权限；指定图片保存路径Uri
     *
     * @param activity    当前 {@link Activity}
     * @param imageUri    拍照后照片存储路径
     * @param requestCode 调用系统相机请求码
     * @see #takePicture(Activity, int)
     */
    public static void takePicture(@NonNull Activity activity, @NonNull Uri imageUri, int requestCode) {
        // 调用系统相机
        Intent intentCamera = getTakePictureIntent(imageUri);
        activity.startActivityForResult(intentCamera, requestCode);
    }

    /**
     * 调用系统相机，需要申请权限；不指定图片保存位置，，<br/>
     * 通过 {@code Bitmap bm = (Bitmap) data.getExtras().get("data")} 或 {@code Bitmap bm = (Bitmap)data.getExtras().getParcelable("data")} 的形式获取
     *
     * @param fragment    当前 {@link Fragment}
     * @param requestCode 调用系统相机请求码
     * @see #takePicture(Fragment, Uri, int)
     */
    public static void takePicture(@NonNull Fragment fragment, int requestCode) {
        takePicture(fragment, null, requestCode);
    }

    /**
     * 调用系统相机，需要申请权限；指定图片保存路径Uri
     *
     * @param fragment    当前 {@link Fragment}
     * @param imageUri    拍照后照片存储路径
     * @param requestCode 调用系统相机请求码
     * @see #takePicture(Fragment, int)
     */
    public static void takePicture(@NonNull Fragment fragment, @NonNull Uri imageUri, int requestCode) {
        Intent intentCamera = getTakePictureIntent(imageUri);
        fragment.startActivityForResult(intentCamera, requestCode);
    }

    /**
     * 调用系统相机，需要申请权限；不指定图片保存位置，，<br/>
     * 通过 {@code Bitmap bm = (Bitmap) data.getExtras().get("data")} 或 {@code Bitmap bm = (Bitmap)data.getExtras().getParcelable("data")} 的形式获取
     *
     * @param fragment    当前 {@link android.support.v4.app.Fragment}
     * @param requestCode 调用系统相机请求码
     * @see #takePicture(android.support.v4.app.Fragment, Uri, int)
     */
    public static void takePicture(@NonNull android.support.v4.app.Fragment fragment, int requestCode) {
        takePicture(fragment, null, requestCode);
    }

    /**
     * 调用系统相机，需要申请权限；指定图片保存路径Uri
     *
     * @param fragment    当前 {@link android.support.v4.app.Fragment}
     * @param imageUri    拍照后照片存储路径
     * @param requestCode 调用系统相机请求码
     * @see #takePicture(android.support.v4.app.Fragment, int)
     */
    public static void takePicture(@NonNull android.support.v4.app.Fragment fragment, @NonNull Uri imageUri, int requestCode) {
        // 调用系统相机
        Intent intentCamera = getTakePictureIntent(imageUri);
        fragment.startActivityForResult(intentCamera, requestCode);
    }

    /**
     * 获取调用系统拍照时需要的 {@link Intent}
     *
     * @param imageUri 图片保存的路径 Uri
     * @return
     */
    @NonNull
    private static Intent getTakePictureIntent(@NonNull Uri imageUri) {
        // 调用系统相机
        Intent intentCamera = new Intent();
        if (isMoreHeightVersion(Build.VERSION_CODES.N)) {
            // 添加这一句表示对目标应用临时授权该Uri所代表的文件
            intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (null != imageUri) {
            // 将拍照结果保存至指定的Uri中，不保留在相册中
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }
        return intentCamera;
    }

    /**
     * @param activity    当前 {@link Activity}
     * @param requestCode 打开相册的请求码
     */
    public static void openPhotos(Activity activity, int requestCode) {
        Intent photoPickerIntent = getOpenPhotosIntent();
        activity.startActivityForResult(photoPickerIntent, requestCode);
    }

    /**
     * @param fragment    当前 {@link Fragment}
     * @param requestCode 打开相册的请求码
     */
    public static void openPhotos(Fragment fragment, int requestCode) {
        Intent photoPickerIntent = getOpenPhotosIntent();
        fragment.startActivityForResult(photoPickerIntent, requestCode);
    }

    /**
     * @param fragment    当前 {@link android.support.v4.app.Fragment}
     * @param requestCode 打开相册的请求码
     */
    public static void openPhotos(android.support.v4.app.Fragment fragment, int requestCode) {
        Intent photoPickerIntent = getOpenPhotosIntent();
        fragment.startActivityForResult(photoPickerIntent, requestCode);
    }

    /**
     * 获取打开相册的 {@link Intent}
     *
     * @return
     */
    @NonNull
    private static Intent getOpenPhotosIntent() {
        Intent openPhotosIntent = new Intent();
        openPhotosIntent.setType("image/*");
        if (isMoreHeightVersion(Build.VERSION_CODES.KITKAT)) {
            openPhotosIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        } else {
            openPhotosIntent.setAction(Intent.ACTION_GET_CONTENT);
        }
        return openPhotosIntent;
    }


    /**
     * 调用系统裁剪图片功能，不指定图片保存位置，<br/>
     * 通过 {@code Bitmap bm = (Bitmap) data.getExtras().get("data")} 或 {@code Bitmap bm = (Bitmap)data.getExtras().getParcelable("data")} 的形式获取。<br/>
     * <b>特别注意：不指定图片保存位置时，裁剪后的图片不能过大（不同手机可能不同），否则返回 {@code null}；
     * 建议使用指定图片路径的方式 {@link #cropImageUri(Activity, Uri, Uri, int, int, int, int, boolean, int)}</b>
     *
     * @param activity    当前 {@link Activity}
     * @param orgUri      剪裁原图的Uri
     * @param aspectX     X方向的比例
     * @param aspectY     Y方向的比例
     * @param width       剪裁图片的宽度
     * @param height      剪裁图片高度
     * @param circleCrop  是否圆形裁剪
     * @param requestCode 剪裁图片的请求码
     * @see #cropImageUri(Activity, Uri, Uri, int, int, int, int, boolean, int)
     */
    public static void cropImageUri(Activity activity, Uri orgUri,
                                    int aspectX, int aspectY,
                                    int width, int height,
                                    boolean circleCrop, int requestCode) {
        cropImageUri(activity, orgUri, null, aspectX, aspectY, width, height, circleCrop, requestCode);
    }

    /**
     * 调用系统裁剪图片功能；指定图片保存路径Uri
     *
     * @param activity    当前 {@link Activity}
     * @param orgUri      剪裁原图的Uri
     * @param desUri      剪裁后的图片的Uri
     * @param aspectX     X方向的比例
     * @param aspectY     Y方向的比例
     * @param width       剪裁图片的宽度
     * @param height      剪裁图片高度
     * @param circleCrop  是否圆形裁剪
     * @param requestCode 剪裁图片的请求码
     * @see #cropImageUri(Activity, Uri, int, int, int, int, boolean, int)
     */
    public static void cropImageUri(Activity activity, Uri orgUri,
                                    Uri desUri, int aspectX, int aspectY,
                                    int width, int height,
                                    boolean circleCrop, int requestCode) {
        Intent intent = getCropImageIntent(orgUri, desUri, aspectX, aspectY, width, height, circleCrop);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 调用系统裁剪图片功能，不指定图片保存位置，<br/>
     * 通过 {@code Bitmap bm = (Bitmap) data.getExtras().get("data")} 或 {@code Bitmap bm = (Bitmap)data.getExtras().getParcelable("data")} 的形式获取。<br/>
     * <b>特别注意：不指定图片保存位置时，裁剪后的图片不能过大（不同手机可能不同），否则返回 {@code null}；
     * 建议使用指定图片路径的方式 {@link #cropImageUri(Fragment, Uri, Uri, int, int, int, int, boolean, int)}</b>
     *
     * @param fragment    当前 {@link Fragment}
     * @param orgUri      剪裁原图的Uri
     * @param aspectX     X方向的比例
     * @param aspectY     Y方向的比例
     * @param width       剪裁图片的宽度
     * @param height      剪裁图片高度
     * @param circleCrop  是否圆形裁剪
     * @param requestCode 剪裁图片的请求码
     * @see #cropImageUri(Fragment, Uri, Uri, int, int, int, int, boolean, int)
     */
    public static void cropImageUri(Fragment fragment, Uri orgUri,
                                    int aspectX, int aspectY, int width, int height,
                                    boolean circleCrop, int requestCode) {
        cropImageUri(fragment, orgUri, null, aspectX, aspectY, width, height, circleCrop, requestCode);
    }

    /**
     * 调用系统裁剪图片功能；指定图片保存路径Uri
     *
     * @param fragment    当前 {@link Fragment}
     * @param orgUri      剪裁原图的Uri
     * @param desUri      剪裁后的图片的Uri
     * @param aspectX     X方向的比例
     * @param aspectY     Y方向的比例
     * @param width       剪裁图片的宽度
     * @param height      剪裁图片高度
     * @param circleCrop  是否圆形裁剪
     * @param requestCode 剪裁图片的请求码
     * @see #cropImageUri(Fragment, Uri, int, int, int, int, boolean, int)
     */
    public static void cropImageUri(Fragment fragment, Uri orgUri, Uri desUri,
                                    int aspectX, int aspectY, int width, int height,
                                    boolean circleCrop, int requestCode) {
        Intent intent = getCropImageIntent(orgUri, desUri, aspectX, aspectY, width, height, circleCrop);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 调用系统裁剪图片功能，不指定图片保存位置，<br/>
     * 通过 {@code Bitmap bm = (Bitmap) data.getExtras().get("data")} 或 {@code Bitmap bm = (Bitmap)data.getExtras().getParcelable("data")} 的形式获取。<br/>
     * <b>特别注意：不指定图片保存位置时，裁剪后的图片不能过大（不同手机可能不同），否则返回 {@code null}；
     * 建议使用指定图片路径的方式 {@link #cropImageUri(android.support.v4.app.Fragment, Uri, Uri, int, int, int, int, boolean, int)}</b>
     *
     * @param fragment    当前 {@link android.support.v4.app.Fragment}
     * @param orgUri      剪裁原图的Uri
     * @param aspectX     X方向的比例
     * @param aspectY     Y方向的比例
     * @param width       剪裁图片的宽度
     * @param height      剪裁图片高度
     * @param circleCrop  是否圆形裁剪
     * @param requestCode 剪裁图片的请求码
     * @see #cropImageUri(android.support.v4.app.Fragment, Uri, Uri, int, int, int, int, boolean, int)
     */
    public static void cropImageUri(android.support.v4.app.Fragment fragment, Uri orgUri,
                                    int aspectX, int aspectY, int width, int height,
                                    boolean circleCrop, int requestCode) {
        cropImageUri(fragment, orgUri, null, aspectX, aspectY, width, height, circleCrop, requestCode);
    }

    /**
     * 调用系统裁剪图片功能；指定图片保存路径Uri
     *
     * @param fragment    当前 {@link android.support.v4.app.Fragment}
     * @param orgUri      剪裁原图的Uri
     * @param desUri      剪裁后的图片的Uri
     * @param aspectX     X方向的比例
     * @param aspectY     Y方向的比例
     * @param width       剪裁图片的宽度
     * @param height      剪裁图片高度
     * @param circleCrop  是否圆形裁剪
     * @param requestCode 剪裁图片的请求码
     * @see #cropImageUri(android.support.v4.app.Fragment, Uri, int, int, int, int, boolean, int)
     */
    public static void cropImageUri(android.support.v4.app.Fragment fragment, Uri orgUri, Uri desUri,
                                    int aspectX, int aspectY, int width, int height,
                                    boolean circleCrop, int requestCode) {
        Intent intent = getCropImageIntent(orgUri, desUri, aspectX, aspectY, width, height, circleCrop);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 获取裁剪图片的 {@link Intent}
     *
     * @return
     */
    @NonNull
    private static Intent getCropImageIntent(Uri orgUri, Uri desUri, int aspectX, int aspectY, int width, int height, boolean circleCrop) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (isMoreHeightVersion(Build.VERSION_CODES.N)) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.setDataAndType(orgUri, "image/*");
        intent.putExtra("crop", "true");    // 发送裁剪信号
        intent.putExtra("aspectX", aspectX);        // X方向上的比例
        intent.putExtra("aspectY", aspectY);        // Y方向上的比例
        intent.putExtra("outputX", width);          // 裁剪区的宽
        intent.putExtra("outputY", height);         // 裁剪区的高
        intent.putExtra("circleCrop", circleCrop);  // 圆形裁剪区域
        intent.putExtra("scale", true);      // 是否保留比例
        if (null != desUri) {
            intent.putExtra("return-data", false); // 是否将数据保留在Bitmap中返回
            intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);     // 将剪切的图片保存到目标Uri中
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()); // 输出格式
        } else {
            intent.putExtra("return-data", true); // 是否将数据保留在Bitmap中返回
        }
        intent.putExtra("noFaceDetection", true); // 是否取消人脸识别功能
        return intent;
    }

    /**
     * 判断系统当前版本是否于目标版本一样或者比目标版本更高
     *
     * @return
     */
    private static boolean isMoreHeightVersion(int targetVersionCode) {
        return Build.VERSION.SDK_INT >= targetVersionCode;
    }

    /**
     * 读取 {@link Uri} 所在的图片
     *
     * @param uri      图片对应的 {@link Uri}
     * @param mContext 上下文对象
     * @return 获取图像的Bitmap
     */
    @Nullable
    public static Bitmap getBitmapFromUri(Uri uri, Context mContext) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 打开系统相册选择了图片之后，获取返回的Uri，兼容不同版本
     *
     * @param context {@link Context}
     * @param data    {@code onActivityResult(int requestCode, int resultCode, Intent data)} 方法中的 {@code data}
     * @return 解析之后的Uri
     */
    @Nullable
    public static Uri getUriForPhotosReturn(@NonNull Context context, Intent data) {
        if (data == null) return null;

        if (isMoreHeightVersion(Build.VERSION_CODES.KITKAT)) {
            String pathFormUri = getPathFormUri(context, data.getData(), true);
            if (pathFormUri == null) return null;

            return Uri.parse(pathFormUri);
        } else
            return data.getData();
    }

    /**
     * 打开系统相册选择了图片之后，获取返回的File，兼容不同版本
     *
     * @param context {@link Context}
     * @param data    {@code onActivityResult(int requestCode, int resultCode, Intent data)} 方法中的 {@code data}
     * @return 解析之后的File
     */
    @Nullable
    public static File getFileForPhotosReturn(@NonNull Context context, Intent data) {
        if (data == null) return null;

        if (isMoreHeightVersion(Build.VERSION_CODES.KITKAT)) {
            String pathFormUri = getPathFormUri(context, data.getData(), false);
            if (pathFormUri == null) return null;

            return new File(pathFormUri);
        } else {
            if (data.getData() == null) return null;
            return new File(data.getData().getPath());
        }
    }

    /**
     * Android KITKAT (4.4) 以上从 {@link Uri} 中读取图片路径
     *
     * @param context        上下文对象
     * @param uri            当前相册照片的  {@link Uri}
     * @param needFilePrefix 是否需要前缀 "file:///"
     * @return 解析后的 {@link Uri} 对应的 {@link String}
     */
    @Nullable
    public static String getPathFormUri(@NonNull Context context, @NonNull Uri uri, boolean needFilePrefix) {
        String pathHead = needFilePrefix ? "file:///" : "";
        // DocumentProvider
        if (isMoreHeightVersion(Build.VERSION_CODES.KITKAT) && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type))
                    return pathHead + Environment.getExternalStorageDirectory() + "/" + split[1];
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                String id = DocumentsContract.getDocumentId(uri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return pathHead + getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};

                return pathHead + getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return pathHead + uri.getLastPathSegment();

            return pathHead + getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return pathHead + uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    @Nullable
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToNext()) {
                return cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
