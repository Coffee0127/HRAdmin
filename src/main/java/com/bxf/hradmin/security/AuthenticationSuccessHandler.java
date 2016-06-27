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
package com.bxf.hradmin.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.bxf.hradmin.aamgr.service.AuthService;
import com.bxf.hradmin.aamgr.service.UserMgrService;
import com.bxf.hradmin.common.web.utils.UserUtils;

/**
 * AuthenticationSuccessHandler
 *
 * @since 2016-06-26
 * @author Bo-Xuan Fan
 */
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    /**
     * redirectStrategy
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 登入後跳轉頁面
     */
    private String defaultTargetUrl = "/";

    @Autowired
    private UserMgrService userService;

    @Autowired
    private AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        // update last login dt
        userService.updateLastLoginDt(new Date(), UserUtils.getUser().getAccount());

        // generate menu
        request.getSession(false).setAttribute("rootMenu", authService.findMenu(UserUtils.getUser()));

        doRedirect(request, response);
    }

    /**
     * 根據session中保存的url做重導
     * @param request request
     * @throws IOException IOException
     */
    protected void doRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if (savedRequest != null) {
            redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
        } else {
            redirectStrategy.sendRedirect(request, response, defaultTargetUrl);
        }
    }

    /**
     * set default redirect url
     * @param defaultTargetUrl
     */
    public void setDefaultTargetUrl(String defaultTargetUrl) {
        if (StringUtils.isNotEmpty(defaultTargetUrl) && !defaultTargetUrl.startsWith("/")) {
            defaultTargetUrl = '/' + defaultTargetUrl;
        }
        this.defaultTargetUrl = defaultTargetUrl;
    }
}
