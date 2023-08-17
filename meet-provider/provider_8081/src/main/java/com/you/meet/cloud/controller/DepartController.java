package com.you.meet.cloud.controller;

import com.you.meet.cloud.repository.db.test.handler.DepartHandler;
import com.you.meet.cloud.repository.db.test.model.Depart;
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

/**
 * @author zhoujunlin
 * @date 2023年08月16日 22:37
 * @desc
 */
@RestController
@RequestMapping("/depart")
public class DepartController {

    @Resource
    private DepartHandler departHandler;

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
    public Depart getHandle(@PathVariable("id") int id) {
        return departHandler.selectByPrimaryKey(id);
    }

    @GetMapping("/list")
    public List<Depart> list() {
        return departHandler.selectAll();
    }
}
