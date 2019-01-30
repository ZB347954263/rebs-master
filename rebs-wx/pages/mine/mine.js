// mine.js
var app = getApp();
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
Page({
  // { "desc": "手机号码", "id": "myHeared" }, 
  data: {
    hasLogin: false,
    userInfo: {},
    myProfile: [{ "desc": "我的错题", "id": "coin" }, { "desc": "我的收藏", "id": "myQues" }],
    myAccount: ["手机号码", "帮助", "结算说明", "关于分答"],
    workNo: ''
  },
  onLoad: function () {
    console.log(this.data);
  },
  tabClick: function (e) {
    if (!app.globalData.hasLogin) {
      wx.showModal({
        title: '提示',
        content: '您未登录，无法看到错题',
        showCancel: true,
        cancelText: '知道了',
        cancelColor: '#00bcd5',
        confirmText: '去登录',
        confirmColor: '#00bcd5',
        success: function (res) {

        }
      })
    } else {
      if (e.currentTarget.id == "coin") {
        wx.navigateTo({
          url: `../error/error`
        })
      } else if (e.currentTarget.id == "myQues") {
        wx.navigateTo({
          url: `../collection/collection`
        })
      }
    }
  },
  onShow: function () {
    // 页面显示
    if (app.globalData.hasLogin) {
      this.onLoad();
    }
    else {
      // this.goLogin();
    }
    this.setData({
      hasLogin: app.globalData.hasLogin,
      userInfo: app.globalData.userInfo,
      workNo: app.globalData.userInfo.workNo
    });
  },
  loadProfile: function (e) {
    console.log(e.target)
  },
  //练习
  toLianXi: function (e) {
    wx.navigateTo({
      url: '/pages/catalog/catalog',
    })
  },
  //退出登录
  loginOut: function (e) {
    app.getUserInfo.hasLogin = false,
      app.getUserInfo.userInfo = {}
    wx.removeStorageSync('userInfo');
    wx.removeStorageSync('token');
    wx.removeStorage({
      key: 'token',
      success: function (res) { },
    })
    wx.clearStorage();
    wx.clearStorageSync();
    wx.redirectTo({
      url: '/pages/auth/login/login',
    })
  },
  //授权
  authLogin: function (e) {
    var that = this;
    if (this.data.workNo.length == 0) {
      wx.showModal({
        title: '错误信息',
        content: '工号获取失败',
        showCancel: false
      });
      return false;
    }
    if (e.detail.userInfo == undefined) {
      app.globalData.hasLogin = false;
      util.showErrorToast('授权失败');
      return;
    }

    wx.login({
      success: function (res) {
        if (res.code) {
          wx.request({
            url: api.AuthRegister,
            data: {
              workNo: that.data.workNo,
              code: res.code
            },
            method: 'POST',
            header: {
              'content-type': 'application/json'
            },
            success: function (res) {
              if (res.data.errno == 0) {
                app.globalData.hasLogin = true;
                wx.setStorageSync('userInfo', res.data.data.userInfo);
                wx.setStorage({
                  key: "token",
                  data: res.data.data.token,
                  success: function () {
                    // wx.switchTab({
                    //   url: '/pages/index/index'
                    // });
                    wx.showModal({
                      title: '授权成功',
                      content: '可直接通过微信登录',
                      showCancel: false
                    });
                  }
                });
              }
              else {
                wx.showModal({
                  title: '错误信息',
                  content: res.data.errmsg,
                  showCancel: false
                });
              }
            }
          });
        } else {
          util.showErrorToast('授权失败');
        }
      },
      fail: function (err) {
        util.showErrorToast('授权失败');
      }
    });
  },
  goLogin() {
    wx.navigateTo({
      url: "/pages/auth/login/login"
    });
  },
})
