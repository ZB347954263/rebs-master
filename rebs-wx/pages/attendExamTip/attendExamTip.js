var util = require('../../utils/util.js');
var api = require('../../config/api.js');

// pages/attendExamTip/attendExamTip.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    employeeId:0,
    examid:0,
    examUserInfo: {},
    randomExam: {},
    randomExamSubject: {},
    randomExamResultCurrent:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    // 获取考试基本信息/考试人的信息/考试题数和分数
    this.setData({
      employeeId: options.employeeId,
      examid: options.examid,
    });
    // console.log(this.data);
    this.examInfo(options.employeeId, options.examid);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },

  /**
   * 获取考试信息
   */
  examInfo: function (employeeId, examid) {
    var that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.getExamInfo, {
      examid: examid,
      employeeID: employeeId
    }).then(function (res) {
      // console.log(res.data);
      var randomExamSubjects = res.data.randomExamSubject;
      var randomExamSubject = {
        itemCount:0,
        totalScore:0
      };
      for (var i = 0; i < randomExamSubjects.length; i++) {
        randomExamSubject.itemCount += randomExamSubjects[i].itemCount * randomExamSubjects[i].unitScore;
        randomExamSubject.totalScore += randomExamSubjects[i].totalScore;
      }
      that.setData({
        examUserInfo: res.data.examUserInfo,
        randomExam: res.data.randomExam,
        randomExamSubject: randomExamSubject,
        randomExamResultCurrent: res.data.randomExamResultCurrent
      });
      wx.hideLoading();
    });
  },

  toExam: function (event) {
    var beginTime = this.data.randomExamResultCurrent.beginTime;
    var year = new Date().getFullYear();
    if(beginTime != null) {
      year = beginTime.substring(0, 4);
    }
    var useExamTime = this.data.randomExamResultCurrent.examTime;
    var employeeId = this.data.examUserInfo.employeeId
    // var useExamTime = 0;
    var examTime = this.data.randomExam.examTime;
    var hourLong = examTime * 60 - useExamTime;
    wx.navigateTo({
      url: "/pages/answer_simulate_info/simulate_info?examid=" + this.data.examid + "&employeeId=" + employeeId + "&randomExamResultId=" + this.data.randomExamResultCurrent.randomExamResultId + '&year=' + year + '&hourLong=' + hourLong + "&useExamTime=" + useExamTime
    });
  }
})