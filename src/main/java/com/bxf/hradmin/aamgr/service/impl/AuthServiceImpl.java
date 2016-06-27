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
package com.bxf.hradmin.aamgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bxf.hradmin.aamgr.dto.AppAuthorizationDto;
import com.bxf.hradmin.aamgr.dto.AppFunctionDto;
import com.bxf.hradmin.aamgr.dto.AppRoleDto;
import com.bxf.hradmin.aamgr.dto.IUser;
import com.bxf.hradmin.aamgr.dto.MenuItem;
import com.bxf.hradmin.aamgr.repositories.AuthRepository;
import com.bxf.hradmin.aamgr.repositories.FunctionRepository;
import com.bxf.hradmin.aamgr.repositories.RoleRepository;
import com.bxf.hradmin.aamgr.service.AuthService;
import com.bxf.hradmin.common.utils.BeanUtils;
import com.bxf.hradmin.common.utils.SystemConfig;

/**
 * AuthServiceImpl
 *
 * @since 2016-06-18
 * @author Bo-Xuan Fan
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private SystemConfig config;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FunctionRepository funcRepository;

    @Override
    public List<AppAuthorizationDto> findAuthorization(String userAccount) {
        List<AppAuthorizationDto> authorizations = new ArrayList<AppAuthorizationDto>();
        for (Long roleId : authRepository.findRoleIdsByAccount(userAccount)) {
            AppAuthorizationDto authorization = new AppAuthorizationDto();
            authorization.setUserAccount(userAccount);
            AppRoleDto role = new AppRoleDto();
            BeanUtils.copyProperties(roleRepository.findOne(roleId), role);
            authorization.setRole(role);
            authorizations.add(authorization);
        }
        return authorizations;
    }

    @Override
    public List<MenuItem> findMenu(IUser user) {
        Set<AppFunctionDto> functions = findUserFunctions(user);
        List<MenuItem> menus = new ArrayList<>();
        if (Boolean.valueOf(config.getProperty("menu.index.show", "false"))) {
            menus.add(new MenuItem(config.getProperty("menu.index.icon"), config.getProperty("menu.index.desc"), config.getProperty("menu.index.url")));
        }

        Map<String, MenuItem> menuContainer = new HashMap<>();
        for (AppFunctionDto function : functions) {
            MenuItem menu = new MenuItem(function.getIcon(), function.getName(), function.getPath());
            if (function.getParent().equals(function.getCode())) {
                menuContainer.put(function.getCode(), menu);
                menus.add(menu);
                continue;
            }
            menuContainer.get(function.getParent()).addSubMenuItem(menu);
        }
        return menus;
    }

    private Set<AppFunctionDto> findUserFunctions(IUser user) {
        Set<AppFunctionDto> functions = new TreeSet<>();
        @SuppressWarnings("unchecked")
        List<AppAuthorizationDto> authorites = (List<AppAuthorizationDto>) user.getAuthorities();
        for (AppAuthorizationDto authorization : authorites) {
            for (Long funcId : authRepository.findFuncIdsByRoleId(authorization.getRole().getId())) {
                AppFunctionDto function = BeanUtils.copyProperties(AppFunctionDto.class, funcRepository.findOne(funcId));
                functions.add(function);
            }
        }
        return functions;
    }
}
