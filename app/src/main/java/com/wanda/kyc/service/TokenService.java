package com.wanda.kyc.service;

import com.wanda.kyc.constant.TokenConst;
import com.wanda.kyc.dto.token.TokenInfo;
import com.wanda.kyc.dto.user.AdminUser;
import com.wanda.kyc.dto.user.AppUser;
import com.wanda.kyc.dto.user.User;
import com.wanda.kyc.exception.SystemRuntimeException;
import com.wanda.kyc.exception.enumeration.SystemExceptionEnum;
import com.wanda.kyc.utils.GeneratorUtil;
import com.wanda.kyc.utils.JwtUtil;
import com.wanda.kyc.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class TokenService {

    @Autowired
    private RedisService redisService;

    /**
     * 取得token
     *
     * @param tokenInfo the token info
     * @return token
     */
    public String getToken(TokenInfo tokenInfo) {
        String tokenKey = getTokenKey(tokenInfo);
        String tokenSecret = getTokenSecret(tokenInfo.getUserId(), tokenKey,tokenInfo.getRole());
        setUserInfoToRedis(tokenInfo,tokenKey,tokenSecret);

        return JwtUtil.signByBackstage(getTokenParamMap(tokenInfo), tokenSecret);
    }

    private String getTokenKey(TokenInfo tokenInfo) {
        switch (tokenInfo.getRole()) {
            case TokenConst.ROLE_USER_ADMIN:
                return RedisUtil.adminUserInfoKey(tokenInfo.getUserId().toString());
            case TokenConst.ROLE_USER_APP:
                return RedisUtil.memberUserInfoKey(tokenInfo.getUserId().toString());
            default:
                throw new SystemRuntimeException(SystemExceptionEnum.DATA_ERROR);
        }
    }

    private void setUserInfoToRedis(TokenInfo tokenInfo,String tokenKey, String tokenSecret) {
        User user;
        switch (tokenInfo.getRole()) {
            case TokenConst.ROLE_USER_ADMIN:
                user = new AdminUser();
                break;
            case TokenConst.ROLE_USER_APP:
                user = new AppUser();
                break;
            default:
                throw new SystemRuntimeException(SystemExceptionEnum.DATA_ERROR);
        }
        user.setTokenSecret(tokenSecret);
        user.setRole(tokenInfo.getRole());
        user.setUserId(tokenInfo.getUserId().toString());
        redisService.hashPutAll(tokenKey, user);
        redisService.expire(tokenKey, TokenConst.TOKEN_EXPIRE_TIME_BACKSTAGE, TimeUnit.MINUTES);
    }

    /**
     * 取得token 簽名key
     *
     * @param account
     * @param tokenKey
     * @return
     */
    private String getTokenSecret(Long account, String tokenKey,String role) {
        User user;
        switch (role) {
            case TokenConst.ROLE_USER_ADMIN:
                user = redisService.hashGetAll(tokenKey, AdminUser.class);
                break;
            case TokenConst.ROLE_USER_APP:
                user = redisService.hashGetAll(tokenKey, AppUser.class);
                break;
            default:
                throw new SystemRuntimeException(SystemExceptionEnum.DATA_ERROR);
        }
        if (null != user) {
            return user.getTokenSecret();
        }
        return GeneratorUtil.tokenSecret();
    }

    /**
     * 取得token param map
     *
     * @param tokenInfo
     * @return
     */
    private Map<String, String> getTokenParamMap(TokenInfo tokenInfo) {
        Map<String, String> tokenParamMap = new HashMap<>();
        tokenParamMap.put(TokenConst.ROLE, tokenInfo.getRole());
        tokenParamMap.put(TokenConst.USER_ID, tokenInfo.getUserId().toString());
        return tokenParamMap;
    }

}
