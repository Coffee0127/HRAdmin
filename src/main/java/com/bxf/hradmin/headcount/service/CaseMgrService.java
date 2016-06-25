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
package com.bxf.hradmin.headcount.service;

import com.bxf.hradmin.common.model.QueryPage;
import com.bxf.hradmin.headcount.dto.CaseMainDto;

/**
 * CaseMainService
 *
 * @since 2016-05-15
 * @author Bo-Xuan Fan
 */
public interface CaseMgrService {

    void saveCaseMain(CaseMainDto caseMain);

    QueryPage find(CaseMainDto queryCond);

    CaseMainDto findOne(String caseId);

    void updateConfirmCase(String caseId, boolean confirm, String msgDetail);

    void updateReplyCase(String caseId, String msgDetail);

    void updateDisposedCase(String caseId, String msgDetail);

    void updateProcessCase(String caseId, String msgDetail);

    void updateCloseCase(String caseId, String msgDetail);
}
