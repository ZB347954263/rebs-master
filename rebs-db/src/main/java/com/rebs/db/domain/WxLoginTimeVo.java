package com.rebs.db.domain;

import java.util.Date;

import lombok.Data;

/**
 * @author zb
 *  记录微信登录次数 练习时长
 */
@Data
public class WxLoginTimeVo extends WxLoginTime{
  
    private Date beginTime;
    
    private Date endTime;
}
