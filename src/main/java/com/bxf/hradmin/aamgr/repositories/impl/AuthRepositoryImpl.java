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
package com.bxf.hradmin.aamgr.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.bxf.hradmin.aamgr.repositories.AuthRepository;

/**
 * AuthRepositoryImpl
 *
 * @since 2016-06-18
 * @author Bo-Xuan Fan
 */
@Repository
@SuppressWarnings("unchecked")
public class AuthRepositoryImpl implements AuthRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Long> findRoleIdsByAccount(String account) {
        Query query = entityManager.createNativeQuery("SELECT ROLE_ID FROM APP_USER_ROLE WHERE USER_ACCOUNT = :account")
            .unwrap(SQLQuery.class).addScalar("ROLE_ID", StandardBasicTypes.LONG);
        query.setParameter("account", account);
        return query.list();
    }

    @Override
    public List<Long> findFuncIdsByRoleId(Long roleId) {
        Query query = entityManager.createNativeQuery("SELECT FUNC_ID FROM APP_ROLE_FUNCTION WHERE ROLE_ID = :roleId")
                .unwrap(SQLQuery.class).addScalar("FUNC_ID", StandardBasicTypes.LONG);
        query.setParameter("roleId", roleId);
        return query.list();
    }
}
