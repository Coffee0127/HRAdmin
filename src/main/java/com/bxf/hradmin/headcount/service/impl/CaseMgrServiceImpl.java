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
package com.bxf.hradmin.headcount.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bxf.hradmin.common.constant.CaseStatus;
import com.bxf.hradmin.common.exception.HRHeadCountException;
import com.bxf.hradmin.common.model.QueryMode;
import com.bxf.hradmin.common.model.QueryPage;
import com.bxf.hradmin.common.model.QueryParameter;
import com.bxf.hradmin.common.utils.BeanUtils;
import com.bxf.hradmin.common.utils.QueryParameterTransformer;
import com.bxf.hradmin.common.web.utils.UserUtils;
import com.bxf.hradmin.headcount.dto.CaseDetailDto;
import com.bxf.hradmin.headcount.dto.CaseMainDto;
import com.bxf.hradmin.headcount.model.CaseDetail;
import com.bxf.hradmin.headcount.model.CaseMain;
import com.bxf.hradmin.headcount.repositories.CaseDetailRepository;
import com.bxf.hradmin.headcount.repositories.CaseMainRepository;
import com.bxf.hradmin.headcount.service.CaseMgrService;

/**
 * CaseMainServiceImpl
 *
 * @since 2016-05-15
 * @author Bo-Xuan Fan
 */
@Service
public class CaseMgrServiceImpl implements CaseMgrService {

    private static final Map<String, CaseStatusJudger> JUDGERS = new HashMap<>();

    @Autowired
    private CaseMainRepository mainRepository;

    @Autowired
    private CaseDetailRepository detailRepository;

    static {
        JUDGERS.put(CaseStatusJudger.CONFIRM_CASE, (caseStatus, preCaseStatus) -> {
            return preCaseStatus == null ||
                    CaseStatus.REPLY_CASE_STATUS.getCode().equals(caseStatus);
        });
        JUDGERS.put(CaseStatusJudger.REPLY_CASE, (caseStatus, preCaseStatus) -> {
            return CaseStatus.UNPASSED_CASE_STATUS.getCode().equals(caseStatus);
        });
        JUDGERS.put(CaseStatusJudger.DISPOSED_CASE, (caseStatus, preCaseStatus) -> {
            return CaseStatus.UNPASSED_CASE_STATUS.getCode().equals(caseStatus);
        });
        JUDGERS.put(CaseStatusJudger.HANDLING_CASE, (caseStatus, preCaseStatus) -> {
            return CaseStatus.PASSED_CASE_STATUS.getCode().equals(caseStatus) ||
                    CaseStatus.HANDLING_CASE_STATUS.getCode().equals(caseStatus);
        });
        JUDGERS.put(CaseStatusJudger.CLOSE_CASE, (caseStatus, preCaseStatus) -> {
            return CaseStatus.HANDLING_CASE_STATUS.getCode().equals(caseStatus);
        });
    }

    @Override
    public void saveCaseMain(CaseMainDto caseMain) {
        caseMain.setApplier(UserUtils.getUser().getName());
        Date updateDatetime = new Date();
        caseMain.setUpdateDatetime(updateDatetime);
        caseMain.setCaseStatus(CaseStatus.RECEIVED_CASE_STATUS.getCode());
        CaseMain domain = BeanUtils.copyProperties(CaseMain.class, caseMain);
        domain.setCaseId(mainRepository.getCaseNo(caseMain.getDept()));
        mainRepository.save(domain);

        CaseDetail detail = new CaseDetail();
        detail.setCaseId(domain.getCaseId());
        detail.setUpdateDatetime(updateDatetime);
        detail.setUpdater(UserUtils.getUser().getName());
        detail.setCaseStatus(CaseStatus.RECEIVED_CASE_STATUS.getCode());
        detailRepository.save(detail);
    }

    @Override
    public QueryPage find(CaseMainDto queryCond) {
        Specification<CaseMain> spec = (root, query, criteriaBuilder) -> {
            return generateCondition(queryCond, root, criteriaBuilder);
        };
        Direction direction = queryCond.isAsc() ? Direction.ASC : Direction.DESC;
        Pageable pageable = new PageRequest(queryCond.getActivePage(), queryCond.getLimit(), new Sort(direction, queryCond.getSort()));
        Page<CaseMain> caseMains = mainRepository.findAll(spec, pageable);
        QueryPage result = new QueryPage();
        result.setActivePage(queryCond.getActivePage());
        result.setTotalPages(caseMains.getTotalPages());
        result.setTotalCounts(caseMains.getTotalElements());
        result.setResult(caseMains.getContent());
        return result;
    }

    private Predicate generateCondition(CaseMainDto queryCond, Root<CaseMain> root, CriteriaBuilder criteriaBuilder) {
        List<QueryParameter> queryParameters = new ArrayList<>();
        queryParameters.add(new QueryParameter(QueryMode.LIKE, "caseId", queryCond.getCaseId()));
        queryParameters.add(new QueryParameter(QueryMode.GREATER_EQUALS,
                "updateDatetime", generateBeginDatetime(queryCond.getBeginDatetime())));
        queryParameters.add(new QueryParameter(QueryMode.LESS_EQUALS,
                "updateDatetime", generateBeginDatetime(queryCond.getEndDateTime())));
        queryParameters.add(new QueryParameter(QueryMode.EQUALS, "caseStatus", queryCond.getCaseStatus()));
        queryParameters.add(new QueryParameter(QueryMode.EQUALS, "dept", queryCond.getDept()));
        queryParameters.add(new QueryParameter(QueryMode.EQUALS, "unit", queryCond.getUnit()));
        queryParameters.add(new QueryParameter(QueryMode.EQUALS, "hrmRole", queryCond.getHrmRole()));
        queryParameters.add(new QueryParameter(QueryMode.EQUALS, "hrmType", queryCond.getHrmType()));
        queryParameters.add(new QueryParameter(QueryMode.EQUALS, "applier", queryCond.getApplier()));
        queryParameters.add(new QueryParameter(QueryMode.GREATER_EQUALS,
                "requiredBeginDate", generateBeginDatetime(queryCond.getRequiredBeginDate())));
        queryParameters.add(new QueryParameter(QueryMode.LESS_EQUALS,
                "requiredEndDate", generateEndDatetime(queryCond.getRequiredEndDate())));
        return QueryParameterTransformer.generatePredicate(root, criteriaBuilder, queryParameters.toArray(new QueryParameter[0]));
    }

