package com.vector.service.impl;

import com.vector.domain.Emp;
import com.vector.mapper.EmpMapper;
import com.vector.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName：
 * Description：
 *
 * @author：yuanjie
 * @date：2022/6/14 10:04
 */
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    /**
     * 注册
     * @param emp
     * @return
     */
    @Override
    public int addEmp(Emp emp) {
        try {
            Emp empByUsername = empMapper.findEmpByUsername(emp.getUsername());
            if (empByUsername == null){
                //注册
                int i = empMapper.addEmp(emp);
                return i;
            }else {
                //用户名已被占用
                return -1;
            }
        }catch (Exception e){
            //注册失败
            return -2;
        }
    }

    /**
     * 登录
     * @param emp
     * @return
     */
    @Override
    public int login(Emp emp) {
        try {
            Emp emp1 = empMapper.findEmpByUsername(emp.getUsername());
            if (emp1.getPasswd().equals(emp.getPasswd())){
                //密码正确
                return emp1.getEid();
            }
            //用户密码输入错误
            return -1;
        }catch (Exception e){
            //没有此用户
            return -2;
        }
    }
}
