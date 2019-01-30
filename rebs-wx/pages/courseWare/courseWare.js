var util = require('../../utils/util.js');
var api = require('../../config/api.js');

Page({
  data: {
    orgId: 17467,
    courseWareName: '',
    keyWords: '',
    authors: '',
    courseWareTypeList: [],
    currentCourseWareType: {},
    courseWareList: [],
    scrollLeft: 0,
    scrollTop: 0,
    goodsCount: 0,
    scrollHeight: 0
  },
  onLoad: function (options) {
    this.getCourseWareType();
  },
  onPullDownRefresh() {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getCourseWareType();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },
  getCourseWareType: function () {
    //CatalogList
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.getCourseWareType, {}).then(function (res) {
      that.setData({
        courseWareTypeList: res.data.courseWareTypeList,
        currentCourseWareType: res.data.currentCourseWareType,
        courseWareList: res.data.courseWareList
      });
      wx.hideLoading();
    });
  },
  getCourseWare: function (courseWareTypeId) {
    let that = this;
    util.request(api.getCourseWare, {
      courseWareTypeId: courseWareTypeId
      // orgId: this.data.orgId
    })
      .then(function (res) {
        that.setData({
          currentCourseWareType: res.data.currentCourseWareType,
          courseWareList: res.data.courseWareList
        });
      });
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
  bindKeyInput: function (e) {
    this.setData({
      courseWareName: e.detail.value
    })
  },
  searchByCourseWare: function () {
    let that = this;
    util.request(api.searchCourseWare, {
      courseWareName: this.data.courseWareName,
      // orgId: this.data.orgId,
      courseWareTypeId: this.data.currentCourseWareType.courseWareTypeId
      // keyWords: this.data.keyWords,
      // authors: this.data.authors
    }, 'POST')
      .then(function (res) {
        that.setData({
          currentCourseWareType: res.data.currentCourseWareType,
          courseWareList: res.data.courseWareList
        });
      });
  },
  switchCate: function (event) {
    var that = this;
    var currentTarget = event.currentTarget;
    if (that.data.currentCourseWareType.courseWareTypeId == event.currentTarget.dataset.coursewaretypeid) {
      return false;
    }
    that.getCourseWare(event.currentTarget.dataset.coursewaretypeid);
  },
  getCourseWareVideo: function (event) {
    var that = this;
    var currentTarget = event.currentTarget;
    var courseWareId = event.currentTarget.dataset.id;
    var title = event.currentTarget.dataset.title;
    wx.navigateTo({
      url: "/pages/videoIndex/videoIndex?courseWareId=" + courseWareId +"&title="+title
    });
  }
})