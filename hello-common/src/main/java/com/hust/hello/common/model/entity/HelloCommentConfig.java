package com.hust.hello.common.model.entity;

import java.io.Serializable;
import java.util.Date;

public class HelloCommentConfig implements Serializable {
    private Long id;

    private String commentId;

    private String postId;

    private String objectId;

    private String creatorId;

    private String commentContent;

    private Integer commentStatus;

    private Date createTime;

    private Date lastEditTime;

    private Integer likeSum;

    private Integer dislikeSum;

    private Integer version;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId == null ? null : commentId.trim();
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId == null ? null : postId.trim();
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId == null ? null : objectId.trim();
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId == null ? null : creatorId.trim();
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getLikeSum() {
        return likeSum;
    }

    public void setLikeSum(Integer likeSum) {
        this.likeSum = likeSum;
    }

    public Integer getDislikeSum() {
        return dislikeSum;
    }

    public void setDislikeSum(Integer dislikeSum) {
        this.dislikeSum = dislikeSum;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}