package com.wanda.kyc.constant;

public class SortConst {

	private SortConst() {
		throw new IllegalStateException("Constant class");
	}

	public static final String TIME_DESC = "timeDesc";
	public static final String TIME_ASC = "timeAsc";
	public static final String PRICE_DESC = "priceDesc";
	public static final String PRICE_ASC = "priceAsc";
	
	//最多瀏覽次數
	public static final String MOST_VIEW = "mostView";
	//最新成交
	public static final String SOLD_DESC = "soldDesc";
	//最新上架
	public static final String SELL_DESC = "sellDesc";

}
