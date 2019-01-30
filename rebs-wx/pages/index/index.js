//index.js
//获取应用实例
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
const app = getApp()

Page({
  data: {
    hasLogin: false,
    practiceTime:0,
    loginTime:0,
    examMaxScore: 0,
    collectionCount: 0,
    errorCount: 0,
    courseWareTime:0,
    bookTime:0,
    taskCount:0,
    examCount:0,
    swiper: {
      imgUrls: [
        // 'http://img02.tooopen.com/images/20150928/tooopen_sy_143912755726.jpg',
        // 'http://img06.tooopen.com/images/20160818/tooopen_sy_175866434296.jpg',
        // 'http://img06.tooopen.com/images/20160818/tooopen_sy_175833047715.jpg'
        '/static/images/index-baner-1.jpg',
        '/static/images/index-baner-2.jpg',
        '/static/images/index-baner-3.jpg',
        '/static/images/index-baner-4.jpg',
        '/static/images/index-baner-5.jpg'
      ],
      indicatorDots: true,
      autoplay: true,
      interval: 5000,
      duration: 1000
    },
    item:{
      highest:0
    },
  },
  //练习，跳转选择知识体系-选一本教材页面
  tapLianXi(e){
    wx.navigateTo({
      url: `../catalog/catalog?toUrlType=1`
    })
  },
  tapExam(e) {
    wx.navigateTo({
      url: `../exam/exam`
    })
  },
  tapJiaoCai(e) {
    wx.navigateTo({
      url: `../catalog/catalog?toUrlType=2`
    })
  },
  tapRenWu(e) {
    wx.navigateTo({
      url: `../studyTask/studyTask`
    })
  },
  tapPastExam(e) {
    wx.navigateTo({
      url: `../pastExam/pastExam`
    })
  },
  tapKejian(e) {
    wx.navigateTo({
      url: `../courseWare/courseWare`
    })
  },
  tapInletsMk(e) {
    var subject = e.currentTarget.dataset.urlparem;
    // if(!app.globalData.hasLogin){
    //     wx.showModal({
    //       title:'提示',
    //       content: '您未登录，无法看到收藏',
    //       showCancel:true,
    //       cancelText:'知道了',
    //       cancelColor:'#00bcd5',
    //       confirmText:'去登录',
    //       confirmColor:'#00bcd5',
    //       success: function(res) {

    //       }
    //     })
    //   }else{
    wx.navigateTo({
      url: `../collection/collection`
    })
    // }
  },
  tapInletsError(e) {
    var subject = e.currentTarget.dataset.urlparem;
    // if(!app.globalData.hasLogin){
    //     wx.showModal({
    //       title:'提示',
    //       content: '您未登录，无法看到收藏',
    //       showCancel:true,
    //       cancelText:'知道了',
    //       cancelColor:'#00bcd5',
    //       confirmText:'去登录',
    //       confirmColor:'#00bcd5',
    //       success: function(res) {

    //       }
    //     })
    //   }else{
    wx.navigateTo({
      url: `../error/error`
    })
    // }
  },
  tapInletsSC: function (e) {
    var that = this,
      subject = e.currentTarget.dataset.urlparem,
      collection = e.currentTarget.dataset.collection - 0;
    if (!!collection) {
      wx.navigateTo({
        url: `../../pages/answer_info/info?subject=${subject}&type=wdsc`
      })
    } else {
      if (!app.globalData.hasLogin) {
        wx.showModal({
          title: '提示',
          content: '您未登录，无法看到收藏',
          showCancel: true,
          cancelText: '知道了',
          cancelColor: '#00bcd5',
          confirmText: '去登录',
          confirmColor: '#00bcd5',
          success: function (res) {

          }
        })
      } else {
        wx.showModal({
          title: '提示',
          content: '未发现您的收藏',
          showCancel: false,
          confirmText: '知道了',
          confirmColor: '#00bcd5',
          success: function (res) {

          }
        })
      }
    }
  },
  tapInletsCT: function (e) {
    var subject = e.currentTarget.dataset.urlparem,
      answerError = e.currentTarget.dataset.answererror - 0;
    if (!!answerError) {
      wx.navigateTo({
        url: `../../pages/answer_info/info?subject=${subject}&type=wdct`
      })
    } else {
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
        wx.showModal({
          title: '提示',
          content: '恭喜您，暂无错题。',
          showCancel: false,
          confirmText: '知道了',
          confirmColor: '#00bcd5',
          success: function (res) {

          }
        })
      }
    }
  },
  getIndexData: function () {
    //CatalogList
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.getIndexData, { employeeId: that.data.userInfo.employeeId }).then(function (res) {
      that.setData({
        practiceTime: that.secondToDate(res.data.indexData.practiceTime),
        loginTime: res.data.indexData.loginTime,
        examMaxScore: res.data.indexData.examMaxScore,
        collectionCount: res.data.indexData.collectionCount,
        errorCount: res.data.indexData.errorCount,
        taskCount: res.data.indexData.taskCount,
        examCount: res.data.indexData.examCount,
        courseWareTime: that.secondToDate(res.data.indexData.courseWareTime),
        bookTime: that.secondToDate(res.data.indexData.bookTime)
      });
      wx.hideLoading();
    });
  },
  /**
     * 秒转换为时分秒格式
     */
  secondToDate(time) {
    var timeDate = 0;
    if (time) {
      if (time > 60 && time < 60 * 60) {
        timeDate = Math.floor(parseInt(time) / 60);
      }
      else if (time >= 60 * 60) {
        timeDate = Math.floor(parseInt(time) / 3600) + '时' + Math.floor(parseInt(time) % 3600 / 60);
      }
      else {
        timeDate = 0;
      }
    }
    return timeDate;
  },
  onPullDownRefresh() { // 监听该页面用户下拉刷新事件
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getIndexData();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },
  onLoad() {
    var that = this;
    that.setData({
      userInfo: app.globalData.userInfo
    });
    // app.getUserInfo(function () {
      // https.index('weixin/small/1.0?m=SmallApp&c=weixin&a=homepage').then((data) => {
      //   if (data.data.status == 1) {
      //     that.data.examInlets = data.data.data.examInlets
      //     that.data.swiper.imgUrls = data.data.data.imgUrls;
      //     that.setData(that.data);
      //   }
      // })
    // });
    if (that.data.userInfo.employeeId){
        that.getIndexData();
    }
  },
  onShow() {
    // 页面显示
    if (app.globalData.hasLogin) {
       this.onLoad();
    }
    else {
      this.goLogin();
    }
    this.setData({
      hasLogin: app.globalData.hasLogin,
      userInfo: app.globalData.userInfo
    });

  },
  goLogin() {
    wx.navigateTo({
      url: "/pages/auth/login/login"
    });
  },
});
// Page({
//   data: {
//     motto: 'Hello World,我的第一个小程序',
//     userInfo: {},
//     hasUserInfo: false,
//     canIUse: wx.canIUse('button.open-type.getUserInfo')
//   },
//   //事件处理函数
//   bindViewTap: function() {
//     wx.navigateTo({
//       url: '../logs/logs'
//     })
//   },
//   onLoad: function () {
//     if (app.globalData.userInfo) {
//       this.setData({
//         userInfo: app.globalData.userInfo,
//         hasUserInfo: true
//       })
//     } else if (this.data.canIUse){
//       // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
//       // 所以此处加入 callback 以防止这种情况
//       app.userInfoReadyCallback = res => {
//         this.setData({
//           userInfo: res.userInfo,
//           hasUserInfo: true
//         })
//       }
//     } else {
//       // 在没有 open-type=getUserInfo 版本的兼容处理
//       wx.getUserInfo({
//         success: res => {
//           app.globalData.userInfo = res.userInfo
//           this.setData({
//             userInfo: res.userInfo,
//             hasUserInfo: true
//           })
//         }
//       })
//     }
//   },
//   getUserInfo: function(e) {
//     console.log(e)
//     app.globalData.userInfo = e.detail.userInfo
//     this.setData({
//       userInfo: e.detail.userInfo,
//       hasUserInfo: true
//     })
//   }
// })
