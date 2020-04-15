package com.ripple.permission.plugin.descreader;

import org.objectweb.asm.Opcodes;

/**
 * Author: fanyafeng
 * Data: 2020/4/15 10:10
 * Email: fanyafeng@live.cn
 * Description:
 */
public class DescReader {
    private char[] mDesc;
    private int mLength;

    //下一个要读取的字符位置
    private int mCursor = 0;

    public DescReader(String desc) {
        if (desc == null) {
            mLength = 0;
        } else {
            mLength = desc.length();
            mDesc = desc.toCharArray();
        }
    }

    public boolean hasNext() {
        return mCursor < mLength;
    }

    /**
     * 获取下一个参数类型。
     * 如果是非基本类型，返回O
     *
     * @return
     */
    public char readNextLoadType() {
        char start = next();
        if (isPrimitive(start)) {
            return start;
        } else {
            moveToTypeEnd();
            return 'O';
        }
    }


    void reset() {
        mCursor = 0;
    }

    /**
     * 读取一个字符，并移动标记指针
     *
     * @return
     */
    private char next() {
        char next = mDesc[mCursor];
        mCursor++;
        return next;
    }


    private void moveToTypeEnd() {
        mCursor--;
        char cursor = next();
        char start = 'L';
        boolean isObject = false;
        while (!meetEnd(isObject, cursor)) {
            if (cursor == start) {
                isObject = true;
            }
            cursor = next();
        }
    }

    private boolean meetEnd(boolean isObject, char c) {
        char endMark = ';';
        //不是对象类型的描述，遇到基础描述符则到结尾
        if (!isObject && isPrimitive(c))
            return true;
        //是对象的，看对象描述符结尾
        if (endMark == c) {
            return true;
        }

        return false;
    }


    private boolean isPrimitive(char type) {
        switch (type) {
            case 'Z':
            case 'C':
            case 'B':
            case 'S':
            case 'I':
            case 'F':
            case 'J':
            case 'D':
                return true;
            default:
                return false;
        }
    }

    private int getLoadType(String type) {
        switch (type) {
            case "Z":
            case "C":
            case "B":
            case "S":
            case "I":
                return Opcodes.ILOAD;
            case "F":
                return Opcodes.FLOAD;
            case "J":
                return Opcodes.LLOAD;
            case "D":
                return Opcodes.DLOAD;
            default:
                return Opcodes.ALOAD;
        }


    }
}
