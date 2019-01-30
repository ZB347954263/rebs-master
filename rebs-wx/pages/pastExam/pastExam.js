var util = require('../../utils/util.js');
var api = require('../../config/api.js');
const app = getApp();

// pages/pastExam/pastExam.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      beginDate: new Date().getFullYear() - 1 + "-01-01",
      endDate: new Date().getFullYear() + '-' + ((new Date().getMonth() + 1) < 10 ? '0' + (new Date().getMonth() + 1) : (new Date().getMonth() + 1)) + '-' + (new Date().getDate() < 10 ? '0' + new Date().getDate() : new Date().getDate()),
      oldDate: new Date().getFullYear() - 5 + "-01-01",
      futureDate: new Date().getFullYear() + 5 + "-01-01"
    });
    this.setData({
      userInfo: app.globalData.userInfo
    });
    this.getPastExamListByUserId();
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
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getPastExamListByUserId();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
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

  bindBeginDateChange: function(e) {
    this.setData({
      beginDate: e.detail.value
    })
  },
  bindEndDateChange: function(e) {
    this.setData({
      endDate: e.detail.value
    })
  },
  getPastExamListByUserId: function(e){
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.getPastExamByUserId, {
      userID: this.data.userInfo.employeeId,
      beginDate: this.data.beginDate,
      endDate: this.data.endDate
    }, 'POST').then(function (res) {
      that.setData({
        examList: res.data.examList
      });
      wx.hideLoading();
    });
  },
  onClickExam: function(e){
    var canSeeAnswer = e.currentTarget.dataset.canseeanswer;
    if(canSeeAnswer != 1){
      wx.showModal({
        title: '提示',
        content: '无法查看历史试卷！',
        showCancel: false,
        confirmText: '确认关闭',
        confirmColor: '#00bcd5',
        success: function (res) {
        }
      })
    }else{
      var randomExamId = e.currentTarget.dataset.randomexamid;
      var randomexamresultid = e.currentTarget.dataset.randomexamresultid;
      var begintime = e.currentTarget.dataset.begintime;
      var year = begintime.substr(0,4);
      wx.navigateTo({
        url: `../pastExamTip/pastExamTip?examid=` + randomExamId + '&randomexamresultid=' + randomexamresultid + '&year=' + year
      })
    }
  }
})