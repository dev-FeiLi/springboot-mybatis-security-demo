package com.chenyou.admin.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/8.
 */
public class SysManage implements Serializable {
    private Long manId;
    private String manAccount;
    private String manPasswd;
    private String manName;
    private Date manAddTime;
    private Date manLoginTime;
    private String manLoginIp;
    private Date manLastTime;
    private String manLastIp;
    private String manStatus;
    private Long manRole;
    private Long manVersion;
    private String menuPos;

    public SysManage() {
    }


    public Long getManId() {
        return manId;
    }

    public void setManId(Long manId) {
        this.manId = manId;
    }

    public String getManAccount() {
        return manAccount;
    }

    public void setManAccount(String manAccount) {
        this.manAccount = manAccount;
    }

    public String getManPasswd() {
        return manPasswd;
    }

    public void setManPasswd(String manPasswd) {
        this.manPasswd = manPasswd;
    }

    public String getManName() {
        return manName;
    }

    public void setManName(String manName) {
        this.manName = manName;
    }


    public Date getManAddTime() {
        return manAddTime;
    }

    public void setManAddTime(Date manAddTime) {
        this.manAddTime = manAddTime;
    }


    public Date getManLoginTime() {
        return manLoginTime;
    }

    public void setManLoginTime(Date manLoginTime) {
        this.manLoginTime = manLoginTime;
    }

    public String getManLoginIp() {
        return manLoginIp;
    }

    public void setManLoginIp(String manLoginIp) {
        this.manLoginIp = manLoginIp;
    }

    public Date getManLastTime() {
        return manLastTime;
    }

    public void setManLastTime(Date manLastTime) {
        this.manLastTime = manLastTime;
    }

    public String getManLastIp() {
        return manLastIp;
    }

    public void setManLastIp(String manLastIp) {
        this.manLastIp = manLastIp;
    }

    public String getManStatus() {
        return manStatus;
    }

    public void setManStatus(String manStatus) {
        this.manStatus = manStatus;
    }

    public Long getManRole() {
        return manRole;
    }

    public void setManRole(Long manRole) {
        this.manRole = manRole;
    }

    public Long getManVersion() {
        return manVersion;
    }

    public void setManVersion(Long manVersion) {
        this.manVersion = manVersion;
    }

    public String getMenuPos() {
        return menuPos;
    }

    public void setMenuPos(String menuPos) {
        this.menuPos = menuPos;
    }

    @Override
    public String toString() {
        return "SysManage{" +
                "manId=" + manId +
                ", manAccount='" + manAccount + '\'' +
                ", manPasswd='" + manPasswd + '\'' +
                ", manName='" + manName + '\'' +
                ", manAddTime=" + manAddTime +
                ", manLoginTime=" + manLoginTime +
                ", manLoginIp='" + manLoginIp + '\'' +
                ", manLastTime=" + manLastTime +
                ", manLastIp='" + manLastIp + '\'' +
                ", manStatus='" + manStatus + '\'' +
                ", manRole=" + manRole +
                ", manVersion=" + manVersion +
                ", menuPos='" + menuPos + '\'' +
                '}';
    }
}
