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
package com.bxf.hradmin.headcount.dto;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;

import com.bxf.hradmin.common.model.QueryCond;

/**
 * CaseMainDto
 *
 * @since 2016-05-18
 * @author Bo-Xuan Fan
 */
public class CaseMainDto extends QueryCond {

    /** serialVersionUID */
    private static final long serialVersionUID = -7612063811042622657L;
    /** 案件編號 */
    private String caseId;
    /** 案件狀態 */
    private String caseStatus;
    /** 前次案件狀態 */
    private String preCaseStatus;
    /** 更新時間 */
    private Date updateDatetime;
    /** 案件更新起始時間 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDatetime;
    /** 案件更新結束時間 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDateTime;
    /** 部門 */
    private String dept;
    /** 單位 */
    private String unit;
    /** 需求人力專案名稱 */
    private String projectName;
    /** 需求人力專案代號 */
    private String projectCode;
    /** 需求人力角色 */
    private String hrmRole;
    /** 需求人員類別 */
    private String hrmType;
    /** 需求人數 */
    private Integer requiredCount;
    /** Skill */
    private String requiredSkill;
    /** 人力需求起日(YYYY/MM/DD) */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date requiredBeginDate;
    /** 人力需求訖日(YYYY/MM/DD) */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date requiredEndDate;
    /** 增補原因 */
    private String reason;
    /** 備註 */
    private String note;
    /** 填寫人 */
    private String applier;
    /** HR處理 */
    private String hrProcess;
    /** 案件歷程 */
    private List<CaseDetailDto> caseDetails;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public String getPreCaseStatus() {
        return preCaseStatus;
    }

    public void setPreCaseStatus(String preCaseStatus) {
        this.preCaseStatus = preCaseStatus;
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

    public Date getBeginDatetime() {
        return beginDatetime;
    }

    public void setBeginDatetime(Date beginDatetime) {
        this.beginDatetime = beginDatetime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
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

    public List<CaseDetailDto> getCaseDetails() {
        return caseDetails;
    }

    public void setCaseDetails(List<CaseDetailDto> caseDetails) {
        this.caseDetails = caseDetails;
    }

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
