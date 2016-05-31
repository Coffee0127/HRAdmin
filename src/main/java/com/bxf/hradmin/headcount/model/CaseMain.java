/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Bo-Xuan Fan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.bxf.hradmin.headcount.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CaseMain
 *
 * @since 2016-05-09
 * @author Bo-Xuan Fan
 */
@Entity
@Table(name = "CASE_MAIN")
public class CaseMain {

    /** 案件編號 */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CASE_ID")
    private Long caseId;
    /** 案件狀態 */
    @Column(name = "CASE_STATUS")
    private String caseStatus;
    /** 更新時間 */
    @Column(name = "UPDATE_DATETIME")
    private Date updateDatetime;
    /** 部門 */
    @Column(name = "DEPT")
    private String dept;
    /** 單位 */
    @Column(name = "UNIT")
    private String unit;
    /** 需求人力專案名稱 */
    @Column(name = "PROJECT_NAME")
    private String projectName;
    /** 需求人力專案代號 */
    @Column(name = "PROJECT_CODE")
    private String projectCode;
    /** 需求人力角色 */
    @Column(name = "HRM_ROLE")
    private String hrmRole;
    /** 需求人員類別 */
    @Column(name = "HRM_TYPE")
    private String hrmType;
    /** 需求人數 */
    @Column(name = "REQUIRED_COUNT")
    private Integer requiredCount;
    /** Skill */
    @Column(name = "REQUIRED_SKILL")
    private String requiredSkill;
    /** 人力需求起日(YYYY/MM/DD) */
    @Column(name = "REQUIRED_BEGIN_DATE")
    @Temporal(TemporalType.DATE)
    private Date requiredBeginDate;
    /** 人力需求訖日(YYYY/MM/DD) */
    @Column(name = "REQUIRED_END_DATE")
    @Temporal(TemporalType.DATE)
    private Date requiredEndDate;
    /** 增補原因 */
    @Column(name = "REASON")
    private String reason;
    /** 備註 */
    @Column(name = "NOTE")
    private String note;
    /** 填寫人 */
    @Column(name = "APPLIER")
    private String applier;
    /** HR處理 */
    @Column(name = "HR_PROCESS")
    private String hrProcess;

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getHrmRole() {
        return hrmRole;
    }

    public void setHrmRole(String hrmRole) {
        this.hrmRole = hrmRole;
    }

    public String getHrmType() {
        return hrmType;
    }

    public void setHrmType(String hrmType) {
        this.hrmType = hrmType;
    }

    public Integer getRequiredCount() {
        return requiredCount;
    }

    public void setRequiredCount(Integer requiredCount) {
        this.requiredCount = requiredCount;
    }

    public String getRequiredSkill() {
        return requiredSkill;
    }

    public void setRequiredSkill(String requiredSkill) {
        this.requiredSkill = requiredSkill;
    }

    public Date getRequiredBeginDate() {
        return requiredBeginDate;
    }

    public void setRequiredBeginDate(Date requiredBeginDate) {
        this.requiredBeginDate = requiredBeginDate;
    }

    public Date getRequiredEndDate() {
        return requiredEndDate;
    }

    public void setRequiredEndDate(Date requiredEndDate) {
        this.requiredEndDate = requiredEndDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getApplier() {
        return applier;
    }

    public void setApplier(String applier) {
        this.applier = applier;
    }

    public String getHrProcess() {
        return hrProcess;
    }

    public void setHrProcess(String hrProcess) {
        this.hrProcess = hrProcess;
    }
}
