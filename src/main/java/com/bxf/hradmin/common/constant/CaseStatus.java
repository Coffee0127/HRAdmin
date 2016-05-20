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
package com.bxf.hradmin.common.constant;

/**
 * 案件狀態常數
 *
 * @since 2016-05-08
 * @author Bo-Xuan Fan
 */
public enum CaseStatus {

    /** 已收件狀態 **/
    RECEIVED_CASE_STATUS("01"),
    /** 已申請狀態 **/
    PASSED_CASE_STATUS("02"),
    /** 未通過狀態 **/
    UNPASSED_CASE_STATUS("03"),
    /** 已回覆狀態 **/
    RESPONSE_CASE_STATUS("04"),
    /** 處理中狀態 **/
    HANDLING_CASE_STATUS("05"),
    /** 已結案狀態 **/
    CLOSE_CASE_STATUS("10"),
    /** 案件異常狀態 **/
    EXCEPTION_CASE_STATUS("97"),
    /** 放棄申請狀態 **/
    DISPOSED_CASE_STATUS("98");

    private String code;

    CaseStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
