var util = require('../../utils/util.js');
var api = require('../../config/api.js');
const app = getApp();

// pages/pastExamTip/pastExamTip.js
Page({
  
  /**
   * 页面的初始数据
   */
  data: {
    examid: 0,
    year: 0,
    employeeId: 0,
    itemView: {},
    userInfo: {},
    isLoading: false, //加载
    swiper: {
      active: 0
    },
    layerlayer: {
      isLayerShow: false, //默认弹窗
      layerAnimation: {}, //弹窗动画
    },
    answerUrl: 'weixin/small/1.0?m=SmallApp&c=weixin&a=mnksHandPaper', //交卷URL
    answers: {
      onLoadUrl: 'weixin/small/1.0?m=SmallApp&c=weixin&a=questionID', //题目号链接      
      start: 0, //初始题号
      end: 0, //结束题号
      allLists: [], //题号数据
      activeNum: 0, //当前条数
      showActiveNum: 0, //当前显示条数
      onceLoadLength: 5, //一次向俩端加载条数
      url: 'weixin/small/1.0?m=SmallApp&c=weixin&a=getQuestion', //题目详情链接
      isShowTip: false //默认是否显示提示
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      userInfo: app.globalData.userInfo
    });
    // 页面初始化 options为页面跳转所带来的参数
    this.setData({
      examid: parseInt(options.examid),
      year: parseInt(options.year),
      randomExamResultId: parseInt(options.randomexamresultid)
    });
    this.getItemView();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getItemView();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  getItemView: function() {
    //CatalogList
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.getPastExam, {
      examid: this.data.examid,
      randomExamResultId: this.data.randomExamResultId,
      year: this.data.year
    }).then(function(res) {
      if (res.errno == 300) {
        that.callBackError(res.errmsg);
      } else {
        that.setData({
          itemView: res.data.itemView
        });
        that.data.answers.activeNum = 0;
        that.data.answers.showActiveNum = 0;
        that.data.answers.allLists = that.data.itemView.allItemList;
        that.data.answers.success = res.data.itemView.success;
        that.data.answers.error = res.data.itemView.error;
        that.data.answers.end = that.data.itemView.allItemList.length;
        that.data.answers.loading = false;
        that.setData({
          answers: that.data.answers
        });
        that.setSwiperList();
        that.setData({
          swiper: that.data.swiper
        });
        that.data.startTime = new Date().getTime();
        that.setData({
          startTime: that.data.startTime,
          beginTime: new Date()
        });
        // that.setTime();
      }
      wx.hideLoading();
    });
  },
  //收藏/取消收藏逻辑
  collectList: function() {
    var that = this;
    var isStore = 0;
    this.data.answers.allLists[this.data.answers.activeNum].isStore = !this.data.answers.allLists[this.data.answers.activeNum].isStore;
    this.setData({
      answers: this.data.answers
    });
    isStore = this.data.answers.allLists[this.data.answers.activeNum].isStore;
    //判断是否已经收藏
    //收藏
    if (isStore) {
      util.request(api.insertConllect, {
        itemId: that.data.answers.allLists[that.data.answers.activeNum].itemId,
        employeeId: that.data.userInfo.employeeId,
        type: 1
      }, 'POST').then(function(res) {
        if (res.errno == 300) {
          that.callBackError(res.errmsg);
        } else {
          if (res.data.collectionId) {
            that.data.answers.allLists[that.data.answers.activeNum].isStore = true;
            that.data.answers.allLists[that.data.answers.activeNum].collectionId = res.data.collectionId;
          } else {
            that.data.answers.allLists[that.data.answers.activeNum].isStore = false;
          }
          that.setData({
            answers: that.data.answers
          });
        }
      });
    }
    //取消收藏
    else {
      util.request(api.deleteConllect, {
        collectionId: that.data.answers.allLists[that.data.answers.activeNum].collectionId
        // itemId: that.data.answers.allLists[that.data.answers.activeNum].itemId,
        // employeeId: that.data.userInfo.employeeId,
        // type: 1
      }, 'GET').then(function(res) {
        if (res.errno == 300) {
          that.callBackError(res.errmsg);
        } else {
          that.data.answers.allLists[that.data.answers.activeNum].isStore = false;
          that.data.answers.allLists[that.data.answers.activeNum].collectionId = null;
          that.setData({
            answers: that.data.answers
          });
        }
      });
    }
  },
  setSwiperList() {
    var oldStar = this.data.answers.activeNum - 1,
      oldEnd = this.data.answers.activeNum + 1,
      star = oldStar >= 0 ? oldStar : 0,
      end = oldEnd <= this.data.answers.allLists.length ? oldEnd : this.data.answers.allLists.length;
    this.data.swiper.list = this.data.answers.allLists.slice(star, end + 1);
    if (oldStar < 0) {
      this.data.swiper.list.unshift({});
    }
    if (oldEnd > this.data.answers.allLists.length) {
      this.data.swiper.list.push({});
    }
  },
  //单选逻辑
  tapRadio: function(e) {
    //判断是否为已答题
    if (this.data.answers.allLists[this.data.answers.activeNum].isNoFirst) {
      return false;
    }
    var thisOption = e.currentTarget.dataset.option,
      list = this.data.answers.allLists[this.data.answers.activeNum].answerList.map(function(option, i) {
        if (thisOption == option.tip) {
          option.isSelect = true;
          // if (!option.isSelect) {
          //   // option.isActive = true;
          //   option.isSelect = true;
          // } else {
          //   // option.isActive = false;
          //   option.isSelect = false;
          // }
        } else {
          option.isSelect = false;
        }
        return option
      });
    this.data.answers.allLists[this.data.answers.activeNum].options = list;
    this.setData({
      answers: this.data.answers
    });
    this.tapSelect(e);
  },
  //多选逻辑
  tapCheckbox: function(e) {
    //判断是否为已答题
    if (this.data.answers.allLists[this.data.answers.activeNum].isNoFirst) {
      return false;
    }
    var thisOption = e.currentTarget.dataset.option,
      list = this.data.answers.allLists[this.data.answers.activeNum].answerList.map(function(option, i) {
        if (thisOption == option.tip) {
          if (!option.isSelect) {
            // option.isActive = true;
            option.isSelect = true;
          } else {
            // option.isActive = false;
            option.isSelect = false;
          }
        }
        return option
      });
    this.data.answers.allLists[this.data.answers.activeNum].options = list;
    this.setData({
      answers: this.data.answers
    });
    this.setSwiperList();
    this.setData({
      swiper: this.data.swiper
    });
    //this.setData(this.data);
  },

  //确定多选答案判断逻辑
  tabCheckbokSelect: function(e) {
    var that = this;
    if (!this.data.answers.allLists[this.data.answers.activeNum].options) {
      this.callBackError('您还未选种答案！');
      return false;
    }
    var answered = '',
      answer = '',
      bool = true;
    this.data.answers.allLists[this.data.answers.activeNum].options.forEach(function(option, i) {
      //解析答案数字编码
      if (option.isSelect) {
        //拼接多选的结果用于判断对错
        answer += option.tip;
        answered += (answered == '' ? '' + option.index : '|' + option.index);
      }
      //判断选项的对错
      if (that.data.answers.allLists[that.data.answers.activeNum].standardAnswer.indexOf(option.index + '') > -1) {
        option.correct = true;
      } else {
        option.correct = false;
      }
      return option;
    });

    //判断多选题答案的正确与否
    if (answer != null && answer.length > 0) {
      if (answer.length != that.data.answers.allLists[that.data.answers.activeNum].standardAnswerList.length) {
        bool = false;
      }
    } else {
      bool = false;
    }
    that.data.answers.allLists[that.data.answers.activeNum].standardAnswerList.forEach(function(standardAnswer, i) {
      if (!(answer.indexOf(standardAnswer.tip) > -1)) {
        bool = false;
      }
    });


    //存放本次答案数字编码
    this.data.answers.allLists[this.data.answers.activeNum].answered = answered;

    //改变题目状态为已答
    if (bool) {
      //修正答案统计
      if (this.data.answers.allLists[this.data.answers.activeNum].isAnswer == 0) {
        this.data.answers.success++;
      }
      //修正答案统计
      if (this.data.answers.allLists[this.data.answers.activeNum].isAnswer == 2) {
        this.data.answers.success++;
        this.data.answers.error--;
      }
      //设置为对题
      this.data.answers.allLists[this.data.answers.activeNum].isAnswer = 1;
    } else {
      //修正答案统计
      if (this.data.answers.allLists[this.data.answers.activeNum].isAnswer == 0) {
        this.data.answers.error++;
      }
      //修正答案统计
      if (this.data.answers.allLists[this.data.answers.activeNum].isAnswer == 1) {
        this.data.answers.success--;
        this.data.answers.error++;
      }
      //设置为错题
      this.data.answers.allLists[this.data.answers.activeNum].isAnswer = 2;
      //保存错题
      // this.insertErrorItem();
    }
    //改变为已答题状态
    this.data.answers.allLists[this.data.answers.activeNum].isNoFirst = true;
    this.data.isShowTip = !bool;
    this.setData({
      isShowTip: this.data.isShowTip
    });
    this.setData({
      answers: this.data.answers
    });
    this.setSwiperList();
    this.setData({
      swiper: this.data.swiper
    });
    // console.log(this.data);
    //延迟加载滑动
    if (this.data.answers.activeNum + 1 < this.data.answers.allLists.length) {
      setTimeout(() => this.onSwiper('left'), 100);
    } else if (this.data.answers.activeNum + 1 == this.data.answers.allLists.length) {
      // this.submitTip();
      console.log("进入了提交试卷");
    }
  },

  //单选、判断答案判断逻辑
  tapSelect: function(e) {
    var that = this;
    //判断是否为已答题
    if (this.data.answers.allLists[this.data.answers.activeNum].isNoFirst) {
      return false;
    }
    var answered = 0,
      bool = true;
    this.data.answers.allLists[this.data.answers.activeNum].options.forEach(function(option, i) {
      //解析答案数字编码
      if (option.isSelect) {
        answered = option.index;
      }
      if (option.isSelect && !that.data.answers.allLists[that.data.answers.activeNum].standardAnswesInt == i) {
        bool = false;
      }
      if (!option.isSelect && that.data.answers.allLists[that.data.answers.activeNum].standardAnswesInt == i) {
        bool = false;
      }
    });
    //存放本次答案数字编码
    this.data.answers.allLists[this.data.answers.activeNum].answered = answered;

    //改变题目状态为已答
    if (bool) {
      //修正答案统计
      if (this.data.answers.allLists[this.data.answers.activeNum].isAnswer == 0) {
        this.data.answers.success++;
      }
      //修正答案统计
      if (this.data.answers.allLists[this.data.answers.activeNum].isAnswer == 2) {
        this.data.answers.success++;
        this.data.answers.error--;
      }
      //设置为对题
      this.data.answers.allLists[this.data.answers.activeNum].isAnswer = 1;
    } else {
      //修正答案统计
      if (this.data.answers.allLists[this.data.answers.activeNum].isAnswer == 0) {
        this.data.answers.error++;
      }
      //修正答案统计
      if (this.data.answers.allLists[this.data.answers.activeNum].isAnswer == 1) {
        this.data.answers.success--;
        this.data.answers.error++;
      }
      //设置为错题
      this.data.answers.allLists[this.data.answers.activeNum].isAnswer = 2;
      //保存错题
      // this.insertErrorItem();
    }
    //改变为已答题状态
    this.data.answers.allLists[this.data.answers.activeNum].isNoFirst = true;
    this.data.isShowTip = !bool;
    this.setData({
      isShowTip: this.data.isShowTip
    });
    this.setData({
      answers: this.data.answers
    });
    this.setSwiperList();
    this.setData({
      swiper: this.data.swiper
    });
    // console.log(this.data);
    //延迟加载滑动
    if (this.data.answers.activeNum + 1 < this.data.answers.allLists.length) {
      setTimeout(() => this.onSwiper('left'), 100);
    } else if (this.data.answers.activeNum + 1 == this.data.answers.allLists.length) {
      // this.submitTip();
      console.log("进入了提交试卷");
    }
  },

  //页码切换列表效果
  pageClick: function() {
    var layerAnimation = wx.createAnimation({
      transformOrigin: "50% 50%",
      duration: 500,
      timingFunction: "ease",
      delay: 0
    });
    if (!this.data.layerlayer.isLayerShow) {
      layerAnimation.translate3d(0, 0, 0).step();
    } else {
      layerAnimation.translate3d(0, '100%', 0).step();
    }
    this.data.layerlayer.isLayerShow = !this.data.layerlayer.isLayerShow;
    this.data.layerlayer.layerAnimation = layerAnimation;
    // this.setData(this.data);
    this.setData({
      layerlayer: this.data.layerlayer
    });
  },

  //页码切换列表收缩
  layerFooterClick: function() {
    var layerAnimation = wx.createAnimation({
      transformOrigin: "50% 50%",
      duration: 500,
      timingFunction: "ease",
      delay: 0
    });
    layerAnimation.translate3d(0, '100%', 0).step();
    this.data.layerlayer.isLayerShow = false;
    this.data.layerlayer.layerAnimation = layerAnimation;
    this.setData({
      layerlayer: this.data.layerlayer
    });
    // this.setData(this.data);
  },

  //题号变更逻辑
  setActiveNum: function(e) {
    var thisOption = e.currentTarget.dataset.option - 0;
    this.data.answers.activeNum = thisOption;
    this.data.answers.showActiveNum = thisOption;
    this.data.isLoading = false;
    this.setData({
      isLoading: this.data.isLoading,
      answers: this.data.answers
    });
    this.layerFooterClick();
    this.setSwiperList();
    this.setData({
      swiper: this.data.swiper
    });
    // this.setData(this.data);
  },

  //swiper切换
  setEvent: function(e) {
    this.data.swiper.touchstartEvent = e;
    this.setData({
      swiper: this.data.swiper
    });
    return false;
  },
  //滑动结束
  touchEnd: function(e) {
    this.onSwiper(this.getDirection(this.data.swiper.touchstartEvent, e));
    return false;
  },

  //swiper切换
  onSwiper: function(dire) {
    var that = this,
      active = 0,
      storeSetTime,
      animationO = wx.createAnimation({
        transformOrigin: "50% 50%",
        duration: 200,
        timingFunction: "linear",
        delay: 0
      }),
      animationT = wx.createAnimation({
        transformOrigin: "50% 50%",
        duration: 200,
        timingFunction: "linear",
        delay: 0
      }),
      animationS = wx.createAnimation({
        transformOrigin: "50% 50%",
        duration: 200,
        timingFunction: "linear",
        delay: 0
      });

    if (!this.$isLock) { //锁屏控制

      this.$isLock = true;

      if (dire == 'bottom' || dire == 'top' || !dire) {
        this.$isLock = false;
        return false;
      }

      if (this.data.answers.activeNum >= this.data.answers.allLists.length - 1 && dire == 'left') {
        this.$isLock = false;
        wx.showModal({
          tip: '提示',
          content: '已经是最后一道题了',
          showCancel: false,
          confirmText: '知道了',
          success: function(res) {
            // wx.navigateBack(1)
          }
        });
        return false;
      }
      if (this.data.answers.activeNum <= 0 && dire == 'right') {
        this.$isLock = false;
        wx.showModal({
          tip: '提示',
          content: '已经是第一道题了',
          showCancel: false,
          confirmText: '知道了',
          success: function(res) {
            // wx.navigateBack(1)
          }
        });
        return false;
      }

      if (dire == 'right') {
        animationO.translate3d('0', 0, 0).step();
        animationT.translate3d('100%', 0, 0).step();
        if (this.data.answers.activeNum > this.data.answers.start) {
          active = -1;
        } else {
          this.$isLock = false;
          return;
        }
      }
      if (dire == 'left') {
        animationT.translate3d('-100%', 0, 0).step();
        animationS.translate3d('0', 0, 0).step();
        if (this.data.answers.activeNum < this.data.answers.end) {
          active = 1;
        } else {
          this.$isLock = false;
          return;
        }
      }

      this.data.swiper.animationO = animationO.export();
      this.data.swiper.animationT = animationT.export();
      this.data.swiper.animationS = animationS.export();
      this.data.answers.showActiveNum = this.data.answers.activeNum + active;
      // this.setData(this.data);
      this.setData({
        answers: this.data.answers
      });
      this.setData({
        swiper: this.data.swiper
      });
      setTimeout(function() {
        that.setHtmlsetHtml(active);
      }, 100);
    }
  },
  //修改页面至正常位置
  setHtmlsetHtml: function(active) {
    // console.log('setHtmlsetHtml---------begin');
    var animationO = wx.createAnimation({
        transformOrigin: "50% 50%",
        duration: 0,
        delay: 0
      }),
      animationT = wx.createAnimation({
        transformOrigin: "50% 50%",
        duration: 0,
        delay: 0
      }),
      animationS = wx.createAnimation({
        transformOrigin: "50% 50%",
        duration: 0,
        delay: 0
      });
    animationO.translate3d('-100%', 0, 0).step();
    animationT.translate3d('0', 0, 0).step();
    animationS.translate3d('100%', 0, 0).step();
    this.data.answers.activeNum = this.data.answers.activeNum + active;
    this.data.answers.showActiveNum = this.data.answers.activeNum;
    this.setData({
      answers: this.data.answers
    });
    this.data.swiper.animationO = animationO;
    this.data.swiper.animationT = animationT;
    this.data.swiper.animationS = animationS;
    this.setSwiperList();
    this.setData({
      swiper: this.data.swiper
    });
    // this.setData(this.data);
    //调用加载数据方法
    // if ((this.data.answers.activeNum - this.data.answers.start == 2 && this.data.answers.start > 0) || (this.data.answers.activeNum + 2 == this.data.answers.end && this.data.answers.end + 1 < this.data.answers.allLists.length)) {
    //   this.getSubject();
    // }
    //调用滑动结束回调
    // if (this.isLockCall && typeof this.isLockCall == 'function') {
    //   this.isLockCall();
    //   this.isLockCall = false;
    // }
    this.$isLock = false;
    // console.log('setHtmlsetHtml---------end');
  },
  //获得手势方向
  getDirection: function(startEvent, endEvent) {
    var x = endEvent.changedTouches[0].clientX - startEvent.changedTouches[0].clientX,
      y = endEvent.changedTouches[0].clientY - startEvent.changedTouches[0].clientY,
      pi = 360 * Math.atan(y / x) / (2 * Math.PI);
    if (pi < 25 && pi > -25 && x > 0 && Math.abs(x) > 10) {
      return 'right';
    }
    if (pi < 25 && pi > -25 && x < 0 && Math.abs(x) > 10) {
      return 'left';
    }
    if ((pi < -75 || pi > 750) && y > 0 && Math.abs(y) > 10) {
      return 'bottom';
    }
    if ((pi < -75 || pi > 75) && y < 0 && Math.abs(y) > 10) {
      return 'top';
    }
  },

  //错误的回调
  callBackError: function(e) {
    wx.showModal({
      title: '错误',
      content: '错误提示是：' + e,
      showCancel: false,
      confirmText: '确认关闭',
      success: function(res) {
        // if (res.confirm) {
        //   console.log('用户点击确定')
        // }
      }
    })
  }

})