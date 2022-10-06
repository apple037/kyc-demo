package com.wanda.kyc.service;

import com.wanda.kyc.dto.app.UserAppDto;
import com.wanda.kyc.dto.user.UserApp;
import com.wanda.kyc.exception.SystemRuntimeException;
import com.wanda.kyc.exception.Validator;
import com.wanda.kyc.exception.enumeration.OperatorExceptionEnum;
import com.wanda.kyc.exception.enumeration.SystemExceptionEnum;
import com.wanda.kyc.mapper.UserAppMapper;
import com.wanda.kyc.message.MailInfo;
import com.wanda.kyc.message.MailTemplateEnum;
import com.wanda.kyc.utils.GeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.wanda.kyc.constant.VerifyConst.*;

@Slf4j
@Service
public class MailService {

    @Autowired
    private UserAppMapper userAppMapper;

    @Autowired
    private VerifyService verifyService;

    @Autowired
    private AsyncService asyncService;

    /**
     * 發送email
     *
     * @param email
     * @param lang
     * @param action
     */
    public void sendVerifyEmail(String email, String lang, String action) {
        MailTemplateEnum mailTemplateEnum = checkAndGetTemplateByAction(email, action);
        // 寄信參數
        String verifyCode = GeneratorUtil.verifyCode();
        // 存到redis
        verifyService.saveVerify(email, action, verifyCode);
        // 發送信件
        Map<String, String> templateParamMap = new HashMap<>();
        templateParamMap.put("verifyCode", verifyCode);
        // 寄信
        log.info("[code][{}][{}]",email,verifyCode);
        MailInfo info = MailInfo.create(lang, email, mailTemplateEnum, templateParamMap);
        log.info("[mail][{}]",info);
        asyncService.sendMail(info);
    }

    private MailTemplateEnum checkAndGetTemplateByAction(String email, String action) {
        switch (action) {
            case ACTION_LOGIN:
            case ACTION_FORGET_PASSWORD:
                checkEmail(email);
                return MailTemplateEnum.APP_VERIFY_CODE;
            case ACTION_REGISTER:
            case ACTION_UPDATE_PASSWORD:
            case ACTION_WITHDRAW:
            case ACTION_PAYMENT:
                return MailTemplateEnum.APP_VERIFY_CODE;
            default:
                log.error("[發送Email]無匹配信件模板");
                throw new SystemRuntimeException(SystemExceptionEnum.PARAM_ERROR);
        }

    }

    private void checkEmail(String email) {
        UserAppDto user = userAppMapper.findDtoByAccount(email);
        Validator.isNullThrow(user, OperatorExceptionEnum.ACCOUNT_NOT_EXIST);
    }

}