package com.wanda.kyc.constant;

public class AuditStatusConst {

    private AuditStatusConst() {
        throw new IllegalStateException("Constant class");
    }

    public static final String NORMAL = "NORMAL";
    // 證件審核
    public static final String id_audit = "ID_AUDIT";
    // 姓名審核
    public static final String name_audit = "NAME_AUDIT";
    // 風險審核
    public static final String risk_audit = "RISK_AUDIT";
    // 法遵審核
    public static final String legal_audit = "LEGAL_AUDIT";
    // 審核通過
    public static final String audit_pass = "AUDIT_PASS";
    // 審核不通過
    public static final String audit_reject = "AUDIT_REJECT";
}
