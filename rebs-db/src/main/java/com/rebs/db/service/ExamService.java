package com.rebs.db.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebs.db.dao.ExamMapper;
import com.rebs.db.domain.Answer;
import com.rebs.db.domain.Employee;
import com.rebs.db.domain.ErrorItem;
import com.rebs.db.domain.Exam;
import com.rebs.db.domain.ExamVo;
import com.rebs.db.domain.RandomExam;
import com.rebs.db.domain.RandomExamItem;
import com.rebs.db.domain.RandomExamItemView;
import com.rebs.db.domain.RandomExamResultAnswerCurrent;
import com.rebs.db.domain.RandomExamResultCurrent;
import com.rebs.db.domain.RandomExamResultTemp;
import com.rebs.db.domain.RandomExamSubject;
import com.rebs.db.domain.SystemExam;

@Service
//@Transactional
public class ExamService {

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ErrorService errorService;
    /**
     * 根据userId得到考试列表
     * @param UserID
     * @param orgID
     * @param serverNo 
     * @return
     */
    public ArrayList<Exam> callProcedureWX_USP_Exam_By_user(Integer UserID,Integer orgID,Integer serverNo) {
    	Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("P_USER_ID", UserID);
        map.put("P_ORG_ID", orgID);
        map.put("P_SERVER_NO", serverNo);
        map.put("P_EXAM_MODE_ID", 2);
//        map.put("P_EXAM_MODE_ID", 1);
        map.put("CUR_OUT", new ArrayList<Exam>());
        examMapper.callProcedureWX_USP_Exam_By_user(map);
        @SuppressWarnings("unchecked")
        ArrayList<Exam> cur_out = (ArrayList<Exam>)map.get("CUR_OUT");
        return cur_out;
    }
    
    /**
     * 查询考试规则
     * @param systemExamId
     * @return
     */
    public SystemExam getExamTips(Integer systemExamId) {
    	return examMapper.getExamTips(systemExamId);
    }
    
    /**
     * 获取考试人的信息
     * @param employeeID
     * @return
     */
    public Employee getExamUserInfo(Integer employeeID) {
    	return examMapper.getExamUserInfo(employeeID);
    }
    
    /**
     * 考试基本信息
     * @param examid
     * @return
     */
    public ArrayList<RandomExam> getExamInfoUSP_Random_Exam_G(Integer examid) {
    	Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("P_RANDOM_EXAM_ID", examid);
//        map.put("EXAM_MODE_ID", 2);
        map.put("CUR_OUT", new ArrayList<RandomExam>());
        examMapper.getExamInfoUSP_Random_Exam_G(map);
        @SuppressWarnings("unchecked")
        ArrayList<RandomExam> cur_out = (ArrayList<RandomExam>)map.get("CUR_OUT");
        return cur_out;
    }
    
    /**
     * 获取考试题目类型和各个分数
     * @param examid
     * @return
     */
    public ArrayList<RandomExamSubject> getExamTiFenShuUSP_Random_Exam_SUBJECT_Q(Integer examid) {
    	Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("P_RANDOM_EXAM_ID", examid);
        map.put("CUR_OUT", new ArrayList<RandomExamSubject>());
        examMapper.callUSP_Random_Exam_SUBJECT_Q(map);
        @SuppressWarnings("unchecked")
        ArrayList<RandomExamSubject> cur_out = (ArrayList<RandomExamSubject>)map.get("CUR_OUT");
        return cur_out;
    }
    
