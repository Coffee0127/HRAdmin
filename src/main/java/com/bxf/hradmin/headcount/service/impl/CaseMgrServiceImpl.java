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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
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
import com.bxf.hradmin.common.model.QueryPage;
import com.bxf.hradmin.common.utils.BeanUtils;
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

    @Autowired
    private CaseMainRepository mainRepository;

    @Autowired
    private CaseDetailRepository detailRepository;

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

    private Predicate generateCondition(CaseMainDto queryCond,
            Root<CaseMain> root, CriteriaBuilder criteriaBuilder) {
        Predicate condition = criteriaBuilder.conjunction();
        if (StringUtils.isNotBlank(queryCond.getCaseId())) {
            condition = criteriaBuilder.and(condition, criteriaBuilder.like(root.<String>get("caseId"), "%" + queryCond.getCaseId() + "%"));
        }

        if (queryCond.getBeginDatetime() != null) {
            condition = criteriaBuilder.and(condition, criteriaBuilder
                    .greaterThanOrEqualTo(root.<Date>get("updateDatetime"),
                            generateBeginDatetime(queryCond.getBeginDatetime())));
        }

        if (queryCond.getEndDateTime() != null) {
            condition = criteriaBuilder.and(condition, criteriaBuilder
                    .lessThanOrEqualTo(root.<Date>get("updateDatetime"),
                            generateEndDatetime(queryCond.getEndDateTime())));
        }

        if (StringUtils.isNotEmpty(queryCond.getCaseStatus())) {
            condition = criteriaBuilder.and(condition, criteriaBuilder.equal(root.get("caseStatus"), queryCond.getCaseStatus()));
        }

        if (StringUtils.isNotEmpty(queryCond.getDept())) {
            condition = criteriaBuilder.and(condition, criteriaBuilder.equal(root.get("dept"), queryCond.getDept()));
        }

        if (StringUtils.isNotEmpty(queryCond.getUnit())) {
            condition = criteriaBuilder.and(condition, criteriaBuilder.equal(root.get("unit"), queryCond.getUnit()));
        }

        if (StringUtils.isNotEmpty(queryCond.getHrmRole())) {
            condition = criteriaBuilder.and(condition, criteriaBuilder.equal(root.get("hrmRole"), queryCond.getHrmRole()));
        }

        if (StringUtils.isNotEmpty(queryCond.getHrmType())) {
            condition = criteriaBuilder.and(condition, criteriaBuilder.equal(root.get("hrmType"), queryCond.getHrmType()));
        }

        if (queryCond.getRequiredBeginDate() != null) {
            condition = criteriaBuilder.and(condition, criteriaBuilder
                    .greaterThanOrEqualTo(root.<Date>get("requiredBeginDate"),
                            generateBeginDatetime(queryCond.getRequiredBeginDate())));
        }

        if (queryCond.getRequiredEndDate() != null) {
            condition = criteriaBuilder.and(condition, criteriaBuilder
                    .lessThanOrEqualTo(root.<Date>get("requiredEndDate"),
                            generateEndDatetime(queryCond.getRequiredEndDate())));
        }

        return condition;
    }

    private Date generateBeginDatetime(Date date) {
        Calendar beginDatetime = new GregorianCalendar();
        beginDatetime.setTime(date);
        beginDatetime.set(Calendar.HOUR_OF_DAY, 0);
        beginDatetime.set(Calendar.MINUTE, 0);
        beginDatetime.set(Calendar.SECOND, 0);
        beginDatetime.set(Calendar.MILLISECOND, 0);
        return beginDatetime.getTime();
    }

    private Date generateEndDatetime(Date date) {
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
        if (!isValidCaseStatus(caseMain)) {
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

        CaseDetail caseDetail = new CaseDetail();
        caseDetail.setCaseId(caseMain.getCaseId());
        caseDetail.setCaseStatus(caseMain.getCaseStatus());
        caseDetail.setUpdateDatetime(caseMain.getUpdateDatetime());
        caseDetail.setMsgDetail(msgDetail);
        detailRepository.save(caseDetail);
    }

    private boolean isValidCaseStatus(CaseMain caseMain) {
        String preCaseStatus = caseMain.getPreCaseStatus();
        return preCaseStatus == null || CaseStatus.RESPONSE_CASE_STATUS.getCode().equals(preCaseStatus);
    }
}
