package com.wanda.kyc.utils.captcha;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;


public abstract class AbstractCaptcha {

	protected int width;
	protected int height;
	protected int codeCount;

	protected BufferedImage captcha;
	protected String verifyCode;

	public BufferedImage getBufferedImage() {
		return this.captcha;
	}

	public String getVerifyCode() {
		return this.verifyCode;
	}

	private static final char[] CODE_ARRAY = "123456789ABCDEFGHJKLMNOPQRSTUVWXYZ".toCharArray();

	protected static final String[] FONT_NAMES = { "Times New Roman", "宋體", "楷體" };

	protected static Random rand;

	static {
		try {
			rand = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public AbstractCaptcha(int width, int height, int codeCount) {
		init(width, height, codeCount);
		generate();
	}

	protected void init(int width, int height, int codeCount) {
		this.width = width;
		this.height = height;
		this.codeCount = codeCount;
	}

	protected abstract void generate();

	/**
	 * 隨機數字
	 * 
	 * @param num
	 * @return
	 */
	protected int randomInt(int num) {
		return rand.nextInt(num);
	}

	/**
	 * 隨機顏色
	 * 
	 * @return
	 */
	protected Color randomColor() {
		int r = randomInt(225);
		int g = randomInt(225);
		int b = randomInt(225);
		return new Color(r, g, b);
	}

	/**
	 * 隨機顏色
	 * 
	 * @return
	 */
	protected Color randomLineColor() {
		int r = randomInt(225);
		int g = randomInt(225);
		int b = randomInt(225);
		return new Color(r, g, b);
	}

	/**
	 * 隨機字體
	 * 
	 * @return
	 */
	protected Font randomFont() {
		int index = randomInt(FONT_NAMES.length);
		int style = randomInt(4);
		int size = randomInt(10) + 23;
		return new Font(FONT_NAMES[index], style, size);
	}

	/**
	 * 隨機角度
	 * 
	 * @return
	 */
	protected float randomRotate() {
		float num = rand.nextInt(40) * 1.0f % 40 / 100;
		boolean flag = rand.nextBoolean();
		if (flag) {
			return num;
		} else {
			return num * -1;
		}
	}

	/**
	 * 隨機字元
	 * 
	 * @return
	 */
	protected String randomCode() {
		return String.valueOf(CODE_ARRAY[randomInt(CODE_ARRAY.length)]);
	}

	public void write(BufferedImage image) {
		try {
			ImageIO.write(image, "jpg", new File("captcha_image.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(HttpServletRequest request, HttpServletResponse response, AbstractCaptcha captcha) {

		request.getSession().setAttribute("verifyCode", captcha.getVerifyCode());

		// 關閉快取
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			ImageIO.write(captcha.getBufferedImage(), "jpg", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
