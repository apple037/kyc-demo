package com.wanda.kyc.utils.redis.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
/**
 * 用來操作 redis 的封裝介面。
 */
public class RedisBase {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 返回一個 stringRedisTemplate，讓使用者可自行決定操作
     * @return StringRedisTemplate
     */
    public StringRedisTemplate operate() {
        return stringRedisTemplate;
    }

    /**
     * 確認 redis 中有無指定的鍵
     * @param redisKey redis 存資料的鍵
     * @return true = 此鍵存在 || false = 此鍵不存在
     */
    public Boolean hasKey(String redisKey) {
        return stringRedisTemplate.hasKey(redisKey);
    }

    /**
     * 刪除指定的鍵和其對應的值
     * @param redisKey redis 存資料的鍵
     * @return 該鍵是否存在
     */
    public Boolean delete(String redisKey) {
        return stringRedisTemplate.delete(redisKey);
    }

    /**
     * 刪除所有指定的鍵和其對應的值
     * @param redisKeys redis 存資料的鍵的集合
     * @return 成功刪除了多少鍵
     */
    public Long deleteAll(Set<String> redisKeys) {
        return stringRedisTemplate.delete(redisKeys);
    }

    /**
     * 設定指定 redis 鍵於幾天後過期
     * @param redisKey redis 存資料的鍵
     * @param timeUnitNum 過期時間單位量
     * @return true = 設置成功 || false = 指定鍵不存在
     */
    public Boolean setKeyExpireDays(String redisKey, long timeUnitNum) {
        return stringRedisTemplate.expire(redisKey, timeUnitNum, TimeUnit.DAYS);
    }

    /**
     * 設定指定 redis 鍵於幾小時後過期
     * @param redisKey redis 存資料的鍵
     * @param timeUnitNum 過期時間單位量
     * @return true = 設置成功 || false = 指定鍵不存在
     */
    public Boolean setKeyExpireHours(String redisKey, long timeUnitNum) {
        return stringRedisTemplate.expire(redisKey, timeUnitNum, TimeUnit.HOURS);
    }

    /**
     * 設定指定 redis 鍵於幾分鐘後過期
     * @param redisKey redis 存資料的鍵
     * @param timeUnitNum 過期時間單位量
     * @return true = 設置成功 || false = 指定鍵不存在
     */
    public Boolean setKeyExpireMinutes(String redisKey, long timeUnitNum) {
        return stringRedisTemplate.expire(redisKey, timeUnitNum, TimeUnit.MINUTES);
    }

    /**
     * 設定指定 redis 鍵於幾秒鐘後過期
     * @param redisKey redis 存資料的鍵
     * @param timeUnitNum 過期時間單位量
     * @return true = 設置成功 || false = 指定鍵不存在
     */
    public boolean setKeyExpireSeconds(String redisKey, long timeUnitNum) {
        return stringRedisTemplate.expire(redisKey, timeUnitNum, TimeUnit.SECONDS);
    }

    /**
     * 設定指定 redis 鍵於幾毫秒後過期
     * @param redisKey redis 存資料的鍵
     * @param timeUnitNum 過期時間單位量
     * @return true = 設置成功 || false = 指定鍵不存在
     */
    public boolean setKeyExpireMS(String redisKey, long timeUnitNum) {
        return stringRedisTemplate.expire(redisKey, timeUnitNum, TimeUnit.MILLISECONDS);
    }

    /**
     * 取得指定範圍的redis key
     * @param pattern key的表達式，可使用以下的Redis模糊查詢
     *                *  匹配任意多个字符        Ex : SBB12345,SBBrw  --> SBB*
     *                ?  匹配一个字符           EX :  SBB123, SBBaaa  --> SBB???
     *                [] 匹配括号内的一个字符    EX :  賽車SR 但不要賽艇SRW  --> SR[0123456789]*
     * @return
     */
    public Set<String> getKeys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }

}
