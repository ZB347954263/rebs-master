var util = require('../../utils/util.js');
var api = require('../../config/api.js');
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    tabs: ["练习", "考试"],
    lianxiOfBookList: [],
    eaxmOfExamList: [],
    lianxiTotal:0,
    examTotal:0,
    userInfo: {},
    activeIndex: 1,
    bookId:0,
    examId:0
  },
  tabClick: function (e) {
    this.setData({
      activeIndex: e.currentTarget.id
    })
  },

  getErrorItemOfBook: function () {
    //CatalogList
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.getErrorCountOfCategory, { employeeId: that.data.userInfo.employeeId }, 'POST').then(function (res) {
      that.setData({
        lianxiOfBookList: res.data.lianxiOfBookList,
        eaxmOfExamList: res.data.eaxmOfExamList,
        lianxiTotal: res.data.lianxiTotal,
        examTotal: res.data.examTotal
      });
      wx.hideLoading();
    });
  },
  toErrorItem: function (event) {
    var that = this;
    var currentTarget = event.currentTarget;
    var bookId = event.currentTarget.dataset.bookid;
    if (that.data.activeIndex == 2){
      bookId = event.currentTarget.dataset.examid;
    }
    wx.navigateTo({
      url: "/pages/errorItem/errorItem?id=" + bookId + "&type=" + that.data.activeIndex
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      userInfo: app.globalData.userInfo
    });
    this.getErrorItemOfBook();
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
    this.getErrorItemOfBook();
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
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

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
    return {
      title: '课表查询',
      desc: '「文经课表」提供烟台大学文经学院在校生班级与教师课表和空闲教室、图书馆藏及考试安排等查询服务。',
      path: '/pages/core/table/index'
    }
  }
})