package com.wanda.kyc.provider;

import com.wanda.kyc.constant.AdminTypeConst;
import com.wanda.kyc.dto.app.UserSearchDto;
import com.wanda.kyc.dto.kyc.KycApplyDto;
import com.wanda.kyc.utils.Page;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class KycApplyProvider {
    private static final String member = "member";
    private static final String kyc_apply = "kyc_apply";

    public String findRecordByParam(UserSearchDto param){
        SQL sql = null;
        String column = "member.id, member.member_id, member.username, kyc_apply.identity_code, kyc_apply.birthdate, kyc_apply.real_name, member.register_at, kyc_apply.status, " +
                "kyc_apply.job, kyc_apply.title, kyc_apply.is_multi_nationality, kyc_apply.purpose, " +
                "kyc_apply.job_other, kyc_apply.title_other, kyc_apply.multi_other, kyc_apply.purpose_other, " +
                "kyc_apply.id_front, kyc_apply.id_back, kyc_apply.id_hand, kyc_apply.second_id, kyc_apply.bank_account";
        sql = new SQL()
                .SELECT(column);
        sql.FROM(member);
        sql.LEFT_OUTER_JOIN(kyc_apply + " on `member`.id = `kyc_apply`.member_id");
        addCriteria(sql, param);
        Page page = new Page(param.getPage(),param.getSize());
        sql.LIMIT(page.getLimit()).OFFSET(page.getOffset());
        //log.info("sql:{}",sql.toString());

        return sql.toString();
    }

    private void addCriteria(SQL sql, UserSearchDto param){
        // type
        String type = param.getType();
        if (StringUtils.hasText(param.getUsername())) {
            sql.WHERE("username = #{param.username}");
        }
        if (StringUtils.hasText(param.getIdentityCode())) {
            sql.WHERE("member.identity_code = #{param.identityCode}");
        }
        if (StringUtils.hasText(param.getStatus())) {
            sql.WHERE("kyc_apply.status = #{param.status}");
        }
    }
}
