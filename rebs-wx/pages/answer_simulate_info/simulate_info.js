//获取应用实例
var app = getApp();
var util = require('../../utils/util.js');
var api = require('../../config/api.js');

Page({
  params: {

  },
  data: {
    useExamTime: 0, // 消耗了多少时间
    subNum: 0, //全局，用于保存题目
    subjectId: 0,
    randomExamResultId: 0,
    examid: 0,
    year: 0,
    employeeId: 0,
    itemView: {},
    userInfo: {},
    hourLong: 45 * 60, //答题时长，单位秒
    time: '45:00', //答题时长，单位秒
    maxError: 10, //最大错题数
    userType: 'xuechetiku',
    isShowNewExam: false, //是否显示后台答案统计
    isNewExam: false, //是否使用后台答案。为true时必须isShowNewExam也为true
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
  onPullDownRefresh() {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getItemView();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },
  getItemView: function() {
    //CatalogList
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.getSubjectItems, {
      subjectId: this.data.subjectId, 
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
        that.setTime();
      }
      wx.hideLoading();
    });
  },

  //单选逻辑
  tapRadio: function(e) {
    //判断是否为已答题
    // if (this.data.answers.allLists[this.data.answers.activeNum].isNoFirst) {
    //   return false;
    // }
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
    // if (this.data.answers.allLists[this.data.answers.activeNum].isNoFirst) {
    //   return false;
    // }
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
    this.saveAnswerChange();
    this.setData({
      swiper: this.data.swiper
    });
    // console.log(this.data);
    //延迟加载滑动
    if (this.data.answers.activeNum + 1 < this.data.answers.allLists.length) {
      setTimeout(() => this.onSwiper('left'), 100);
    } else if (this.data.answers.activeNum + 1 == this.data.answers.allLists.length) {
      this.submitTip();
    }
  },

  //单选、判断答案判断逻辑
  tapSelect: function(e) {
    var that = this;
    //判断是否为已答题
    // if (this.data.answers.allLists[this.data.answers.activeNum].isNoFirst) {
    //   return false;
    // }
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
    this.saveAnswerChange();
    this.setData({
      swiper: this.data.swiper
    });
    // console.log(this.data);
    //延迟加载滑动
    if (this.data.answers.activeNum + 1 < this.data.answers.allLists.length) {
      setTimeout(() => this.onSwiper('left'), 100);
    } else if (this.data.answers.activeNum + 1 == this.data.answers.allLists.length) {
      this.submitTip();
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
        // wx.showModal({
        //   tip: '提示',
        //   content: '已经是最后一道题了，是否交卷？',
        //   showCancel: true,
        //   cancelText: '取消',
        //   confirmText: '交卷',
        //   success: function (res) {
        //     if (res.confirm) {
        //       //提交试卷
        //       that.submitTip();
        //     }
        //     // wx.navigateBack(1)
        //   }
        // });
        //提交试卷
        that.submitTip();
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
  },
  //交卷
  submitTip: function() {
    const that = this;
    if (this.data.answers.allLists.length > this.data.answers.error + this.data.answers.success) {
      this.setData({
        subNum: 1
      })
      wx.showModal({
        title: '提示',
        content: `您已经回答了${this.data.answers.error + this.data.answers.success}题，还有${this.data.answers.allLists.length - this.data.answers.error - this.data.answers.success}题未答，确定要交卷吗？`,
        showCancel: true,
        cancelText: '继续答题',
        cancelColor: '#00bcd5',
        confirmText: '交卷',
        confirmColor: '#00bcd5',
        success: function(res) {
          if (res.confirm) {
            that.setSubmit();
          }
        }
      });
    } else {
      wx.showModal({
        title: '提示',
        content: '已经是最后一道题了了，交卷后可立即查看成绩',
        showCancel: false,
        confirmText: '提交',
        confirmColor: '#00bcd5',
        success: function(res) {
          that.setSubmit();
        }
      })
    }
  },
  //提交函数
  setSubmit: function() {
    // var record = this.data.answers.allLists.map((option,i) => {
    //   return {
    //     id:option.id,
    //     answer:option.isAnswer,
    //     choose:option.answered || 0
    //   };
    // });
    //console.log(this.data);
    var that = this;
    util.request(api.saveAnswerToDB, {
      allLists: that.data.answers.allLists,
      employeeId: that.data.userInfo.employeeId,
      randomExamResultID: that.data.randomExamResultId,
      examId: that.data.examid,
      beginTime: that.data.beginTime,
      endTime: new Date(),
      type: 2,
      useExamTime: that.data.useExamTime
    }, 'POST').then(function(res) {
      if (res.errno == 300) {
        that.callBackError(res.errmsg);
      } else {
        var examVo = res.data.examVo;
        if (examVo != null) {
          wx.redirectTo({
            url: '/pages/answer_mark/mark?randomExamResultIDReturn=' + examVo.randomExamResultIDReturn + '&examId=' + that.data.examid + '&errorItemCount=' + examVo.errorItemCount,
          })
          // wx.showModal({
          //   title: '考试结果',
          //   content: '本次考试得分：' + examVo.fraction + '% \n' + '错题数：' + examVo.errorItemCount + '，正确题数：' + examVo.rightItemCount,
          //   showCancel: false,
          //   confirmText: '知道了',
          //   success: function (res) {
          //     // if (res.confirm) {
          //     //   console.log('用户点击确定')
          //     // }11
          //   }
          // })
        }
      }
    });
    // https.setExamInfo(this.data.answerUrl,{
    //   subject:this.data.subject,
    //   type:this.data.type,record,
    //   subject:this.data.subject,
    //   useTime:this.params.seconds,
    //   city:app.globalData.getLocation
    //   },{}
    // )
    // .then((data) =>{
    //   if(data.data.status == 1){
    //     wx.redirectTo({
    //       url: `../../pages/answer_mark/mark?subject=${this.data.subject}&type=${this.data.type}&time=${this.params.seconds}&mark=${data.data.data.score}&mid=${data.data.data.mid}&error=${this.data.answers.error}`
    //     })
    //   }
    // })
  },
  //计时
  setTime: function() {
    let that = this,
      seconds = Math.floor((new Date().getTime() - this.data.startTime) / 1000),
      minutes = 0;
    this.params.seconds = seconds;
    if (seconds >= this.data.hourLong) {
      this.params.seconds = this.data.hourLong;
      this.data.time = '00:00';
      this.setData(this.data);
      wx.showModal({
        title: '提示',
        content: '考试时间已到，交卷后可立即查看成绩',
        showCancel: false,
        confirmText: '知道了',
        confirmColor: '#00bcd5',
        success: function(res) {
          that.setSubmit();
        }
      })
    } else {
      seconds = this.data.hourLong - seconds;
      minutes = Math.floor(seconds / 60);
      seconds = seconds % 60;
      this.data.time = `${minutes > 9 ? minutes: '0' + minutes}:${seconds > 9 ? seconds: '0' +　seconds}`;
      this.setData({
        time: this.data.time
      });
      this.swiperTime = setTimeout(() => {
        this.setTime();
      }, 1000);
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
    var that = this;
    //判断是否已经收藏
    // util.request(api.isCollection, {
    //   itemId: this.data.swiper.list[1].itemId,
    //   employeeId: that.data.userInfo.employeeId,
    //   type: 2
    // }, 'POST').then(function(res) {
    //   if (res.errno == 300) {
    //     that.callBackError(res.errmsg);
    //   } else {
    //     if (res.data.isCollection) {
    //       that.data.answers.allLists[that.data.answers.activeNum].isStore = true;
    //       that.data.answers.allLists[that.data.answers.activeNum].collectionId = res.data.collectionId;
    //     } else {
    //       that.data.answers.allLists[that.data.answers.activeNum].isStore = false;
    //     }
    //     that.setData({
    //       answers: that.data.answers
    //     });
    //   }
    // });

    // 保存每一题
    var swiper = this.data.swiper.list;
    // console.log(swiper);
    // debugger;
    // if (swiper[0].content != undefined) {
    //   this.saveAnswerChange();
    // }
  },
  saveAnswerChange: function() {
    // var swiper = this.data.swiper.list;
    // var sbNum = this.data.subNum;
    // var list;
    // if (swiper.length == 2 && sbNum == 1){// 最后两道题，sbNum == 1，说明是最后一题
    //   list = swiper[1];
    // }else{
    //   list = swiper[0];
    // }
    // console.log(list);
    util.request(
      api.saveAnswerChange, {
        randomExamResultID: this.data.randomExamResultId,
        randomExamItem: this.data.answers.allLists[this.data.answers.activeNum],
        beginTime: this.data.beginTime,
        endTime: new Date(),
        examId: this.data.examid,
        useExamTime: this.data.useExamTime
      },
      'POST'
    ).then(function (res) {
      if (res.errno == 300) {
        this.data.callBackError(res.errmsg);
      } else {
        console.log(res.data);
      }
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
  //页面初始化
  onLoad: function(options) {
    this.setData({
      userInfo: app.globalData.userInfo
    });
    // 页面初始化 options为页面跳转所带来的参数
    if (options.examid) {
      this.setData({
        examid: parseInt(options.examid),
        employeeId: parseInt(options.employeeId),
        year: parseInt(options.year),
        hourLong: parseInt(options.hourLong),
        randomExamResultId: parseInt(options.randomExamResultId),
        useExamTime: parseInt(options.useExamTime)
      });
      this.getItemView();
    }
    // console.log(options);
  },
  onReady: function() {
    // 页面渲染完成
  },
  onShow: function() {
    // 页面显示
  },
  onHide: function() {
    // 页面隐藏
    clearInterval(this.swiperTime);
  },
  onUnload: function() {
    // 页面关闭
    clearInterval(this.swiperTime);

    // wx.switchTab({
    //   url: "/pages/index/index"
    // })
  }

  // onLoad (params) {
  //   var that = this;
  //   this.data.subject = params.subject;
  //   this.data.type = params.type;

  //   if(params.subject == 'kemu3'){
  //     this.data.maxError = 5;
  //   }
  //   https.initialize(this.data.answers.onLoadUrl,{subject:params.subject,type:params.type},{
  //     isNewExam:this.data.isShowNewExam && this.data.isNewExam,
  //     isShowNewExam:this.data.isShowNewExam
  //   })
  //   .then(d => {
  //       this.data.answers.allLists = d.data;
  //       this.data.answers.success = d.success;
  //       this.data.answers.error = d.error;
  //       this.data.answers.loading = false;    
  //       this.setData(this.data);
  //       this.getSubject(() => {
  //         this.data.startTime = new Date().getTime();
  //         this.setTime();
  //       });
  //   })
  //   .catch(e => {
  //     this.callBackError(e.message);
  //     // this.setData({ subtitle: '获取数据异常', movies: [], loading: false })
  //     // console.error(e)
  //   });
  // },
  // onHide(){
  //   clearInterval(this.swiperTime);
  // },
  // onUnload(){//页面卸载
  //   clearInterval(this.swiperTime);
  // }
});