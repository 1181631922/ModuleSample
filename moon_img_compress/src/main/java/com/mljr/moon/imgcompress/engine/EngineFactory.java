package com.mljr.moon.imgcompress.engine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

import com.mljr.moon.imgcompress.compressrule.ICompressRule;
import com.mljr.moon.imgcompress.compressrule.MNCompressOption;
import com.mljr.moon.imgcompress.util.Preconditions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author： fanyafeng
 * Date： 17/12/27 下午2:22
 * Email: fanyafeng@live.cn
 * <p>
 * 压缩引擎抽象类
 * 需要实现两个抽象方法
 */
public abstract class EngineFactory {

    /**
     * @param file          需要压缩的图片
     * @param iCompressRule 压缩图片规则
     * @return
     */
    public abstract File compress(File file, ICompressRule iCompressRule);

    /**
     * 上面的方法必须定义，然后可以自己写一个或者用sdk提供的两个模板来传参实现此方法
     * {@link MNCompressOption#PHOTO_HD}
     * {@link MNCompressOption#PHOTO_LD}
     * eg:compress(file,PHOTO_HD)
     * compress(file,PHOTO_LD)
     *
     * @param file 需要压缩的图片
     * @return
     */
    public abstract File compress(File file);

    /**
     * 判断图片是否是jpg或jpeg格式，非jpg或jpeg没有exif信息
     *
     * @param path 图片路径
     * @return
     */
    protected boolean isJPG(String path) {
        if (path != null && !path.equals("")) {
            String suffix = path.substring(path.lastIndexOf("."), path.length()).toLowerCase();
            if (suffix.contains("jpg") || suffix.contains("jpeg")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    protected boolean isPNG(String path) {
        if (path != null && !path.equals("")) {
            String suffix = path.substring(path.lastIndexOf("."), path.length()).toLowerCase();
            if (suffix.contains("png")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 获取图片旋转角度
     * 某些手机拍照存储图片会有旋转的问题，三星手机问题居多
     * 因为图片进行压缩以后会丢失原来的exif信息
     * 所以需要将翻转的图片进行旋转
     * 用此方法获取exif信息然后采用{@link #rotateImage(float, Bitmap)}进行图片旋转
     *
     * @param path 图片路径
     * @return
     */
    protected final int getImageSpinAngle(String path) {
        int degree = 0;
        if (!isJPG(path)) {
            return degree;
        }
        try {
            ExifInterface exifInterface = new ExifInterface(path);//仅支持jpg
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    protected final Bitmap.CompressFormat getCompressFormat(String path) {
        if (isPNG(path)) {
            return Bitmap.CompressFormat.PNG;
        } else {
            return Bitmap.CompressFormat.JPEG;
        }
    }

    /**
     * 旋转bitmap
     *
     * @param angle
     * @param bitmap
     * @return
     */
    protected final Bitmap rotateImage(float angle, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 存储bitmap为图片文件
     *
     * @param compressPath 压缩后图片路径
     * @param bitmap       压缩图片的bitmap
     * @param quality      图片存储质量
     * @return
     */
    protected File saveImage(Bitmap.CompressFormat compressFormat, String compressPath, Bitmap bitmap, int quality) {
        Preconditions.checkNotNull(bitmap, "bitmap is null");
        File result = new File(compressPath.substring(0, compressPath.lastIndexOf("/")));
        if (!result.exists() && !result.mkdirs()) {
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(compressFormat, quality, byteArrayOutputStream);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(compressPath);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(compressPath);
    }

    /**
     * 获取图片像素值宽高
     *
     * @param imagePath
     * @return
     */
    protected final int[] getImageSize(String imagePath) {
        int[] res = new int[2];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeFile(imagePath, options);

        res[0] = options.outWidth;
        res[1] = options.outHeight;
        return res;
    }

    private final String getSDCardPath() {
        return Environment.getExternalStorageDirectory() + File.separator;
    }

    protected final String createDir(String path) {
        File file = new File(getSDCardPath() + path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
}
