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
package com.bxf.hradmin.common.web.utils;

import javax.naming.ldap.LdapName;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bxf.hradmin.aamgr.dto.AppUserDto;
import com.bxf.hradmin.aamgr.dto.IUser;

/**
 * UserUtils
 *
 * @since 2016-05-12
 * @author Bo-Xuan Fan
 */
public final class UserUtils {

    private UserUtils() {
    }

    /**
     * get User object
     *
     * @return user object
     * @throws AuthenticationException
     */
    public static IUser getUser() throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserDto user = null;
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal != null) {
                user = new AppUserDto();
                user.setSourceIp(getUserSourceIp());
                // xml defined user
                /*if (principal instanceof org.springframework.security.core.userdetails.User) {
                }*/

                if (principal instanceof LdapUserDetails) {
                    LdapUserDetails ldapUserDetails = (LdapUserDetails) principal;
                    String dn = ldapUserDetails.getDn();
                    LdapName ldapName = LdapUtils.newLdapName(dn);
                    String userName = String.valueOf(LdapUtils.getValue(ldapName, "cn"));
                    user.setName(userName);
                    user.setAccount(ldapUserDetails.getUsername());
                    user.setAuthorities(ldapUserDetails.getAuthorities());
                }
            }
        }
        return user;
    }

    private static String getUserSourceIp() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getRemoteAddr();
    }
}
