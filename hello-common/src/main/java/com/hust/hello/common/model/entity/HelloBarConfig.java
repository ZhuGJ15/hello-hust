package com.hust.hello.common.model.entity;

import java.io.Serializable;
import java.util.Date;

public class HelloBarConfig implements Serializable {
    private Long id;

    private String barId;

    private String barName;

    private String introduction;

    private String avatarPath;

    private Integer barType;

    private Integer barStatus;

    private Integer followerSum;

    private Integer postSum;

    private Integer popularity;

    private String barPrincipal;

    private String barAdmin;

    private Date createTime;

    private String creatorId;

    private Integer version;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarId() {
        return barId;
    }

    public void setBarId(String barId) {
        this.barId = barId == null ? null : barId.trim();
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName == null ? null : barName.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath == null ? null : avatarPath.trim();
    }

    public Integer getBarType() {
        return barType;
    }

    public void setBarType(Integer barType) {
        this.barType = barType;
    }

    public Integer getBarStatus() {
        return barStatus;
    }

    public void setBarStatus(Integer barStatus) {
        this.barStatus = barStatus;
    }

    public Integer getFollowerSum() {
        return followerSum;
    }

    public void setFollowerSum(Integer followerSum) {
        this.followerSum = followerSum;
    }

    public Integer getPostSum() {
        return postSum;
    }

    public void setPostSum(Integer postSum) {
        this.postSum = postSum;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public String getBarPrincipal() {
        return barPrincipal;
    }

    public void setBarPrincipal(String barPrincipal) {
        this.barPrincipal = barPrincipal == null ? null : barPrincipal.trim();
    }

    public String getBarAdmin() {
        return barAdmin;
    }

    public void setBarAdmin(String barAdmin) {
        this.barAdmin = barAdmin == null ? null : barAdmin.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId == null ? null : creatorId.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}