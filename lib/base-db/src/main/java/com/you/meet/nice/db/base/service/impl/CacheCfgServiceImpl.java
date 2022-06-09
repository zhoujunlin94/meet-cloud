package com.you.meet.nice.db.base.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.you.meet.nice.db.base.mapper.CacheCfgMapper;
import com.you.meet.nice.db.base.model.CacheCfg;
import com.you.meet.nice.db.base.service.CacheCfgService;
import com.you.meet.nice.starter.rabbit.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhoujunlin
 * @date 2021/12/26
 * @desc
 **/
@Slf4j
@Service
public class CacheCfgServiceImpl implements CacheCfgService {

    @Resource
    private CacheCfgMapper cacheCfgMapper;

    private final ConcurrentHashMap<String, String> cacheCfgMap = new ConcurrentHashMap<>();

    @Override
    public List<CacheCfg> listPage(String key) {
        Example example = new Example(CacheCfg.class);
        Example.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotBlank(key)) {
            criteria.andLike("key", "%" + key + "%");
        }
        return cacheCfgMapper.selectByExample(example);
    }

    @Override
    public String findOne(String key) {
        CacheCfg cacheCfg = cacheCfgMapper.selectByPrimaryKey(key);
        return Objects.isNull(cacheCfg) ? "" : cacheCfg.getValue();
    }

    @Override
    public String findOneFromCache(String key) {
        return cacheCfgMap.getOrDefault(key, "");
    }

    @Override
    public List<CacheCfg> findByKeyList(List<String> keyList) {
        Example example = new Example(CacheCfg.class);
        example.createCriteria()
                .andIn("key", keyList);
        return cacheCfgMapper.selectByExample(example);
    }

    @Override
    public boolean update(CacheCfg cacheCfg) {
        int result = cacheCfgMapper.updateByPrimaryKeySelective(cacheCfg);
        return result == 1;
    }

    @Override
    public boolean save(CacheCfg cacheCfg) {
        int result = cacheCfgMapper.insertSelective(cacheCfg);
        return result == 1;
    }

    @Override
    public boolean delete(String cfgKey) {
        return cacheCfgMapper.deleteByPrimaryKey(cfgKey) == 1;
    }

    @Override
    public String messageKey() {
        return MqConstant.HandlerName.CACHE_HANDLER_DIRECT;
    }

    @Override
    public void initCache() {
        if (CollUtil.isEmpty(cacheCfgMap)) {
            // 初始化，缓存所有的放在map里
            List<CacheCfg> cacheCfgList = cacheCfgMapper.selectAll();
            if (CollUtil.isNotEmpty(cacheCfgList)) {
                cacheCfgList.forEach(model -> {
                    cacheCfgMap.put(model.getKey(), model.getValue());
                });
            }
        }
    }

    @Override
    public void refreshAll() {
        log.info("刷新cache_cfg表的所有数据进缓存");
        cacheCfgMap.clear();
        initCache();
    }

    @Override
    public void refreshPart(List<String> keyList) {
        Assert.notEmpty(keyList, "主键集合不能为空");
        log.info("刷新cache_cfg表的部分数据{}进缓存", keyList.toString());
        List<CacheCfg> cacheCfgList = findByKeyList(keyList);
        if (CollUtil.isNotEmpty(cacheCfgList)) {
            cacheCfgList.forEach(model -> {
                cacheCfgMap.put(model.getKey(), model.getValue());
            });
        }
    }

}
