var util = require('../../utils/util.js');
var api = require('../../config/api.js');
const app = getApp();
function getRandomColor() {
  const rgb = []
  for (let i = 0; i < 3; ++i) {
    let color = Math.floor(Math.random() * 256).toString(16)
    color = color.length == 1 ? '0' + color : color
    rgb.push(color)
  }
  return '#' + rgb.join('')
}

Page({
  onReady: function (res) {
    if (this.data.fileType = 'mp4') {
       this.videoContext = wx.createVideoContext('myVideo')
    }
    else {
       this.ctx = wx.createLivePlayerContext('player')
    }
  },
  onLoad: function (options) {
    let that = this;
    if (options.courseWareId) {
      that.setData({ courseWareId: options.courseWareId });
      that.getCourseWareByCourseWareId();
    }
    if (options.title) {
      this.setData({
        title: options.title
      });
      wx.setNavigationBarTitle({
        title: options.title
      })
    }
  },
  getCourseWareByCourseWareId: function () {
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.getCourseWareByCourseWareId, {
      courseWareId: that.data.courseWareId
    }, 'GET').then(function (res) {
      if (res.errno == 300) {
        that.callBackError(res.errmsg);
      }
      else {
        that.setData({
          courseWare: res.data.courseWare
        });
        let fileUrl = encodeURI(app.globalData.userInfo.fileUrl + that.data.courseWare.url);
        let url = that.data.courseWare.url;
        if (url && (url.substring(url.length - 4) == ".mp4" || url.substring(url.length - 4) == ".MP4")) {
          that.setData({
            fileUrl: fileUrl,
            isUpdate: true
          });
          that.bindPlay();
        } else {
          wx.showModal({
            title: '视频格式错误',
            content: '请上网页端查看视频',
            showCancel: false,
            success(res) {
              if (res.confirm) {
                wx.navigateBack({
                  delta: 1
                })
              } else if (res.cancel) {

              }
            }
          });
        }
      }
      wx.hideLoading();
    });
  },
  //错误的回调
  callBackError: function (e) {
    wx.showModal({
      title: '错误',
      content: '提示：' + e,
      showCancel: false,
      confirmText: '确认关闭',
      success: function (res) {
        // if (res.confirm) {
        //   console.log('用户点击确定')
        // }
      }
    })
  },
  onShow: function () {
    // 页面显示
    // console.log('onShow');
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
    this.setCourseWareTime();
  },
  onUnload: function () {
    // 页面关闭
    // console.log('onUnload');
    this.setData({
      endTime: new Date()
    });
    this.setCourseWareTime();
  },
  setCourseWareTime: function () {
    var that = this;
    //判断是否更新
    if(that.data.isUpdate){
      util.request(api.setCourseWareTime,
        { employeeId: app.globalData.userInfo.employeeId, beginTime: that.data.beginTime, endTime: that.data.endTime }, 'POST'
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
  inputValue: '',
  data: {
    beginTime: new Date(),
    endTime: new Date(),
    fileType:'mp4',
    isUpdate:false, // 判断是否保存  文件格式错误时不更新练习时间
    src: '',
    danmuList:
      [{
        text: '第 1s 出现的弹幕',
        color: '#ff0000',
        time: 1
      },
      {
        text: '第 3s 出现的弹幕',
        color: '#ff00ff',
        time: 3
      }],
      fileUrl:''
  },
  bindInputBlur: function (e) {
    this.inputValue = e.detail.value
  },
  bindSendDanmu: function () {
    this.videoContext.sendDanmu({
      text: this.inputValue,
      color: getRandomColor()
    })
  },
  bindPlay: function () {
    if (this.data.fileType = 'mp4') {
       this.videoContext.play();
    }
    else {
      this.ctx.play({
        success: res => {
          console.log('play success')
        },
        fail: res => {
          console.log('play fail')
        }
      })
    }
  },
  bindPause: function () {
    if (this.data.fileType = 'mp4') {
       this.videoContext.pause()
    }
    else {
      this.ctx.pause({
        success: res => {
          console.log('pause success')
        },
        fail: res => {
          console.log('pause fail')
        }
      })
    }
  },
  videoErrorCallback: function (e) {
    console.log('视频错误信息:')
    console.log(e.detail.errMsg)
  },

  statechange(e) {
    console.log('live-player code:', e.detail.code)
  },
  error(e) {
    console.error('live-player error:', e.detail.errMsg)
  },
 
  
  bindResume() {
    this.ctx.resume({
      success: res => {
        console.log('resume success')
      },
      fail: res => {
        console.log('resume fail')
      }
    })
  },
  bindMute() {
    this.ctx.mute({
      success: res => {
        console.log('mute success')
      },
      fail: res => {
        console.log('mute fail')
      }
    })
  }
})