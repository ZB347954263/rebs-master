package com.rebs.db.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebs.db.dao.CollectionMapper;
import com.rebs.db.domain.Answer;
import com.rebs.db.domain.CollectionItem;
import com.rebs.db.domain.CollectionOfBook;
import com.rebs.db.domain.Item;
import com.rebs.db.domain.ItemView;

@Service
public class CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;
    
    public int deleteByPrimaryKey(Integer id) {
        return collectionMapper.deleteByPrimaryKey(id);
    };

    public int insert(CollectionItem collection) {
        return collectionMapper.insert(collection);
    }

    public CollectionItem selectByPrimaryKey(Integer id) {
        return collectionMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(CollectionItem collection) {
        return collectionMapper.updateByPrimaryKey(collection);
    }
    
    public List<CollectionItem> selectCollection(CollectionItem collection){
        return collectionMapper.selectCollection(collection);
    }

	public int cancelConllect(Integer itemId, Integer employeeId, Integer type) {
		CollectionItem collection = new CollectionItem();
		collection.setItemId(itemId);
		collection.setEmployeeId(employeeId);
		collection.setType(type);
		return collectionMapper.deleteByCollection(collection);
	}
	/**
	 * 查询收藏的题数通过书名分类
	 * @param collection
	 * @return
	 */
	public List<CollectionOfBook> selectCollectionCountOfBook(CollectionItem collection){
        return collectionMapper.selectCollectionCountOfBook(collection);
    }
	
	
	/**
     *  获取试题
     * @param bookId
     * @param typeId
     * @param employeeId
     * @return
     */
    public ArrayList<Item> getItem(Integer bookId,Integer employeeId, Integer typeId)
    {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("employeeId", employeeId);
        map.put("bookId", bookId);
        map.put("typeId", typeId);
        ArrayList<Item> selectCollectionItemDetail = collectionMapper.selectCollectionItemDetail(map);
        return selectCollectionItemDetail;
    }
    
    
    /**
     * 获取各类型（单选、多选、判断、综合选择题）题目
     * @param bookId
     * @param chapterId
     * @return
     */
    public ItemView getItemView(Integer bookId,Integer employeeId){
        ItemView itemView = new ItemView();
        //所有题目
        ArrayList<Item> allItemList = new ArrayList<Item>();
        //单选
        ArrayList<Item> items1 = this.getItem(bookId, employeeId, 1);
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
            item.setAnswerList(answerList);
            item.setStrAnswer(strAnswer);
            allItemList.add(item);
        }
        //多选
        ArrayList<Item> items2 = this.getItem(bookId, employeeId, 2);
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
            item.setStandardAnswerList(standardAnswerList);
            item.setAnswerList(answerList);
            item.setStrAnswer(strAnswer);
            allItemList.add(item);
        }
        //判断
        ArrayList<Item> items3 = this.getItem(bookId, employeeId, 3);
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
            item.setAnswerList(answerList);
            item.setStrAnswer(strAnswer);
            allItemList.add(item);
        }
        //综合选择题
        ArrayList<Item> items4 = this.getItem(bookId, employeeId, 4);
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
}
