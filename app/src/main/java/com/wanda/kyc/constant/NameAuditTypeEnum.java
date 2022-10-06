package com.wanda.kyc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NameAuditTypeEnum {
    overall("OVERALL","總攬"),
    rca("RCA", "rca"),
    pep("PEP","pep"),
    blacklist("BLACKLIST","黑名單"),
    negative("NEGATIVE","負面新聞"),
    other("OTHER","其他"),
    telephone("TELEPHONE","電話照會"),
    statement("STATEMENT","假命中聲明"),
    legal("LEGAL","法尊補充"),
    ;

    private String type;
    private String memo;

}
