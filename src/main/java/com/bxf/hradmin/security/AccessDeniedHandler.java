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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.AccessDeniedException;

/**
 * AccessDeniedHandler
 *
 * @since 2016-06-25
 * @author Bo-Xuan Fan
 */
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    /**
     * 預設重導頁面
     */
    private String defaultRedirectUrl = "/";

    /**
     * 多久後跳轉
     */
    private int timeToRedirect = 5;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {

        request.setAttribute("defaultRedirectUrl", defaultRedirectUrl);
        request.setAttribute("timeToRedirect", timeToRedirect);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/errorPages/403.jsp");
        dispatcher.forward(request, response);
        return;
    }

    public void setDefaultRedirectUrl(String defaultRedirectUrl) {
        if (StringUtils.isNotEmpty(defaultRedirectUrl) && !defaultRedirectUrl.startsWith("/")) {
            defaultRedirectUrl = '/' + defaultRedirectUrl;
        }
        this.defaultRedirectUrl = defaultRedirectUrl;
    }

    /**
     * set timeToRedirect in seconds
     * @param timeToRedirect
     */
    public void setTimeToRedirect(int timeToRedirect) {
        this.timeToRedirect = timeToRedirect;
    }
}
