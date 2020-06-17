package com.mljr.moon.imgcompress.compressrule;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Author： fanyafeng
 * Date： 17/12/26 下午6:14
 * Email: fanyafeng@live.cn
 */
public class MNCompressOption implements ICompressRule {

    private int compressQuality;
    private Bitmap.Config inPreferredConfig;
    private int maxSize;

    /**
     * 内置的高清压缩，适用于带有文字的图片，可直接只用，不支持重新
     */
    public final static MNCompressOption PHOTO_HD = new MNCompressOption(80, Bitmap.Config.RGB_565, 500 * 1024) {
        @Override
        public int inSampleSize(int width, int height) {
            int thumbW = width;
            int thumbH = height;

            if (width > 4000) {
                thumbW = (int) (width * 0.4);
                thumbH = (int) (height * 0.4);
            } else if (width > 3000) {
                thumbW = width >> 1;
                thumbH = height >> 1;
            } else if (width > 2048) {
                thumbW = (int) (width * 0.6);
                thumbH = (int) (height * 0.6);
            }

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            int outH = height;
            int outW = width;

            int inSampleSize = 1;

            if (outH > thumbH || outW > thumbW) {
                int halfH = outH >> 1;
                int halfW = outW >> 1;

                while ((halfH / inSampleSize) > thumbH && (halfW / inSampleSize) > thumbW) {
                    inSampleSize *= 2;
                }
            }
            options.inJustDecodeBounds = false;

            int heightRatio = (int) Math.ceil(height / (float) thumbH);
            int widthRatio = (int) Math.ceil(width / (float) thumbW);

            if (heightRatio > 1 || widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    inSampleSize = heightRatio;
                } else {
                    inSampleSize = widthRatio;
                }
            }

            if (inSampleSize > 2) {
                return 2;
            }
            return inSampleSize;
        }
    };
    /**
     * 内置普清压缩，可以按照此规则自己进行相应的定义
     */
    public final static MNCompressOption PHOTO_LD = new MNCompressOption(80, Bitmap.Config.RGB_565, 500 * 1024) {
        @Override
        public int inSampleSize(int width, int height) {
            width = width % 2 == 1 ? width + 1 : width;
            height = height % 2 == 1 ? height + 1 : height;

            int longSide = Math.max(width, height);
            int shortSide = Math.min(width, height);

            float scle = ((float) shortSide / longSide);

            int inSampleSize = 1;
            if (scle <= 1 && scle > 0.5625) {
                if (longSide < 1664) {
                    inSampleSize = 1;
                } else if (longSide >= 1664 && longSide < 4990) {
                    inSampleSize = 2;
                } else if (longSide > 4990 && longSide < 10240) {
                    inSampleSize = 4;
                } else {
                    inSampleSize = longSide / 200 == 0 ? 1 : longSide / 1280;
                }
            } else if (scle <= 0.5625 && scle > 0.5) {
                inSampleSize = longSide / 1280 == 0 ? 1 : longSide / 1280;
            } else {
                inSampleSize = (int) Math.ceil(longSide / (1280.0 / scle));
            }
            return inSampleSize;
        }
    };

    public MNCompressOption(int compressQuality, Bitmap.Config inPreferredConfig, int maxSize) {
        this.compressQuality = compressQuality;
        this.inPreferredConfig = inPreferredConfig;
        this.maxSize = maxSize;
    }


    @Override
    public int inSampleSize(int width, int height) {
        int thumbW = width;
        int thumbH = height;

        if (width > 4000) {
            thumbW = (int) (width * 0.4);
            thumbH = (int) (height * 0.4);
        } else if (width > 3000) {
            thumbW = width >> 1;
            thumbH = height >> 1;
        } else if (width > 2048) {
            thumbW = (int) (width * 0.6);
            thumbH = (int) (height * 0.6);
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        int outH = height;
        int outW = width;

        int inSampleSize = 1;

        if (outH > thumbH || outW > thumbW) {
            int halfH = outH >> 1;
            int halfW = outW >> 1;

            while ((halfH / inSampleSize) > thumbH && (halfW / inSampleSize) > thumbW) {
                inSampleSize *= 2;
            }
        }
        options.inJustDecodeBounds = false;

        int heightRatio = (int) Math.ceil(height / (float) thumbH);
        int widthRatio = (int) Math.ceil(width / (float) thumbW);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                inSampleSize = heightRatio;
            } else {
                inSampleSize = widthRatio;
            }
        }

        if (inSampleSize > 2) {
            return 2;
        }
        return inSampleSize;
    }

    @Override
    public int compressQuality() {
        return compressQuality;
    }

    @Override
    public Bitmap.Config inPreferredConfig() {
        return inPreferredConfig;
    }

    @Override
    public int maxSize() {
        return maxSize;
    }
}
