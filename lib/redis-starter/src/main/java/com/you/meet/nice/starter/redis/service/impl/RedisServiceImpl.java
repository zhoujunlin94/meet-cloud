package com.you.meet.nice.starter.redis.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.you.meet.nice.lib.common.util.JsonUtil;
import com.you.meet.nice.starter.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * spring redis实现
 *
 * @author zhoujl
 */
@Slf4j
public class RedisServiceImpl implements RedisService {

    private RedisTemplate redisTemplate;

    public RedisServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    @Override
    public <T> T executeLua(String lua, List<String> keys, List<String> args, Class<T> resultType) {
        DefaultRedisScript<T> defaultRedisScript = new DefaultRedisScript<>(lua, resultType);
        return (T) redisTemplate.execute(defaultRedisScript, keys, args.toArray());
    }

    @Override
    public void remove(final String... keys) {
        redisTemplate.delete(CollUtil.newArrayList(keys));
    }

    @Override
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public void removeByVague(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (CollUtil.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    public void removeHashValue(String key, Object... hashKey) {
        if (exists(key)) {
            redisTemplate.boundHashOps(key).delete(hashKey);
        }
    }

    @Override
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public <T> T get(final String key, Class<T> cls) {
        try {
            String value = (String) redisTemplate.boundValueOps(key).get();
            return JsonUtil.parseStr2Obj(value, cls);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <T> T get(final String key, TypeReference<T> cls) {
        try {
            String value = (String) redisTemplate.boundValueOps(key).get();
            return JsonUtil.parseStr2Collection(value, cls);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getString(final String key) {
        try {
            return (String) redisTemplate.boundValueOps(key).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean setString(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.boundValueOps(key).set(value);
            result = true;
        } catch (Exception e) {
            log.error("出错啦!", e);
        }
        return result;
    }

    @Override
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            redisTemplate.boundValueOps(key).set(JsonUtil.parseObj2Str(value));
            result = true;
        } catch (Exception e) {
            log.error("出错啦!", e);
        }
        return result;
    }

    @Override
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            redisTemplate.boundValueOps(key).set(JsonUtil.parseObj2Str(value), expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            log.error("出错啦!", e);
        }
        return result;
    }

    @Override
    public Long remainTimeToLive(String key) {
        if (exists(key)) {
            return redisTemplate.boundValueOps(key).getExpire();
        } else {
            return -1L;
        }
    }

    @Override
    public Boolean expire(String key, Long expireTime, TimeUnit timeUnit) {
        return redisTemplate.expire(key, expireTime, timeUnit);
    }

    @Override
    public void expireAt(String key, Date date) {
        byte[] rawKey = rawKey(key);
        redisTemplate.execute((RedisCallback<Boolean>) connection ->
                connection.expireAt(rawKey, date.getTime() / 1000), true);
    }

    @Override
    public boolean setNx(String key, Object value) {
        boolean result = false;
        try {
            redisTemplate.boundValueOps(key).setIfAbsent(JsonUtil.parseObj2Str(value));
            result = true;
        } catch (Exception e) {
            log.error("出错啦!", e);
        }
        return result;
    }

    @Override
    public Long incr(String key) {
        return (Long) redisTemplate.execute((RedisCallback<Long>) redisConnection ->
                redisConnection.incr(key.getBytes())
        );
    }

    @Override
    public Long incrBy(String key, long value) {
        return (Long) redisTemplate.execute((RedisCallback<Long>) redisConnection -> redisConnection.incrBy(key.getBytes(), value));
    }

    @Override
    public Long decr(String key) {
        return (Long) redisTemplate.execute((RedisCallback<Long>) redisConnection -> redisConnection.decr(key.getBytes()));
    }

    @Override
    public Long decrBy(String key, long value) {
        return (Long) redisTemplate.execute((RedisCallback<Long>) redisConnection -> redisConnection.decrBy(key.getBytes(), value));
    }

    @Override
    public boolean setBitMap(String key, long offset, boolean flag) {
        return redisTemplate.opsForValue().setBit(key, offset, flag);
    }

    @Override
    public boolean getBitMap(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    @Override
    public long bitCount(String key) {
        return (long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
    }

    @Override
    public List<Long> bitField(String key, int limit, long offset) {
        return (List<Long>) redisTemplate.execute((RedisCallback<List<Long>>) con -> con.bitField(key.getBytes(),
                BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(limit)).valueAt(offset)));
    }

    @Override
    public Map<Integer, Boolean> bitData(List<Long> bitFields, int limit, long offset) {
        Map<Integer, Boolean> bitMap = Maps.newTreeMap();
        if (CollUtil.isNotEmpty(bitFields)) {
            long value = bitFields.get(0) == null ? 0 : bitFields.get(0);
            for (int i = limit; i > offset; i--) {
                bitMap.put(i, value >> 1 << 1 != value);
                value >>= 1;
            }
        }
        return bitMap;
    }

    @Override
    public int maxContinueCount(Map<Integer, Boolean> bitMap) {
        if (CollUtil.isNotEmpty(bitMap)) {
            List<Boolean> valueList = new ArrayList<>(bitMap.values());
            int max = 0;
            int tempMax = 0;
            for (Boolean value : valueList) {
                if (value) {
                    tempMax++;
                    max = Math.max(tempMax, max);
                } else {
                    tempMax = 0;
                }
            }
            return max;
        }
        return 0;
    }

    @Override
    public int latestContinueCount(Map<Integer, Boolean> bitMap, int now) {
        if (CollUtil.isNotEmpty(bitMap)) {
            Assert.isTrue(now >= 1, "当前角标要大于1");
            List<Boolean> valueList = new ArrayList<>(bitMap.values());
            if (now > valueList.size()) {
                return 0;
            }
            if (!valueList.get(now - 1)) {
                return 0;
            }
            int max = 0;
            for (int i = now; i > 0; i--) {
                if (!valueList.get(i - 1)) {
                    break;
                }
                max++;
            }
            return max;
        }
        return 0;
    }

    @Override
    public Map getMap(String key) {
        return redisTemplate.boundHashOps(key).entries();
    }

    @Override
    public Long hashSize(String key) {
        return redisTemplate.boundHashOps(key).size();
    }

    @Override
    public boolean addHashSet(String key, Object hashKey, Object value) {
        boolean result = false;
        try {
            redisTemplate.boundHashOps(key).put(hashKey, value);
            result = true;
        } catch (Exception e) {
            log.error("出错啦!", e);
        }
        return result;
    }

    @Override
    public boolean batchAddHashSet(String key, Map<String, String> hashKeyAndValue) {
        try {
            redisTemplate.opsForHash().putAll(key, hashKeyAndValue);
            return true;
        } catch (Exception e) {
            log.error("批量插入hash出错", e);
            return false;
        }
    }

    @Override
    public Object getHashSet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    @Override
    public Long hincrBy(String key, Object hashKey, Long value) {
        return redisTemplate.boundHashOps(key).increment(hashKey, value);
    }

    @Override
    public Long delHashByKey(String key, Object hashKey) {
        return redisTemplate.boundHashOps(key).delete(hashKey);
    }

    @Override
    public boolean addSet(String key, String... value) {
        boolean result = false;
        try {
            SetOperations<String, String> set = redisTemplate.opsForSet();
            set.add(key, value);
            result = true;
        } catch (Exception e) {
            log.error("出错啦!", e);
        }
        return result;
    }

    @Override
    public Set<String> getSet(String key) {
        SetOperations<String, String> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    @Override
    public Long getSetSize(String key) {
        SetOperations<String, String> set = redisTemplate.opsForSet();
        return set.size(key);
    }

    @Override
    public String getOneSet(String key) {
        SetOperations<String, String> set = redisTemplate.opsForSet();
        return set.pop(key);
    }

    @Override
    public boolean zadd(Object key, Object obj, double v1) {
        return redisTemplate.opsForZSet().add(key, obj, v1);
    }

    @Override
    public Set<String> rangeByScore(Object key, double v, double v1, long l, long l1) {
        return redisTemplate.opsForZSet().rangeByScore(key, v, v1, l, l1);
    }

    @Override
    public long removeZSet(Object key, Object... objects) {
        return redisTemplate.opsForZSet().remove(key, objects);
    }

    @Override
    public Object lpush(String key, String... values) {
        return redisTemplate.execute((RedisCallback) connection -> {
            StringRedisConnection conn = (StringRedisConnection) connection;
            conn.lPush(key, values);
            return null;
        });
    }

    @Override
    public Long lpushAll(String key, Collection<String> stringCollection, Date expireDate) {
        Long result = 0L;
        if (CollUtil.isNotEmpty(stringCollection)) {
            result = redisTemplate.opsForList().leftPushAll(key, stringCollection);
            expireAt(key, expireDate);
        }
        return result;
    }

    @Override
    public String lpopOne(String key) {
        return (String) redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public String rpopOne(String key) {
        return (String) redisTemplate.opsForList().rightPop(key);
    }

    @Override
    public Long ttl(String key) {
        return (Long) redisTemplate.execute((RedisCallback) connection -> {
            StringRedisConnection conn = (StringRedisConnection) connection;
            return conn.ttl(key, TimeUnit.SECONDS);
        });
    }

    @Override
    public void publishMsg(String channel, Object msg) {
        redisTemplate.convertAndSend(channel, msg);
    }

    //================================私有方法======================================

    private byte[] rawKey(Object key) {
        if (keySerializer() == null && key instanceof byte[]) {
            return (byte[]) key;
        }
        return keySerializer().serialize(key);
    }

    private RedisSerializer keySerializer() {
        return redisTemplate.getKeySerializer();
    }
}
