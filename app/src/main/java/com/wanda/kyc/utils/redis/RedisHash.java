package com.wanda.kyc.utils.redis;

import com.wanda.kyc.utils.redis.base.RedisBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;


@Service
public class RedisHash extends RedisBase {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static HashOperations<String, String, String> ops;

    // 創建bean時先將ops設置好
    @PostConstruct
    private void initOps() {
        ops = stringRedisTemplate.opsForHash();
    }

    /**
     * 在 redis 放入 hashMap
     * @param redisKey redis 存資料的鍵
     * @param hashKey hashMap 的鍵
     * @param value hashMap 的值
     */
    public void put(String redisKey, String hashKey, String value) {
        ops.put(redisKey, hashKey, value);
    }

    /**
     * 取得存放於 redis 中的 hashMap，其指定鍵值對應的值
     * @param redisKey redis 存資料的鍵
     * @param hashKey hashMap 的鍵
     * @return 指定鍵所對應到的 hashMap 的鍵，其所對應的值
     */
    public String get(String redisKey, String hashKey) {
        return ops.get(redisKey, hashKey);
    }

    /**
     * 確認 redis 中指定鍵所對應到的全部 hashMap 中，有無此鍵
     * @param redisKey redis 存資料的鍵
     * @param hashKey hashMap 的鍵
     * @return true = 此鍵存在 || false = 此鍵不存在
     */
    public boolean hasKey(String redisKey, String hashKey) {
        return ops.hasKey(redisKey, hashKey);
    }

    /**
     * 清除 redis 中 redisKey 所對應到的 hashMap 的 hashKey，和其所對應的值
     * @param redisKey redis 存資料的鍵
     * @param hashKey hashMap 的鍵
     * @return 從 hashKey 中，刪除了多少元素
     */
    public long remove(String redisKey, String hashKey) {
        return ops.delete(redisKey, hashKey);
    }

    /**
     * 對 redis 中指定鍵所對應的 hashMap，其 hashKey 對應的值進行加減 <br>
     * 須注意 hashKey 所對應的值，必須可解析成數字
     * @param redisKey redis 存資料的鍵
     * @param hashKey hashMap 的鍵
     * @param value 長整數
     * @return 計算後的數值
     */
    public long increment(String redisKey, String hashKey, long value) {
        return ops.increment(redisKey, hashKey, value);
    }

    /**
     * 對 redis 中指定鍵所對應的 hashMap，其 hashKey 對應的值進行加減 <br>
     * 須注意 hashKey 所對應的值，必須可解析成數字
     * @param redisKey redis 存資料的鍵
     * @param hashKey hashMap 的鍵
     * @param value 浮點數
     * @return 計算後的數值
     */
    public double increment(String redisKey, String hashKey, double value) {
        return ops.increment(redisKey, hashKey, value);
    }

    /**
     * 取得存放於 redis 中的 hashMap，其所有的鍵值組
     * @param redisKey redis 存資料的鍵
     * @return redisKey 存放的鍵值組
     */
    public Map<String, String> entries(String redisKey) {
        return ops.entries(redisKey);
    }

    /**
     * 將所有鍵值都放進 redis 存資料的鍵裡面
     * @param hashKey redis 存資料的鍵
     * @param map 要放入的鍵值組
     */
    public void putAll(String hashKey, Map<String, String> map) {
        ops.putAll(hashKey, map);
    }
}
