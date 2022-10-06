package com.wanda.kyc.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class CommonUtil {

	private CommonUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static Map<String, String> paramsMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = parameterNames.nextElement();
			String parameterValues = request.getParameter(name);
			map.put(name, parameterValues);
		}
		return map;
	}

	public static Map<String, String> dtoStrToMap(String dtoText) {
		dtoText = dtoText.replace(" ", "").substring(dtoText.indexOf("(") + 1).replace(")", "");
		String[] split = dtoText.split(",");

		Map<String, String> map = new HashMap<>();
		for (String str : split) {
			String[] arg = str.split("=");
			map.put(arg[0], arg[1]);
		}
		return map;
	}

	/**
	 * @description map轉為字串 &串接
	 * @param map
	 * @return String
	 */
	public static String mapTransfer(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
			sb.append("&");
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

}
