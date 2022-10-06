package com.wanda.kyc.utils.redis;

import com.wanda.kyc.utils.redis.base.RedisBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;


@Service
public class RedisZSet extends RedisBase {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static ZSetOperations<String, String> ops;

    // 創建bean時先將ops設置好
    @PostConstruct
    private void initOps () {
        ops = stringRedisTemplate.opsForZSet();
    }

    /**
     * 取得zSet的長度
     * @param key zSet的key
     * @return zSet的長度
     */
    public Long sizeOf(String key) {
        return ops.size(key);
    }

    /**
     * 新增元素到zSet
     * @param key   zSet的key
     * @param value 欲放入的值
     * @param score 用來排序的分數
     * @return 是否成功新增
     */
    public Boolean add(String key, String value, double score) {
        return ops.add(key, value, score);
    }

    /**
     * 增加指定元素的分數 (沒有該元素則新增)
     * @param key   zSet的key
     * @param value 指定的元素
     * @param score 增加的分數
     * @return 增加後的分數
     */
    public Double addScore(String key, String value, double score) {
        return ops.incrementScore(key, value, score);
    }

    /**
     * 取得指定元素的分數
     * @param key   zSet的key
     * @param value 指定的元素
     * @return
     */
    public Double getScore(String key, String value) {
        return ops.score(key, value);
    }

    /**
     * 取得指定元素的排名 (初位為0)
     * @param key          zSet的key
     * @param value        指定的元素
     * @param isBigToSmall 是否由大到小排序
     * @return 排名 (沒有該元素則回傳null)
     */
    public Long getRank(String key, String value, boolean isBigToSmall) {
        return isBigToSmall ? ops.reverseRank(key, value) : ops.rank(key, value);
    }

    /**
     * 取得zSet所有的元素
     * @param key          zSet的key
     * @param isBigToSmall 是否由大到小排序
     * @return 所有元素的Set
     */
    public Set<String> getAll(String key, boolean isBigToSmall) {
        return isBigToSmall ? ops.reverseRange(key, 0, -1) : ops.range(key, 0, -1);
    }

    /**
     * 取得zSet所有的元素及分數
     * @param key          zSet的key
     * @param isBigToSmall 是否由大到小排序
     * @return 所有元素的TypedTuple的Set
     */
    public Set<ZSetOperations.TypedTuple<String>> getAllWithScores(String key, boolean isBigToSmall) {
        return isBigToSmall ? ops.reverseRangeWithScores(key, 0, -1) : ops.rangeWithScores(key, 0, -1);
    }

    /**
     * 取得zSet指定範圍內的元素
     * @param key          zSet的key
     * @param startIndex   起始元素的索引
     * @param endIndex     結束元素的索引(包含該元素)
     * @param isBigToSmall 是否由大到小排序
     * @return 範圍內的元素的Set
     */
    public Set<String> getRange(String key, long startIndex, long endIndex, boolean isBigToSmall) {
        return isBigToSmall ? ops.reverseRange(key, startIndex, endIndex) : ops.range(key, startIndex, endIndex);
    }

    /**
     * 取得zSet指定範圍內的元素及分數
     * @param key          zSet的key
     * @param startIndex   起始元素的索引
     * @param endIndex     結束元素的索引(包含該元素)
     * @param isBigToSmall 是否由大到小排序
     * @return 範圍內的元素的TypedTuple的Set
     */
    public Set<ZSetOperations.TypedTuple<String>> getRangeWithScores(String key, long startIndex, long endIndex, boolean isBigToSmall) {
        return isBigToSmall ? ops.reverseRangeWithScores(key, startIndex, endIndex) : ops.rangeWithScores(key, startIndex, endIndex);
    }

    /**
     * 取得分數範圍內的元素
     * @param key          zSet的key
     * @param score1       分數範圍邊界
     * @param score2       分數範圍邊界
     * @param isBigToSmall 是否由大到小排序
     * @return 範圍內的元素的Set
     */
    public Set<String> getRangeByScore(String key, double score1, double score2, boolean isBigToSmall) {
        return isBigToSmall ? ops.reverseRangeByScore(key, score1, score2) : ops.rangeByScore(key, score1, score2);
    }

    /**
     * 取得分數範圍內的元素及分數
     * @param key          zSet的key
     * @param score1       分數範圍邊界
     * @param score2       分數範圍邊界
     * @param isBigToSmall 是否由大到小排序
     * @return 範圍內的元素的TypedTuple的Set
     */
    public Set<ZSetOperations.TypedTuple<String>> getRangeByScoreWithScores(String key, double score1, double score2, boolean isBigToSmall) {
        return isBigToSmall ? ops.reverseRangeByScoreWithScores(key, score1, score2) : ops.rangeByScoreWithScores(key, score1, score2);
    }

    /**
     * 移除指定的元素
     * @param key   zSet的keyx
     * @param value 指定的元素
     * @return 移除的個數
     */
    public Long remove(String key, String value) {
        return ops.remove(key, value);
    }

}
