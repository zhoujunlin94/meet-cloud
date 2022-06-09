package com.you.meet.nice.db.base.service;

import com.you.meet.nice.db.base.model.CacheCfg;
import com.you.meet.nice.starter.rabbit.service.EasyCacheService;

import java.util.List;

/**
 * @author zhoujunlin
 * @date 2021/12/26
 * @desc
 **/
public interface CacheCfgService extends EasyCacheService {

    List<CacheCfg> listPage(String key);

    String findOne(String key);

    String findOneFromCache(String key);

    List<CacheCfg> findByKeyList(List<String> keyList);

    boolean update(CacheCfg cacheCfg);

    boolean save(CacheCfg cacheCfg);

    boolean delete(String cfgKey);
}
