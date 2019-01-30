package com.rebs.wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.rebs.db.domain.SystemUser;
import com.rebs.db.domain.SystemUserView;
import com.rebs.db.service.SystemUserService;

@Service
public class UserInfoService {
    
    
    @Autowired
    private SystemUserService systemUserService;


    public SystemUserView getInfo(String userId) {
        SystemUser user = systemUserService.selectByPrimaryKey(userId);
        Assert.state(user != null, "用户不存在");
        SystemUserView userInfo = new SystemUserView();
        userInfo.setUserId(user.getUserId());
        userInfo.setEmployeeId(user.getEmployeeId());
        userInfo.setMemo(user.getMemo());
        userInfo.setRoleId(user.getRoleId());
        return userInfo;
    }
}
