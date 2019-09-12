package com.qianfeng.repeat.model;

import javax.persistence.Id;
import javax.persistence.Table;

@Table
public class VDeptEmpEmp {

    @Id
    private Long id;
    private String ename;
    private Long jobId;
    private Long mgr;
    private java.util.Date joindate;
    private Double salary;
    private Double bonus;
    private Long deptId;
    private String dname;
    private String mgrName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }


    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }


    public Long getMgr() {
        return mgr;
    }

    public void setMgr(Long mgr) {
        this.mgr = mgr;
    }


    public java.util.Date getJoindate() {
        return joindate;
    }

    public void setJoindate(java.util.Date joindate) {
        this.joindate = joindate;
    }


    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }


    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }


    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }


    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }


    public String getMgrName() {
        return mgrName;
    }

    public void setMgrName(String mgrName) {
        this.mgrName = mgrName;
    }

}
