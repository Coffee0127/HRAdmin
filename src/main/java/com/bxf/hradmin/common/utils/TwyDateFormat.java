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

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * use the character 'e' to represent the ROC year
 *
 * @since 2016-05-07
 * @author Bo-Xuan Fan
 */
public class TwyDateFormat extends SimpleDateFormat {

    /** ROC_DATE_DEFAULT_PATTERN */
    public static final String ROC_DEFAULT_DATE_PATTERN = "eee-MM-dd";
    /** ROC_DATE_DEFAULT_TIME_PATTERN */
    public static final String ROC_DEFAULT_DATETIME_PATTERN = "eee-MM-dd HH:mm:ss";
    /** ROC_DATE_PATTERN_NO_DASH */
    public static final String ROC_DATE_PATTERN_NO_DASH = "eeeMMdd";
    /** ROC_DATE_TIME_PATTERN_NO_DASH */
    public static final String ROC_DATETIME_PATTERN_NO_DASH = "eeeMMddHHmmss";
    /** ROC_BEGIN_YEAR */
    private static final int ROC_BEGIN_YEAR = 1911;
    /** serialVersionUID */
    private static final long serialVersionUID = 5029425640887880353L;

    // 是否是民國年
    private boolean isROCYear = false;

    public TwyDateFormat(String pattern) {
        super();
        super.applyPattern(compile(pattern));
    }

    private String compile(String pattern) {
        int length = pattern.length();
        // 存放真正的pattern
        StringBuilder compiledPattern = new StringBuilder(length);
        // 存放單引號內的字串
        StringBuilder tmpCompiledPattern = new StringBuilder(0);

        // 標識是否在單引號內
        boolean isInQuote = false;
        for (int index = 0; index < length; index++) {
            char currentChar = pattern.charAt(index);
            // 當遇到單引號時
            if (currentChar == '\'') {
                // 進<-->出 單引號
                isInQuote = !isInQuote;
                // pattern直接串上單引號內字串
                compiledPattern.append(tmpCompiledPattern);
                // 清空暫存buffer
                tmpCompiledPattern.setLength(0);
            }
            if (isInQuote) {
                // 單引號內 由暫存buffer存放
                tmpCompiledPattern.append(currentChar);
            } else {
                // 不在單引號內
                // 將民國年pattern置換成y
                if (currentChar == 'e') {
                    isROCYear = true;
                    currentChar = 'y';
                }
                compiledPattern.append(currentChar);
            }
        }
        return compiledPattern.toString();
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
        // 若為民國年 則將年份-1911做format
        if (isROCYear) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            // 民國年 begin at 1912!
            if (calendar.get(Calendar.YEAR) > ROC_BEGIN_YEAR) {
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - ROC_BEGIN_YEAR);
                return super.format(calendar.getTime(), toAppendTo, pos);
            } else {
                throw new IllegalArgumentException("\"" + date + "\" is not a valid ROC year.");
            }
        }
        return super.format(date, toAppendTo, pos);
    }

    @Override
    public Date parse(String text, ParsePosition pos) {
        // 若為民國年 則將parse後的年份+1911
        if (isROCYear) {
            Date date = super.parse(text, pos);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + ROC_BEGIN_YEAR);
            return calendar.getTime();
        }
        return super.parse(text, pos);
    }
}
