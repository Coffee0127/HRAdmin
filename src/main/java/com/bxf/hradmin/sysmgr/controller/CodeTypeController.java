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
package com.bxf.hradmin.sysmgr.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.bxf.hradmin.common.constant.CodeTypeConstants;
import com.bxf.hradmin.sysmgr.model.CodeType;
import com.bxf.hradmin.sysmgr.service.CodeTypeService;

/**
 * CodeTypeController
 *
 * @since 2016-05-13
 * @author Bo-Xuan Fan
 */
@Controller
@RequestMapping("/codeType")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class CodeTypeController {

    private static JSONObject _depts = new JSONObject();
    private static JSONObject _units = new JSONObject();
    private static JSONArray _hrmRoles = new JSONArray();
    private static JSONArray _hrmTypes = new JSONArray();

    @Autowired
    private CodeTypeService codeTypeService;

    @PostConstruct
    private void init() {
        refreshDeptsAndUnits();
        refreshHrmRoles();
        refreshHrmTypes();
    }

    private void refreshDeptsAndUnits() {
        List<CodeType> depts = codeTypeService.findByCodeType(CodeTypeConstants.DEPT_CAT);
        for (CodeType dept : depts) {
            String deptId = dept.getCodeId();
            _depts.put(deptId, dept.getCodeValue());
            _units.put(deptId, new JSONArray());
            List<CodeType> units = codeTypeService.findByCodeIdAndCodeCat(deptId, CodeTypeConstants.UNIT_CAT);
            if (units.isEmpty()) {
                JSONObject unitJSON = new JSONObject();
                unitJSON.put(deptId, dept.getCodeValue());
                _units.getJSONArray(deptId).put(unitJSON);
                continue;
            }
            for (CodeType unit : units) {
                JSONObject unitJSON = new JSONObject();
                unitJSON.put(unit.getCodeId(), unit.getCodeValue());
                _units.getJSONArray(deptId).put(unitJSON);
            }
        }
    }

    private void refreshHrmRoles() {
        refreshJSONArray(CodeTypeConstants.HRM_ROLE_CAT, _hrmRoles);
    }

    private void refreshHrmTypes() {
        refreshJSONArray(CodeTypeConstants.HRM_TYPE_CAT, _hrmTypes);
    }

    private void refreshJSONArray(String codeCat, JSONArray toBeUpdate) {
        List<CodeType> roles = codeTypeService.findByCodeType(codeCat);
        for (CodeType role : roles) {
            JSONObject roleJSON = new JSONObject();
            roleJSON.put(role.getCodeId(), role.getCodeValue());
            toBeUpdate.put(roleJSON);
        }
    }

    @RequestMapping(value = "/query", produces = { "application/json; charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public String queryCodeTypes() {
        JSONObject codeTypes = new JSONObject();
        codeTypes.put("depts", _depts);
        codeTypes.put("hrmRoles", _hrmRoles);
        codeTypes.put("hrmTypes", _hrmTypes);
        return codeTypes.toString();
    }


    @RequestMapping(value = "/queryUnits", produces = { "application/json; charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public String queryUnits(String deptId) {
        if (StringUtils.isBlank(deptId)) {
            return "{}";
        }
        return _units.getJSONArray(deptId).toString();
    }

    @RequestMapping(value = "/codeMap.js", headers = "Accept=*/*", produces = { "application/javascript; charset=UTF-8" })
    @ResponseBody
    public String codeMap() {
        JSONObject codeMap = new JSONObject(_depts.toString());
        for (int i = 0; i < _hrmRoles.length(); i++) {
            JSONObject json = _hrmRoles.getJSONObject(i);
            String key = json.keys().next().toString();
            codeMap.put(key, json.getString(key));
        }
        for (int i = 0; i < _hrmTypes.length(); i++) {
            JSONObject json = _hrmTypes.getJSONObject(i);
            String key = json.keys().next().toString();
            codeMap.put(key, json.getString(key));
        }

        List<CodeType> cases = codeTypeService.findByCodeType(CodeTypeConstants.CASE_STATUS_CAT);
        for (CodeType codeType : cases) {
            codeMap.put(codeType.getCodeId(), codeType.getCodeValue());
        }

        for (Object key : _units.keySet()) {
            JSONArray jsonArray = _units.getJSONArray(String.valueOf(key));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String subKey = json.keys().next().toString();
                codeMap.put(subKey, json.getString(subKey));
            }
        }

        return "var codeMap = " + codeMap.toString() + ";";
    }
}
