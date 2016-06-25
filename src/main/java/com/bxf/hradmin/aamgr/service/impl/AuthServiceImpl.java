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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bxf.hradmin.aamgr.dto.AppAuthorizationDto;
import com.bxf.hradmin.aamgr.dto.AppRoleDto;
import com.bxf.hradmin.aamgr.repositories.AuthRepository;
import com.bxf.hradmin.aamgr.repositories.FunctionRepository;
import com.bxf.hradmin.aamgr.repositories.RoleRepository;
import com.bxf.hradmin.aamgr.service.AuthService;
import com.bxf.hradmin.common.utils.BeanUtils;

/**
 * AuthServiceImpl
 *
 * @since 2016-06-18
 * @author Bo-Xuan Fan
 */
@Service
public class AuthServiceImpl implements AuthService {

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
}
