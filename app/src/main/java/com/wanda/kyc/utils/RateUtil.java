package com.wanda.kyc.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

// 機率工具
public class RateUtil {
    // 使用安全隨機數
    private static final SecureRandom sr = new SecureRandom();

    /**
     * 隨機決定是否通過機率
     * @param rate (單位為千分,例如1000的通過機率為100%)
     * @return
     */
    public static boolean passRate(int rate) {
        return sr.nextInt(1000) < rate;
    }

    /**
     * 取得隨機結果
     * @param results 可傳多個結果進來
     * @param <T>
     * @return 隨機返回傳來結果的其中之一
     */
    @SafeVarargs // 加此註解表示即使傳不同型別的參數進來也沒問題
    public static <T> T getRandomResult(T... results) {
        int ran = sr.nextInt(results.length);
        return results[ran];
    }

    /**
     * 取得隨機結果
     * @param results 結果的List
     * @param <T>
     * @return 隨機返回結果List的其中之一
     */
    public static <T> T getRandomResult(List<T> results) {
        int ran = sr.nextInt(results.size());
        return results.get(ran);
    }

    /**
     * 取得加權的隨機結果
     * 註：總權重不可超過int範圍
     * @param resultAndWeightsMap Map<結果,權重>
     * @return 回傳結果的key
     */
    public static <T> T getRandomResultByWeights(Map<T, Integer> resultAndWeightsMap) {
        // 計算總權重
        int totalWeights = 0;
        for (Integer weight : resultAndWeightsMap.values())
            totalWeights += weight;

        // 取得權重範圍內的隨機數
        SecureRandom secureRandom = new SecureRandom();
        int random = secureRandom.nextInt(totalWeights);

        // 累計權重
        int progressiveWeight = 0;

        for (T result : resultAndWeightsMap.keySet()) {
            progressiveWeight += resultAndWeightsMap.get(result);
            // 小於該累進權重則回傳該結果
            if (random < progressiveWeight)
                return result;
        }
        return null;
    }

    /**
     * 取得上下範圍內的隨機數字
     * @param lowerLimit 下限(包含)
     * @param upperLimit 上限(包含)
     * @return
     */
    public static int getRandomNumberInRange(int lowerLimit, int upperLimit) {
        return lowerLimit + sr.nextInt(upperLimit - lowerLimit + 1);
    }

    public static long getRandomNumberInRange(long lowerLimit, long upperLimit) {
        return lowerLimit + (long) (sr.nextDouble() * (upperLimit - lowerLimit + 1));
    }

    public static float getRandomNumberInRange(float lowerLimit, float upperLimit) {
        return lowerLimit + (float) (sr.nextDouble() * (upperLimit - lowerLimit + 1));
    }

    public static double getRandomNumberInRange(double lowerLimit, double upperLimit) {
        return lowerLimit + sr.nextDouble() * (upperLimit - lowerLimit + 1);
    }

    /**
     * 取得範圍內的隨機數字
     * @param range 數字範圍(不包含)
     * @return 0 ~ range-1 的隨機數字
     */
    public static int getRandomNumberInRange(int range) {
        return sr.nextInt(range);
    }

    public static long getRandomNumberInRange(long range) {
        return (long) (sr.nextDouble() * range);
    }

    public static float getRandomNumberInRange(float range) {
        return (float) (sr.nextDouble() * range);
    }

    public static double getRandomNumberInRange(double range) {
        return sr.nextDouble() * range;
    }

    /**
     * 取得切分後的數字
     * @param number   被切分數
     * @param splitNum 切分數量
     * @return 切分後的數字list
     */
    public static List<Integer> getSplitNumber(int number, int splitNum) {
        List<Integer> resultList = new ArrayList<>();
        // 設定斷點
        boolean[] booArr = new boolean[number];
        for (int i = 0; i < splitNum - 1; i++)
            booArr[sr.nextInt(number)] = true;
        booArr[booArr.length - 1] = true;// 尾部也需有斷點
        // 記錄斷點間的數字
        int preNum = 0;
        for (int i = 0; i < booArr.length; i++) {
            if (booArr[i]) {
                resultList.add(i + 1 - preNum);
                preNum = i + 1;
            }
        }
        // 不足的補0
        int dif = splitNum - resultList.size();
        for (int i = 0; i < dif; i++) {
            resultList.add(0);
        }
        // 打亂
        Collections.shuffle(resultList);
        return resultList;
    }
}
