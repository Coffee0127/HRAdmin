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
package com.bxf.hradmin.common.utils;

import java.lang.reflect.Array;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import com.bxf.hradmin.common.model.QueryParameter;

/**
 * QueryParameterHelpler
 *
 * @since 2016-06-25
 * @author Bo-Xuan Fan
 */
public final class QueryParameterTransformer {

    private QueryParameterTransformer() {
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Predicate generatePredicate(Root root, CriteriaBuilder builder, QueryParameter... queryParameters) {
        Predicate condition = builder.conjunction();
        for (QueryParameter queryParameter : queryParameters) {
            Object value = queryParameter.getValue();
            if (value == null || StringUtils.isBlank(value.toString())) {
                continue;
            }
            Path path = root.get(queryParameter.getKey());
            switch (queryParameter.getMode()) {
                case BETWEEN:
                    Object[] values = asArray(value);
                    if (values != null) {
                        condition = builder.and(builder.between((Path<Comparable>) path, asComparable(values[0]), asComparable(values[1])));
                    }
                    break;
                case GREATER_THAN:
                    condition = builder.and(condition, builder.greaterThan((Path<Comparable>) path, asComparable(value)));
                    break;
                case GREATER_EQUALS:
                    condition = builder.and(condition, builder.greaterThanOrEqualTo((Path<Comparable>) path, asComparable(value)));
                    break;
                case LESS_THAN:
                    condition = builder.and(condition, builder.lessThan((Path<Comparable>) path, asComparable(value)));
                    break;
                case LESS_EQUALS:
                    condition = builder.and(condition, builder.lessThanOrEqualTo((Path<Comparable>) path, asComparable(value)));
                    break;
                case IS_NULL:
                    condition = builder.and(condition, builder.isNull(path));
                    break;
                case IS_NOT_NULL:
                    condition = builder.and(condition, builder.isNotNull(path));
                    break;
                case IN:
                    condition = builder.and(condition, path.in(asArray(value)));
                    break;
                case NOT_IN:
                    condition = builder.and(condition, builder.not(path.in(asArray(value))));
                    break;
                case LIKE:
                    condition = builder.and(condition, builder.like(path, "%" + String.valueOf(value) + "%"));
                    break;
                case NOT_LIKE:
                    condition = builder.and(condition, builder.notLike(path, "%" + String.valueOf(value) + "%"));
                    break;
                case EQUALS:
                    condition = builder.and(condition, builder.equal(path, value));
                    break;
                case NOT_EQUALS:
                    condition = builder.and(condition, builder.notEqual(path, value));
                    break;
                default:
                    break;
            }
        }
        return condition;
    }

    @SuppressWarnings("rawtypes")
    private static Object[] asArray(Object value) {
        if (value.getClass().isArray()) {
            Object[] result = new Object[Array.getLength(value)];
            for (int i = 0; i < result.length; ++i) {
                result[i] = Array.get(value, i);
            }
            return result;
        } else if (value instanceof Collection) {
            return ((Collection) value).toArray();
        }
        return new Object[] { value };
    }

    @SuppressWarnings("rawtypes")
    private static Comparable asComparable(Object value) {
        if (value instanceof Comparable) {
            return (Comparable) value;
        }
        throw new IllegalArgumentException(value + " cannot be cast to java.lang.Comparable");
    }
}
