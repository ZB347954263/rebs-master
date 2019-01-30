//index.js
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
//获取应用实例
var app = getApp();

Page({
  data: {
   randomExamResultIDReturn:0,
   examId: 0,
   errorItemCount: 0,
   examResult:{},
   randomExam:{},
   time:0
  },
  gotoWDCT(){
    if (this.data.errorItemCount > 0){
        wx.redirectTo({
          url: '/pages/errorItem/errorItem?id=' + this.data.examId+'&type=2'
        })
    }else{
      wx.showModal({
        title:'提示',
        content: '恭喜您，暂无错题。即将返回首页！',
        showCancel:false,
        confirmText:'知道了',
        confirmColor:'#00bcd5',
        success: function(res) {
          wx.redirectTo({
            url: `/pages/index/index`
          })
        }
      })
    }
  },
  //页面初始化
  onLoad: function (options) {
    var that = this;
    app.getUserInfo(function () {
      that.data.hasUserInfo = true;
      that.data.userInfo = app.globalData.userInfo;
      that.setData(that.data)
    })
    // 页面初始化 options为页面跳转所带来的参数
    if (options.randomExamResultIDReturn) {
      this.setData({
        randomExamResultIDReturn: parseInt(options.randomExamResultIDReturn),
        examId: parseInt(options.examId),
        errorItemCount: parseInt(options.errorItemCount)
      });
      this.showExamResult();
    }
  },
  showExamResult: function () {
    //CatalogList
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.showExamResult, { examResultID: this.data.randomExamResultIDReturn}).then(function (res) {
      if (res.errno == 300) {
        that.callBackError(res.errmsg);
      }
      else {
        that.setData({
          examResult: res.data.examResult
        });
        that.setData({
          randomExam: res.data.randomExam[0]
        });
        // if (that.data.randomExam.examTime != null && that.data.randomExam.examTime != 0) {
        //   var minutes = Math.floor(that.data.examResult.examTime / 60);
        //   var seconds = that.data.randomExam.examTime % 60;
        //   that.data.time = `${minutes > 9 ? minutes : '0' + minutes}:${seconds > 9 ? seconds : '0' + 　seconds}`;
        //   that.setData({ time: that.data.time });
        // }
        that.setData({ time: res.data.secToTime});
      }
      wx.hideLoading();
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
  }
});