var api = require('../../../config/api.js');
var check = require('../../../utils/check.js');
var util = require('../../../utils/util.js');
var user = require('../../../utils/user.js');

var app = getApp();
Page({
  data: {
    username: '',
    password: '',
    confirmPassword: '',
    mobile: '',
    code: '',
    workNo:''
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    // 页面渲染完成
    this.setData({
      workNo: app.globalData.userInfo.workNo
    });

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
  //注册
  register: function(e){
    var that = this;
    if (this.data.workNo.length == 0) {
      wx.showModal({
        title: '错误信息',
        content: '工号不能为空',
        showCancel: false
      });
      return false;
    }
    if (e.detail.userInfo == undefined) {
      app.globalData.hasLogin = false;
      util.showErrorToast('注册失败');
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
                    wx.switchTab({
                      url: '/pages/index/index'
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
          util.showErrorToast('注册失败');
        }
      },
      fail: function (err) {
        util.showErrorToast('注册失败');
      }
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
        // if (res.confirm) {
        //   console.log('用户点击确定')
        // }
      }
    })
  },
  // sendCode: function () {
  //   let that = this;
  //   wx.request({
  //     url: api.AuthRegisterCaptcha,
  //     data: {
  //       mobile: that.data.mobile
  //     },
  //     method: 'POST',
  //     header: {
  //       'content-type': 'application/json'
  //     },
  //     success: function (res) {
  //       if (res.data.errno == 0) {
  //         wx.showModal({
  //           title: '发送成功',
  //           content: '验证码已发送',
  //           showCancel: false
  //         });
  //       }
  //       else {
  //         wx.showModal({
  //           title: '错误信息',
  //           content: res.data.errmsg,
  //           showCancel: false
  //         });
  //       }
  //     }
  //   });
  // },
  // startRegister: function () {
  //   var that = this;

  //   if (this.data.password.length < 3 || this.data.username.length < 3) {
  //     wx.showModal({
  //       title: '错误信息',
  //       content: '用户名和密码不得少于3位',
  //       showCancel: false
  //     });
  //     return false;
  //   }

  //   if (this.data.password != this.data.confirmPassword) {
  //     wx.showModal({
  //       title: '错误信息',
  //       content: '确认密码不一致',
  //       showCancel: false
  //     });
  //     return false;
  //   }

  //   if (this.data.mobile.length == 0 || this.data.code.length == 0) {
  //     wx.showModal({
  //       title: '错误信息',
  //       content: '手机号和验证码不能为空',
  //       showCancel: false
  //     });
  //     return false;
  //   }

  //   if (!check.isValidPhone(this.data.mobile)) {
  //     wx.showModal({
  //       title: '错误信息',
  //       content: '手机号输入不正确',
  //       showCancel: false
  //     });
  //     return false;
  //   }
    
  //   wx.request({
  //     url: api.AuthRegister,
  //     data: {
  //       username: that.data.username,
  //       password: that.data.password,
  //       mobile: that.data.mobile,
  //       code: that.data.code
  //     },
  //     method: 'POST',
  //     header: {
  //       'content-type': 'application/json'
  //     },
  //     success: function (res) {
  //       if (res.data.errno == 0) {
  //         app.globalData.hasLogin = true;
  //         wx.setStorageSync('userInfo', res.data.data.userInfo);
  //         wx.setStorage({
  //           key: "token",
  //           data: res.data.data.token,
  //           success: function () {
  //             wx.switchTab({
  //               url: '/pages/ucenter/index/index'
  //             });
  //           }
  //         });
  //       }
  //       else{
  //         wx.showModal({
  //           title: '错误信息',
  //           content: res.data.errmsg,
  //           showCancel: false
  //         });
  //       }
  //     }
  //   });
  // },
  // bindUsernameInput: function (e) {

  //   this.setData({
  //     username: e.detail.value
  //   });
  // },
  // bindPasswordInput: function (e) {

  //   this.setData({
  //     password: e.detail.value
  //   });
  // },
  // bindConfirmPasswordInput: function (e) {

  //   this.setData({
  //     confirmPassword: e.detail.value
  //   });
  // },
  // bindMobileInput: function (e) {
  //   this.setData({
  //     mobile: e.detail.value
  //   });
  // },

  bindWorkNoInput: function (e) {
    this.setData({
      workNo: e.detail.value
    });
  },

  
  // bindCodeInput: function (e) {

  //   this.setData({
  //     code: e.detail.value
  //   });
  // },
  clearInput: function (e) {
    switch (e.currentTarget.id) {
      case 'clear-username':
        this.setData({
          username: ''
        });
        break;
      case 'clear-password':
        this.setData({
          password: ''
        });
        break;
      case 'clear-confirm-password':
        this.setData({
          confirmPassword: ''
        });
        break;
      case 'clear-mobile':
        this.setData({
          mobile: ''
        });
        break;        
      case 'clear-code':
        this.setData({
          code: ''
        });
        break;
      case 'clear-workno':
        this.setData({
          workNo: ''
        });
        break;
    }
  }
})