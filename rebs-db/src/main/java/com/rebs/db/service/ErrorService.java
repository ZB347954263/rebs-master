package com.rebs.db.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebs.db.dao.ErrorMapper;
import com.rebs.db.domain.Answer;
import com.rebs.db.domain.CollectionItem;
import com.rebs.db.domain.ErrorItem;
import com.rebs.db.domain.ErrorItemOfCategory;
import com.rebs.db.domain.Item;
import com.rebs.db.domain.ItemView;

@Service
public class ErrorService {

    @Autowired
    private ErrorMapper ErrorMapper;
    
    public int deleteByPrimaryKey(Integer id) {
        return ErrorMapper.deleteByPrimaryKey(id);
    };

    public int insert(ErrorItem record) {
        return ErrorMapper.insert(record);
    }

    public ErrorItem selectByPrimaryKey(Integer id) {
        return ErrorMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(ErrorItem record) {
        return ErrorMapper.updateByPrimaryKey(record);
    }
    
    public List<ErrorItem> selectError(ErrorItem record){
        return ErrorMapper.selectError(record);
    }
    
    /**
	 * 按教材分类查询错题数量
	 * @param errorItem
	 * @return
	 */
	public List<ErrorItemOfCategory> selectErrorCountOfBook(ErrorItem errorItem){
        return ErrorMapper.selectErrorCountOfBook(errorItem);
    }
	
	/**
	 * 按考试分类查询错题数量
	 * @param errorItem
	 * @return
	 */
	public List<ErrorItemOfCategory> selectErrorCountOfExam(ErrorItem errorItem){
        return ErrorMapper.selectErrorCountOfExam(errorItem);
    }
	
	/**
     *  获取试题
     * @param bookId
     * @param employeeId
     * @param type  ： 类型  1：练习  2：考试
     * @param typeId : 题型（单选、多选、判断、综合选择题）
     * @return
     */
    public ArrayList<Item> getItem(Integer bookId,Integer employeeId, Integer type, Integer typeId)
    {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("bookId", bookId);
        map.put("employeeId", employeeId);
        map.put("type", type);
        map.put("typeId", typeId);
        ArrayList<Item> selectErrorItemDetail = ErrorMapper.selectErrorItemDetail(map);
        return selectErrorItemDetail;
    }
    
    
    /**
     * 获取各类型（单选、多选、判断、综合选择题）题目
     * @param bookId
     * @param employeeId
     * @param type 类型  1：练习  2：考试
     * @return
     */
    public ItemView getItemView(Integer bookId,Integer employeeId,Integer type){
        ItemView itemView = new ItemView();
        //所有题目
        ArrayList<Item> allItemList = new ArrayList<Item>();
        //单选
        ArrayList<Item> items1 = this.getItem(bookId, employeeId, type, 1);
        for(int i=0;i<items1.size();i++) {
            Item item = items1.get(i);
            item.setIsAnswer(0);
            String strAnswer[] = item.getSelectAnswer() != null ? item.getSelectAnswer().split("\\|") : new String[]{};
            ArrayList<Answer> answerList = new ArrayList<Answer>();
            for (int j=0;j<strAnswer.length;j++) {
                Answer answer = new Answer();
                answer.setIndex(j);
                answer.setTip((char)(j+65) +"");
                answer.setContent(strAnswer[j]);
                answerList.add(answer);
            }
            if(item.getStandardAnswer() != null && item.getStandardAnswer() !="") {
                item.setStandardAnswesInt(Integer.valueOf(item.getStandardAnswer()));
            }
            // 错误答案
            String strErrorAnswer[] = item.getErrorAnswer() != null ? item.getErrorAnswer().split("\\|") : new String[]{};
            ArrayList<Answer> errorAnswerList = new ArrayList<Answer>();
            for (int j=0;j<strErrorAnswer.length;j++) {
                Answer answer = new Answer();
                answer.setIndex(j);
                answer.setTip((char)(Integer.parseInt(strErrorAnswer[j])+65) +"");
                answer.setContent(strErrorAnswer[j]);
                errorAnswerList.add(answer);
            }
            item.setErrorAnswerList(errorAnswerList);
            
            item.setAnswerList(answerList);
            item.setStrAnswer(strAnswer);
            allItemList.add(item);
        }
        //多选
        ArrayList<Item> items2 = this.getItem(bookId, employeeId, type, 2);
        for(int i=0;i<items2.size();i++) {
            Item item = items2.get(i);
            item.setIsAnswer(0);
            String strAnswer[] = item.getSelectAnswer() != null ? item.getSelectAnswer().split("\\|") : new String[]{};
            ArrayList<Answer> answerList = new ArrayList<Answer>();
            for (int j=0;j<strAnswer.length;j++) {
                Answer answer = new Answer();
                answer.setIndex(j);
                answer.setTip((char)(j+65) +"");
                answer.setContent(strAnswer[j]);
                answerList.add(answer);
            }
            
            String standardAnswers[] = item.getStandardAnswer() != null ? item.getStandardAnswer().split("\\|") : new String[]{};
            ArrayList<Answer> standardAnswerList = new ArrayList<Answer>();
            for (int j=0;j<standardAnswers.length;j++) {
                Answer answer = new Answer();
                answer.setIndex(j);
                answer.setTip((char)(Integer.valueOf(standardAnswers[j])+65) +"");
                answer.setContent(standardAnswers[j]);
                standardAnswerList.add(answer);
            }
            
            // 错误答案
            String strErrorAnswer[] = item.getErrorAnswer() != null ? item.getErrorAnswer().split("\\|") : new String[]{};
            ArrayList<Answer> manyErrorAnswerList = new ArrayList<Answer>();
            for (int j=0;j<strErrorAnswer.length;j++) {
                Answer answer = new Answer();
                answer.setIndex(j);
                answer.setTip((char)(Integer.parseInt(strErrorAnswer[j])+65) +"");
                answer.setContent(strErrorAnswer[j]);
                manyErrorAnswerList.add(answer);
            }
            item.setManyErrorAnswersList(manyErrorAnswerList);
            
            item.setStandardAnswerList(standardAnswerList);
            item.setAnswerList(answerList);
            item.setStrAnswer(strAnswer);
            allItemList.add(item);
        }
        //判断
        ArrayList<Item> items3 = this.getItem(bookId, employeeId, type, 3);
        for(int i=0;i<items3.size();i++) {
            Item item = items3.get(i);
            item.setIsAnswer(0);
            String strAnswer[] = item.getSelectAnswer() != null ? item.getSelectAnswer().split("\\|") : new String[]{};
            ArrayList<Answer> answerList = new ArrayList<Answer>();
            for (int j=0;j<strAnswer.length;j++) {
                Answer answer = new Answer();
                answer.setIndex(j);
                answer.setTip((char)(j+65) +"");
                answer.setContent(strAnswer[j]);
                answerList.add(answer);
            }
            if(item.getStandardAnswer() != null && item.getStandardAnswer() !="") {
                item.setStandardAnswesInt(Integer.valueOf(item.getStandardAnswer()));
            }
            
         // 错误答案
            String strErrorAnswer[] = item.getErrorAnswer() != null ? item.getErrorAnswer().split("\\|") : new String[]{};
            ArrayList<Answer> errorAnswerList = new ArrayList<Answer>();
            for (int j=0;j<strErrorAnswer.length;j++) {
                Answer answer = new Answer();
                answer.setIndex(j);
                answer.setTip((char)(Integer.parseInt(strErrorAnswer[j])+65) +"");
                answer.setContent(strErrorAnswer[j]);
                errorAnswerList.add(answer);
            }
            item.setErrorAnswerList(errorAnswerList);
            
            
            item.setAnswerList(answerList);
            item.setStrAnswer(strAnswer);
            allItemList.add(item);
        }
        //综合选择题
        ArrayList<Item> items4 = this.getItem(bookId, employeeId, type, 4);
        for(int i=0;i<items4.size();i++) {
            Item item = items4.get(i);
            item.setIsAnswer(0);
            String strAnswer[] = item.getSelectAnswer() != null ? item.getSelectAnswer().split("\\|") : new String[]{};
            ArrayList<Answer> answerList = new ArrayList<Answer>();
            for (int j=0;j<strAnswer.length;j++) {
                Answer answer = new Answer();
                answer.setIndex(j);
                answer.setTip((char)(j+65) +"");
                answer.setContent(strAnswer[j]);
                answerList.add(answer);
            }
            
         // 错误答案
            String strErrorAnswer[] = item.getErrorAnswer() != null ? item.getErrorAnswer().split("\\|") : new String[]{};
            ArrayList<Answer> manyErrorAnswerList = new ArrayList<Answer>();
            for (int j=0;j<strErrorAnswer.length;j++) {
                Answer answer = new Answer();
                answer.setIndex(j);
                answer.setTip((char)(Integer.parseInt(strErrorAnswer[j])+65) +"");
                answer.setContent(strErrorAnswer[j]);
                manyErrorAnswerList.add(answer);
            }
            item.setManyErrorAnswersList(manyErrorAnswerList);
            
            item.setAnswerList(answerList);
            item.setStrAnswer(strAnswer);
            allItemList.add(item);
        }
       
        itemView.setItemList1(items1);
        itemView.setItemList2(items2);
        itemView.setItemList3(items3);
        itemView.setItemList4(items4);
        itemView.setItems1(items1.size());
        itemView.setItems2(items2.size());
        itemView.setItems3(items3.size());
        itemView.setItems4(items4.size());
        itemView.setAllItemList(allItemList);
        itemView.setAllItemCount(itemView.getItems1()+itemView.getItems2()+itemView.getItems3()+itemView.getItems4());
        return itemView;
    }

    /**
     * 移除错题
     * @param itemId
     * @param employeeId
     * @param type
     */
	public int removeErrorItem(Integer itemId, Integer employeeId, Integer type) {
		// TODO Auto-generated method stub
		ErrorItem errorItem = new ErrorItem();
		errorItem.setItemId(itemId);
		errorItem.setEmployeeId(employeeId);
		errorItem.setType(type);
		return ErrorMapper.removeErrorItem(errorItem);
	}
    
}
