package com.dawei.model;

public class Article {

    private String title;
    private String coverpicture;
    private String abst;
    private String content;
    private String createTime;
    private String updateTime;
    private int managetype; // 0新闻
    private String platform;  // S
    private String isPass;   //0 未审核

    public int getManagetype() {
        return managetype;
    }

    public void setManagetype(int managetype) {
        this.managetype = managetype;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String getAbst() {
        return abst;
    }

    public void setAbst(String abst) {
        this.abst = abst;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverpicture() {
        return coverpicture;
    }

    public void setCoverpicture(String coverpicture) {
        this.coverpicture = coverpicture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", coverpicture='" + coverpicture + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", managetype='" + managetype + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }
}
