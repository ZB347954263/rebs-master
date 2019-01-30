package com.rebs.db.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebs.db.dao.PastExamMapper;
import com.rebs.db.domain.Answer;
import com.rebs.db.domain.PastExamIN;
import com.rebs.db.domain.RandomExamItem;
import com.rebs.db.domain.RandomExamItemView;
import com.rebs.db.domain.RandomExamResultCurrent;

@Service
//@Transactional
public class PastExamService {

    @Autowired
    private PastExamMapper pastExamMapper;

    /**
     * 根据userId得到已考试列表
     * @param UserID
     * @param orgID
     * @param serverNo 
     * @return
     */
    public ArrayList<RandomExamResultCurrent> getPastExamByUserId(PastExamIN pastExamIn) {
    	return pastExamMapper.getPastExamByUserId(pastExamIn);
    }
    
    /**
     * 获取历史考试题目
     * @param subjectId
     * @param randomExamResultId
     * @param year
     * @return
     */
    public RandomExamItemView getSubjectItemsr(Integer examid,Integer randomExamResultId,Integer employeeId,Integer year){
    	RandomExamItemView randomExamItemView = new RandomExamItemView();
        randomExamItemView.setSuccess(0);
        randomExamItemView.setError(0);
		ArrayList<RandomExamItem> TotalItems = this.getItems(0, randomExamResultId, year);
		ArrayList<RandomExamItem> arrayList = new ArrayList<RandomExamItem>();
    	ArrayList<RandomExamItem> danxuan = new ArrayList<RandomExamItem>();
    	ArrayList<RandomExamItem> duoxuan = new ArrayList<RandomExamItem>();
    	ArrayList<RandomExamItem> panduan = new ArrayList<RandomExamItem>();
    	for (RandomExamItem randomExamItem : TotalItems) {
        	Integer typeId = randomExamItem.getTypeId();
        	randomExamItem.setIsAnswer(0);
        	String answers = randomExamItem.getAnswer();//用户选择的答案
        	String standardAnswer = randomExamItem.getStandardAnswer();// 准确答案
        	if(answers != null && answers != "") {
        		randomExamItem.setIsNoFirst(true);
    			randomExamItem.setAnswered(answers);
    			if(answers.equals(standardAnswer)) {
    				randomExamItemView.setSuccess(randomExamItemView.getSuccess()+1);
    				randomExamItem.setIsAnswer(1);
    			}else {
    				randomExamItem.setIsAnswer(2);
    				randomExamItemView.setError(randomExamItemView.getError()+1);
    			}
    		}
        	if(typeId == 1) {// 单选
        		//得到选项
        		String strAnswer[] = randomExamItem.getSelectAnswer() != null ? randomExamItem.getSelectAnswer().split("\\|") : new String[]{};
        		ArrayList<Answer> answerList = new ArrayList<Answer>();
        		ArrayList<Answer> options = new ArrayList<Answer>();
        		for (int j=0;j<strAnswer.length;j++) {
        			Answer answer = new Answer();
        			answer.setIndex(j);
        			answer.setTip((char)(j+65) +"");
        			answer.setContent(strAnswer[j]);
        			if(answers != null && answers != "") {
        				if(answers.contains(j+"")) {
        					answer.setIsSelect(true);
        				}
        			}
        			answerList.add(answer);
        			options.add(answer);
        		}
        		if(randomExamItem.getStandardAnswer() != null && randomExamItem.getStandardAnswer() != "") {
        			randomExamItem.setStandardAnswesInt(Integer.valueOf(randomExamItem.getStandardAnswer()));
                }
        		randomExamItem.setOptions(options);
        		randomExamItem.setStrAnswer(strAnswer);
        		randomExamItem.setAnswerList(answerList);
//        		arrayList.add(randomExamItem);
        		danxuan.add(randomExamItem);
        	}
        	if(typeId == 2) {// 多选
        		//得到选项
        		String strAnswer[] = randomExamItem.getSelectAnswer() != null ? randomExamItem.getSelectAnswer().split("\\|") : new String[]{};
        		ArrayList<Answer> answerList = new ArrayList<Answer>();
        		ArrayList<Answer> options = new ArrayList<Answer>();
        		for (int j=0;j<strAnswer.length;j++) {
        			Answer answer = new Answer();
        			answer.setIndex(j);
        			answer.setTip((char)(j+65) +"");
        			answer.setContent(strAnswer[j]);
        			if(answers != null && answers != "") {
        				if(answers.contains(j+"")) {
        					answer.setIsSelect(true);
        				}
        			}
        			answerList.add(answer);
        			options.add(answer);
        		}
        		randomExamItem.setOptions(options);
        		randomExamItem.setStrAnswer(strAnswer);
        		randomExamItem.setAnswerList(answerList);
        		//得到答案
        		String standardAnswers[] = randomExamItem.getStandardAnswer() != null ? randomExamItem.getStandardAnswer().split("\\|") : new String[]{};
                ArrayList<Answer> standardAnswerList = new ArrayList<Answer>();
                for (int j=0;j<standardAnswers.length;j++) {
                    Answer answer = new Answer();
                    answer.setIndex(j);
                    answer.setTip((char)(Integer.valueOf(standardAnswers[j])+65) +"");
                    answer.setContent(standardAnswers[j]);
                    standardAnswerList.add(answer);
                }
                randomExamItem.setStandardAnswerList(standardAnswerList);
//                arrayList.add(randomExamItem);
                duoxuan.add(randomExamItem);
        	}
        	if(typeId == 3) {// 判断
        		String strAnswer[] = randomExamItem.getSelectAnswer() != null ? randomExamItem.getSelectAnswer().split("\\|") : new String[]{};
                ArrayList<Answer> answerList = new ArrayList<Answer>();
                ArrayList<Answer> options = new ArrayList<Answer>();
                for (int j=0;j<strAnswer.length;j++) {
                    Answer answer = new Answer();
                    answer.setIndex(j);
                    answer.setTip((char)(j+65) +"");
                    answer.setContent(strAnswer[j]);
                    if(answers != null && answers != "") {
        				if(answers.contains(j+"")) {
        					answer.setIsSelect(true);
        				}
        			}
                    answerList.add(answer);
                    options.add(answer);
                }
                if(randomExamItem.getStandardAnswer() != null && randomExamItem.getStandardAnswer() !="") {
                	randomExamItem.setStandardAnswesInt(Integer.valueOf(randomExamItem.getStandardAnswer()));
                }
                randomExamItem.setOptions(options);
                randomExamItem.setAnswerList(answerList);
                randomExamItem.setStrAnswer(strAnswer);
//                arrayList.add(randomExamItem);
                panduan.add(randomExamItem);
        	}
//        	if() {}// 综合
		}
        arrayList.addAll(danxuan);
        arrayList.addAll(duoxuan);
        arrayList.addAll(panduan);
        randomExamItemView.setAllItemList(arrayList);
		
		return randomExamItemView;
    }
    
    /**
     * 根据考试randomExamResultId获取题目
     * @param subjectId
     * @param randomExamResultId
     * @param year
     * @return
     */
    public ArrayList<RandomExamItem> getItems(Integer subjectId,Integer randomExamResultId, Integer year){
    	Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("P_SUBJECT_ID", subjectId);
        map.put("P_RANDOM_EXAM_RESULT_ID", randomExamResultId);
        map.put("P_YEAR", year);
        map.put("CUR_OUT", new ArrayList<RandomExamItem>());
        pastExamMapper.callUSP_random_exam_ITEM_G(map);
        @SuppressWarnings("unchecked")
        ArrayList<RandomExamItem> cur_out = (ArrayList<RandomExamItem>)map.get("CUR_OUT");
        return cur_out;
    }
}
