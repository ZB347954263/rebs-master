var util = require('../../utils/util.js');
var api = require('../../config/api.js');

// pages/examTips/examTips.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let that = this;
    console.log(options);
    that.setData({
      employeeId: options.employeeId,
      examid: options.examid
    });
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.getExamTips, {
      systemExamId: 1
    }).then(function(res) {
      that.setData({
        examTips: res.data.systemExam
      });
      wx.hideLoading();
    });
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
  onClickStartExam: function() {
    wx.navigateTo({
      url: `../attendExamTip/attendExamTip?examid=` + this.data.examid + '&employeeId=' + this.data.employeeId
    })
  }
})