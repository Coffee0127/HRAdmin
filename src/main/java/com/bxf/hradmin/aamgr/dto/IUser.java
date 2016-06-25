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
package com.bxf.hradmin.aamgr.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * IUser
 *
 * @since 2016-05-28
 * @author Bo-Xuan Fan
 */
public interface IUser {

    /**
     * 取得使用者帳號
     * @return
     */
    String getAccount();

    /**
     * 取得使用者姓名
     * @return
     */
    String getName();


    /**
     * 取得使用者權限
     * @return
     */
    Collection<? extends GrantedAuthority> getAuthorities();

    /**
     * 帳號是否啟用
     * @return
     */
    UserStatus getUserStatus();

    enum UserStatus {
        /** 啟用 */
        ENABLE("Y"),
        /** 未啟用 */
        DISABLE("N"),
        /** 鎖定 */
        LOCKED("L");

        String code;

        UserStatus(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static UserStatus transformCode(String code) {
            for (UserStatus status : values()) {
                if (status.code.equals(code)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown code: " + code);
        }
    }
}
