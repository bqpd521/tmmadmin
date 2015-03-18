package com.yougou.common;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;
/**
 * 类 说明：帮助类
 * 创建时间：2014-10-09 liuqianan
 */
public class OtherUtils {

	/**
	 * 方法说明：返回json对象
	 * 创建时间：2014-09-29 liuqianan
	 * @param recDate 格式 yyyy-MM-dd
	 */
	public static JSONObject objToJSONObject(Object obj){
		JSONObject jsonList=JSONObject.fromObject(obj);
		return jsonList;
	}

	
	/**
	 * 方法说明：返回json字符串
	 * @param obj
	 * @return
	 */
	public static String objToJsonStr(Object obj){
		JSONObject jsonList=JSONObject.fromObject(obj);
		return jsonList.toString();
	}

	/**
	 * 方法说明：发送数据
	 * @param res
	 * @param jsonStr
	 */
	public static void send(HttpServletResponse res,String jsonStr,String contentType){
		res.setCharacterEncoding("UTF-8");
		try {
			res.setContentType(contentType);
			res.getWriter().print(jsonStr);
			res.getWriter().flush();
			res.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 方法说明：发送数据
	 * @param res
	 * @param jsonObj
	 */
	public static void send(HttpServletResponse res,JSONObject jsonObj,String contentType){
		res.setCharacterEncoding("UTF-8");
		try {
			res.setContentType(contentType);
			res.getWriter().print(jsonObj);
			res.getWriter().flush();
			res.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 方法说明：判断字符是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		if(StringUtils.isNotEmpty(str) && StringUtils.isNotBlank(str)){
			return false;
		}
		return true;
	}
}
