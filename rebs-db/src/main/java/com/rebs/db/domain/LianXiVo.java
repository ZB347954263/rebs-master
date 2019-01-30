package com.rebs.db.domain;

import java.math.BigDecimal;
import java.util.ArrayList;

import lombok.Data;

/**
 * @author zb
 *  练习提交答案vo
 */
@Data
public class LianXiVo {
    
    /**
     * 类型1：练习
     */
    private Integer type;
    
    
    /**
     * 员工Id
     */
    private Integer employeeId;
    
    
    /**
     * 所有题目
     */
    private ArrayList<Item> allLists;
    
    
    
    // 错题数
    private Integer errorItemCount;
    // 正确题数
    private Integer rightItemCount;
    //总题数
    private Integer totalItem;
    //正确率
    private BigDecimal rightRate;
}