    /**
     * 获取考试题目
     * @param subjectId
     * @param randomExamResultId
     * @param year
     * @return
     */
    public RandomExamItemView getSubjectItemsr(Integer subjectId,Integer randomExamResultId, Integer year){
        RandomExamItemView randomExamItemView = new RandomExamItemView();
        randomExamItemView.setSuccess(0);
        randomExamItemView.setError(0);
    	ArrayList<RandomExamItem> items = this.getItems(subjectId, randomExamResultId, year);
    	ArrayList<RandomExamItem> arrayList = new ArrayList<RandomExamItem>();
    	ArrayList<RandomExamItem> danxuan = new ArrayList<RandomExamItem>();
    	ArrayList<RandomExamItem> duoxuan = new ArrayList<RandomExamItem>();
    	ArrayList<RandomExamItem> panduan = new ArrayList<RandomExamItem>();
        for (RandomExamItem randomExamItem : items) {
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
    public ArrayList<RandomExamItem> getItems(Integer subjectId,Integer randomExamResultId, Integer year){
    	Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("P_SUBJECT_ID", subjectId);
        map.put("P_RANDOM_EXAM_RESULT_ID", randomExamResultId);
        map.put("P_YEAR", year);
        map.put("CUR_OUT", new ArrayList<RandomExamItem>());
        examMapper.callUSP_random_exam_ITEM_G_Cur(map);
        @SuppressWarnings("unchecked")
        ArrayList<RandomExamItem> cur_out = (ArrayList<RandomExamItem>)map.get("CUR_OUT");
        return cur_out;
    }
    
    /**
     * 获取考试题目的参数
     * @param employeeID
     * @param examid
     * @return
     */
    public RandomExamResultCurrent getNowRandomExamResultInfo(Integer employeeID, Integer examid) {
    	Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("P_EMPLOYEE_ID", employeeID);
        map.put("P_RANDOM_EXAM_ID", examid);
        map.put("CUR_OUT", new ArrayList<RandomExamResultCurrent>());
        examMapper.callUSP_Random_EXAM_RESULT_Cur_N(map);
        @SuppressWarnings("unchecked")
        ArrayList<RandomExamResultCurrent> cur_out = (ArrayList<RandomExamResultCurrent>)map.get("CUR_OUT");
        return cur_out.get(0);
    }
    
    /**
     * 保存考试结果
     * @param examVo
     * @return
     */
    public ExamVo saveAnswerToDB(ExamVo examVo) {
    	RandomExamResultCurrent nowRandomExamResultInfo = this.getNowRandomExamResultInfo(examVo.getEmployeeId(),examVo.getExamId());
    	nowRandomExamResultInfo.setAutoScore(new BigDecimal(0));
    	nowRandomExamResultInfo.setBeginDateTime(examVo.getBeginTime());
    	nowRandomExamResultInfo.setCurrentDateTime(examVo.getEndTime());
    	nowRandomExamResultInfo.setExamTime(examVo.getUseExamTime() + getSecondBetweenTwoDate(examVo.getEndTime(),examVo.getBeginTime()));
    	nowRandomExamResultInfo.setEndDateTime(examVo.getEndTime());
    	nowRandomExamResultInfo.setScore(new BigDecimal(0));
    	nowRandomExamResultInfo.setOrganizationId(200);
    	nowRandomExamResultInfo.setMemo("");
    	nowRandomExamResultInfo.setStatusId(2);
    	nowRandomExamResultInfo.setCorrectRate(new BigDecimal(0));
    	nowRandomExamResultInfo.setMemo("");
    	// 错题数
        Integer errorItemCount = 0;
    	ArrayList<RandomExamItem> allLists = examVo.getAllLists();
    	for (RandomExamItem randomExamItem : allLists) {
//			String str2 = randomExamItem.getAnswered();
//			String[] str3 = str2.split("\\|");
//			String strPaperItemId = str3[0].toString();
//			String strTrueAnswer = str2.substring(strPaperItemId.length() + 1);
			RandomExamResultAnswerCurrent randomExamResultAnswer = new RandomExamResultAnswerCurrent();
			randomExamResultAnswer.setRandomExamResultId(nowRandomExamResultInfo.getRandomExamResultId());
			randomExamResultAnswer.setRandomExamItemId(randomExamItem.getRandomExamItemId());
			randomExamResultAnswer.setJudgeStatusId(0);
			randomExamResultAnswer.setJudgeRemark("");
			randomExamResultAnswer.setExamTime(0);
			randomExamResultAnswer.setAnswer(randomExamItem.getAnswered());
			// 执行每题的结果正确方法
			this.updateExamResultAnswerCurrent(randomExamResultAnswer);
			if(randomExamItem.getIsAnswer() == 2) {
				errorItemCount += 1;
                ErrorItem errorItem = new ErrorItem();
                errorItem.setAnswer(randomExamItem.getAnswered());
                errorItem.setItemId(randomExamItem.getItemId());
                errorItem.setType(examVo.getType());
                errorItem.setEmployeeId(examVo.getEmployeeId());
                errorItem.setExamId(examVo.getExamId());
//                errorService.insert(errorItem);
                
               // 根据employeeId,itemId,type 判断错题是否存在
                List<ErrorItem> selectError = errorService.selectError(errorItem);
                // 存在执行修改
                if(selectError != null && selectError.size() > 0){
                    errorItem.setErrorId(selectError.get(0).getErrorId());
                    errorService.updateByPrimaryKey(errorItem);
                }else{
                    errorService.insert(errorItem);
                }
			}
		}
    	examVo.setErrorItemCount(errorItemCount);
    	try {
    		//更新实时考试记录
    		this.updateRandomExamResultCurrent(nowRandomExamResultInfo);
    		//将实时考试记录（临时表）转存到中间考试成绩表和答卷表
    		Object randomExamResultID = this.RemoveResultAnswerCurrent(examVo.getRandomExamResultID());
    		examVo.setRandomExamResultIDReturn(randomExamResultID);
    		//删除登录信息
    		
		} catch (Exception e) {
			//删除登录信息
			
			//修改Random_Exam_Result_Current考试信息
			RandomExamResultCurrent randomExamResultCurrent = new RandomExamResultCurrent();
			randomExamResultCurrent.setRandomExamId(examVo.getExamId());
			randomExamResultCurrent.setExamineeId(examVo.getEmployeeId());
			examMapper.upDateRandomExamResultCurrent(randomExamResultCurrent);
			return null;
		}
    	
    	return examVo;
    }
    public Integer getSecondBetweenTwoDate(Date endTime,Date benginTime) {
    	long end = (endTime.getTime()) / 1000;
    	long bengin = (benginTime.getTime()) / 1000;
    	Integer endSeconds = new Integer((int)end);
    	Integer benginSeconds = new Integer((int)bengin);
    	Integer useSeconds = endSeconds - benginSeconds;
    	return useSeconds;
    }
    
    /**
     * 执行每题的结果正确方法
     * @param randomExamResultAnswer
     */
    public void updateExamResultAnswerCurrent(RandomExamResultAnswerCurrent randomExamResultAnswer) {
    	Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("P_RANDOM_EXAM_RESULT_ID", randomExamResultAnswer.getRandomExamResultId());
        map.put("P_RANDOM_EXAM_ITEM_ID", randomExamResultAnswer.getRandomExamItemId());
        map.put("P_ANSWER", randomExamResultAnswer.getAnswer());
        map.put("P_EXAM_TIME", randomExamResultAnswer.getExamTime());
        map.put("P_JUDGE_REMARK", randomExamResultAnswer.getJudgeRemark());
        examMapper.callUSP_Random_EXAM_ANSWER_Cur_U(map);
    }
    
    /**
     * 更新实时考试记录
     * @param nowRandomExamResultInfo
     * @return
     */
    public void updateRandomExamResultCurrent(RandomExamResultCurrent nowRandomExamResultInfo) {
    	Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("P_RANDOM_EXAM_RESULT_ID", nowRandomExamResultInfo.getRandomExamResultId());
        map.put("P_ORG_ID", nowRandomExamResultInfo.getOrgid());
        map.put("P_RANDOM_EXAM_ID", nowRandomExamResultInfo.getRandomExamId());
        map.put("P_EXAMINEE_ID", nowRandomExamResultInfo.getExamineeId());
        map.put("P_BEGIN_TIME", nowRandomExamResultInfo.getBeginTime());
        map.put("P_CURRENT_TIME", nowRandomExamResultInfo.getCurrentTime());
        map.put("P_END_TIME", nowRandomExamResultInfo.getEndTime());
        map.put("P_EXAM_TIME", nowRandomExamResultInfo.getExamTime());
        map.put("P_AUTO_SCORE", nowRandomExamResultInfo.getAutoScore());
        map.put("P_SCORE", nowRandomExamResultInfo.getScore());
        map.put("P_CORRECT_RATE", nowRandomExamResultInfo.getCorrectRate());
        map.put("P_IS_PASS", 4);
        map.put("P_STATUS_ID", nowRandomExamResultInfo.getStatusId());
        map.put("P_MEMO", nowRandomExamResultInfo.getMemo());
        map.put("P_EXAM_SEQ_NO", nowRandomExamResultInfo.getExamSeqNo());
        examMapper.callUSP_Random_EXAM_RESULT_Cur_U(map);
    }
    
    /**
     * 将实时考试记录（临时表）转存到中间考试成绩表和答卷表
     * @param randomExamResultID
     * @return
     */
    public Object RemoveResultAnswerCurrent(Integer randomExamResultID) {
    	Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("P_RANDOM_RESULT_ID", randomExamResultID);
        map.put("P_NEXT_ID", 4);
        examMapper.callUSP_Random_EXAM_Result_Re_C(map);
        Object cur_out = map.get("P_NEXT_ID");
        return cur_out;
//        RandomExamResultAnswerCurrent randomExamResultAnswerCurrent = cur_out.get(0);
//        return randomExamResultAnswerCurrent.getRandomExamResultId();
    }
    
    /**
     * 获取考试结果
     * @param examResultID
     * @return
     */
    public RandomExamResultTemp showExamResult(Integer examResultID) {
    	Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("P_RANDOM_EXAM_RESULT_ID", examResultID);
        map.put("CUR_OUT", new ArrayList<RandomExamResultTemp>());
        examMapper.callUSP_Random_EXAM_RESULT_s_Temp(map);
        @SuppressWarnings("unchecked")
		ArrayList<RandomExamResultTemp> cur_out = (ArrayList<RandomExamResultTemp>)map.get("CUR_OUT");
        return cur_out.get(0);
    }
    
    /**
     * 获取每一题
     * @param randomExamResultId
     * @param randomExamItemId
     * @return
     */
    public RandomExamResultAnswerCurrent getExamResultAnswerCurrent(Integer randomExamResultId,Integer randomExamItemId) {
    	Map<Object, Object> map = new HashMap<Object, Object>();
    	map.put("P_RANDOM_EXAM_RESULT_ID", randomExamResultId);
    	map.put("P_ITEM_ID", randomExamItemId);
    	map.put("CUR_OUT", new ArrayList<RandomExamResultAnswerCurrent>());
    	examMapper.callUSP_Random_EXAM_ANSWER_Cur_G1(map);
    	@SuppressWarnings("unchecked")
    	ArrayList<RandomExamResultAnswerCurrent> cur_out = (ArrayList<RandomExamResultAnswerCurrent>)map.get("CUR_OUT");
    	return cur_out.get(0);
    }
    
    /**
     * 获取当前考生的考试
     * @param randomExamResultId
     * @return
     */
    public RandomExamResultCurrent getRandomExamResult(ExamVo examVo) {
    	Map<Object, Object> map = new HashMap<Object, Object>();
    	map.put("P_RANDOM_EXAM_RESULT_ID", examVo.getRandomExamResultID());
    	map.put("CUR_OUT", new ArrayList<RandomExamResultCurrent>());
    	examMapper.callUSP_Random_EXAM_RESULT_Cur_G(map);
    	@SuppressWarnings("unchecked")
    	ArrayList<RandomExamResultCurrent> cur_out = (ArrayList<RandomExamResultCurrent>)map.get("CUR_OUT");
    	return cur_out.get(0);
    }
    
}
