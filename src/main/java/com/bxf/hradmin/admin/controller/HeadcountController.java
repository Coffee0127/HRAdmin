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
package com.bxf.hradmin.admin.controller;

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.bxf.hradmin.admin.dto.CaseMainDto;
import com.bxf.hradmin.admin.service.CaseMgrService;

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
    public ModelAndView index(Model model) {
        CaseMainDto caseMain = new CaseMainDto();
        caseMain.setRequiredBeginDate(new java.sql.Date(new Date().getTime()));
        model.addAttribute("caseMain", caseMain);
        return new ModelAndView("apply");
    }

    @RequestMapping("/view")
    public ModelAndView manager(Model model) {
        return new ModelAndView("view");
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ModelAndView submit(@ModelAttribute("caseMain") CaseMainDto caseMain, Model model) {
        model.addAttribute("caseMain", caseMain);
        service.saveCaseMain(caseMain);
        return new ModelAndView("apply");
    }

    @RequestMapping(value = "/find", produces = { "application/json; charset=UTF-8" })
    @ResponseBody
    public String find(@RequestParam("activePage") int activePage, @RequestParam("limit") int size) {
        CaseMainDto queryCond = new CaseMainDto();
        return service.find(queryCond, activePage, size).toString();
    }

    @RequestMapping(value = "/findOne", produces = { "application/json; charset=UTF-8" })
    @ResponseBody
    public String findOne(@RequestParam("caseId") long caseId) {
        return new JSONObject(service.findOne(caseId)).toString();
    }

    @RequestMapping(value = "/confirm", produces = { "application/json; charset=UTF-8" })
    @ResponseBody
    public String confirm(@RequestParam("caseId") long caseId,
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
