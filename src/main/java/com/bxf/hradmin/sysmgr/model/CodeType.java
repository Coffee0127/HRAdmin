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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CodeType
 *
 * @since 2016-05-08
 * @author Bo-Xuan Fan
 */
@Entity
@Table(name = "CFG_CODETYPE")
public class CodeType {

    @EmbeddedId
    private CodeTypePK codeTypePK = new CodeTypePK();

    @Column(length = 300)
    private String codeValue;

    /** 修改操作者 */
    @Column(length = 10)
    private String updateUser;

    /** 修改時間 */
    @Column
    private Timestamp updateTime;

    public CodeTypePK getCodeTypePK() {
        return codeTypePK;
    }

    public void setCodeTypePK(CodeTypePK codeTypePK) {
        this.codeTypePK = codeTypePK;
    }

    public String getCodeId() {
        return codeTypePK.getCodeId();
    }

    public void setCodeId(String codeId) {
        codeTypePK.setCodeId(codeId);
    }

    public String getCodeCat() {
        return codeTypePK.getCodeCat();
    }

    public void setCodeCat(String Cat) {
        codeTypePK.setCodeCat(Cat);
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
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
