package com.wanda.kyc.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wanda.kyc.constant.*;
import com.wanda.kyc.dto.LoginReqDto;
import com.wanda.kyc.dto.LoginResDto;
import com.wanda.kyc.dto.RegisterDto;
import com.wanda.kyc.dto.admin.UserAdmin;
import com.wanda.kyc.dto.admin.UserAdminDto;
import com.wanda.kyc.dto.app.UserAppDto;
import com.wanda.kyc.dto.app.UserSearchDto;
import com.wanda.kyc.dto.kyc.KycApplyResp;
import com.wanda.kyc.dto.token.TokenInfo;
import com.wanda.kyc.dto.user.CheckCodeDto;
import com.wanda.kyc.dto.user.User;
import com.wanda.kyc.dto.user.UserApp;
import com.wanda.kyc.exception.SystemRuntimeException;
import com.wanda.kyc.exception.Validator;
import com.wanda.kyc.exception.enumeration.OperatorExceptionEnum;
import com.wanda.kyc.exception.enumeration.SystemExceptionEnum;
import com.wanda.kyc.mapper.UserAdminMapper;
import com.wanda.kyc.mapper.UserAppMapper;
import com.wanda.kyc.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/*
 * 使用者相關
 */
@Slf4j
@Service
public class UserService {

