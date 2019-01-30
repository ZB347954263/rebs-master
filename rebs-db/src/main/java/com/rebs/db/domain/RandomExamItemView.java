package com.rebs.db.domain;

import java.util.ArrayList;

import lombok.Data;

/**
 * @author zb
 * 考试题目 vo
 */
@Data
public class RandomExamItemView {

    private ArrayList<RandomExamItem> allItemList;
    
    private Integer allItemCount;
    
    private Integer items1;
    private Integer items2;
    private Integer items3;
    private Integer items4;
    private Integer success;
    private Integer error;
 
    private ArrayList<RandomExamItem> randomExamItemList1;
    private ArrayList<RandomExamItem> randomExamItemList2;
    private ArrayList<RandomExamItem> randomExamItemList3;
    private ArrayList<RandomExamItem> randomExamItemList4;
}
