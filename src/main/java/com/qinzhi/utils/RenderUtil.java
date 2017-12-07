package com.qinzhi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Render data util
 * 
 * @author WangJian
 * @since 2014-5-6
 */
public final class RenderUtil {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RenderUtil.class);
    
    private RenderUtil() {
        
    }
 
    public static String render(String text, String contentType, HttpServletResponse response) {
        try {
            response.setContentType(contentType);
            response.setHeader("Pragma","No-cache"); 
        	response.setHeader("Cache-Control","no-cache"); 
        	response.setDateHeader("Expires", 0);
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(text);
            response.flushBuffer();
        } catch (IOException e) {
            LOGGER.warn("render出错contentType: " + contentType  
                    + ",body:" + text, e);  
        }
        return null;
    }

    public static String renderText(String text, HttpServletResponse response) {
        return render(text, "text/plain;charset=UTF-8", response);
    }

    public static String renderHtml(String html, HttpServletResponse response) {
        return render(html, "text/html;charset=UTF-8", response);
    }

    public static String renderXML(String xml, HttpServletResponse response) {
        return render(xml, "text/xml;charset=UTF-8", response);
    }

    public static String renderJson(String json, HttpServletResponse response) {
        return render(json, "application/json;charset=UTF-8", response);
    }

}
