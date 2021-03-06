var api = require('../../../config/api.js');
var util = require('../../../utils/util.js');
var user = require('../../../utils/user.js');

var app = getApp();
Page({
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    // 页面渲染完成

  },
  onReady: function () {

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
  wxLogin: function (e) {
    if (e.detail.userInfo == undefined){
      app.globalData.hasLogin = false;
      util.showErrorToast('微信登录失败');
      return;
    }
    // var cc = user.checkLogin();

    user.checkLogin().catch(() => {
      user.loginByWeixin(e.detail.userInfo).then(res => {
        app.globalData.hasLogin = true;
        // wx.navigateBack({
        //   delta: 1
        // })
        // wx.switchTab({
        //   url: '/pages/index/index'
        // });
        app.globalData.hasLogin = true;
        app.globalData.userInfo = res.data.userInfo;
        wx.setStorageSync('userInfo', res.data.userInfo);
        wx.setStorage({
          key: "token",
          data: res.data.token,
          success: function () {
            wx.switchTab({
              url: '/pages/index/index'
            });
          }
        });
      }).catch((err) => {
        app.globalData.hasLogin = false;
        // util.showErrorToast('登录失败，请先通过工号登录授权');
        wx.showModal({
          title: '登录失败',
          content: '请先通过工号/身份证登录进行授权',
          showCancel: false
        });
      });

    });
  },
  accountLogin: function () {
    wx.navigateTo({ url: "/pages/auth/accountLogin/accountLogin" });
  }
})