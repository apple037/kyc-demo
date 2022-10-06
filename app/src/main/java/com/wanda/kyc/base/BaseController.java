package com.wanda.kyc.base;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wanda.kyc.dto.user.User;
import com.wanda.kyc.exception.SystemRuntimeException;
import com.wanda.kyc.utils.IpUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class BaseController {

//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.registerCustomEditor(String.class, new DateEditor());
//	}

	protected String getSessionId(HttpServletRequest request) {
		return request.getSession().getId();
	}

	/**
	 * 取得操作者ip
	 * 
	 * @param request
	 * @return
	 */
	protected String getClientIp(HttpServletRequest request) {
		return IpUtil.getRemoteIp(request);
	}

	/**
	 * URL重導
	 * 
	 * @param response
	 * @param redirectUrl
	 */
	public void redirect(HttpServletResponse response, String redirectUrl) {
		try {
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			log.error("URL重導失敗");
			e.printStackTrace();
			throw SystemRuntimeException.systemError();
		}
	}

	/**
	 * 本地 輸出圖片
	 * 
	 * @param image
	 * @param fileName
	 */
	public void writeImage(BufferedImage image, String fileName) {
		try {
			ImageIO.write(image, "jpg", new File(fileName.concat(".jpg")));
		} catch (IOException e) {
			log.error("圖片輸出失敗");
			e.printStackTrace();
			throw SystemRuntimeException.systemError();
		}
	}

	/**
	 * response 輸出圖片
	 * 
	 * @param response
	 * @param image
	 */
	protected void writeImage(HttpServletResponse response, BufferedImage image) {

		// 關閉快取
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			ImageIO.write(image, "jpg", response.getOutputStream());
		} catch (IOException e) {
			log.error("圖片輸出失敗");
			e.printStackTrace();
			throw SystemRuntimeException.systemError();
		}
	}

	public <T> void setStartTimeAndEndTime(T t, String startTime, String endTime) {
		try {
			Method method = t.getClass().getMethod("setStartTime", String.class);
			method.invoke(t, startTime);
			Method method2 = t.getClass().getMethod("setEndTime", String.class);
			method2.invoke(t, endTime);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			throw SystemRuntimeException.systemError();
		}
	}

	public <T> void addUser(T t, User user) {
		try {
			Method method = t.getClass().getMethod("setUser", User.class);
			method.invoke(t, user);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			throw SystemRuntimeException.systemError();
		}
	}

}
