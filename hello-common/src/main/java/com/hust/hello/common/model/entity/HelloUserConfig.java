package com.hust.hello.common.model.entity;

import java.io.Serializable;
import java.util.Date;

public class HelloUserConfig implements Serializable {
    private Long id;

    private String userId;

    private String userName;

    private String studentId;

    private String password;

    private String email;

    private Byte sex;

    private Date birthday;

    private String avatarPath;

    private Byte accountStatus;

    private Integer accountClass;

    private Integer accountScore;

    private Date lastLoginTime;

    private Integer signInDays;

    private Date registrationTime;

    private Integer followingUsersum;

    private Integer followingBarsum;

    private Integer postSum;

    private Integer followerSum;

    private Integer showSex;

    private Integer showConstellation;

    private Integer reportedSum;

    private Integer version;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId == null ? null : studentId.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath == null ? null : avatarPath.trim();
    }

    public Byte getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Byte accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Integer getAccountClass() {
        return accountClass;
    }

    public void setAccountClass(Integer accountClass) {
        this.accountClass = accountClass;
    }

    public Integer getAccountScore() {
        return accountScore;
    }

    public void setAccountScore(Integer accountScore) {
        this.accountScore = accountScore;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getSignInDays() {
        return signInDays;
    }

    public void setSignInDays(Integer signInDays) {
        this.signInDays = signInDays;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Integer getFollowingUsersum() {
        return followingUsersum;
    }

    public void setFollowingUsersum(Integer followingUsersum) {
        this.followingUsersum = followingUsersum;
    }

    public Integer getFollowingBarsum() {
        return followingBarsum;
    }

    public void setFollowingBarsum(Integer followingBarsum) {
        this.followingBarsum = followingBarsum;
    }

    public Integer getPostSum() {
        return postSum;
    }

    public void setPostSum(Integer postSum) {
        this.postSum = postSum;
    }

    public Integer getFollowerSum() {
        return followerSum;
    }

    public void setFollowerSum(Integer followerSum) {
        this.followerSum = followerSum;
    }

    public Integer getShowSex() {
        return showSex;
    }

    public void setShowSex(Integer showSex) {
        this.showSex = showSex;
    }

    public Integer getShowConstellation() {
        return showConstellation;
    }

    public void setShowConstellation(Integer showConstellation) {
        this.showConstellation = showConstellation;
    }

    public Integer getReportedSum() {
        return reportedSum;
    }

    public void setReportedSum(Integer reportedSum) {
        this.reportedSum = reportedSum;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}