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
import com.rebs.db.domain.PastExamIN;
import com.rebs.db.domain.RandomExamItemView;
import com.rebs.db.domain.RandomExamResultCurrent;
import com.rebs.db.service.PastExamService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/wx/pastExam")
@Validated
public class PastExamController {
	
	private final Log logger = LogFactory.getLog(PastExamController.class);
	
	@Autowired
	private PastExamService pastExamService;
	
	/**
	 * 根据userId得到已考试列表
	 * @param UserID
	 * @param orgID
	 * @param serverNo
	 * @return
	 */
	@PostMapping("/getPastExamByUserId")
	public Object getPastExamByUserId(@ApiParam @RequestBody PastExamIN pastExamIn){
		Map<String, Object> data = new HashMap<String, Object>();
		ArrayList<RandomExamResultCurrent> examList = pastExamService.getPastExamByUserId(pastExamIn);
		for (int i = 0;i<examList.size();i++) {
			RandomExamResultCurrent randomExamResultCurrent = examList.get(i);
			Integer examTime = randomExamResultCurrent.getExamTime();
			String secToTime = secToTime(examTime);
			randomExamResultCurrent.setExamUseTime(secToTime);
			examList.set(i, randomExamResultCurrent);
		}
        data.put("examList", examList);
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
	 * 获取历史考试试卷
	 * @param randomExamId
	 * @param employeeId
	 * @return
	 */
	@GetMapping("/getPastExam")
	public Object getPastExam(Integer examid,Integer randomExamResultId,Integer employeeId,Integer year) {
		Map<String, Object> data = new HashMap<String, Object>();
		RandomExamItemView itemView = pastExamService.getSubjectItemsr(examid,randomExamResultId,employeeId,year);
        data.put("itemView", itemView);
		return ResponseUtil.ok(data);
	}
}
