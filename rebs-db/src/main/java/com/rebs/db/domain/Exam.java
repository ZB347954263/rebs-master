package com.rebs.db.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 考试
 * @author wdd
 *
 */
public class Exam {
	private Integer examid;
    private Integer categoryid;
    private Integer orgid;
    private Integer examtypeid;
    private String typename;
    private String categoryname;
    private String examname;
    private Integer examtime;
    private Date begintime;
    private Date endtime;
    private Integer exammodeid;
    private Integer minexamtimes;
    private BigDecimal maxexamtimes;
    private BigDecimal converttotalscore;
    private Integer passscore;
    private Boolean autosaveinterval;        
    private String isundercontrol;
    private Boolean isautoscore;
    private Boolean canseeanswer;
    private Boolean canseescore;
    private Boolean ispublicscore;
    private String statusid;
    private String createperson;
    private Date createtime;
    private String description;
    private String memo;
    private Integer paperid;
    private Integer createmode;
    private Integer examineecount;
    private BigDecimal examaveragescore;
    private Integer examtype;
    private Integer downloaded;
	private String examstylename;
	private String startmodename;

	private RandomExamResultCurrent randomExamResultCurrent;
	
	
	/**
	 * 获取 examid
	 * @return examid examid
	 */
	public Integer getExamid() {
		return examid;
	}
	/**
	 * 设置 examid
	 * @param examid examid
	 */
	public void setExamid(Integer examid) {
		this.examid = examid;
	}
	/**
	 * 获取 categoryid
	 * @return categoryid categoryid
	 */
	public Integer getCategoryid() {
		return categoryid;
	}
	/**
	 * 设置 categoryid
	 * @param categoryid categoryid
	 */
	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}
	/**
	 * 获取 orgid
	 * @return orgid orgid
	 */
	public Integer getOrgid() {
		return orgid;
	}
	/**
	 * 设置 orgid
	 * @param orgid orgid
	 */
	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}
	/**
	 * 获取 examtypeid
	 * @return examtypeid examtypeid
	 */
	public Integer getExamtypeid() {
		return examtypeid;
	}
	/**
	 * 设置 examtypeid
	 * @param examtypeid examtypeid
	 */
	public void setExamtypeid(Integer examtypeid) {
		this.examtypeid = examtypeid;
	}
	/**
	 * 获取 typename
	 * @return typename typename
	 */
	public String getTypename() {
		return typename;
	}
	/**
	 * 设置 typename
	 * @param typename typename
	 */
	public void setTypename(String typename) {
		this.typename = typename;
	}
	/**
	 * 获取 categoryname
	 * @return categoryname categoryname
	 */
	public String getCategoryname() {
		return categoryname;
	}
	/**
	 * 设置 categoryname
	 * @param categoryname categoryname
	 */
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	/**
	 * 获取 examname
	 * @return examname examname
	 */
	public String getExamname() {
		return examname;
	}
	/**
	 * 设置 examname
	 * @param examname examname
	 */
	public void setExamname(String examname) {
		this.examname = examname;
	}
	/**
	 * 获取 examtime
	 * @return examtime examtime
	 */
	public Integer getExamtime() {
		return examtime;
	}
	/**
	 * 设置 examtime
	 * @param examtime examtime
	 */
	public void setExamtime(Integer examtime) {
		this.examtime = examtime;
	}
	/**
	 * 获取 begintime
	 * @return begintime begintime
	 */
	@JsonFormat(pattern = "yyyy年MM月dd日 HH:mm",timezone="GMT+8")
	public Date getBegintime() {
		return begintime;
	}
	/**
	 * 设置 begintime
	 * @param begintime begintime
	 */
	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	/**
	 * 获取 endtime
	 * @return endtime endtime
	 */
	@JsonFormat(pattern = "yyyy年MM月dd日 HH:mm",timezone="GMT+8")
	public Date getEndtime() {
		return endtime;
	}
	/**
	 * 设置 endtime
	 * @param endtime endtime
	 */
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	/**
	 * 获取 exammodeid
	 * @return exammodeid exammodeid
	 */
	public Integer getExammodeid() {
		return exammodeid;
	}
	/**
	 * 设置 exammodeid
	 * @param exammodeid exammodeid
	 */
	public void setExammodeid(Integer exammodeid) {
		this.exammodeid = exammodeid;
	}
	/**
	 * 获取 minexamtimes
	 * @return minexamtimes minexamtimes
	 */
	public Integer getMinexamtimes() {
		return minexamtimes;
	}
	/**
	 * 设置 minexamtimes
	 * @param minexamtimes minexamtimes
	 */
	public void setMinexamtimes(Integer minexamtimes) {
		this.minexamtimes = minexamtimes;
	}
	/**
	 * 获取 maxexamtimes
	 * @return maxexamtimes maxexamtimes
	 */
	public BigDecimal getMaxexamtimes() {
		return maxexamtimes;
	}
	/**
	 * 设置 maxexamtimes
	 * @param maxexamtimes maxexamtimes
	 */
	public void setMaxexamtimes(BigDecimal maxexamtimes) {
		this.maxexamtimes = maxexamtimes;
	}
	/**
	 * 获取 converttotalscore
	 * @return converttotalscore converttotalscore
	 */
	public BigDecimal getConverttotalscore() {
		return converttotalscore;
	}
	/**
	 * 设置 converttotalscore
	 * @param converttotalscore converttotalscore
	 */
	public void setConverttotalscore(BigDecimal converttotalscore) {
		this.converttotalscore = converttotalscore;
	}
	/**
	 * 获取 passscore
	 * @return passscore passscore
	 */
	public Integer getPassscore() {
		return passscore;
	}
	/**
	 * 设置 passscore
	 * @param passscore passscore
	 */
	public void setPassscore(Integer passscore) {
		this.passscore = passscore;
	}
	/**
	 * 获取 autosaveinterval
	 * @return autosaveinterval autosaveinterval
	 */
	public Boolean getAutosaveinterval() {
		return autosaveinterval;
	}
	/**
	 * 设置 autosaveinterval
	 * @param autosaveinterval autosaveinterval
	 */
	public void setAutosaveinterval(Boolean autosaveinterval) {
		this.autosaveinterval = autosaveinterval;
	}
	/**
	 * 获取 isundercontrol
	 * @return isundercontrol isundercontrol
	 */
	public String getIsundercontrol() {
		return isundercontrol;
	}
	/**
	 * 设置 isundercontrol
	 * @param isundercontrol isundercontrol
	 */
	public void setIsundercontrol(String isundercontrol) {
		this.isundercontrol = isundercontrol;
	}
	/**
	 * 获取 isautoscore
	 * @return isautoscore isautoscore
	 */
	public Boolean getIsautoscore() {
		return isautoscore;
	}
	/**
	 * 设置 isautoscore
	 * @param isautoscore isautoscore
	 */
	public void setIsautoscore(Boolean isautoscore) {
		this.isautoscore = isautoscore;
	}
	/**
	 * 获取 canseeanswer
	 * @return canseeanswer canseeanswer
	 */
	public Boolean getCanseeanswer() {
		return canseeanswer;
	}
	/**
	 * 设置 canseeanswer
	 * @param canseeanswer canseeanswer
	 */
	public void setCanseeanswer(Boolean canseeanswer) {
		this.canseeanswer = canseeanswer;
	}
	/**
	 * 获取 canseescore
	 * @return canseescore canseescore
	 */
	public Boolean getCanseescore() {
		return canseescore;
	}
	/**
	 * 设置 canseescore
	 * @param canseescore canseescore
	 */
	public void setCanseescore(Boolean canseescore) {
		this.canseescore = canseescore;
	}
	/**
	 * 获取 ispublicscore
	 * @return ispublicscore ispublicscore
	 */
	public Boolean getIspublicscore() {
		return ispublicscore;
	}
	/**
	 * 设置 ispublicscore
	 * @param ispublicscore ispublicscore
	 */
	public void setIspublicscore(Boolean ispublicscore) {
		this.ispublicscore = ispublicscore;
	}
	/**
	 * 获取 statusid
	 * @return statusid statusid
	 */
	public String getStatusid() {
		return statusid;
	}
	/**
	 * 设置 statusid
	 * @param statusid statusid
	 */
	public void setStatusid(String statusid) {
		this.statusid = statusid;
	}
	/**
	 * 获取 createperson
	 * @return createperson createperson
	 */
	public String getCreateperson() {
		return createperson;
	}
	/**
	 * 设置 createperson
	 * @param createperson createperson
	 */
	public void setCreateperson(String createperson) {
		this.createperson = createperson;
	}
	/**
	 * 获取 createtime
	 * @return createtime createtime
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * 设置 createtime
	 * @param createtime createtime
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取 description
	 * @return description description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 description
	 * @param description description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取 memo
	 * @return memo memo
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * 设置 memo
	 * @param memo memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * 获取 paperid
	 * @return paperid paperid
	 */
	public Integer getPaperid() {
		return paperid;
	}
	/**
	 * 设置 paperid
	 * @param paperid paperid
	 */
	public void setPaperid(Integer paperid) {
		this.paperid = paperid;
	}
	/**
	 * 获取 createmode
	 * @return createmode createmode
	 */
	public Integer getCreatemode() {
		return createmode;
	}
	/**
	 * 设置 createmode
	 * @param createmode createmode
	 */
	public void setCreatemode(Integer createmode) {
		this.createmode = createmode;
	}
	/**
	 * 获取 examineecount
	 * @return examineecount examineecount
	 */
	public Integer getExamineecount() {
		return examineecount;
	}
	/**
	 * 设置 examineecount
	 * @param examineecount examineecount
	 */
	public void setExamineecount(Integer examineecount) {
		this.examineecount = examineecount;
	}
	/**
	 * 获取 examaveragescore
	 * @return examaveragescore examaveragescore
	 */
	public BigDecimal getExamaveragescore() {
		return examaveragescore;
	}
	/**
	 * 设置 examaveragescore
	 * @param examaveragescore examaveragescore
	 */
	public void setExamaveragescore(BigDecimal examaveragescore) {
		this.examaveragescore = examaveragescore;
	}
	/**
	 * 获取 examtype
	 * @return examtype examtype
	 */
	public Integer getExamtype() {
		return examtype;
	}
	/**
	 * 设置 examtype
	 * @param examtype examtype
	 */
	public void setExamtype(Integer examtype) {
		this.examtype = examtype;
	}
	/**
	 * 获取 downloaded
	 * @return downloaded downloaded
	 */
	public Integer getDownloaded() {
		return downloaded;
	}
	/**
	 * 设置 downloaded
	 * @param downloaded downloaded
	 */
	public void setDownloaded(Integer downloaded) {
		this.downloaded = downloaded;
	}
	/**
	 * 获取 examstylename
	 * @return examstylename examstylename
	 */
	public String getExamstylename() {
		return examstylename;
	}
	/**
	 * 设置 examstylename
	 * @param examstylename examstylename
	 */
	public void setExamstylename(String examstylename) {
		this.examstylename = examstylename;
	}
	/**
	 * 获取 startmodename
	 * @return startmodename startmodename
	 */
	public String getStartmodename() {
		return startmodename;
	}
	/**
	 * 设置 startmodename
	 * @param startmodename startmodename
	 */
	public void setStartmodename(String startmodename) {
		this.startmodename = startmodename;
	}
	/**
	 * 获取 randomExamResultCurrent
	 * @return randomExamResultCurrent randomExamResultCurrent
	 */
	public RandomExamResultCurrent getRandomExamResultCurrent() {
		return randomExamResultCurrent;
	}
	/**
	 * 设置 randomExamResultCurrent
	 * @param randomExamResultCurrent randomExamResultCurrent
	 */
	public void setRandomExamResultCurrent(RandomExamResultCurrent randomExamResultCurrent) {
		this.randomExamResultCurrent = randomExamResultCurrent;
	}
}
