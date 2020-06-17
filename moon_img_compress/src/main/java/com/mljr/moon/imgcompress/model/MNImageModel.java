package com.mljr.moon.imgcompress.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Author： fanyafeng
 * Date： 17/12/21 下午2:53
 * Email: fanyafeng@live.cn
 * <p>
 * sourcePath 压缩源文件路径
 * targetPath 压缩后文件路径
 * position   可根据此position进行排序，用来确认位置
 * tag        可以自定义TAG
 */
public class MNImageModel extends BaseModel implements Parcelable, Comparable {
    private String sourcePath;
    private String targetPath;
    private int position;
    private Object tag;

    public MNImageModel() {
    }

    public MNImageModel(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public MNImageModel(String sourcePath, int position) {
        this.sourcePath = sourcePath;
        this.position = position;
    }

    /**
     * @param sourcePath
     * @param targetPath
     * @param position
     * @param tag
     */
    public MNImageModel(String sourcePath, String targetPath, int position, Object tag) {
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
        this.position = position;
        this.tag = tag;
    }

    protected MNImageModel(Parcel in) {
        sourcePath = in.readString();
        targetPath = in.readString();
        position = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sourcePath);
        dest.writeString(targetPath);
        dest.writeInt(position);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MNImageModel> CREATOR = new Creator<MNImageModel>() {
        @Override
        public MNImageModel createFromParcel(Parcel in) {
            return new MNImageModel(in);
        }

        @Override
        public MNImageModel[] newArray(int size) {
            return new MNImageModel[size];
        }
    };

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isCompressed() {
        if (this.sourcePath != null && this.targetPath != null) {
            if (!this.sourcePath.equals(this.targetPath)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "MLImageBean{" +
                "sourcePath='" + sourcePath + '\'' +
                ", targetPath='" + targetPath + '\'' +
                ", position=" + position +
                ", tag=" + tag +
                '}';
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return this.position > ((MNImageModel) o).getPosition() ? 1 : -1;
    }
}
