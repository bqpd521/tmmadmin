package com.yougou.common;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class WebUtils {

	private WebUtils() {
	}

	/**
	 * 重载Spring WebUtils中的函数,作用如函数名所示 加入泛型转换,改变输入参数为request 而不是session
	 *
	 * @param name  session中变量名称
	 * @param clazz session中变量的类型
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getOrCreateSessionAttribute(HttpServletRequest request, String name, Class<T> clazz) {
		return (T) org.springframework.web.util.WebUtils.getOrCreateSessionAttribute(request.getSession(), name, clazz);
	}
	
	
	/**
	 * 获取真实客户端ip地址,
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		//可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串Ｉｐ值
		//是取X-Forwarded-For中第一个非unknown的有效IP字符串
		String[] str = ip.split(",");
		if(str!=null && str.length>1){
			ip = str[0];
		}
		return ip;
	}
	
	

	/**
	 * 获得项目跟目录
	 * @param request
	 * @return
	 */
	public static String getBasePath(HttpServletRequest request){
    	String path = request.getContextPath();
    	String basePath = "";
		if (!"80".equals(request.getServerPort()+"")) {
			basePath = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + path;
		}else{
			basePath = request.getScheme() + "://" + request.getServerName() + path ;
		}
		return basePath;
    }
	
	/**
	 * 根据cookie的名称获得该cookie对象
	 * @param request
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request,String cookieName){
		Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(int i = 0; i < cookies.length; i++) {
                if(cookieName.equals(cookies[i].getName()) && !"".equals(cookies[i].getValue())) {
                    return cookies[i];
                }
            }
        }
        return null;
	}
	
	
	/**
	 * 读取url流转换为字符串,可以用来生成静态网页
	 * @param url
	 * @param characterSet
	 * @return
	 */
	public static final String readHtml(final String url,String characterSet) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		Pattern pattern = Pattern
				.compile("(http://|https://){1}[\\w\\.\\-/:]+");
		Matcher matcher = pattern.matcher(url);
		if (!matcher.find()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		try {
			URL _url = new URL(url);
			URLConnection urlConnection = _url.openConnection();
			InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream(), characterSet);
			BufferedReader in = new BufferedReader(isr);

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	/**
	 * 获得printwriter对象,用来进行out输出,和ajax配合使用
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static PrintWriter getPrintWriter(HttpServletResponse response,String characterSet)throws Exception{
		PrintWriter out = null;
		if(response!=null){
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding(characterSet);
			out = response.getWriter();
		}
		return out;
	}
}
