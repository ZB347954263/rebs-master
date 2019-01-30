package com.rebs.wx.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebs.core.notify.NotifyService;
import com.rebs.core.notify.NotifyType;
import com.rebs.core.util.CharUtil;
import com.rebs.core.util.JacksonUtil;
import com.rebs.core.util.ResponseUtil;
import com.rebs.db.domain.Employee;
import com.rebs.db.domain.Org;
import com.rebs.db.domain.SystemUserView;
import com.rebs.db.domain.WxLoginTime;
import com.rebs.db.domain.WxSystem;
import com.rebs.db.service.EmployeeService;
import com.rebs.db.service.OrgService;
import com.rebs.db.service.SystemUserService;
import com.rebs.db.service.WxLoginTimeService;
import com.rebs.db.service.WxSystemService;
import com.rebs.wx.dao.UserToken;
import com.rebs.wx.dao.WxLoginInfo;
import com.rebs.wx.service.CaptchaCodeManager;
import com.rebs.wx.service.UserTokenManager;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;

@RestController
@RequestMapping("/wx/auth")
@Validated
public class WxAuthController {
    private final Log logger = LogFactory.getLog(WxAuthController.class);

    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private OrgService orgService;
    
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WxMaService wxService;

    @Autowired
    private NotifyService notifyService;
    
    @Autowired
    private WxLoginTimeService wxLoginTimeService;
    
    @Autowired
    private WxSystemService wxSystemService;
    

