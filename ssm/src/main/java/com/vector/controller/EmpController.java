package com.vector.controller;

import com.vector.domain.Emp;
import com.vector.service.EmpService;
import com.vector.util.MD5Util;
import com.vector.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName：
 * Description：
 *
 * @author：yuanjie
 * @date：2022/6/14 10:50
 */
@RestController
@RequestMapping("/emp")
public class EmpController {
    @Autowired
    private EmpService empService;

    /**
     * 注册
     */
    @RequestMapping("/register")
    public ResponseEntity<Integer> reg(Emp emp){
        emp.setPasswd(MD5Util.toMd5(emp.getPasswd()));
        int i = empService.addEmp(emp);
        ResponseEntity<Integer> responseEntity = new ResponseEntity<>();
        if (i == -1){
            responseEntity.setCode(200);
            responseEntity.setMessage("用户名已存在！");
            responseEntity.setData(i);
            return responseEntity;
        }
        if (i == -2){
            responseEntity.setCode(500);
            responseEntity.setMessage("添加失败！");
            responseEntity.setData(i);
            return responseEntity;
        }
        responseEntity.setCode(200);
        responseEntity.setMessage("添加成功！");
        responseEntity.setData(i);
        return responseEntity;
    }
    /**
     * 登录
     */
    @RequestMapping("/login")
    public ResponseEntity<Integer> login(Emp emp){
        emp.setPasswd(MD5Util.toMd5(emp.getPasswd()));
        int i = empService.login(emp);
        ResponseEntity<Integer> responseEntity = new ResponseEntity<>();
        if (i == -1){
            responseEntity.setCode(200);
            responseEntity.setMessage("用户密码输入错误！");
            responseEntity.setData(i);
            return responseEntity;
        }
        if (i == -2){
            responseEntity.setCode(500);
            responseEntity.setMessage("没有此用户！");
            responseEntity.setData(i);
            return responseEntity;
        }
        responseEntity.setCode(200);
        responseEntity.setMessage("登录成功！");
        responseEntity.setData(i);
        return responseEntity;
    }
}