    @Resource
    private UserAppMapper userAppMapper;
    @Resource
    private UserAdminMapper userAdminMapper;
    @Resource
    private TokenService tokenService;
    @Resource
    private RedisService redisService;
    @Resource
    private MailService mailService;
    @Resource
    private VerifyService verifyService;

    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterDto reqDto) {
        confirmVerifyCodePassed(reqDto.getEmail());
        UserApp otherUserByEmail = userAppMapper
                .selectOne(new QueryWrapper<UserApp>()
                        .eq("username", reqDto.getEmail())); //email即帳號
        Validator.isNotNullThrow(otherUserByEmail, OperatorExceptionEnum.ALREADY_HAVE_EMAIL_ERROR);

        String userId = saveMember(reqDto);

    }

    private String saveMember(RegisterDto reqDto) {

        String salt = GeneratorUtil.salt();
        String userId = GeneratorUtil.memberId();

        UserApp userApp = new UserApp();
        userApp.setMemberId(userId);
        userApp.setPassword(SecretSecurityUtil.passwordEncode(reqDto.getPassword(), salt));
        userApp.setSalt(salt);
        userApp.setUsername(reqDto.getEmail());
        userApp.setStatus(StatusConst.NORMAL);

        userAppMapper.insert(userApp);

        return userId;
    }


    public void logout(User user) {
        redisService.delKey(RedisUtil.memberUserInfoKey(user.getUserId()));
    }

    @Transactional(rollbackFor = Exception.class)
    public LoginResDto login(LoginReqDto reqDto, String type) {
        switch(type) {
            case "NORMAL":
                UserAppDto userAppDto = userAppMapper.findDtoByAccount(reqDto.getAccount());
                Validator.isNullThrow(userAppDto, OperatorExceptionEnum.ACCOUNT_NOT_EXIST);
                boolean passwordMatch = SecretSecurityUtil.passwordMatch(reqDto.getPassword(), userAppDto.getSalt(), userAppDto.getPassword());
                Validator.isFalseThrow(passwordMatch, OperatorExceptionEnum.PASSWORD_ERROR);
                return getLoginResDto(userAppDto);
            case "SUPER":
                UserAdminDto userAdminDto = userAdminMapper.getAdmin(reqDto.getAccount());
                Validator.isNullThrow(userAdminDto, OperatorExceptionEnum.ACCOUNT_NOT_EXIST);
                boolean match = reqDto.getPassword().equals(userAdminDto.getPassword());
                Validator.isFalseThrow(match, OperatorExceptionEnum.PASSWORD_ERROR);
                return getAdminLoginResDto(userAdminDto);
            default:
                return null;
        }
    }

    public LoginResDto getLoginResDto(UserAppDto userAppDto) {

        TokenInfo tokenInfo = TokenInfo.builder()
                .role(TokenConst.ROLE_USER_APP)
                .userId(userAppDto.getId())
                .email(userAppDto.getUsername())
                .build();

        return LoginResDto.builder()
                .id(userAppDto.getId())
                .name(userAppDto.getUsername())
                .email(userAppDto.getUsername())
                .token(tokenService.getToken(tokenInfo))
                .build();
    }

    public LoginResDto getAdminLoginResDto(UserAdminDto userAdminDto) {

        TokenInfo tokenInfo = TokenInfo.builder()
                .role(TokenConst.ROLE_USER_ADMIN)
                .userId(userAdminDto.getId())
                .email(userAdminDto.getUsername())
                .build();

        return LoginResDto.builder()
                .id(userAdminDto.getId())
                .name(userAdminDto.getUsername())
                .email(userAdminDto.getUsername())
                .token(tokenService.getToken(tokenInfo))
                .build();
    }


    /**
     * 取得用戶資訊
     * @param user user資訊
     * @return UserAppDto
     */
    public UserAppDto getUserInfo(User user) {

        UserAppDto dto = userAppMapper.findDtoById2(user.getUserId());
        Validator.isNullThrow(dto, OperatorExceptionEnum.ACCOUNT_NOT_EXIST);

        return dto;
    }

    /**
     * 修改用戶資訊
     * @param reqDto user修改資訊
     */
    public void updateUserInfo(RegisterDto reqDto) {

        String encodePassword = null;
        //TODO 驗證需要再修更簡短
        if (StringUtils.isNotBlank(reqDto.getPassword())) {
            if (StringUtils.isNotBlank(reqDto.getOldPassword())) {
                UserApp userApp = userAppMapper.selectOne(
                        Wrappers.lambdaQuery(UserApp.class)
                                .select(UserApp::getSalt, UserApp::getPassword)
                                .eq(UserApp::getId, reqDto.getUserId()));
                Validator.isNullThrow(userApp, OperatorExceptionEnum.ACCOUNT_NOT_EXIST);
                String salt = userApp.getSalt();
                boolean passwordMatch = SecretSecurityUtil.passwordMatch(reqDto.getOldPassword(), salt, userApp.getPassword());
                Validator.isFalseThrow(passwordMatch, OperatorExceptionEnum.PASSWORD_ERROR);

                encodePassword = SecretSecurityUtil.passwordEncode(reqDto.getPassword(), salt);
            } else {
                throw new SystemRuntimeException(SystemExceptionEnum.PARAM_ERROR);
            }
        }
        LambdaUpdateWrapper<UserApp> luw = Wrappers.lambdaUpdate();
        luw
                .set(StringUtils.isNotBlank(reqDto.getPassword()), UserApp::getPassword, encodePassword)
                .eq(UserApp::getId, reqDto.getUserId());

        if (userAppMapper.update(new UserApp(), luw) != 1)
            throw new SystemRuntimeException(SystemExceptionEnum.UPDATE_ERROR);

    }

    public void sendCode(String action, String account, String lang ) {
        mailService.sendVerifyEmail(account,lang,action);
    }

    public void confirmVerifyCodePassed(String email) {
        verifyService.checkVerifyCodePassed(email);
    }

    public String checkVerifyCode(CheckCodeDto reqDto) {
        verifyService.checkVerify(reqDto.getEmail(), reqDto.getAction(), reqDto.getCode());
        verifyService.saveVerifyCodePassed(reqDto.getEmail());
        return null;
    }

    public List<KycApplyResp> getApplyList(UserSearchDto dto){
        return userAppMapper.getApplyList(dto);
    }

    public String getAdminType(User user){
        UserAdmin admin = userAdminMapper.selectById(Long.parseLong(user.getUserId()));
        return admin.getType();
    }

}
