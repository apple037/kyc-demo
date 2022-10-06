package com.wanda.kyc.utils;

public class RedisUtil {

	private RedisUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static final String KEY_ADMIN_USER = "userInfo:admin:";
	private static final String KEY_ADMIN_OFFICIAL_USER = "userInfo:admin-official:";
	private static final String KEY_MEMBER_USER = "userInfo:member:";
	// private static final String KEY_ROBOT_USER = "userInfo:robot:";
	private static final String KEY_CAPTCHA = "captcha";
	private static final String KEY_VERIFY_CODE = "verifyCode";
	private static final String KEY_VERIFY_CODE_PASSED = "verifyCode-pass";
	private static final String KEY_GOOGLE_AUTH = "googleAuth";
	private static final String KEY_GOOGLE_AUTH_PASSED = "googleAuth-pass";
	private static final String KEY_FORBID_WITHDRAW = "forbidWithdraw";
	private static final String KEY_NFT_LOCK = "lock:nft:";
	public static final long NFT_LOCK_EXPIRE = 15000;
	private static final String KEY_RPC_NEW_ADDRESS_LOCK = "lock:rpcNewAddress";
	private static final String KEY_RPC_WITHDRAW_LOCK = "lock:rpcWithdraw";
	private static final String KEY_DUNGEON_WAVE = "dungeon:wave";
	private static final String KEY_DUNGEON_MONSTER = "dungeon:mon";
	private static final String KEY_DUNGEON_REWARD = "dungeon:rew";
	private static final String KEY_PROP_LIST = "prop:list";

	// 排程 維護一份資料
	public static final String KEY_CURRENCY_PRICE = "currencyPrice";
	private static final String KEY_TICKER_24HR = "ticker24H";

	// 後台帳號資訊
	public static String adminUserInfoKey(String account) {
		return KEY_ADMIN_USER.concat(account);
	}

	// 後台帳號資訊
	public static String adminOfficialUserInfoKey(String account) {
		return KEY_ADMIN_OFFICIAL_USER.concat(account);
	}

	// 會員帳號資訊
	public static String memberUserInfoKey(String id) {
		return KEY_MEMBER_USER.concat(id);
	}

	// 圖形驗證碼
	public static String captcha(String captchaId) {
		return KEY_CAPTCHA.concat(":").concat(captchaId);
	}

	// 驗證碼
	public static String getKeyByVerifyAction(String account, String verifyAction) {
		return KEY_VERIFY_CODE.concat(":").concat(account).concat(":").concat(verifyAction);
	}

	public static String verifyCodePassed(String account) {
		return KEY_VERIFY_CODE_PASSED.concat(":").concat(account);
	}

	public static String googleAuthPassed(String account) {
		return KEY_GOOGLE_AUTH_PASSED.concat(":").concat(account);
	}

	// google驗證碼
	public static String googleAuth(String account) {
		return KEY_GOOGLE_AUTH.concat(":").concat(account);
	}

	// 修改google驗証碼後提現限制
	public static String forbidWithdraw(String account) {
		return KEY_FORBID_WITHDRAW.concat(":").concat(account);
	}

	// 創建提幣地址鎖
	public static String rpcNewAddressLock(String account) {
		return KEY_RPC_NEW_ADDRESS_LOCK.concat(":").concat(account);
	}

	// 提幣鎖
	public static String rpcWithdrawLock(String account) {
		return KEY_RPC_WITHDRAW_LOCK.concat(":").concat(account);
	}

	// 搶購鎖
	public static String nftLock(String itemId) {
		return KEY_NFT_LOCK.concat(":").concat(itemId);
	}

	// 交易對價格 根據交易所分別存
	public static String symbolPrice(String market) {
		return KEY_TICKER_24HR.concat(":").concat(market);
	}

	// 副本獎勵
	public static String dungeonReward(String dungeonWaveId) {
		return KEY_DUNGEON_REWARD.concat(":").concat(dungeonWaveId);
	}

	// 道具列表
	public static String propList(String id) {
		return KEY_PROP_LIST.concat(":").concat(id);
	}

}
