package com.you.meet.cloud.starter.redis.service;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存帮助类
 *
 * @author zhoujl
 */
public interface RedisService {

    /**
     * 执行Lua脚本  返回字符串类型
     *
     * @param lua  lua脚本内容
     * @param keys redis key
     * @param args 参数
     * @return
     */
    <T> T executeLua(String lua, List<String> keys, List<String> args, Class<T> resultType);

    /**
     * str  设值
     *
     * @param key
     * @param value
     * @return
     */
    boolean setString(final String key, String value);

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    void remove(final String... keys);

    /**
     * 删除对应的value
     *
     * @param key
     */
    void remove(final String key);

    /**
     * 根据正则模糊搜索key 后删除
     *
     * @param pattern 正则表达式
     */
    void removeByVague(final String pattern);

    /**
     * 删除hash中的某些键
     *
     * @param key
     * @param hashKey
     */
    void removeHashValue(String key, Object... hashKey);

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    boolean exists(final String key);

    /**
     * 读取缓存
     *
     * @param key
     * @param cls
     * @return
     */
    <T> T get(final String key, Class<T> cls);

    /**
     * 读取缓存
     *
     * @param key
     * @param cls
     * @param <T>
     * @return
     */
    <T> T get(final String key, TypeReference<T> cls);

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    String getString(final String key);

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    boolean set(final String key, Object value);

    /**
     * 在key不存在的情况下写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    boolean setNx(final String key, Object value);

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @param expireTime 单位秒
     * @return
     */
    boolean set(final String key, Object value, Long expireTime);

    /**
     * 根据key获取存活时间
     *
     * @param key
     * @return
     */
    Long remainTimeToLive(final String key);

    /**
     * 设置一个key的存活时间
     *
     * @param key
     * @param expireTime
     * @param timeUnit
     * @return
     */
    Boolean expire(final String key, Long expireTime, TimeUnit timeUnit);

    /**
     * 在指定日期过期
     *
     * @param key  redis key
     * @param date 过期时间点
     * @return
     */
    void expireAt(final String key, Date date);

    /**
     * 根据指定key获取自增的id
     *
     * @param key
     * @return
     */
    Long incr(final String key);

    /**
     * 自增
     *
     * @param key
     * @param value
     * @return
     */
    Long incrBy(final String key, long value);

    /**
     * 自减
     *
     * @param key
     * @return
     */
    Long decr(final String key);

    /**
     * 自减
     *
     * @param key
     * @param value
     * @return
     */
    Long decrBy(final String key, long value);

    /**
     * setBitMap
     *
     * @param key    rediskey
     * @param offset 偏移量
     * @param flag   true/false
     * @return
     */
    boolean setBitMap(String key, long offset, boolean flag);

    /**
     * getBitMap
     *
     * @param key    rediskey
     * @param offset 偏移量
     * @return
     */
    boolean getBitMap(String key, long offset);

    /**
     * bitCount  统计true个数
     *
     * @param key rediskey
     * @return
     */
    long bitCount(String key);

    /**
     * bitField
     *
     * @param key    rediskey
     * @param limit  最大偏移量
     * @param offset 起始偏移量
     * @return
     */
    List<Long> bitField(String key, int limit, long offset);

    /**
     * 获取所有明细
     *
     * @param bitFields
     * @param limit
     * @param offset
     * @return
     */
    Map<Integer, Boolean> bitData(List<Long> bitFields, int limit, long offset);

    /**
     * 全部bitmap中最大连续true的个数（活动期间最大连续打卡天数）
     *
     * @param bitMap
     * @return
     */
    int maxContinueCount(Map<Integer, Boolean> bitMap);

    /**
     * 指定角标now附近的最大连续true个数（最近连续打卡天数）
     *
     * @param bitMap
     * @param now
     * @return
     */
    int latestContinueCount(Map<Integer, Boolean> bitMap, int now);

    /**
     * hash  -> map
     *
     * @param key
     * @return
     */
    Map getMap(String key);

    /**
     * 获取hash size
     *
     * @param key
     * @return
     */
    Long hashSize(String key);

    /**
     * 哈希添加数据
     *
     * @param key     key 值
     * @param hashKey 哈希key
     * @param value
     */
    boolean addHashSet(String key, Object hashKey, Object value);

    /**
     * 批量插入hash表
     *
     * @param key             key
     * @param hashKeyAndValue hash表每一条记录的key and value
     * @return
     */
    boolean batchAddHashSet(String key, Map<String, String> hashKeyAndValue);

    /**
     * 根据hashKey获取数据
     *
     * @param key     key 值
     * @param hashKey 哈希key
     * @return
     */
    Object getHashSet(String key, Object hashKey);

    /**
     * 根据指定hashKey值自增value(1,2,3,4...);
     *
     * @param key     key 值
     * @param hashKey 哈希key
     * @param value
     * @return
     */
    Long hincrBy(String key, Object hashKey, Long value);

    /**
     * 根据key与hashkey删除hash中的某个值
     *
     * @param key     key值
     * @param hashKey 哈希key
     * @return 删除个数
     */
    Long delHashByKey(String key, Object hashKey);

    /**
     * 集合添加数据
     *
     * @param key
     * @param value
     * @return
     */
    boolean addSet(String key, String... value);

    /**
     * 集合获取数据
     *
     * @param key
     * @return
     */
    Set<String> getSet(String key);

    /**
     * 获取集合的大小
     *
     * @param key
     * @return
     */
    Long getSetSize(String key);

    /**
     * 根据key值随机获取集合中一个值，得到该值之后并删除该值
     *
     * @param key
     * @return
     */
    String getOneSet(String key);

    /**
     * zset中添加数据
     *
     * @param key
     * @param obj
     * @param v1
     * @return
     */
    boolean zadd(Object key, Object obj, double v1);

    /**
     * 从zset中获取
     *
     * @param key
     * @param v   最小分值
     * @param v1  最大分值
     * @param l   角标1
     * @param l1  角标2
     * @return
     */
    Set<String> rangeByScore(Object key, double v, double v1, long l, long l1);

    /**
     * 删除zset中指定数据
     *
     * @param key
     * @param objects
     * @return
     */
    long removeZSet(Object key, Object... objects);

    /**
     * 将一个或多个值插入到列表头部
     *
     * @param key
     * @param values
     * @return
     */
    Object lpush(String key, String... values);

    /**
     * 将string集合插入list中
     *
     * @param key              key
     * @param stringCollection string集合值
     * @param expireDate       过期时间
     * @return
     */
    Long lpushAll(String key, Collection<String> stringCollection, Date expireDate);

    /**
     * list中获取一个
     *
     * @param key
     * @return
     */
    String lpopOne(String key);

    /**
     * 右边pop一个
     *
     * @param key
     * @return
     */
    String rpopOne(String key);

    /**
     * 获取剩余的有效时间  返回单位秒
     *
     * @param key
     * @return
     */
    Long ttl(String key);

    /**
     * redis发布消息到指定通道
     *
     * @param channel
     * @param msg
     */
    void publishMsg(String channel, Object msg);

}
