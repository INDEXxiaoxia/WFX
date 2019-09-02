package com.example.model;


public class SysRole {

  private String roleCode;
  private String roleName;
  private String roleDesc;
  private Long roleOrder;
  private Long roleType;


  public String getRoleCode() {
    return roleCode;
  }

  public void setRoleCode(String roleCode) {
    this.roleCode = roleCode;
  }


  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }


  public String getRoleDesc() {
    return roleDesc;
  }

  public void setRoleDesc(String roleDesc) {
    this.roleDesc = roleDesc;
  }


  public Long getRoleOrder() {
    return roleOrder;
  }

  public void setRoleOrder(Long roleOrder) {
    this.roleOrder = roleOrder;
  }


  public Long getRoleType() {
    return roleType;
  }

  public void setRoleType(Long roleType) {
    this.roleType = roleType;
  }

}
