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
package com.bxf.hradmin.admin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CodeTypePK
 *
 * @since 2016-05-08
 * @author Bo-Xuan Fan
 */
@Embeddable
public class CodeTypePK implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 7217310762383695826L;

    @Column(length = 32)
    private String codeId;

    @Column(length = 32)
    private String codeType;

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codeId == null) ? 0 : codeId.hashCode());
        result = prime * result
                + ((codeType == null) ? 0 : codeType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CodeTypePK other = (CodeTypePK) obj;
        if (codeId == null) {
            if (other.codeId != null) {
                return false;
            }
        } else if (!codeId.equals(other.codeId)) {
            return false;
        }
        if (codeType == null) {
            if (other.codeType != null) {
                return false;
            }
        } else if (!codeType.equals(other.codeType)) {
            return false;
        }
        return true;
    }
}
