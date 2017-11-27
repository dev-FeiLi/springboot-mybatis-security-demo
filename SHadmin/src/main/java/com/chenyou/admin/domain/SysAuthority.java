package com.chenyou.admin.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/8.
 */
public class SysAuthority implements Serializable {
    private Long authId;
    private String authName;
    private String authUrl;
    private Long authParent;
    private String authSort;
    private String authDescription;

    public SysAuthority() {
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public Long getAuthParent() {
        return authParent;
    }

    public void setAuthParent(Long authParent) {
        this.authParent = authParent;
    }

    public String getAuthSort() {
        return authSort;
    }

    public void setAuthSort(String authSort) {
        this.authSort = authSort;
    }

    public String getAuthDescription() {
        return authDescription;
    }

    public void setAuthDescription(String authDescription) {
        this.authDescription = authDescription;
    }

    @Override
    public String toString() {
        return "SysAuthority{" +
                "authId=" + authId +
                ", authName='" + authName + '\'' +
                ", authUrl='" + authUrl + '\'' +
                ", authParent=" + authParent +
                ", authSort='" + authSort + '\'' +
                ", authDescription='" + authDescription + '\'' +
                '}';
    }
}
