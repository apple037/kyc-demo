package com.wanda.kyc.utils.redis;

import com.wanda.kyc.utils.redis.base.RedisBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;


@Service
public class RedisSet extends RedisBase {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static SetOperations<String, String> ops;

    // 創建bean時先將ops設置好
    @PostConstruct
    private void initOps() {
        ops = stringRedisTemplate.opsForSet();
    }

    /**
     * 取得存放於 redis 中指定鍵所對應的 Set 集合
     * @param redisKey redis 存資料的鍵
     * @return Set 集合
     */
    public Set<String> get(String redisKey) {
        return ops.members(redisKey);
    }

    /**
     * 在 redis 中存放 Set
     * @param redisKey redis 存資料的鍵
     * @param value 要存放的值
     * @return 加了多少元素
     */
    public Long add(String redisKey, String... value) {
        return ops.add(redisKey, value);
    }

    /**
     * 移除指定 Set 的元素
     * @param redisKey redis 存資料的鍵
     * @param value 要移除的元素
     * @return 移除了多少元素
     */
    public Long remove(String redisKey, String... value) {
        return ops.remove(redisKey, (Object[]) value);
    }

    /**
     * 確認 redis 中 redisKey 對應的 hashSet，有無 value 存在
     * @param redisKey redis 存資料的鍵
     * @param value 要檢查的元素
     * @return true = 該 value 存在 || false = 該 value 不存在
     */
    public Boolean contains(String redisKey, String value) {
        return ops.isMember(redisKey, value);
    }
}
