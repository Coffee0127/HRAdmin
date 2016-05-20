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
package com.bxf.hradmin.admin.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bxf.hradmin.admin.dto.CaseDetailDto;
import com.bxf.hradmin.admin.dto.CaseMainDto;
import com.bxf.hradmin.admin.model.CaseDetail;
import com.bxf.hradmin.admin.model.CaseMain;
import com.bxf.hradmin.admin.repositories.CaseDetailRepository;
import com.bxf.hradmin.admin.repositories.CaseMainRepository;
import com.bxf.hradmin.admin.service.CaseMgrService;
import com.bxf.hradmin.common.constant.CaseStatus;
import com.bxf.hradmin.common.model.Page;
import com.bxf.hradmin.common.utils.BeanUtils;
import com.bxf.hradmin.common.web.utils.UserUtils;

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
        caseMain.setApplier(UserUtils.getUser().getUserName());
        Date updateDatetime = new Date();
        caseMain.setUpdateDatetime(updateDatetime);
        caseMain.setCaseStatus(CaseStatus.RECEIVED_CASE_STATUS.getCode());
        CaseMain domain = BeanUtils.copyProperties(CaseMain.class, caseMain);
        mainRepository.save(domain);

        CaseDetail detail = new CaseDetail();
        detail.setCaseId(domain.getCaseId());
        detail.setUpdateDatetime(updateDatetime);
        detail.setCaseStatus(CaseStatus.RECEIVED_CASE_STATUS.getCode());
        detailRepository.save(detail);
    }

    @Override
    public Page find(CaseMainDto queryCond, int page, int size) {
        Specification<CaseMain> spec = (root, query, criteriaBuilder) -> {
            Predicate condition = criteriaBuilder.conjunction();
            return condition;
        };
        Pageable pageable = new PageRequest(page, size);
        org.springframework.data.domain.Page<CaseMain> caseMains = mainRepository.findAll(spec, pageable);
        Page result = new Page();
        result.setActivePage(page);
        result.setTotalPages(caseMains.getTotalPages());
        result.setTotalCounts(caseMains.getTotalElements());
        result.setResult(caseMains.getContent());
        return result;
    }

    @Override
    public CaseMainDto findOne(Long caseId) {
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
    public void updateConfirmCase(Long caseId, boolean confirm, String msgDetail) {
        CaseMain caseMain = mainRepository.findOne(caseId);
        if (confirm) {
            caseMain.setCaseStatus(CaseStatus.PASSED_CASE_STATUS.getCode());
        } else {
            caseMain.setCaseStatus(CaseStatus.UNPASSED_CASE_STATUS.getCode());
        }
        caseMain.setUpdateDatetime(new Date());
        mainRepository.save(caseMain);

        CaseDetail caseDetail = new CaseDetail();
        caseDetail.setCaseId(caseMain.getCaseId());
        caseDetail.setCaseStatus(caseMain.getCaseStatus());
        caseDetail.setUpdateDatetime(caseMain.getUpdateDatetime());
        caseDetail.setMsgDetail(msgDetail);
        detailRepository.save(caseDetail);
    }
}
