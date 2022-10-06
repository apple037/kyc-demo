package com.wanda.kyc.constant;

public class AdminTypeConst {
    private AdminTypeConst() {
        throw new IllegalStateException("Constant class");
    }
    // 客服
    public static final String CUSTOMER = "CUSTOMER";
    // 風控
    public static final String RISK = "RISK";
    // 法遵
    public static final String LEGAL = "LEGAL";
}
