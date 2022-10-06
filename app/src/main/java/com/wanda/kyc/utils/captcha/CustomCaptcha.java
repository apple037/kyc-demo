package com.wanda.kyc.utils.captcha;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class CustomCaptcha extends AbstractCaptcha {

	// 參考：https://segmentfault.com/a/1190000021265504

	public CustomCaptcha() {
		super(120, 40, 5);
	}

	public CustomCaptcha(int width, int height, int codeCount) {
		super(width, height, codeCount);
	}

	@Override
	public void generate() {
		// 創建圖片緩衝區
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D graphics2d = bufferedImage.createGraphics();

		// 設置背景顏色
		graphics2d.setBackground(Color.WHITE);
		graphics2d.clearRect(0, 0, width, height);

		StringBuilder sb = new StringBuilder();
		// 隨機字元
		for (int i = 0; i < codeCount; i++) {
			String code = randomCode();
			sb.append(code);

			// 暫存副本
			AffineTransform original = graphics2d.getTransform();
			// 定義字元X座標
			float x = i * 0.9F * width / codeCount + 3;
			graphics2d.setFont(randomFont());
			graphics2d.setColor(randomColor());
			graphics2d.rotate(randomRotate(), x, height - 3 - randomInt(16));
			graphics2d.drawString(code, x, height - 3 - randomInt(16));

			// 還原副本
			graphics2d.setTransform(original);
		}

		// 干擾線
		int num = 7;
		Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
		for (int i = 0; i < num; i++) {
			int x1 = randomInt(width);
			int y1 = randomInt(height);
			int x2 = randomInt(width);
			int y2 = randomInt(height);
			graphics.setColor(randomLineColor());
			graphics.drawLine(x1, y1, x2, y2);
		}

		graphics.dispose();

		this.captcha = bufferedImage;
		this.verifyCode = sb.toString();
	}

	@Override
	protected float randomRotate() {
		float num = rand.nextInt(20) * 1.0f % 40 / 100;
		boolean flag = rand.nextBoolean();
		if (flag) {
			return num;
		} else {
			return num * -1;
		}
	}

	@Override
	protected Color randomLineColor() {
		int i = LineColor.getLineColorArrSize() - 1;
		return LineColor.getByIndex(randomInt(i));
	}

	@Override
	protected Font randomFont() {
		int index = randomInt(FONT_NAMES.length);
		int style = randomInt(4);
		int size = randomInt(5) + 26;
		return new Font(FONT_NAMES[index], style, size);
	}

}
