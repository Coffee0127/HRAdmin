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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 封裝 Spring {@link org.springframework.beans.BeanUtils}, 擴充功能
 *
 * @since 2016-05-07
 * @author Bo-Xuan Fan
 */
@Service
public final class BeanUtils {

    /**
     * Spring ApplicationContext object
     */
    private static ApplicationContext applicationContext = null;

    private BeanUtils() {
    }

    @Autowired
    public void init(ApplicationContext applicationContext) {
        setApplicationContext(applicationContext);
    }

    /**
     * 取得ApplicationContext
     *
     * @return application context
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 存入ApplicationContext
     *
     * @param applicationContext applicationContext
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        if (BeanUtils.getApplicationContext() == null) {
            BeanUtils.applicationContext = applicationContext;
        }
    }

    /**
     * 取得 Spring 定義之Bean
     *
     * @param <T>
     *            Bean所屬類別
     * @param clazz
     *            Bean所屬類別
     * @return T Spring Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 取得 Spring 定義之Bean
     *
     * @param beanName
     *            Spring Bean
     * @param args
     *            建構參數
     * @return Object Spring Bean
     */
    public static Object getBean(String beanName, Object... args) {
        return applicationContext.getBean(beanName, args);
    }

    /**
     * 取得 Spring 定義之Bean
     *
     * @param <T>
     *            Bean所屬類別
     * @param beanID
     *            BeanID
     * @param clazz
     *            Bean所屬類別
     * @return T Spring Bean
     */
    public static <T> T getBean(String beanID, Class<T> clazz) {
        return applicationContext.getBean(beanID, clazz);
    }

    /**
     * 自WebServiceContext取得Spring定義之Bean
     * @param context WebServiceContext
     * @param clazz Class<T>
     * @return T
     */
    public static <T> T getBeanFromWebServiceContext(WebServiceContext context, Class<T> clazz) {
        MessageContext messageContext = context.getMessageContext();
        ServletContext servletContext = (ServletContext) messageContext.get(MessageContext.SERVLET_CONTEXT);
        WebApplicationContext webApplicationContext = WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContext);
        return webApplicationContext.getBean(clazz);
    }

    /**
     * 給定clazz產生該實體
     * @param clazz class to instantiate
     * @return the new instance
     */
    public static <T> T instantiate(Class<T> clazz) {
        return org.springframework.beans.BeanUtils.instantiate(clazz);
    }

    /**
     * 複製 source bean 的屬性到 target bean
     * @param source the source bean
     * @param target the target bean
     */
    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    /**
     * 將source物件轉成TARGET型態的物件
     * @param <T> target
     * @param <S> source
     * @param clz TARGET class
     * @param source source object
     * @return object which class is TARGET
     */
    public static <T, S> T copyProperties(Class<T> clz, S source) {
        T target = BeanUtils.instantiate(clz);
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 將sourceSet轉成TARGET型態的Set
     * @param <T> target
     * @param <S> source
     * @param clz TARGET class
     * @param sourceSet source Set
     * @return Set that contains TARGET
     */
    public static <T, S> Set<T> copySetProperties(Class<T> clz, Set<S> sourceSet) {
        return (Set<T>) copyCollectionProperties(clz, sourceSet, new HashSet<T>());
    }

    /**
     * 將sourceList轉成TARGET型態的List
     * @param <T> target
     * @param <S> source
     * @param clz TARGET class
     * @param sourceList source List
     * @return List that contains TARGET
     */
    public static <T, S> List<T> copyListProperties(Class<T> clz, List<S> sourceList) {
        return (List<T>) copyCollectionProperties(clz, sourceList, new ArrayList<T>());
    }

    /**
     * copySetProperties & copyListProperties 共用程式碼
     * @param <T> target
     * @param <S> source
     * @param clz
     * @param sourceCollection
     * @param targetCollection
     * @return
     */
    private static <T, S> Collection<T> copyCollectionProperties(
            Class<T> clz, Collection<S> sourceCollection,
            Collection<T> targetCollection) {
        for (S source : sourceCollection) {
            targetCollection.add(BeanUtils.copyProperties(clz, source));
        }
        return targetCollection;
    }
}
