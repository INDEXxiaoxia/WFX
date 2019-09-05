package com.example.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Arrays;

@Table
public class UserInfo {
    @Id
    private String userId;
    private String userName;
    private String account;
    private String password;
    private String remark;
    private String userType;
    @Transient
    private String userTypeStr;
    private String enabled;
    private java.util.Date loginTime;
    private String roleId;
    private String self;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    private String[] userTypeArray = {"客服账号", "管理账号", "内置账号", "财务账号", "物流账号"};
    public String getUserTypeStr() {
        return userTypeArray[Integer.parseInt(userType) - 1];//在这里，对状态类型进行统一修改;
    }

    public void setUserTypeStr(String userTypeStr) {
        this.userTypeStr = userTypeStr;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }


    public java.util.Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(java.util.Date loginTime) {
        this.loginTime = loginTime;
    }


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }


    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", remark='" + remark + '\'' +
                ", userType='" + userType + '\'' +
                ", enabled='" + enabled + '\'' +
                ", loginTime=" + loginTime +
                ", roleId='" + roleId + '\'' +
                ", self='" + self + '\'' +
                ", userTypeArray=" + Arrays.toString(userTypeArray) +
                '}';
    }
}
