package com.rebs.db.domain;

public class BookVo {

    private Integer knowledgeId;
    private Integer orgId;
    private String bookName;
    private String authors;
    private String keyWords;
    /**
     * 获取 knowledgeId
     * @return knowledgeId knowledgeId
     */
    public Integer getKnowledgeId() {
        return knowledgeId;
    }
    
    /**
     * 设置 knowledgeId
     * @param knowledgeId knowledgeId 
     */
    public void setKnowledgeId(Integer knowledgeId) {
        this.knowledgeId = knowledgeId;
    }
    
    /**
     * 获取 orgId
     * @return orgId orgId
     */
    public Integer getOrgId() {
        return orgId;
    }
    
    /**
     * 设置 orgId
     * @param orgId orgId 
     */
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
    
    /**
     * 获取 bookName
     * @return bookName bookName
     */
    public String getBookName() {
        return bookName;
    }
    
    /**
     * 设置 bookName
     * @param bookName bookName 
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    
    /**
     * 获取 authors
     * @return authors authors
     */
    public String getAuthors() {
        return authors;
    }
    
    /**
     * 设置 authors
     * @param authors authors 
     */
    public void setAuthors(String authors) {
        this.authors = authors;
    }
    
    /**
     * 获取 keyWords
     * @return keyWords keyWords
     */
    public String getKeyWords() {
        return keyWords;
    }
    
    /**
     * 设置 keyWords
     * @param keyWords keyWords 
     */
    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
    
    
    
}
