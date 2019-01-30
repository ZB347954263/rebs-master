package com.rebs.db.domain;

import java.util.ArrayList;

import lombok.Data;

/**
 * @author zb
 * 练习题目vo
 */
@Data
public class ItemView {

    private ArrayList<Item> allItemList;
    
    private Integer allItemCount;
    
    private Integer items1;
    private Integer items2;
    private Integer items3;
    private Integer items4;
    
    private ArrayList<Item> itemList1;
    private ArrayList<Item> itemList2;
    private ArrayList<Item> itemList3;
    private ArrayList<Item> itemList4;

}
