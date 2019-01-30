package com.rebs.db.domain;

import lombok.Data;

/**
 * @author zb
 *  记录微信登录次数 练习时长
 */
@Data
public class WxLoginTime {

    private Integer employeeId;
    
    private Integer practiceTime;
    
    private Integer loginTime;
    
    private Integer courseWareTime;
    
    private Integer bookTime;
}
