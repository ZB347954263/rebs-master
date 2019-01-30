// 获取应用实例
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
const app = getApp();

let wxParser = require('../../wxParser/index');

Page({
  data: {
    beginTime: new Date(),
    endTime: new Date(),
    isUpdate: false  // 判断是否保存  文件格式错误时不更新课件时间
  },
  onLoad: function (options) {
    let that = this;
    if (options.bookId) {
      that.setData({ taskId: options.taskId });
      that.setData({ taskDetailId: options.taskDetailId });
      that.setData({ bookId: options.bookId });
      that.getArticleContent(options.bookId);
    }
    // this.getArticleContent('http://192.168.0.82:8080/ceshi/11868.htm');
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
  getArticleContent: function (bookId) {
    //CatalogList
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.getArticleContent, { bookId: bookId, bookType:1}, 'GET').then(function (res) {
      //console.log(res);
      if (res.errno == 300) {
        that.callBackError(res.errmsg);
      }
      else {
        let content = res.data.content;
        wxParser.parse({
          bind: 'richText',
          html: content,
          target: that,
          enablePreviewImage: false
          // tapLink: (url) => {
          //   wx.navigateTo({
          //     url
          //   })
          // }
        });
        that.setData({
          isUpdate: true
        });
      }
      wx.hideLoading();
    });
  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
    this.setData({
      beginTime: new Date()
    });
  },
  onHide: function () {
    // 页面隐藏
    // console.log('onHide');
    this.setData({
      endTime: new Date()
    });
    this.saveStudyTaskDetailResult();
  },
  onUnload: function () {
    // 页面关闭
    this.setData({
      endTime: new Date()
    });
    this.saveStudyTaskDetailResult();
  },
  saveStudyTaskDetailResult: function () {
    var that = this;
    //判断是否更新
    if (that.data.isUpdate) {
      util.request(api.saveStudyTaskDetailResult,
        {
          employeeId: app.globalData.userInfo.employeeId,
          taskId: that.data.taskId,
          taskDetailId: that.data.taskDetailId,
          beginTime: that.data.beginTime,
          endTime: that.data.endTime
        }, 'POST'
      ).then(function (res) {
        if (res.errno == 300) {
          that.callBackError(res.errmsg);
        }
        else {
          // console.log('记录课件练习时长成功！');
          // wx.showModal({
          //   tip: '提示',
          //   content: '记录练习时长成功！',
          //   showCancel: false,
          //   confirmText: '知道了',
          //   success: function (res) {
          //   }
          // });
        }
      });
    }
  },
})
