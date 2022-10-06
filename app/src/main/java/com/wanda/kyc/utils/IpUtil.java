package com.wanda.kyc.utils;

import com.wanda.kyc.exception.SystemRuntimeException;
import com.wanda.kyc.exception.enumeration.SystemExceptionEnum;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * 取得ip
 * 
 * @author hsiang
 *
 */
@Slf4j
public class IpUtil {

	private IpUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static final String UNKNOWN = "unknown";
	private static final String LOCALHOST = "localhost";
	private static final String LOCAL_127 = "127.0.0.1";
	private static final String LOCAL_IPV6 = "0:0:0:0:0:0:0:1";

	// 有缺陷 禁用
    public static String getIp(HttpServletRequest req) throws UnknownHostException {

        String ipAddress = req.getHeader("x-real-ip");

        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress))
            ipAddress = req.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() <= 0 || UNKNOWN.equalsIgnoreCase(ipAddress))
            ipAddress = req.getHeader("Proxy-Client-IP");
        if (ipAddress == null || ipAddress.length() <= 0 || UNKNOWN.equalsIgnoreCase(ipAddress))
            ipAddress = req.getHeader("WL-Proxy-Client-IP");
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress))
            ipAddress = req.getHeader("HTTP_CLIENT_IP");
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress))
            ipAddress = req.getHeader("HTTP_X_FORWARDED_FOR");
        if (ipAddress == null || ipAddress.length() <= 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = req.getRemoteAddr();
			if (LOCAL_127.equalsIgnoreCase(ipAddress) || LOCALHOST.equalsIgnoreCase(ipAddress)) {
                // 根据网卡取本机配置的IP
                try {
                    InetAddress inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    throw e;
                }
            }
        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        // ***.***.***.*** length is 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }


    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null) {
            return ip.replace(" ", "").split(",")[0];
        } else {
            String ipAddress = request.getRemoteAddr();
            switch (ipAddress) {
                case LOCALHOST:
                case LOCAL_127:
                case LOCAL_IPV6:
                    return ipAddress;
                default:
                    log.error("[IP]取得失敗");
                    throw new SystemRuntimeException(SystemExceptionEnum.PARAM_ERROR);
            }
        }
    }

}
