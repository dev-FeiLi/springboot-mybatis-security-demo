package com.chenyou.admin.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */
public class ToolUtil {

    /* 请求IP地址 */
    public static String obtainRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("PRoxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.hasText(ip)) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    public static String tableFormat(List list, int total) {
        String table = null;
        try {
            JSONObject result = new JSONObject();
            result.put("total", total);
            result.put("pageNumber", list.size());
            result.put("rows", JSONArray.fromObject(list));
            table = result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    public static String tableFormat(long total, int size, JSONArray array) {
        String table = null;
        try {
            JSONObject result = new JSONObject();
            result.put("total", total);
            result.put("pageNumber", size);
            result.put("rows", array);
            table = result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    public static String tableFormat(long total, int size, JSONArray array, JSONObject footer) {
        String table = null;
        try {
            JSONObject result = new JSONObject();
            result.put("total", total);
            result.put("pageNumber", size);
            result.put("rows", array);
            result.put("footer", footer);
            table = result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    public static String host(String url) {
        if (null == url) {
            return url;
        }
        url = url.toLowerCase();
        url = url.replaceAll("http://", "");
        url = url.replaceAll("https://", "");
        int index = url.indexOf("/");
        if (index > 0) {
            url = url.substring(0, index);
        }
        return url;
    }
}
