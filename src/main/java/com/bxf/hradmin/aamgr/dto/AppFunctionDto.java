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

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * AppFunctionDto
 *
 * @since 2016-06-18
 * @author Bo-Xuan Fan
 */
public class AppFunctionDto implements Comparable<AppFunctionDto>, Serializable {

    private static final long serialVersionUID = -5809712688334799038L;

    /** 功能編號 */
    private Long id;
    /** 父功能代碼 */
    private String parent;
    /** 功能代碼 */
    private String code;
    /** 功能階層 */
    private Integer level;
    /** 功能名稱 */
    private String name;
    /** 功能圖示 */
    private String icon;
    /** 功能路徑 */
    private String path;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .append(parent)
            .append(code)
            .append(level)
            .append(name)
            .append(path)
            .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AppFunctionDto) {
            AppFunctionDto other = (AppFunctionDto) obj;
            return new EqualsBuilder()
                .append(id, other.id)
                .append(parent, other.parent)
                .append(code, other.code)
                .append(level, other.level)
                .append(name, other.name)
                .append(path, other.path)
                .isEquals();
        }
        return false;
    }

    @Override
    public int compareTo(AppFunctionDto o) {
        return this.getCode().compareTo(o.getCode());
    }
}
