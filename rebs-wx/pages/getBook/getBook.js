var util = require('../../utils/util.js');
var api = require('../../config/api.js');

Page({
  data: {
    bookId:0,
    book:{},
    bookChapterList:[],
    scrollLeft: 0,
    scrollTop: 0,
  
    scrollHeight: 0
  },
  onPullDownRefresh() {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getBook();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },
  getBook: function () {
    //CatalogList
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.getBook, { bookId: this.data.bookId }).then(function (res) {
      console.log(res.data);
      that.setData({
        book: res.data.book,
        bookChapterList: res.data.bookChapterList
      });
      wx.hideLoading();
    });
  },
  bookView: function (event){
    var title = this.data.book.bookName;
    wx.navigateTo({
      url: "/pages/article/article?bookId=" + this.data.bookId + '&bookType=1' + '&title=' + title
    });
  },
  lianXi: function(event) {
    var that = this;
    var currentTarget = event.currentTarget;
    var chapterId = event.currentTarget.dataset.chapterid;
    var title = this.data.book.bookName + event.currentTarget.dataset.title;
    var toUrlType = this.data.toUrlType;
    if (toUrlType == 1){
      //练习
      wx.navigateTo({
        url: "/pages/lianXi/lianXi?chapterId=" + chapterId+"&bookId="+this.data.bookId
      });
    }
    else {
      //阅读章节文章
      wx.navigateTo({
        url: "/pages/article/article?chapterId=" + chapterId + '&bookType=2' + '&title=' + title
      });
    }
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    if (options.bookId) {
      this.setData({
        bookId: parseInt(options.bookId),
        toUrlType: parseInt(options.toUrlType)
      });
      this.getBook();
    }
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
  }
})