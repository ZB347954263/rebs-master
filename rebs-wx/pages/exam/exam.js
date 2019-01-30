var util = require('../../utils/util.js');
var api = require('../../config/api.js');
const app = getApp();

Page({
  data: {
    userInfo:{}
  },
  onPullDownRefresh() { // 监听该页面用户下拉刷新事件
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getExamListByUserId();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },
  onLoad: function(options) {
    this.setData({
      userInfo: app.globalData.userInfo
    });
    this.getExamListByUserId();
  },
  onReady: function() {
    // 页面渲染完成
  },
  onShow: function() {
    // 页面显示
  },
  onHide: function() {
    // 页面隐藏
  },
  onUnload: function() {
    // 页面关闭
  },
  getExamListByUserId: function() {
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.ExamListUrl, {
      UserID: this.data.userInfo.employeeId,
      orgID: 200,
      serverNo: 20001
    }).then(function(res) {
      //console.log(res.data.examList[0]);
      that.setData({
        examList: res.data.examList
      });
      wx.hideLoading();
    });
  },
  onClickExam: function(event) {
    var currentTarget = event.currentTarget;
    var examid = event.currentTarget.dataset.examid;
    var examtype = event.currentTarget.dataset.examtype;
    var paperid = event.currentTarget.dataset.paperid;
    if (examtype == 0) {
      console.log("另外的页面");
    } else {
        // url: `../examTips/examTips?examid=` + examid + '&employeeId=' + this.data.userInfo.employeeId
      wx.navigateTo({
        url: `../attendExamTip/attendExamTip?examid=` + examid + '&employeeId=' + this.data.userInfo.employeeId
      })
    }
  }
})