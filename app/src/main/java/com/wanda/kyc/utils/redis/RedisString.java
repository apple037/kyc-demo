package com.wanda.kyc.utils.redis;

import com.wanda.kyc.utils.redis.base.RedisBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Service
public class RedisString extends RedisBase {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static ValueOperations<String, String> ops;

    // 創建bean時先將ops設置好
    @PostConstruct
    private void initOps () {
        ops = stringRedisTemplate.opsForValue();
    }

    /**
     * 在 redis 放入字串值
     * @param redisKey
     * @param value
     */
    public void set(String redisKey, String value) {
        ops.set(redisKey, value);
    }

    /**
     * 在 redis 放入字串值，並設訂存留時間
     * @param redisKey
     * @param value
     * @param timeout  存留時間
     * @param unit     時間單位
     */
    public void setWithTimeout(String redisKey, String value, long timeout, TimeUnit unit) {
        ops.set(redisKey, value, timeout, unit);
    }

    /**
     * key 在 redis 沒有對應值時放入字串值
     * @param redisKey
     * @param value
     * @return
     */
    public Boolean setIfAbsent(String redisKey, String value) {
        return ops.setIfAbsent(redisKey, value);
    }

    /**
     * 取得存放於 redis 的字串值
     * @param redisKey
     */
    public String get(String redisKey) {
        return ops.get(redisKey);
    }

    /**
     * 取得存放於 redis 的字串值片段
     * @param redisKey
     * @param startIndex 起始字元索引
     * @param endIndex   結束字元索引
     * @return
     */
    public String getCut(String redisKey, long startIndex, long endIndex) {
        return ops.get(redisKey, startIndex, endIndex);
    }

    /**
     * 取得存放於 redis 的字串值並重新賦值
     * @param redisKey
     * @param value    新的值
     * @return
     */
    public String getAndSet(String redisKey, String value) {
        return ops.getAndSet(redisKey, value);
    }

    /**
     * 取得批量的字串值
     * @param redisKeys 指定的key的集合
     * @return
     */
    public List<String> getAll(Set<String> redisKeys) {
        return ops.multiGet(redisKeys);
    }

    /**
     * 在 redis 對應的字串值末尾增加字串
     * @param redisKey
     * @param value
     */
    public void append(String redisKey, String value) {
        ops.append(redisKey, value);
    }

    /**
     * 在 redis 對應的字串覆蓋從指定位置開始的值
     * @param redisKey
     * @param value
     * @param offset   偏移量(起始索引)
     */
    public void cover(String redisKey, String value, long offset) {
        ops.set(redisKey, value, offset);
    }

    /**
     * 取得存放於 redis 的字串的長度
     * @param redisKey
     */
    public Long size(String redisKey) {
        return ops.size(redisKey);
    }

    /**
     * 增加數值並取得增加後的值
     * 須注意指定鍵所對應的值，必須可解析成數字
     * @param redisKey
     * @param value
     * @return
     */
    public Double increment(String redisKey, double value) {
        return ops.increment(redisKey, value);
    }

    /**
     * 增加數值並取得增加後的值
     * 須注意指定鍵所對應的值，必須可解析成數字
     * @param redisKey
     * @param value
     * @return
     */
    public Long increment(String redisKey, long value) {
        return ops.increment(redisKey, value);
    }

}
