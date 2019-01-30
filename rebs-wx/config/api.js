// 以下是业务服务器API地址
// 本机开发时使用
var WxApiRoot = 'http://192.168.0.82:8082/wx/';
// var WxApiRoot = 'http://localhost:8082/wx/';

// var WxApiRoot = 'http://192.168.0.82:8082/wx/';

// 云平台部署时使用
// var WxApiRoot = 'http://122.152.206.172:8080/wx/';
// 云平台上线时使用
// var WxApiRoot = 'https://www.menethil.com.cn/rebs/wx/';

module.exports = {
  IndexUrl: WxApiRoot + 'home/index', //首页数据接口
  getIndexData: WxApiRoot + 'index/getIndexData',
  KnowledgeList: WxApiRoot + 'knowledge/getKnowledge',//知识体系
  KnowledgeCurrent: WxApiRoot + 'knowledge/current',//知识体系分类查询
  searchBook: WxApiRoot + 'knowledge/searchBook',//教材查询
  getBook: WxApiRoot + 'knowledge/getBook',//教材分类查询
  getItemView: WxApiRoot + 'item/getItemView',//获取题目
  setPricticeTime: WxApiRoot + 'loginTime/setPricticeTime',//记录练习时长
  setCourseWareTime: WxApiRoot + 'loginTime/setCourseWareTime',//记录课件时长
  setBookTime: WxApiRoot + 'loginTime/setBookTime',//记录课件时长

  /** 考试Url */
  ExamListUrl: WxApiRoot + 'exam/getExamByUserId',//根据UserId得到考试列表
  getExamTips: WxApiRoot + 'exam/getExamTips',//获取考试规则
  getExamInfo: WxApiRoot + 'exam/getExamInfo',// 获取考试的信息
  getSubjectItems: WxApiRoot + 'exam/getSubjectItems',// 获取考试的题目
  saveAnswerToDB: WxApiRoot + 'exam/saveAnswerToDB',// 提交考试
  showExamResult: WxApiRoot + 'exam/showExamResult',// 获取考试结果
  saveAnswerChange: WxApiRoot + 'exam/saveAnswerChangeCallBack',// 滑动一题保存记录

  getPastExamByUserId: WxApiRoot + 'pastExam/getPastExamByUserId',// 根据UserId得到已经考试列表
  getPastExam: WxApiRoot + 'pastExam/getPastExam',// 获得历史考试题目


  commitLianXi: WxApiRoot + 'item/commitLianXi',//提交练习题目
  insertErrorItem: WxApiRoot + 'error/insert',//保存单个的错题
  // 收藏接口
  isCollection: WxApiRoot + 'collection/isCollection',//判断题目是否收藏
  cancelConllect: WxApiRoot + 'collection/cancelConllect',//取消收藏
  insertConllect: WxApiRoot + 'collection/insert',//新增收藏
  deleteConllect: WxApiRoot + 'collection/delete',//删除收藏

  //错题集
  getErrorCountOfCategory: WxApiRoot + 'error/getErrorCountOfCategory',//获取错题的题数
  getErrorItem: WxApiRoot + 'error/getErrorItem',//获取收藏题目
  deleteErrorItem: WxApiRoot + 'error/delete',//移除错题

  getCollectionOfBook: WxApiRoot + 'collection/getCollectionCountOfBook',//获取书收藏的题数
  getCollectionItem: WxApiRoot + 'collection/getCollectionItem',//获取收藏题目

  //课件
  getCourseWareType: WxApiRoot + 'courseWare/getCourseWareType',//获取课件分类
  getCourseWare: WxApiRoot + 'courseWare/getCourseWare',//获取课件列表
  searchCourseWare: WxApiRoot + 'courseWare/searchCourseWare',//搜索课件

  //教材
  getArticleContent: WxApiRoot + 'article/getArticleContent',//获取教材

  //学习任务
  getStudyTaskList: WxApiRoot + 'studyTask/getStudyTaskList',//获取学习任务
  getStudyTaskDetailList: WxApiRoot + 'studyTaskDetail/getStudyTaskDetailList',//获取学习任务详细
  saveStudyTaskDetailResult: WxApiRoot + 'studyTaskResult/insert',//保存学习任务详细
  getCourseWareByCourseWareId: WxApiRoot + 'courseWare/getCourseWareByCourseWareId',//根据courseWareId获取视频url


  //登录注册接口
  AuthLoginByWeixin: WxApiRoot + 'auth/login_by_weixin', //微信登录
  AuthLoginByAccount: WxApiRoot + 'auth/login', //账号登录
  AuthRegister: WxApiRoot + 'auth/register', //账号注册
  AuthReset: WxApiRoot + 'auth/reset', //账号密码重置
  AuthRegisterCaptcha: WxApiRoot + 'auth/regCaptcha', //验证码
  // AuthBindPhone: WxApiRoot + 'auth/bindPhone', //绑定微信手机号
};