    /**
     * 账号登录
     *
     * @param body    请求内容，{ username: xxx, password: xxx }
     * @param request 请求对象
     * @return 登录结果
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * token: xxx,
     * tokenExpire: xxx,
     * userInfo: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @RequestMapping("login")
    public Object login(@RequestBody String body, HttpServletRequest request) {
        String workNo = JacksonUtil.parseString(body, "workNo");
        String password = JacksonUtil.parseString(body, "password");
        if (workNo == null || workNo == "" || password == null || password == "") {
            return ResponseUtil.fail(300, "工号/身份证和密码不能为空");
        }
        //根据工号查询员工用户信息
        List<SystemUserView> userList = systemUserService.selectByWorkNo(workNo);
        SystemUserView user = null;
        if(userList == null || userList.size()==0) {
            return ResponseUtil.fail(300, "工号/身份证不存在，登录失败");
        }
        if(userList.size()>1) {
            return ResponseUtil.fail(300, "工号/身份证在系统中存在多个，登录失败");
        }
        user = userList.get(0);
        ArrayList<String> orgNames = new ArrayList<String>();
        if(user.getOrgIdPath() != null && user.getOrgIdPath() != "") {
           String orgIdPath[] = user.getOrgIdPath().split("/");
           for(int i=1;i<orgIdPath.length;i++){
               if(orgIdPath[i] != null && orgIdPath[i] != "") {
                   Org org = orgService.selectByPrimaryKey(orgIdPath[i]);
                   orgNames.add(org.getFullName());
               }
           }
        }
        user.setOrgNames(orgNames);
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        if (!encoder.matches(password, user.getPassword())) {
//            return ResponseUtil.fail(403, "账号密码不对");
//        }
        if (!password.equals(user.getPassword())) {
            return ResponseUtil.fail(300, "账号密码不对");
        }
        
        //更新登录次数
        if(user.getEmployeeId() != null) {
            WxLoginTime wxLoginTime  = wxLoginTimeService.selectByPrimaryKey(user.getEmployeeId());
            if(wxLoginTime == null) {
                wxLoginTime = new WxLoginTime();
                wxLoginTime.setEmployeeId(user.getEmployeeId());
                wxLoginTime.setLoginTime(1);
                wxLoginTimeService.insert(wxLoginTime);
            }
            else {
                wxLoginTime.setLoginTime(wxLoginTime.getLoginTime() + 1);
                wxLoginTimeService.updateByPrimaryKeySelective(wxLoginTime);
            }
        }
        // token
        UserToken userToken = UserTokenManager.generateToken(user.getUserId());
        
        //fileUrl
        List<WxSystem> selectByKeyName = wxSystemService.selectByKeyName("FILE_URL");
        if(selectByKeyName != null && selectByKeyName.size() > 0){
        	user.setFileUrl(selectByKeyName.get(0).getKeyValue());
        }
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", userToken.getToken());
        result.put("tokenExpire", userToken.getExpireTime().toString());
        result.put("userInfo", user);
        
        return ResponseUtil.ok(result);
    }

    /**
     * 微信登录
     *
     * @param wxLoginInfo 请求内容，{ code: xxx, userInfo: xxx }
     * @param request     请求对象
     * @return 登录结果
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * token: xxx,
     * tokenExpire: xxx,
     * userInfo: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @RequestMapping("login_by_weixin")
    public Object loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        if (code == null || code == "") {
            return ResponseUtil.fail(300, "你的微信未登录");
        }
        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail(300, "注册获取微信openId异常");
        }

        if (sessionKey == null || openId == null) {
            return ResponseUtil.fail(300, "获取微信openId失败");
        }
         
        List<SystemUserView> userList = systemUserService.selectByOpenId(openId);
       
        
        if(userList == null || userList.size()==0) {
            return ResponseUtil.fail(300, "你的微信没有注册，请先注册");
        }
        if(userList.size()>1) {
            return ResponseUtil.fail(300, "你的微信在系统中存在多个，不能登录");
        }
        SystemUserView userInfo = userList.get(0);
        ArrayList<String> orgNames = new ArrayList<String>();
        if(userInfo.getOrgIdPath() != null && userInfo.getOrgIdPath() != "") {
           String orgIdPath[] = userInfo.getOrgIdPath().split("/");
           for(int i=1;i<orgIdPath.length;i++){
               if(orgIdPath[i] != null && orgIdPath[i] != "") {
                   Org org = orgService.selectByPrimaryKey(orgIdPath[i]);
                   orgNames.add(org.getFullName());
               }
           }
        }
        userInfo.setOrgNames(orgNames);
        //更新登录次数
        if(userInfo.getEmployeeId() != null) {
            WxLoginTime wxLoginTime  = wxLoginTimeService.selectByPrimaryKey(userInfo.getEmployeeId());
            if(wxLoginTime == null) {
                wxLoginTime = new WxLoginTime();
                wxLoginTime.setEmployeeId(userInfo.getEmployeeId());
                wxLoginTime.setLoginTime(1);
                wxLoginTimeService.insert(wxLoginTime);
            }
            else {
                wxLoginTime.setLoginTime(wxLoginTime.getLoginTime() + 1);
                wxLoginTimeService.updateByPrimaryKeySelective(wxLoginTime);
            }
        }
        // token
        UserToken userToken = UserTokenManager.generateToken(userInfo.getUserId());
        userToken.setSessionKey(sessionKey);
        //fileUrl
        List<WxSystem> selectByKeyName = wxSystemService.selectByKeyName("FILE_URL");
        if(selectByKeyName != null && selectByKeyName.size() > 0){
        	userInfo.setFileUrl(selectByKeyName.get(0).getKeyValue());
        }
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", userToken.getToken());
        result.put("tokenExpire", userToken.getExpireTime().toString());
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }


    /**
     * 请求验证码
     *
     * @param body 手机号码{mobile}
     * @return
     */
    @PostMapping("regCaptcha")
    public Object registerCaptcha(@RequestBody String body) {
        String phoneNumber = JacksonUtil.parseString(body, "mobile");
        String code = CharUtil.getRandomNum(6);
        notifyService.notifySmsTemplate(phoneNumber, NotifyType.CAPTCHA, new String[]{code});
        boolean successful = CaptchaCodeManager.addToCache(phoneNumber, code);
        return successful ? ResponseUtil.ok() : ResponseUtil.badArgument();
    }

//    /**
//     * 账号注册
//     *
//     * @param body    请求内容
//     *                {
//     *                username: xxx,
//     *                password: xxx,
//     *                mobile: xxx
//     *                code: xxx
//     *                }
//     *                其中code是手机验证码，目前还不支持手机短信验证码
//     * @param request 请求对象
//     * @return 登录结果
//     * 成功则
//     * {
//     * errno: 0,
//     * errmsg: '成功',
//     * data:
//     * {
//     * token: xxx,
//     * tokenExpire: xxx,
//     * userInfo: xxx
//     * }
//     * }
//     * 失败则 { errno: XXX, errmsg: XXX }
//     */
    @PostMapping("register")
    public Object register(@RequestBody String body, HttpServletRequest request) {
//        String username = JacksonUtil.parseString(body, "username");
//        String password = JacksonUtil.parseString(body, "password");
        String workNo = JacksonUtil.parseString(body, "workNo");
        String code = JacksonUtil.parseString(body, "code");
        if (workNo == null || workNo == "") {
            return ResponseUtil.fail(300, "工号不能为空");
        }
        
        //验证手机号在系统中是否存在，存在的话更新openId,不存在不允许注册
        List<Employee> employeeList = employeeService.getEmployeeByWorkNo(workNo);
        if(employeeList == null || employeeList.size()==0) {
            return ResponseUtil.fail(300, "工号在系统中不存在，不能注册");
        }
        if(employeeList.size()>1) {
            return ResponseUtil.fail(300, "该工号存在多个，不能注册");
        }
        Employee employee = employeeList.get(0);
        
        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail(300, "注册获取微信openId异常");
        }

