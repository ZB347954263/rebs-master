var util = require('../../utils/util.js');
var api = require('../../config/api.js');
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    collectionOfBookList: [],
    total:0,
    userInfo: {}
  },

  getCollectionOfBook: function () {
    //CatalogList
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.getCollectionOfBook, { employeeId: that.data.userInfo.employeeId },'POST').then(function (res) {
      // console.log(res.data);
      that.setData({
        collectionOfBookList: res.data.collectionOfBookList,
        total:res.data.total
      });
      wx.hideLoading();
    });
  },
  toCollectionItem: function (event) {
    var that = this;
    var currentTarget = event.currentTarget;
    var bookId = event.currentTarget.dataset.bookid;
    if (!bookId){
      bookId = -1;
    }
    wx.navigateTo({
      url: "/pages/collectionItem/collectionItem?bookId=" + bookId
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      userInfo: app.globalData.userInfo
    });
    this.getCollectionOfBook();
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
    this.getCollectionOfBook();
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

  }
})