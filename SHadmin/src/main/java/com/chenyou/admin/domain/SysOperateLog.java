package com.chenyou.admin.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/8.
 */
public class SysOperateLog implements Serializable {
    private Long optId;
    private Date optAddTime;
    private Long optManId;
    private String optAccount;
    private String optIp;
    private String optUrl;
    private String optTagValue;
    private Long optStart;
    private Long optEnd;
    private Long optSecond;

    public SysOperateLog() {
    }


    public Long getOptId() {
        return optId;
    }

    public void setOptId(Long optId) {
        this.optId = optId;
    }

    public Date getOptAddTime() {
        return optAddTime;
    }

    public void setOptAddTime(Date optAddTime) {
        this.optAddTime = optAddTime;
    }

    public Long getOptManId() {
        return optManId;
    }

    public void setOptManId(Long optManId) {
        this.optManId = optManId;
    }

    public String getOptAccount() {
        return optAccount;
    }

    public void setOptAccount(String optAccount) {
        this.optAccount = optAccount;
    }

    public String getOptIp() {
        return optIp;
    }

    public void setOptIp(String optIp) {
        this.optIp = optIp;
    }

    public String getOptUrl() {
        return optUrl;
    }

    public void setOptUrl(String optUrl) {
        this.optUrl = optUrl;
    }

    public String getOptTagValue() {
        return optTagValue;
    }

    public void setOptTagValue(String optTagValue) {
        this.optTagValue = optTagValue;
    }

    public Long getOptSecond() {
        return optSecond;
    }

    public void setOptSecond(Long optSecond) {
        this.optSecond = optSecond;
    }

    public Long getOptStart() {
        return optStart;
    }

    public void setOptStart(Long optStart) {
        this.optStart = optStart;
    }

    public Long getOptEnd() {
        return optEnd;
    }

    public void setOptEnd(Long optEnd) {
        this.optEnd = optEnd;
    }

    @Override
    public String toString() {
        return "SysOperateLog{" +
                "optId=" + optId +
                ", optAddTime=" + optAddTime +
                ", optManId=" + optManId +
                ", optAccount='" + optAccount + '\'' +
                ", optIp='" + optIp + '\'' +
                ", optUrl='" + optUrl + '\'' +
                ", optTagValue='" + optTagValue + '\'' +
                ", optStart=" + optStart +
                ", optEnd=" + optEnd +
                ", optSecond=" + optSecond +
                '}';
    }
}
