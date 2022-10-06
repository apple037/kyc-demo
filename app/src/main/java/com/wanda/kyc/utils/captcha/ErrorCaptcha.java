package com.wanda.kyc.utils.captcha;

import java.awt.*;
import java.awt.image.BufferedImage;


public class ErrorCaptcha extends AbstractCaptcha {

	public ErrorCaptcha() {
		super(120, 40, 5);
	}

	public ErrorCaptcha(int width, int height, int codeCount) {
		super(width, height, codeCount);
	}

	@Override
	protected void generate() {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D graphics2d = bufferedImage.createGraphics();

		// 設置背景顏色
		graphics2d.setBackground(Color.WHITE);
		graphics2d.clearRect(0, 0, width, height);

		this.captcha = bufferedImage;
		this.verifyCode = "error";
	}

}
