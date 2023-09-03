package com.you.meet.cloud.provider8081.controller;

import com.you.meet.cloud.provider8081.repository.db.test.handler.DepartHandler;
import com.you.meet.cloud.provider8081.repository.db.test.model.Depart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author zhoujunlin
 * @date 2023年08月16日 22:37
 * @desc @RefreshScope 动态刷新配置
 */
@RefreshScope
@RestController
@RequestMapping("/depart")
public class DepartController {

    @Resource
    private DepartHandler departHandler;

    /**
     * 加载nacos配置中的数据
     */
    @Value("${depart.name}")
    private String departName;

    @PostMapping("/save")
    public boolean save(@RequestBody Depart depart) {
        return departHandler.insertSelective(depart) > 0;
    }

    @DeleteMapping("/del/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return departHandler.deleteByPrimaryKey(id) > 0;
    }

    @PutMapping("/update")
    public boolean update(@RequestBody Depart depart) {
        return departHandler.updateByPrimaryKeySelective(depart) > 0;
    }

    @GetMapping("/get/{id}")
    public Depart get(@PathVariable("id") int id) {
        Depart depart = departHandler.selectByPrimaryKey(id);
        if (Objects.isNull(depart)) {
            depart = Depart.builder().id(-1).name(departName).build();
        }
        return depart;
    }

    @GetMapping("/list")
    public List<Depart> list() {
        return departHandler.selectAll();
    }
}
