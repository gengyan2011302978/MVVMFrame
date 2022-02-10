package com.phjt.mvvmframe.model.bean;

import java.io.Serializable;

public class PopularArticleListBean implements Serializable {

    /**
     * id : 5
     * classifyId : 3
     * articleTitle : 国学研究
     * articleDesc : 国学研究123
     * articleImg : http://test-k8s-oss.peogoo.com/test-shangwuyou/images/5415888280326685.jpg
     * articleNum : 1001
     * articleLabel : 国学
     * articleTop : 1
     * classifyName : 国学精华
     */

    private int id;
    private int classifyId;
    private String articleTitle;
    private String articleDesc;
    private String articleImg;
    private String articleNum;
    private String articleLabel;
    private int articleTop;
    private String classifyName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(int classifyId) {
        this.classifyId = classifyId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    public String getArticleImg() {
        return articleImg;
    }

    public void setArticleImg(String articleImg) {
        this.articleImg = articleImg;
    }

    public String getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(String articleNum) {
        this.articleNum = articleNum;
    }

    public String getArticleLabel() {
        return articleLabel;
    }

    public void setArticleLabel(String articleLabel) {
        this.articleLabel = articleLabel;
    }

    public int getArticleTop() {
        return articleTop;
    }

    public void setArticleTop(int articleTop) {
        this.articleTop = articleTop;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }
}
