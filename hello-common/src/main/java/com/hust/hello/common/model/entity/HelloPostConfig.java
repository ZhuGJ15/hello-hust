package com.hust.hello.common.model.entity;

import java.io.Serializable;
import java.util.Date;

public class HelloPostConfig implements Serializable {
    private Long id;

    private String postId;

    private String barId;

    private Integer postStatus;

    private Integer postType;

    private String postTitle;

    private String postText;

    private String creatorId;

    private Date createTime;

    private Integer popularity;

    private Date lastEditTime;

    private Integer viewSum;

    private Integer likeSum;

    private Integer collectSum;

    private Integer dislikeSum;

    private Integer commentSum;

    private Integer reportSum;

    private Integer version;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId == null ? null : postId.trim();
    }

    public String getBarId() {
        return barId;
    }

    public void setBarId(String barId) {
        this.barId = barId == null ? null : barId.trim();
    }

    public Integer getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(Integer postStatus) {
        this.postStatus = postStatus;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle == null ? null : postTitle.trim();
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText == null ? null : postText.trim();
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId == null ? null : creatorId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getViewSum() {
        return viewSum;
    }

    public void setViewSum(Integer viewSum) {
        this.viewSum = viewSum;
    }

    public Integer getLikeSum() {
        return likeSum;
    }

    public void setLikeSum(Integer likeSum) {
        this.likeSum = likeSum;
    }

    public Integer getCollectSum() {
        return collectSum;
    }

    public void setCollectSum(Integer collectSum) {
        this.collectSum = collectSum;
    }

    public Integer getDislikeSum() {
        return dislikeSum;
    }

    public void setDislikeSum(Integer dislikeSum) {
        this.dislikeSum = dislikeSum;
    }

    public Integer getCommentSum() {
        return commentSum;
    }

    public void setCommentSum(Integer commentSum) {
        this.commentSum = commentSum;
    }

    public Integer getReportSum() {
        return reportSum;
    }

    public void setReportSum(Integer reportSum) {
        this.reportSum = reportSum;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}