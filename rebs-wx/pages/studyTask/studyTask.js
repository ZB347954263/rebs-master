var util = require('../../utils/util.js');
var api = require('../../config/api.js');
const app = getApp();

Page({
  data: {
    userInfo: {},
    studyTaskList:[]
  },
  onPullDownRefresh() { // 监听该页面用户下拉刷新事件
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getStudyTaskList();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },
  onLoad: function (options) {
    this.setData({
      userInfo: app.globalData.userInfo
    });
    this.getStudyTaskList();
  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  },
  getStudyTaskList: function () {
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.getStudyTaskList, {
      employeeId: this.data.userInfo.employeeId
    },'POST').then(function (res) {
      //console.log(res.data.examList[0]);
      if (res.errno == 300) {
        that.callBackError(res.errmsg);
      }
      else {
        that.setData({
          studyTaskList: res.data.wxStudyTaskList
        });
      }
      wx.hideLoading();
    });
  },
  //错误的回调
  callBackError: function (e) {
    wx.showModal({
      title: '错误',
      content: '错误提示是：' + e,
      showCancel: false,
      confirmText: '确认关闭',
      success: function (res) {
        wx.navigateBack({
          delta: 1
        })
      }
    })
  },
  onClickStudy: function (event) {
    var currentTarget = event.currentTarget;
    var taskId = event.currentTarget.dataset.taskid;
    if (taskId != null) {
      wx.navigateTo({
        url: `../studyTaskDetail/studyTaskDetail?taskId=` + taskId
      })
    }
  }
})