        if (sessionKey == null || openId == null) {
            return ResponseUtil.fail(300, "获取微信openId失败");
        }
        
        //新增openID
        Employee wxEmployee = employeeService.selectWxEmployeeByEmployeeId(employee.getEmployeeId());
        employee.setOpenId(openId);
        boolean registerBl = false;
        if(wxEmployee == null) {
            registerBl = employeeService.insertWxEmployee(employee);
        }
        else {
            registerBl = employeeService.updateWxEmployee(employee);
        }
        if(!registerBl) {
            return ResponseUtil.fail(300, "注册失败");
        }
        
        
       List<SystemUserView> userList = systemUserService.selectByWorkNo(workNo);
        
        SystemUserView user = null;
        if (userList.size() > 1) {
            return ResponseUtil.fail(300, "该工号存在多个，不能注册");
        } else if (userList.size() == 0) {
            return ResponseUtil.fail(300, "工号在系统中不存在，不能注册");
        } else {
            user = userList.get(0);
        }
        ArrayList<String> orgNames = new ArrayList<String>();
        if(user.getOrgIdPath() != null && user.getOrgIdPath() != "") {
           String orgIdPath[] = user.getOrgIdPath().split("/");
           for(int i=1;i<orgIdPath.length;i++){
               if(orgIdPath[i] != null && orgIdPath[i] != "") {
                   Org org = orgService.selectByPrimaryKey(orgIdPath[i]);
                   orgNames.add(org.getFullName());
               }
           }
        }
        user.setOrgNames(orgNames);
        //更新登录次数
        if(user.getEmployeeId() != null) {
            WxLoginTime wxLoginTime  = wxLoginTimeService.selectByPrimaryKey(user.getEmployeeId());
            if(wxLoginTime == null) {
                wxLoginTime = new WxLoginTime();
                wxLoginTime.setEmployeeId(user.getEmployeeId());
                wxLoginTime.setLoginTime(1);
                wxLoginTimeService.insert(wxLoginTime);
            }
            else {
                wxLoginTime.setLoginTime(wxLoginTime.getLoginTime() + 1);
                wxLoginTimeService.updateByPrimaryKeySelective(wxLoginTime);
            }
        }
        UserToken userToken = UserTokenManager.generateToken(user.getUserId());
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", userToken.getToken());
        result.put("tokenExpire", userToken.getExpireTime().toString());
        result.put("userInfo", user);
        return ResponseUtil.ok(result);
    }
    
//
//    /**
//     * 账号密码重置
//     *
//     * @param body    请求内容
//     *                {
//     *                password: xxx,
//     *                mobile: xxx
//     *                code: xxx
//     *                }
//     *                其中code是手机验证码，目前还不支持手机短信验证码
//     * @param request 请求对象
//     * @return 登录结果
//     * 成功则 { errno: 0, errmsg: '成功' }
//     * 失败则 { errno: XXX, errmsg: XXX }
//     */
//    @PostMapping("reset")
//    public Object reset(@RequestBody String body, HttpServletRequest request) {
//        String password = JacksonUtil.parseString(body, "password");
//        String mobile = JacksonUtil.parseString(body, "mobile");
//        String code = JacksonUtil.parseString(body, "code");
//
//        if (mobile == null || code == null || password == null) {
//            return ResponseUtil.badArgument();
//        }
//
//        //判断验证码是否正确
//        String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
//        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code))
//            return ResponseUtil.fail(403, "验证码错误");
//
//        List<LitemallUser> userList = userService.queryByMobile(mobile);
//        LitemallUser user = null;
//        if (userList.size() > 1) {
//            return ResponseUtil.serious();
//        } else if (userList.size() == 0) {
//            return ResponseUtil.fail(403, "手机号未注册");
//        } else {
//            user = userList.get(0);
//        }
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String encodedPassword = encoder.encode(password);
//        user.setPassword(encodedPassword);
//
//        userService.update(user);
//
//        return ResponseUtil.ok();
//    }
//
//    @PostMapping("bindPhone")
//    public Object bindPhone(@LoginUser Integer userId, @RequestBody String body) {
//        String sessionKey = UserTokenManager.getSessionKey(userId);
//        String encryptedData = JacksonUtil.parseString(body, "encryptedData");
//        String iv = JacksonUtil.parseString(body, "iv");
//        WxMaPhoneNumberInfo phoneNumberInfo = this.wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
//        String phone = phoneNumberInfo.getPhoneNumber();
//        LitemallUser user = userService.findById(userId);
//        user.setMobile(phone);
//        userService.update(user);
//        return ResponseUtil.ok();
//    }
}
