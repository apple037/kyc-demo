package com.wanda.kyc.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.wanda.kyc.constant.TokenConst;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;


/**
 * JWT-token
 */
@Slf4j
public class JwtUtil {

	private JwtUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 簽名
	 *
	 * @param map
	 * @param secret
	 * @return
	 */
	public static String signByApp(Map<String, String> map, String secret) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		// @off
		return JWT.create()
				.withClaim(TokenConst.ROLE, map.get(TokenConst.ROLE))
				.withClaim(TokenConst.USER_ID, map.get(TokenConst.USER_ID))
				.withClaim(TokenConst.SUPERIOR, map.get(TokenConst.SUPERIOR))
				.sign(algorithm);
		// @on
	}

	/**
	 * 簽名
	 *
	 * @param map
	 * @param secret
	 * @param expireTime
	 * @return
	 */
	public static String signByMachine(Map<String, String> map, String secret, Date expireTime) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		// @off
		return JWT.create()
				.withClaim(TokenConst.ROLE, map.get(TokenConst.ROLE))
				.withClaim(TokenConst.USER_ID, map.get(TokenConst.USER_ID))
				.withExpiresAt(expireTime)
				.sign(algorithm);
		// @on
	}

	/**
	 * 簽名
	 *
	 * @param map
	 * @param secret
	 * @return
	 */
	public static String signByBackstage(Map<String, String> map, String secret) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		// @off
		return JWT.create()
				.withClaim(TokenConst.ROLE, map.get(TokenConst.ROLE))
				.withClaim(TokenConst.USER_ID, map.get(TokenConst.USER_ID))
				.sign(algorithm);
		// @on
	}

	/**
	 * 效驗token是否正確
	 *
	 * @param token
	 * @return
	 */
	public static boolean verify(String token, String secret) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).build();
			verifier.verify(token);
			return true;
		} catch (Exception e) {
			log.error("JWT驗證發生exception:{}", e.getMessage());
			return false;
		}
	}

	/**
	 * 獲得token中的訊息(不需要secretKey也能得到)
	 *
	 * @param token
	 * @param claim
	 * @return
	 */
	public static Claim getClaim(String token, String claim) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			// getClaim的時候如果型態不同會返回null
			return jwt.getClaim(claim);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("取得JWT中data的資料發生exception,exception訊息:{}", e.getMessage());
			throw e;
		}
	}

	// 獲得token中的訊息 role
	public static String getRole(String token) {
		return getClaim(token, TokenConst.ROLE).asString();
	}

	// 獲得token中的訊息 userId
	public static String getUserId(String token) {
		return getClaim(token, TokenConst.USER_ID).asString();
	}

	// 獲得token中的訊息 email
	public static String getEmail(String token) {
		return getClaim(token, TokenConst.EMAIL).asString();
	}

	public static String getRequestToken(HttpServletRequest request) {
		String token = request.getHeader(TokenConst.AUTHORIZATION);
		if (token == null) {
			log.error("請求沒帶token");
			// throw new IllegalArgumentException("請求沒帶token");
		} else if (token.startsWith(TokenConst.PREFIX)) {
			token = token.substring(TokenConst.PREFIX_LENGTH);
		}
		return token;
	}

}
