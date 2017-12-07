package com.qinzhi.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * StringUtil
 *
 * @author WangJian
 * @since 2015-6-9
 */
public final class StringUtil {

    public final static String COMMA_SEPARATOR = ",";

    private StringUtil() {

    }

    public static String replaceSpecialChar(String str) {
        if (str != null && !str.equals("")) {
            str = str.replace("'", "\"").replace("\"", "").replace("\\", "").replace("/", "\\/").replace("\b", "\\b")
                    .replace("\f", "").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t")
                    .replace("<br>", " ").replace("<BR>", " ").replace("<", "《").replace(">", "》").replace("@", "")
                    .replace("&", "").replace("^", "").replace("`", "");
        }
        return str;
    }

    public static String replaceSpecialCharToNull(String str) {
        if (str != null && !str.equals("")) {
            str = str.replace("'", "\"").replace("\"", "").replace("\\", "").replace("/", "\\/").replace("\b", "")
                    .replace("\f", "").replace("\n", "").replace("\r", "").replace("\t", "").replace("<br>", "")
                    .replace("<BR>", "").replace("<", "《").replace(">", "》").replace("@", "").replace("&", "")
                    .replace("^", "").replace("`", "");
        }
        return str;
    }

    public static String replaceChar(String str) {
        if (StringUtils.isNotBlank(str)) {
            return str.replaceAll("\'", "''");
        }
        return str;
    }

    public static final List<String> split(String source) {
        return split(source, COMMA_SEPARATOR);
    }

    public static final List<String> split(String source, String separator) {
        List<String> list = Lists.newArrayList();
        if (source == null) {
            return null;
        }

        if ("".equals(source)) {
            return Collections.emptyList();
        }

        separator = separator == null ? "" : separator;

        String[] ids = source.split(separator);
        if (ids.length > 0) {
            for (String id : ids) {
                list.add(id);
            }
        }
        return list;
    }

    public static final List<Long> splitToLong(String source) {
        return splitToLong(source, COMMA_SEPARATOR);
    }

    public static final List<Long> splitToLong(String source, String separator) {
        if (StringUtils.isBlank(source)) {
            return Collections.emptyList();
        }
        List<Long> list = new ArrayList<Long>();
        separator = separator == null ? "" : separator;
        String[] ids = StringUtils.split(source, separator);
        for (String s : ids) {
            list.add(Long.valueOf(s));
        }
        return list;
    }

    public static <T> String join(List<?> list) {
        return join(list, COMMA_SEPARATOR);
    }

    public static <T> String join(List<?> list, String separator) {
        if (list == null) {
            return null;
        }

        if (list.isEmpty()) {
            return "";
        }

        separator = separator == null ? "" : separator;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(separator);
            }
            if (list.get(i) != null) {
                sb.append(list.get(i));
            }
        }
        return sb.toString();
    }

    public static String getClick(long num) {
        String click = "0";
        if (num >= 100000000) {
            double fnum = Double.parseDouble(String.valueOf(num)) / 100000000;
            if (fnum > 9999.4) {
                click = "9999.9";
            } else {
                click = (float) (Math.round(fnum * 10)) / 10 + "";// 保留一位小数
            }
            click = click + "亿";
        } else if (num >= 10000) {
            double fnum = Double.parseDouble(String.valueOf(num)) / 10000;
            if (fnum > 9999.4) {
                click = "9999.9";
            } else {
                click = (float) (Math.round(fnum * 10)) / 10 + "";// 保留一位小数
            }
            // if(click.length()>2 && Integer.parseInt(click.substring(click.length()-1))<1){
            // click = click.substring(0,click.length()-2);
            // }
            click = click + "万";
            // String click = Math.round(fnum) +"万";
        } else {
            if (num < 1000) {
                num += 1000;
                if (num == 1000) {
                    num += Math.random() * 1000;
                }
            }
            click = num + "";
        }

        return click;
    }

    public static String convert(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }

        return sb.toString();
    }

    public static String decodeBase64(String param) throws UnsupportedEncodingException {
        byte[] resultByte = org.apache.commons.codec.binary.Base64.decodeBase64(param.getBytes());
        String result = new String(resultByte, "UTF-8");
        return result;
    }
    
    public static Integer intValueOf(String data){
        return intValueOf(data, 0);
    }
    
    public static Integer intValueOf(String data, int defaultValue){
        try
        {
            return Integer.valueOf(data);
        }
        catch (NumberFormatException e)
        {
            return defaultValue;
        }
    }
    
    public static Long longValueOf(String data){
        return longValueOf(data, 0);
    }
    
    public static Long longValueOf(String data, long defaultValue){
        try
        {
            return Long.valueOf(data);
        }
        catch (NumberFormatException e)
        {
            return defaultValue;
        }
    }

}
