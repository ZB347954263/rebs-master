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
    // let html = `<a href="/pages/logs/logs">文本内容</a><br><p class="test-class-name" style="text-align: center;" style="color: #ccc;">p 直属内容<u><i><strike color="#f00">tes<b color="#000">t</b></strike></i></u></p><p style="text-align: center;" checked width="100"><img src="https://mp.weixin.qq.com/debug/wxadoc/dev/image/cat/3.png?t=2017213" alt="image"></p><p style="text-align: center; "><b style="background-color: rgb(146, 208, 80);">&nbsp; &nbsp; 分类 &nbsp; &nbsp;&nbsp;</b></p><p style="text-align: center; "><span style="background-color: rgb(255, 255, 255);">&nbsp; <span style="color:#ff0000"><span style="font-size:10px">介</span><span style="font-size:12px">绍</span><font size="3">信</font><font size="4">息</font><font size="5">哈</font><font size="6">哈</font><font size="7">哈</font></span></span></p>`;
    // wxParser.parse({
    //   bind: 'richText',
    //   html: html,
    //   target: that,
    //   enablePreviewImage: false,
    //   tapLink: (url) => {
    //     wx.navigateTo({
    //       url
    //     })
    //   }
    // });
    if (options.bookId) {
      this.setData({ 
        bookId: options.bookId,
        bookType: options.bookType
      });
      that.getArticleContent(options.bookId, options.bookType);
    }
    if(options.title) {
      this.setData({
        title: options.title
      });
      wx.setNavigationBarTitle({
        title: options.title
      })
    }
    if (options.chapterId) {
      this.setData({
         chapterId: options.chapterId ,
         bookType: options.bookType,
      });
      that.getArticleContent(options.chapterId, options.bookType);
    }
    // this.getArticleContent('http://192.168.0.82:8080/ceshi/11868.htm');
  },
  //错误的回调
  callBackError: function (e) {
    wx.showModal({
      title: '错误',
      content: '提示：' + e,
      showCancel: false,
      confirmText: '确认关闭',
      success: function (res) {
        wx.navigateBack({
          delta: 1
        })
      }
    })
  },
  getArticleContent: function (bookId, bookType) {
    //CatalogList
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.getArticleContent, { bookId: bookId, bookType: bookType},'GET').then(function (res) {
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
    this.setBookTime();
  },
  onUnload: function () {
    // 页面关闭
    this.setData({
      endTime: new Date()
    });
    this.setBookTime();
  },
  setBookTime: function () {
    var that = this;
    //判断是否更新
    if (that.data.isUpdate) {
      util.request(api.setBookTime,
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
  }
})
