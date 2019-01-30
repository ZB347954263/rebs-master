var util = require('../../utils/util.js');
var api = require('../../config/api.js');
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo:{},
    studyTaskDetailList:[],
    taskId:null,
    task:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options.taskId) {
      this.setData({
        taskId: options.taskId
      });
    }
    this.setData({
      userInfo: app.globalData.userInfo
    });
    this.getStudyTaskDetailList();
  },
  getStudyTaskDetailList: function () {
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.getStudyTaskDetailList, {
      taskId: that.data.taskId
    }, 'POST').then(function (res) {
      if (res.errno == 300) {
        that.callBackError(res.errmsg);
      }
      else {
        that.setData({
          studyTaskDetailList: res.data.wxStudyTaskDetailList
        });
        that.setData({
          task: res.data.wxStudyTaskList
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
  onClickStudyDetail: function (event) {
    var taskDetailId = event.currentTarget.dataset.taskdetailid;
    var type = event.currentTarget.dataset.type;
    var bookId = event.currentTarget.dataset.bookid;
    var courseWareId = event.currentTarget.dataset.coursewareid;
    var title = event.currentTarget.dataset.title;
    var taskId = this.data.taskId;
    //教材
    if (type == 1) {
      wx.navigateTo({
        url: `../studyTaskDetailBook/studyTaskDetailBook?bookId=` + bookId + '&taskDetailId=' + taskDetailId + '&taskId=' + taskId
      })
    }
    //课件
    if (type == 2) {
      wx.navigateTo({
        url: `../studyTaskDetailCourseWare/studyTaskDetailCourseWare?courseWareId=` + courseWareId + '&taskDetailId=' + taskDetailId + '&taskId=' + taskId +'&title='+title
      })
    }
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() { // 监听该页面用户下拉刷新事件
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getStudyTaskDetailList();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

 

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})