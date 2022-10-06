package com.wanda.kyc.utils.captcha;

import java.awt.*;


public class LineColor {

	private LineColor() {
		throw new IllegalStateException("Constant class");
	}


	public static final Color LIME = new Color(204, 255, 0);

	public static final Color AQUAMARINE = new Color(127, 255, 212);

	public static final Color BABY_BLUE = new Color(137, 207, 240);

	public static final Color TURQUOISE = new Color(48, 213, 200);

	public static final Color PERIWINKLE = new Color(204, 204, 255);

	public static final Color SILVER = new Color(192, 192, 192);

	public static final Color SPRING_GREEN = new Color(10, 255, 128);

	public static final Color PEACH = new Color(255, 229, 180);

	public static final Color CREAM = new Color(255, 253, 208);

	public static final Color BEIGE = new Color(245, 245, 210);

	public static final Color LAVENDER = new Color(230, 230, 250);

	public static final Color SAND_BEIGE = new Color(230, 195, 195);

	public static final Color LIGHT_CORAL = new Color(240, 128, 128);

	public static final Color SALMON = new Color(250, 128, 114);

	public static final Color LIGHT_SALMON = new Color(255, 160, 122);

	public static final Color APRICOT = new Color(230, 153, 102);

	public static final Color PEACH_PUFF = new Color(255, 218, 185);

	public static final Color BISQUE = new Color(255, 228, 196);

	public static final Color NAVOAJO_WHITE = new Color(255, 222, 173);

	public static final Color MOCCASION = new Color(255, 228, 181);

	public static final Color PALE_GOLDENROD = new Color(238, 232, 170);

	public static final Color PALE_GREEN = new Color(152, 251, 152);

	public static final Color CELADON_GREEN = new Color(115, 230, 140);

	public static final Color HORIZON_BLUE = new Color(166, 255, 204);

	public static final Color MEDIUM_SPRING_GREEN = new Color(0, 250, 154);

	public static final Color POWDER_BLUE = new Color(176, 224, 230);

	public static final Color PAIL_LILAC = new Color(230, 207, 230);

	public static final Color THISTLE = new Color(216, 191, 216);

	public static final Color PEARL_PINK = new Color(255, 179, 230);

	public static final Color BABY_PINK = new Color(255, 217, 230);

	//@on
	private static final Color[] LINE_COLOR_ARR = new Color[] {
			LIME, 
			AQUAMARINE, 
			BABY_BLUE, 
			TURQUOISE, 
			PERIWINKLE, 
			SILVER, 
			SPRING_GREEN,
			PEACH,
			CREAM,
			BEIGE,
			LAVENDER,
			SAND_BEIGE,
			LIGHT_CORAL,
			SALMON,
			LIGHT_SALMON,
			APRICOT,
			PEACH_PUFF,
			BISQUE,
			NAVOAJO_WHITE,
			MOCCASION,
			PALE_GOLDENROD,
			PALE_GREEN,
			CELADON_GREEN,
			HORIZON_BLUE,
			MEDIUM_SPRING_GREEN,
			POWDER_BLUE,
			PAIL_LILAC,
			THISTLE,
			PEARL_PINK,
			BABY_PINK
	};
	//@off
	
	public static int getLineColorArrSize() {
		return LINE_COLOR_ARR.length;
	}
	
	public static Color getByIndex(int index) {
		return LINE_COLOR_ARR[index];
	}
}
