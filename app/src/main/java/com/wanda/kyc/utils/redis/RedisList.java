package com.wanda.kyc.utils.redis;

import com.wanda.kyc.utils.redis.base.RedisBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;


@Service
public class RedisList extends RedisBase {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static ListOperations<String, String> ops;

    // 創建bean時先將ops設置好
    @PostConstruct
    private void initOps() {
        ops = stringRedisTemplate.opsForList();
    }

    /**
     * 從 redis 指定鍵對應到的 list 右邊放進元素
     * @param redisKey redis 存資料的鍵
     * @param value 要放進 list 的元素
     */
    public void add(String redisKey, String value) {
        ops.rightPush(redisKey, value);
    }

    /**
     * 從 redis 指定鍵對應到的 list 右邊放進所有元素
     * @param redisKey redis 存資料的鍵
     * @param value 要放進 list 的所有元素
     */
    public void addAll(String redisKey, Collection<String> value) {
        ops.rightPushAll(redisKey, value);
    }

    /**
     * 由左方塞入元素
     * @param redisKey
     * @param value
     * @return
     */
    public Long addLeft(String redisKey, String value) {
        return ops.leftPush(redisKey, value);
    }

    /**
     * 從 redis 指定鍵對應到的 list 左邊放進所有元素
     * @param redisKey redis 存資料的鍵
     * @param value    要放進 list 的所有元素
     */
    public void addAllLeft(String redisKey, Collection<String> value) {
        ops.leftPushAll(redisKey, value);
    }

    /**
     * 取得 redis 指定鍵對應的 list，其所有 String 元素
     * @param redisKey redis 存資料的鍵
     * @return list，其所有元素
     */
    public List<String> getAll(String redisKey) {
        return ops.range(redisKey, 0, -1);
    }

    /**
     * 取得 redis 指定鍵對應的 list，其範圍內的元素
     * @param redisKey   redis 存資料的鍵
     * @param startIndex 起始元素的索引
     * @param endIndex   結束元素的索引(包含該元素)
     * @return list，範圍內的元素
     */
    public List<String> getRange(String redisKey, long startIndex, long endIndex) {
        return ops.range(redisKey, startIndex, endIndex);
    }

    /**
     * 取得 redis 指定鍵對應的 list，其位於 index 位置的元素
     * @param redisKey redis 存資料的鍵
     * @param index 位置座標
     * @return list 位於 index 位置的元素
     */
    public String get(String redisKey, long index) {
        return ops.index(redisKey, index);
    }

    /**
     * 彈出 redis 指定鍵對應的 list，其最右邊元素
     * @param redisKey
     * @return
     */
    public String pop(String redisKey) {
        return ops.rightPop(redisKey);
    }

    /**
     * 刪除 redis 指定鍵對應的 list，其由左至右等於 value 的 num 數量的元素 <br>
     * 注意 ! <br>
     * redis 沒辦法移除指定座標的元素，最好的解決辦法， <br>
     * 是在元素後面，加個可作為 unique index 的字串
     * @param redisKey redis 存資料的鍵
     * @num 欲移除的數量
     * @param value 欲刪除的元素
     * @return 移除了多少個元素
     */
    public Long removeFromHead(String redisKey, long num, String value) {
        if (num <= 0) {
            throw new IllegalArgumentException("removeFromHead : num 不可 <= 0");
        }
        // num > 0 時，移除左至右等於 value 的 num 數量的元素
        // num < 0 時，移除右至左等於 value 的 num 數量的元素
        // num == 0 時，移除所有等於 value 的元素
        return ops.remove(redisKey, num, value);
    }

    /**
     * 刪除 redis 指定鍵對應的 list，其所有等於 value 的元素
     * @param redisKey redis 存資料的鍵
     * @param value 欲刪除的元素
     * @return 移除了多少個元素
     */
    public Long removeAllLike(String redisKey, String value) {
        // 第二個參數 == 0 時，移除所有等於 value 的元素
        return ops.remove(redisKey, 0, value);
    }

    /**
     * 返回 redis 指定鍵對應的 list，其內容物量
     * @param redisKey redis 存資料的鍵
     * @return list 的內容物量
     */
    public Long sizeOf(String redisKey) {
        return ops.size(redisKey);
    }

    /**
     * 彈出 redis 指定鍵對應的 list，其最左邊元素
     * @param redisKey
     * @return
     */
    public String leftPop(String redisKey) {
        return ops.leftPop(redisKey);
    }

    /**
     * 修剪 redis 指定鍵對應的 list，使其只包含指定範圍的元素
     * 例：trim("list",1,3) -> 只包含第1,2,3元素；trim("list",1,-1) -> 只剪裁第0元素
     * @param redisKey
     * @param startIndex 包含範圍起始索引
     * @param endIndex   包含範圍結束索引
     */
    public void trim(String redisKey, long startIndex, long endIndex) {
        ops.trim(redisKey, startIndex, endIndex);
    }
}
