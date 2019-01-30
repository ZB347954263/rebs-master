package com.rebs.wx.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebs.core.util.ResponseUtil;
import com.rebs.db.domain.Employee;
import com.rebs.db.domain.Exam;
import com.rebs.db.domain.ExamVo;
import com.rebs.db.domain.RandomExam;
import com.rebs.db.domain.RandomExamItemView;
import com.rebs.db.domain.RandomExamResultAnswerCurrent;
import com.rebs.db.domain.RandomExamResultCurrent;
import com.rebs.db.domain.RandomExamResultTemp;
import com.rebs.db.domain.RandomExamSubject;
import com.rebs.db.domain.SystemExam;
import com.rebs.db.service.ExamService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/wx/exam")
@Validated
public class ExamController {
	

    @Autowired
    private ExamService examService;
	
	private final Log logger = LogFactory.getLog(ExamController.class);
	
	/**
	 * 根据userId得到考试列表
	 * @param UserID
	 * @param orgID
	 * @param serverNo
	 * @return
	 */
	@GetMapping("/getExamByUserId")
	public Object getExamByUserId(Integer UserID,Integer orgID,Integer serverNo){
		ArrayList<Exam> examList = examService.callProcedureWX_USP_Exam_By_user(UserID, orgID, serverNo);
		Map<String, Object> data = new HashMap<String, Object>();
        data.put("examList", examList);
        return ResponseUtil.ok(data);
	}
	
	/**
	 * 查询考试规则
	 * @param systemExamId
	 * @return
	 */
	@GetMapping("/getExamTips")
	public Object getExamTips(Integer systemExamId){
		SystemExam systemExam = examService.getExamTips(systemExamId);
		Map<String, Object> data = new HashMap<String, Object>();
        data.put("systemExam", systemExam);
        return ResponseUtil.ok(data);
	}
	
	/**
	 * 考试基本信息
	 * @param examid
	 * @return
	 */
	@GetMapping("/getExamInfo")
	public Object getExamInfo(Integer employeeID, Integer examid) {
		Map<String, Object> data = new HashMap<String, Object>();
		// 获取考试人的信息
		Employee examUserInfo = examService.getExamUserInfo(employeeID);
		data.put("examUserInfo", examUserInfo);
		// 获取考试基本信息
		ArrayList<RandomExam> randomExam = examService.getExamInfoUSP_Random_Exam_G(examid);
		data.put("randomExam", randomExam.get(0));
		// 获取考试题数和分数
		ArrayList<RandomExamSubject> randomExamSubject = examService.getExamTiFenShuUSP_Random_Exam_SUBJECT_Q(examid);
        data.put("randomExamSubject", randomExamSubject);
        
        //获取考试题目的参数
        RandomExamResultCurrent randomExamResultCurrent = examService.getNowRandomExamResultInfo(employeeID,examid);
        data.put("randomExamResultCurrent", randomExamResultCurrent);
		return ResponseUtil.ok(data);
	}
	
	/**
	 * 获取考试题目
	 * @param subjectId
	 * @param randomExamResultId
	 * @param year
	 * @return 
	 */
	@GetMapping("/getSubjectItems")
	public Object getSubjectItems(Integer subjectId,Integer randomExamResultId, Integer year) {
		Map<String, Object> data = new HashMap<String, Object>();
		RandomExamItemView itemView = examService.getSubjectItemsr(subjectId,randomExamResultId,year);
        data.put("itemView", itemView);
        
		return ResponseUtil.ok(data);
	}
	
	/**
	 * 保存考试结果
	 * @return
	 */
	@PostMapping("/saveAnswerToDB")
	public Object saveAnswerToDB(@ApiParam @RequestBody ExamVo examVo) {
		Map<String, Object> data = new HashMap<String, Object>();
		ExamVo exam = examService.saveAnswerToDB(examVo);
		if(exam == null) {
			return ResponseUtil.fail(300, "提交试卷失败，请重新进入考试再次进行提交");
		}
	    data.put("examVo", exam);
		return ResponseUtil.ok(data);
	}
	
	/**
	 * 获取考试结果
	 * @param examResultID
	 * @return
	 */
	@GetMapping("/showExamResult")
	public Object showExamResult(Integer examResultID) {
		Map<String, Object> data = new HashMap<String, Object>();
		RandomExamResultTemp result = examService.showExamResult(examResultID);
		// 获取考试基本信息
		ArrayList<RandomExam> randomExam = examService.getExamInfoUSP_Random_Exam_G(result.getRandomExamId());
		data.put("examResult", result);
		Integer examTime = result.getExamTime();
		String secToTime = secToTime(examTime);
		data.put("secToTime", secToTime);
		data.put("randomExam", randomExam);
		return ResponseUtil.ok(data);
	}
	public String secToTime(Integer time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00时00分00秒";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + "分" + unitFormat(second)+ "秒";
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "99时59分59秒";
                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + "时" + unitFormat(minute) + "分" + unitFormat(second)+ "秒";
            }
        }
        return timeStr;
    }
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
	
	/**
	 * 点击一题保存记录
	 * @param randomExamItem
	 * @return
	 * 
	 */
	@PostMapping("/saveAnswerChangeCallBack")
	public Object itemAnswerChangeCallBack_Callback(@ApiParam @RequestBody ExamVo examVo) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			// 得到每一题
			RandomExamResultAnswerCurrent examResult = examService.getExamResultAnswerCurrent(examVo.getRandomExamResultID(),examVo.getRandomExamItem().getRandomExamItemId());
			examResult.setJudgeStatusId(0);
			examResult.setJudgeRemark("");
			examResult.setExamTime(0);
			examResult.setAnswer(examVo.getRandomExamItem().getAnswered());
			// 执行每题的结果正确方法
			examService.updateExamResultAnswerCurrent(examResult);
			// 获取当前考生的考试
			RandomExamResultCurrent randomExamResultCurrent = examService.getRandomExamResult(examVo);
			randomExamResultCurrent.setExamTime(examVo.getUseExamTime() + examService.getSecondBetweenTwoDate(examVo.getEndTime(),examVo.getBeginTime()));
			//更新实时考试记录
			examService.updateRandomExamResultCurrent(randomExamResultCurrent);
		} catch (Exception e) {
			return ResponseUtil.fail(300, "考试出现异常，请重启微机重新登录考试！");
		}
		return ResponseUtil.ok(data);
	}
	

}
