package com.wanda.kyc.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanda.kyc.exception.SystemRuntimeException;
import com.wanda.kyc.utils.AmtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
@Service
public class RedisService {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private StringRedisTemplate redisTemplate;

	// opsForValue String => 字串
	// opsForList Lsit => 按照順序排序，可添加元素 從左邊(頭)或右邊(尾)加入 修改中間值效能較差
	// opsForHash hash => 無順序 類似map
	// opsForSet Set => 無順序
	// opsForZSet zSet => 有順序

	/**
	 * key是否存在
	 *
	 * @param key
	 * @return
	 */
	public boolean exist(String key) {
		boolean result = false;
		try {
			result = redisTemplate.hasKey(key);
		} catch (Exception e) {
			log.error("判斷redis是否有隊定的key失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	/**
	 *
	 * @param key
	 * @param expire
	 * @param unit
	 */
	public void expire(String key, long expire, TimeUnit unit) {
		try {
			redisTemplate.expire(key, expire, unit);
		} catch (Exception e) {
			log.error("定時移除key失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 刪除key
	 *
	 * @param key
	 */
	public void delKey(String key) {
		try {
			if (exist(key)) {
				redisTemplate.delete(key);
			}
		} catch (Exception e) {
			log.error("刪除redis key失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 刪除多個key
	 *
	 * @param keys
	 */
	public void delKeys(Collection<String> keys) {
		for (String key : keys) {
			delKey(key);
		}
	}

	/**
	 * 查出所有符合條件的keys
	 *
	 * @param pattern
	 * @return
	 */
	public Set<String> getKeys(String pattern) {
		Set<String> keys = new HashSet<>();
		this.scan(pattern, item -> {
			String key = new String(item, StandardCharsets.UTF_8);
			keys.add(key);
		});
		return keys;
	}

	public void scan(String pattern, Consumer<byte[]> consumer) {
		redisTemplate.execute((RedisConnection connection) -> {
			try (Cursor<byte[]> cursor = connection
					.scan(ScanOptions.scanOptions().count(Long.MAX_VALUE).match(pattern).build())) {
				cursor.forEachRemaining(consumer);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
	}

	/**
	 * string 放入資料
	 *
	 * @param key
	 * @param value
	 */
	public void stringSet(String key, String value) {
		try {
			redisTemplate.opsForValue().set(key, value);
		} catch (Exception e) {
			log.error("寫入redis失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * string 放入資料 時間到移除
	 *
	 * @param key
	 * @param value
	 * @param expire 單位毫秒
	 */
	public void stringSet(String key, String value, long expire) {
		try {
			redisTemplate.opsForValue().set(key, value);
			redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			log.error("寫入redis失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * string 取得資料
	 *
	 * @param key
	 * @return
	 */
	public String stringGet(String key) {
		String result = null;
		try {
			result = redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	/**
	 * string 取得資料
	 *
	 * @param <T>
	 * @param key
	 * @param clazz 轉為dto
	 * @return
	 */
	public <T> T stringGet(String key, Class<T> clazz) {
		T dto = null;
		try {
			String result = redisTemplate.opsForValue().get(key);
			if (null == result) {
				return null;
			}
			dto = JSON.parseObject(result, clazz);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dto;
	}

	/**
	 * string 取得資料
	 *
	 * @param <T>
	 * @param key
	 * @param clazz 轉為dto
	 * @return
	 */
	public <T> List<T> stringGetList(String key, Class<T> clazz) {
		List<T> dtoList = null;
		try {
			String result = redisTemplate.opsForValue().get(key);
			if (null == result) {
				return Collections.emptyList();
			}
			dtoList = JSON.parseArray(result, clazz);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dtoList;
	}

	/**
	 * list 從最右端推入元素，返回列表的長度
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Long listPush(String key, String value) {
		Long index = null;
		try {
			index = redisTemplate.opsForList().rightPush(key, value);
		} catch (Exception e) {
			log.error("寫入redis失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return index;
	}

	/**
	 * list 從最左端推入元素，返回列表的長度
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Long listLeftPush(String key, String value) {
		Long index = null;
		try {
			index = redisTemplate.opsForList().leftPush(key, value);
		} catch (Exception e) {
			log.error("寫入redis失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return index;
	}

	/**
	 * list 從最右端推入元素，返回目前索引
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Long listPushAndReturnIndex(String key, String value) {
		Long size = listPush(key, value);
		if (size != null && size > 0) {
			size -= 1;
		}
		return size;
	}

	/**
	 * list 取出第一個元素（取出並刪除）
	 *
	 * @param key
	 * @return
	 */
	public String listPop(String key) {
		String result = null;
		try {
			result = redisTemplate.opsForList().leftPop(key);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	/**
	 * list 取得索引資料，並轉換為 dto
	 *
	 * @param key
	 * @param index
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public <T> T listGet(String key, long index, Class<T> clazz) {
		T dto = null;
		try {
			String result = redisTemplate.opsForList().index(key, index);
			dto = JSON.parseObject(result, clazz);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dto;
	}

	/**
	 * list 在對應索引位置覆蓋元素
	 *
	 * @param key
	 * @param index
	 * @param value
	 */
	public void listSet(String key, long index, String value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
		} catch (Exception e) {
			log.error("寫入redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * list 取出所有元素, 沒有key查回空list
	 *
	 * @param key
	 * @return
	 */
	public List<String> listGetAll(String key) {
		List<String> result = null;
		try {
			result = redisTemplate.opsForList().range(key, 0, -1);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	/**
	 * list 取出所有元素
	 *
	 * @param <T>
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> List<T> listGetAll(String key, Class<T> clazz) {
		List<T> resultDto = new ArrayList<>();
		try {
			List<String> result = redisTemplate.opsForList().range(key, 0, -1);
			result.stream().forEach(str -> resultDto.add(JSON.parseObject(str, clazz)));
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return resultDto;
	}

	/**
	 * 获取范围值，闭区间，start和end这两个下标的值都会返回; end为-1时，表示获取的是最后一个；
	 *
	 * 如果希望返回最后两个元素，可以传入 -2, -1
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> listRange(String key, int start, int end) {
		List<String> result = null;
		try {
			result = redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	/**
	 * 获取范围值，闭区间，start和end这两个下标的值都会返回; end为-1时，表示获取的是最后一个；
	 *
	 * 如果希望返回最后两个元素，可以传入 -2, -1
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @param clazz
	 * @return
	 */
	public <T> List<T> listRange(String key, int start, int end, Class<T> clazz) {
		List<T> resultDto = new ArrayList<>();
		try {
			List<String> result = redisTemplate.opsForList().range(key, start, end);
			result.stream().forEach(str -> resultDto.add(JSON.parseObject(str, clazz)));
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return resultDto;
	}

	/**
	 * 取得list的大小
	 *
	 * @param key
	 * @return
	 */
	public Long listSize(String key) {
		Long size;
		try {
			size = redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return size;
	}

	/**
	 * 删除list首尾，只保留 [start, end] 之间的值
	 *
	 * @param key
	 * @param start
	 * @param end
	 */
	public void listTrim(String key, Integer start, Integer end) {
		try {
			redisTemplate.opsForList().trim(key, start, end);
		} catch (Exception e) {
			log.error("寫入redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 删除list值為XXX的元素
	 *
	 * @param key
	 * @param value
	 */
	public void listDel(String key, String value) {
		try {
			redisTemplate.opsForList().remove(key, 0, value);
		} catch (Exception e) {
			log.error("寫入redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * hash 新增元素
	 *
	 * @param key
	 * @param hashKey
	 * @param hashValue
	 */
	public void hashPut(String key, String hashKey, String hashValue) {
		try {
			redisTemplate.opsForHash().put(key, hashKey, hashValue);
		} catch (Exception e) {
			log.error("寫入redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * hash 新增多個元素
	 *
	 * @param key
	 * @param hashMap
	 */
	public void hashPutAll(String key, Map<String, String> hashMap) {
		try {
			redisTemplate.opsForHash().putAll(key, hashMap);
		} catch (Exception e) {
			log.error("寫入redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * hash 新增多個元素
	 *
	 * @param key
	 * @param object dto
	 */
	@SuppressWarnings("unchecked")
	public void hashPutAll(String key, Object object) {
		try {
			Map<String, String> map = objectMapper.readValue(JSON.toJSONString(object), Map.class);
			redisTemplate.opsForHash().putAll(key, map);
		} catch (Exception e) {
			log.error("寫入redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw SystemRuntimeException.systemError();
		}
	}

	/**
	 * hash 取得元素
	 *
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public String hashGet(String key, String hashKey) {
		String result = null;
		try {
			result = (String) redisTemplate.opsForHash().get(key, hashKey);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	/**
	 * hash 取得元素
	 *
	 * @param <T>
	 * @param key
	 * @param hashKey
	 * @param clazz
	 * @return
	 */
	public <T> T hashGet(String key, String hashKey, Class<T> clazz) {
		T dto = null;
		try {
			String result = (String) redisTemplate.opsForHash().get(key, hashKey);
			dto = JSON.parseObject(result, clazz);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dto;
	}

	/**
	 * hash 取得全部元素
	 *
	 * @param key
	 * @return
	 */
	public Map<Object, Object> hashGetAll(String key) {
		Map<Object, Object> map = null;
		try {
			map = redisTemplate.opsForHash().entries(key);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return map;
	}

	/**
	 * hash 取得全部元素
	 *
	 * @param key @return @throws
	 */
	public <T> T hashGetAll(String key, Class<T> clazz) {
		T t = null;
		try {
			Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
			if (map.isEmpty()) {
				return null;
			}
			t = objectMapper.readValue(objectMapper.writeValueAsString(map), clazz);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw SystemRuntimeException.systemError();
		}
		return t;
	}

	/**
	 * hash key是否存在
	 *
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public boolean hashExists(String key, String hashKey) {
		boolean exist = false;
		try {
			exist = redisTemplate.opsForHash().hasKey(key, hashKey);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return exist;
	}

	public long hashDel(String key, String hashKey) {
		long result;
		try {
			result = redisTemplate.opsForHash().delete(key, hashKey);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	// 爲哈希表 key 中的指定字段的整數值加上增量 increment
	public Long hashIncrBy(String key, String hashKey, String increment) {
		Long index = null;
		try {
			increment = AmtUtil.abs(increment);
			index = redisTemplate.opsForHash().increment(key, hashKey, Long.valueOf(increment));
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return index;
	}

	// 爲哈希表 key 中的指定字段的整數值減少值 decrease
	public Long hashDecrBy(String key, String hashKey, String decrease) {
		Long index = null;
		try {
			decrease = AmtUtil.negative(decrease);
			index = redisTemplate.opsForHash().increment(key, hashKey, Long.valueOf(decrease));
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return index;
	}

	// set
	public void setAdd(String key, String... values) {
		try {
			redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			log.error("寫入redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	public void setRemove(String key, Object... values) {
		try {
			redisTemplate.opsForSet().remove(key, values);
		} catch (Exception e) {
			log.error("刪除redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	// 返回隨機元素
	public String setPop(String key) {
		String result = null;
		try {
			result = redisTemplate.opsForSet().pop(key);
		} catch (Exception e) {
			log.error("取出redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	// 取全部
	public Set<String> setGetAll(String key) {
		Set<String> result = null;
		try {
			result = redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			log.error("取出redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	// 是否包含某值
	public boolean setContain(String key, Object obj) {
		boolean result = false;
		try {
			result = redisTemplate.opsForSet().isMember(key, obj);
		} catch (Exception e) {
			log.error("取出redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	// 獲取Set長度
	public Long setSize(String key) {
		Long size = null;
		try {
			size = redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			log.error("讀取redis緩存失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return size;
	}

	public boolean zsetPush(String key, String value) {
		try {
			return redisTemplate.opsForZSet().add(key, value, 0.0);
		} catch (Exception e) {
			log.error("寫入redis失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	public Double zsetAddScore(String key, String value) {
		try {
			return redisTemplate.opsForZSet().incrementScore(key, value, 1);
		} catch (Exception e) {
			log.error("寫入redis失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	public long zsetDel(String key, String value) {
		try {
			return redisTemplate.opsForZSet().remove(key, value);
		} catch (Exception e) {
			log.error("寫入redis失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	public Set<String> zsetGetByScore(String key) {
		try {
			return redisTemplate.opsForZSet().range(key, 0, -1);
		} catch (Exception e) {
			log.error("寫入redis失敗！錯誤訊息為：{}", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 無key回傳true 並把key存進去, 有key回傳false
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setIfAbsent(String key, String value) {
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	public boolean setIfAbsent(String key, String value, long expire) {
		return redisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.MILLISECONDS);
	}

}