    private Date generateBeginDatetime(Date date) {
        if (date == null) {
            return null;
        }

        Calendar beginDatetime = new GregorianCalendar();
        beginDatetime.setTime(date);
        beginDatetime.set(Calendar.HOUR_OF_DAY, 0);
        beginDatetime.set(Calendar.MINUTE, 0);
        beginDatetime.set(Calendar.SECOND, 0);
        beginDatetime.set(Calendar.MILLISECOND, 0);
        return beginDatetime.getTime();
    }

    private Date generateEndDatetime(Date date) {
        if (date == null) {
            return null;
        }

        Calendar endDatetime = new GregorianCalendar();
        endDatetime.setTime(date);
        endDatetime.set(Calendar.HOUR_OF_DAY, 23);
        endDatetime.set(Calendar.MINUTE, 59);
        endDatetime.set(Calendar.SECOND, 59);
        endDatetime.set(Calendar.MILLISECOND, 999);
        return endDatetime.getTime();
    }

    @Override
    public CaseMainDto findOne(String caseId) {
        if (caseId == null) {
            return null;
        }

        CaseMain domain = mainRepository.findOne(caseId);
        if (domain == null) {
            return null;
        }

        CaseMainDto caseMain = BeanUtils.copyProperties(CaseMainDto.class, domain);
        List<CaseDetail> domainDetails = detailRepository.findByCaseId(caseId);
        caseMain.setCaseDetails(BeanUtils.copyListProperties(CaseDetailDto.class, domainDetails));
        return caseMain;
    }

    @Override
    public void updateConfirmCase(String caseId, boolean confirm, String msgDetail) {
        CaseMain caseMain = mainRepository.findOne(caseId);
        if (!JUDGERS.get(CaseStatusJudger.CONFIRM_CASE).validCaseStatus(caseMain.getCaseStatus(), caseMain.getPreCaseStatus())) {
            throw new HRHeadCountException("Unsupported case operation.");
        }

        if (confirm) {
            caseMain.setCaseStatus(CaseStatus.PASSED_CASE_STATUS.getCode());
        } else {
            caseMain.setCaseStatus(CaseStatus.UNPASSED_CASE_STATUS.getCode());
        }
        caseMain.setPreCaseStatus(CaseStatus.RECEIVED_CASE_STATUS.getCode());
        caseMain.setUpdateDatetime(new Date());
        mainRepository.save(caseMain);

        saveCaseDetail(msgDetail, caseMain);
    }

    @Override
    public void updateReplyCase(String caseId, String msgDetail) {
        doUpdate(caseId, msgDetail, CaseStatusJudger.REPLY_CASE, CaseStatus.REPLY_CASE_STATUS.getCode());
    }

    @Override
    public void updateDisposedCase(String caseId, String msgDetail) {
        doUpdate(caseId, msgDetail, CaseStatusJudger.DISPOSED_CASE, CaseStatus.DISPOSED_CASE_STATUS.getCode());
    }

    @Override
    public void updateProcessCase(String caseId, String msgDetail) {
        doUpdate(caseId, msgDetail, CaseStatusJudger.HANDLING_CASE, CaseStatus.HANDLING_CASE_STATUS.getCode());
    }

    @Override
    public void updateCloseCase(String caseId, String msgDetail) {
        doUpdate(caseId, msgDetail, CaseStatusJudger.CLOSE_CASE, CaseStatus.CLOSE_CASE_STATUS.getCode());
    }

    private void doUpdate(String caseId, String msgDetail, String judgerCode, String caseStatusCode) {
        CaseMain caseMain = mainRepository.findOne(caseId);
        if (!JUDGERS.get(judgerCode).validCaseStatus(caseMain.getCaseStatus(), caseMain.getPreCaseStatus())) {
            throw new HRHeadCountException("Unsupported case operation.");
        }

        caseMain.setPreCaseStatus(caseMain.getCaseStatus());
        caseMain.setCaseStatus(caseStatusCode);
        caseMain.setUpdateDatetime(new Date());
        mainRepository.save(caseMain);
        saveCaseDetail(msgDetail, caseMain);
    }

    private void saveCaseDetail(String msgDetail, CaseMain caseMain) {
        CaseDetail caseDetail = new CaseDetail();
        caseDetail.setCaseId(caseMain.getCaseId());
        caseDetail.setCaseStatus(caseMain.getCaseStatus());
        caseDetail.setUpdater(UserUtils.getUser().getName());
        caseDetail.setUpdateDatetime(caseMain.getUpdateDatetime());
        caseDetail.setMsgDetail(msgDetail);
        detailRepository.save(caseDetail);
    }

    @FunctionalInterface
    private interface CaseStatusJudger {
        String CONFIRM_CASE = "confirmCase";
        String REPLY_CASE = "replyCase";
        String DISPOSED_CASE = "discardCase";
        String HANDLING_CASE = "processCase";
        String CLOSE_CASE = "closeCase";
        boolean validCaseStatus(String caseStatus, String preCaseStatus);
    }
}
