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
package com.bxf.hradmin.headcount.controller;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.bxf.hradmin.headcount.dto.CaseMainDto;
import com.bxf.hradmin.headcount.service.CaseMgrService;

/**
 * HeadcountController
 *
 * @since 2016-05-12
 * @author Bo-Xuan Fan
 */
@Controller
@RequestMapping("/headcount")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class HeadcountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeadcountController.class);

    @Autowired
    private CaseMgrService service;

    @RequestMapping("/apply")
    public ModelAndView applyIndex(Model model) {
        CaseMainDto caseMain = new CaseMainDto();
        caseMain.setRequiredBeginDate(new java.sql.Date(new Date().getTime()));
        model.addAttribute("caseMain", caseMain);
        return new ModelAndView("applyHeadcount");
    }

    @RequestMapping("/view")
    public ModelAndView mgrIndex(Model model) {
        return new ModelAndView("viewHeadcount");
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ModelAndView submit(@ModelAttribute("caseMain") CaseMainDto caseMain, Model model) {
        model.addAttribute("caseMain", caseMain);
        service.saveCaseMain(caseMain);
        return new ModelAndView("applyHeadcount");
    }

    @RequestMapping(value = "/find",
            produces = "application/json; charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public String find(@RequestBody CaseMainDto queryCond) {
        if (StringUtils.isNotBlank(queryCond.getSort()) && StringUtils.isNotBlank(queryCond.getOrder())) {
            queryCond.setAsc("asc".equalsIgnoreCase(queryCond.getOrder()));
        } else {
            queryCond.setSort("updateDatetime");
            queryCond.setAsc(false);
        }
        return service.find(queryCond).toString();
    }

    @RequestMapping(value = "/findOne", produces = { "application/json; charset=UTF-8" })
    @ResponseBody
    public String findOne(@RequestParam("caseId") String caseId) {
        return new JSONObject(service.findOne(caseId)).toString();
    }

    @RequestMapping(value = "/confirm", produces = { "application/json; charset=UTF-8" })
    @ResponseBody
    public String confirm(@RequestParam("caseId") String caseId,
            @RequestParam(value = "confirm") String confirm,
            @RequestParam("msgDetail") String msgDetail) {
        JSONObject result = new JSONObject();
        try {
            service.updateConfirmCase(caseId, confirm.equalsIgnoreCase("Y"), msgDetail);
            result.put("code", 0);
            CaseMainDto caseMain = service.findOne(caseId);
            caseMain.setCaseDetails(null);
            result.put("desc", caseMain);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result.put("code", -1);
            result.put("desc", e.getMessage());
        }
        return result.toString();
    }
}
