package com.ripple.permission.plugin.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author: fanyafeng
 * Data: 2020/4/13 20:18
 * Email: fanyafeng@live.cn
 * Description: 权限注解的实体model类
 */
public class PermissionAnnoModel implements Serializable {
    private ArrayList<String> permissionList = new ArrayList<>();
    private String failMethodName;

    public PermissionAnnoModel() {
    }

    public PermissionAnnoModel(ArrayList<String> permissionList, String failMethodName) {
        this.permissionList = permissionList;
        this.failMethodName = failMethodName;
    }

    public ArrayList<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(ArrayList<String> permissionList) {
        this.permissionList = permissionList;
    }

    public String getFailMethodName() {
        return failMethodName;
    }

    public void setFailMethodName(String failMethodName) {
        this.failMethodName = failMethodName;
    }

    @Override
    public String toString() {
        return "PermissionAnnoModel{" +
                "permissionList=" + permissionList +
                ", failMethodName='" + failMethodName + '\'' +
                '}';
    }
}
