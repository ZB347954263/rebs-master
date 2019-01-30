var util = require('../../utils/util.js');
var api = require('../../config/api.js');

Page({
  data: {
    orgId: 17467,
    bookName: '',
    keyWords: '',
    authors: '',
    knowledgeList: [],
    currentKnowledge: {},
    bookList: [],
    scrollLeft: 0,
    scrollTop: 0,
    goodsCount: 0,
    scrollHeight: 0,
    toUrlType:1
  },
  onLoad: function (options) {
    if (options.toUrlType) {
      this.setData({
        toUrlType: options.toUrlType
      });
    }
    this.getCatalog();
  },
  onPullDownRefresh() {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getCatalog();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },
  getCatalog: function () {
    //CatalogList
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.KnowledgeList, {orgId:this.data.orgId}).then(function (res) {
      var knowledgeList = res.data.knowledgeList;
      // knowledgeList.forEach(function(ele,i){
      //   knowledgeList.push(ele);
      // });
      // knowledgeList.forEach(function (ele, i) {
      //   knowledgeList.push(ele);
      // });
      // knowledgeList.forEach(function (ele, i) {
      //   knowledgeList.push(ele);
      // });
      that.setData({
        knowledgeList: knowledgeList,
        currentKnowledge: res.data.currentKnowledge,
        bookList: res.data.bookList
      });
      wx.hideLoading();
    });
  },
  getCurrentKnowledge: function (knowledgeId) {
    let that = this;
    util.request(api.KnowledgeCurrent, {
      knowledgeId: knowledgeId,
      orgId: this.data.orgId 
    })
      .then(function (res) {
        that.setData({
          currentKnowledge: res.data.currentKnowledge,
          bookList: res.data.bookList
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
      bookName: e.detail.value
    })
  },
  searchByBookName: function(){
    let that = this;
    util.request(api.searchBook, {
      bookName: this.data.bookName,
      orgId: this.data.orgId,
      knowledgeId: this.data.currentKnowledge.knowledgeId,
      keyWords: this.data.keyWords,
      authors: this.data.authors
    }, 'POST')
      .then(function (res) {
        that.setData({
          currentKnowledge: res.data.currentKnowledge,
          bookList: res.data.bookList
        });
      });
  },
 
  getList: function () {
    var that = this;
    util.request(api.ApiRootUrl + 'api/catalog/' + that.data.currentKnowledge.catId)
      .then(function (res) {
        that.setData({
          categoryList: res.data,
        });
      });
  },
  switchCate: function (event) {
    var that = this;
    var currentTarget = event.currentTarget;
    if (this.data.currentKnowledge.knowledgeId == event.currentTarget.dataset.knowledgeid) {
      return false;
    }
    this.getCurrentKnowledge(event.currentTarget.dataset.knowledgeid);
  },
  getBook: function (event){
    var that = this;
    var currentTarget = event.currentTarget;
    var bookId = event.currentTarget.dataset.bookid;
    var bookurl = event.currentTarget.dataset.url;
    var toUrlType = that.data.toUrlType;
    // if (toUrlType == 2) {
    //   //教材
    //   wx.navigateTo({
    //     url: "/pages/article/article?bookId=" + bookId + '&toUrlType=' + toUrlType
    //   });
    // }
    // else {
      //练习
      wx.navigateTo({
        url: "/pages/getBook/getBook?bookId=" + bookId + '&toUrlType=' + toUrlType
      });
    // }
  }
})