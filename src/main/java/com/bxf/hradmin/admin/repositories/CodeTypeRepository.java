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
package com.bxf.hradmin.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bxf.hradmin.admin.model.CodeType;
import com.bxf.hradmin.admin.model.CodeTypePK;

/**
 * CodeTypeRepository
 *
 * @since 2016-05-12
 * @author Bo-Xuan Fan
 */
public interface CodeTypeRepository extends JpaSpecificationExecutor<CodeType>,
        JpaRepository<CodeType, CodeTypePK> {

    @Query(value = "SELECT * FROM CFG_CODETYPE c WHERE c.codeCat = :codeCat", nativeQuery = true)
    List<CodeType> findByCodeCat(@Param("codeCat") String codeCat);

    @Query(value = "SELECT c FROM CodeType c WHERE c.codeTypePK.codeId LIKE CONCAT('%', :codeId, '%') AND c.codeTypePK.codeCat = :codeCat")
    List<CodeType> findByCodeIdLike(@Param("codeId") String codeId, @Param("codeCat") String codeCat);
}
