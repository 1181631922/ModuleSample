package com.mljr.moon.imgcompress.engine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mljr.moon.imgcompress.MNCompressConfig;
import com.mljr.moon.imgcompress.compressrule.ICompressRule;
import com.mljr.moon.imgcompress.compressrule.MNCompressOption;

import java.io.File;

/**
 * Author： fanyafeng
 * Date： 17/11/28 上午11:06
 * Email: fanyafeng@live.cn
 */
public class MNCompressEngine extends EngineFactory {

    private final static String TAG = MNCompressEngine.class.getSimpleName();

    public final static MNCompressEngine ML_COMPRESS_ENGINE = new MNCompressEngine();

    private File Compress(File file, ICompressRule compressRule) {

        if (compressRule == null) {
            compressRule = MNCompressOption.PHOTO_HD;
        }
        int quality = 80;
        if (compressRule.compressQuality() != 0) {
            quality = compressRule.compressQuality();
        }

        Bitmap.Config inPreferredConfig = Bitmap.Config.RGB_565;
        if (compressRule.inPreferredConfig() != null) {
            inPreferredConfig = compressRule.inPreferredConfig();
        }

        int maxSize = 500 * 1024;
        if (compressRule.maxSize() > 0) {
            maxSize = compressRule.maxSize();
        }

        if (file.length() <= maxSize) {
            return file;
        }

        String filePath = file.getAbsolutePath();
        int angle = getImageSpinAngle(filePath);
//        boolean isPNG = isPNG(filePath);

        String thumbFilePath;

        if (MNCompressConfig.getCompressConfig().getImagePath() == null) {
            thumbFilePath = new File(MNCompressConfig.getCompressConfig().getContext().getExternalFilesDir(null), "IMG_" + System.currentTimeMillis() + ".jpg").getAbsolutePath();
        } else {
            thumbFilePath = createDir(MNCompressConfig.getCompressConfig().getImagePath()) + File.separator + "IMG_" + System.currentTimeMillis() + ".jpg";
        }


        int width = getImageSize(filePath)[0];
        int height = getImageSize(filePath)[1];

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inSampleSize = compressRule.inSampleSize(width, height);
        options.inPreferredConfig = inPreferredConfig;

        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        bitmap = rotateImage(angle, bitmap);

        File fileImage = saveImage(Bitmap.CompressFormat.JPEG, thumbFilePath, bitmap, quality);
//        File fileImage = saveImage(isPNG ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG, thumbFilePath, bitmap, quality);
        if (fileImage.length() >= file.length()) {
            return file;
        } else {
            return fileImage;
        }
    }

    private File Compress(File file) {
        return Compress(file, MNCompressOption.PHOTO_HD);
    }

    @Override
    public File compress(File file) {
        return Compress(file);
    }

    @Override
    public File compress(File file, ICompressRule iCompressRule) {
        return Compress(file, iCompressRule);
    }
}
