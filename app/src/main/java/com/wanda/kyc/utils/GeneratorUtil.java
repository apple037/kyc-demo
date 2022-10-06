package com.wanda.kyc.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * 產生定用途的亂數
 * 
 * @author hsiang
 *
 */
public class GeneratorUtil {

	private GeneratorUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static final String MEMBER_ID_PREFIX = "M0";
	private static final String UID_PREFIX = "U";

	private static final String ROBOT_ID_SPOT_PREFIX = "R0";
	private static final String ROBOT_ID_CONTRACT_PREFIX = "RC0";

	private static final String DEPOSIT_ORDER_ID = "D";
	private static final String WITHDRAW_ORDER_ID = "W";
	private static final String FINAL_MERGE_ORDER_ID_SPOT = "FV";
	private static final String FINAL_MERGE_ORDER_ID_CONTRACT = "FVC";
	private static final String ACTIVATION_ORDER_ID = "A";
	private static final String WALLET_COLLECT_ORDER_ID = "C";
	private static final String TRANSFER_COLD_WALLET_ORDER_ID = "T";
	private static final String AGENT_SHARE_ORDER_ID = "AS";
	private static final String BLINDBOX_ID = "B";
	private static final String BLINDBOX_ORDER_ID = "BO";
	private static final String NFT_ORDER_ID = "NO";
	private static final String NFT_MARKET_ID = "NM";

	public static String verifyCode() {
		return RandomUtil.numRandom(6);
	}

	// memberId
	public static String memberId() {
		return MEMBER_ID_PREFIX.concat(RandomUtil.numEnglishMixRandom(7, true));
	}

	// 鹽巴
	public static String salt() {
		return RandomUtil.numEnglishMixRandom(20, false);
	}

	// 密碼
	public static String password() {
		return RandomUtil.numEnglishMixRandom(10, false);
	}

	// 邀請碼
	public static String uid() {
		return UID_PREFIX.concat(RandomUtil.numEnglishMixRandom(5, true));
	}

	// token簽名secret
	public static String tokenSecret() {
		return RandomUtil.numEnglishMixRandom(32, false);
	}
	
	// 機器人id 現貨(多頭)
	public static String robotNo(String memberId) {
		return ROBOT_ID_SPOT_PREFIX.concat(memberId.substring(memberId.length() - 3, memberId.length())).concat(RandomUtil.numRandom(10));
	}

	// 機器人id 合約(空頭)
	public static String robotNoContract(String memberId) {
		return ROBOT_ID_CONTRACT_PREFIX.concat(memberId.substring(memberId.length() - 3, memberId.length()))
				.concat(RandomUtil.numRandom(10));
	}

	// 充幣訂單
	public static String depositOrderId() {
		return DEPOSIT_ORDER_ID.concat(generateCurrentDatetime14Digits()).concat(RandomUtil.numRandom(2));
	}

	// 盲盒
	public static String blindboxId() {
		return BLINDBOX_ID.concat(generateCurrentDatetime14Digits()).concat(RandomUtil.numRandom(2));
	}

	// 盲盒訂單
	public static String blindboxOrderId() {
		return BLINDBOX_ORDER_ID.concat(generateCurrentDatetime14Digits()).concat(RandomUtil.numRandom(2));
	}

	// NFT market
	public static String nftMarketId() {
		return NFT_MARKET_ID.concat(generateCurrentDatetime14Digits()).concat(RandomUtil.numRandom(2));
	}

	// NFT訂單
	public static String nftOrderId() {
		return NFT_ORDER_ID.concat(generateCurrentDatetime14Digits()).concat(RandomUtil.numRandom(2));
	}
	
	// 提幣訂單
	public static String withdrawOrderId() {
		return WITHDRAW_ORDER_ID.concat(generateCurrentDatetime14Digits()).concat(RandomUtil.numRandom(2));
	}

	// 合併訂單 現貨(多頭)
	public static String finalMergeOrderId() {
		return FINAL_MERGE_ORDER_ID_SPOT.concat(generateCurrentDatetime17Digits()).concat(RandomUtil.numRandom(2));
	}

	// 合併訂單 合約(空頭)
	public static String finalMergeOrderIdContract() {
		return FINAL_MERGE_ORDER_ID_CONTRACT.concat(generateCurrentDatetime17Digits()).concat(RandomUtil.numRandom(2));
	}

	// 開通訂單
	public static String activationOrderId() {
		return ACTIVATION_ORDER_ID.concat(generateCurrentDatetime14Digits()).concat(RandomUtil.numRandom(2));
	}
	
	// 代理分潤結算訂單
	public static String agentShareRecordId(String date,String memberId,String type,String range) {
		return AGENT_SHARE_ORDER_ID.concat(date).concat(type).concat(range).concat(memberId);
	}

	// 歸集訂單
	public static String walletCollectOrderId() {
		return WALLET_COLLECT_ORDER_ID.concat(generateCurrentDatetime14Digits()).concat(RandomUtil.numRandom(2));
	}

	// 轉冷錢包訂單
	public static String transferColdWalletOrderId() {
		return TRANSFER_COLD_WALLET_ORDER_ID.concat(generateCurrentDatetime14Digits()).concat(RandomUtil.numRandom(2));
	}

	// 14位年月日時分秒字串
	private static String generateCurrentDatetime14Digits() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	}

	// 17位年月日時分秒字串
	private static String generateCurrentDatetime17Digits() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
	}

	public static String machinePassword() {
		return RandomUtil.numEnglishMixRandom(16, false);
	}

	public static String machineSalt() {
		return RandomUtil.numEnglishMixRandom(32, false);
	}

	public static void main(String[] args) {
		System.out.println(machinePassword());
		System.out.println(machinePassword());
		System.out.println(machineSalt());
		System.out.println(machineSalt());
	}

}
