package com.rebs.wx.dao;

import com.rebs.db.domain.SystemUserView;

public class WxLoginInfo {
    private String code;
    private SystemUserView userInfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SystemUserView getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(SystemUserView userInfo) {
        this.userInfo = userInfo;
    }
}
