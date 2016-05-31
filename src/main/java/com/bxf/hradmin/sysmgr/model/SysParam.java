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
package com.bxf.hradmin.sysmgr.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 系統參數
 *
 * @since 2016-05-07
 * @author Bo-Xuan Fan
 */
@Entity
@Table(name = "CFG_SYSPARAM", uniqueConstraints = @UniqueConstraint(columnNames = "paramId"))
public class SysParam {
    /** 系統參數 ID */
    @Id
    @Column(length = 30, nullable = false)
    private String paramId;

    /** 系統參數值 */
    @Column(length = 300, nullable = false)
    private String paramValue;

    /** 系統參數描述 */
    @Column(length = 300)
    private String paramDesc;

    /** 修改操作者 */
    @Column(length = 10)
    private String updateUser;

    /** 修改時間 */
    @Column
    private Timestamp updateTime;

    public String getParamId() {
        return paramId;
    }
    public void setParamId(String paramId) {
        this.paramId = paramId;
    }
    public String getParamValue() {
        return paramValue;
    }
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
    public String getParamDesc() {
        return paramDesc;
    }
    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }
    public String getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
    public Timestamp getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
