package com.you.meet.cloud.tk_mybatis.handler;

import com.you.meet.cloud.common.util.ReflectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author zhoujunlin
 * @date 2023年07月09日 17:02
 * @desc
 */
public class TKHandler<M extends Mapper<T>, T> {

    @Autowired
    protected M baseMapper;

    protected Class<T> entityClass = currentModelClass();

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectUtils.getSuperClassGenericType(this.getClass(), TKHandler.class, 1);
    }

    // =============================CRUD START===================================

    public int insertSelective(T model) {
        return this.baseMapper.insertSelective(model);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.baseMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(T model) {
        return this.baseMapper.updateByPrimaryKeySelective(model);
    }

    public T selectByPrimaryKey(Object key) {
        return this.baseMapper.selectByPrimaryKey(key);
    }

    public List<T> selectAll() {
        return this.baseMapper.selectAll();
    }

    // =============================CRUD END===================================
}
