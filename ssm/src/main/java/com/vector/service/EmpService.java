package com.vector.service;

import com.vector.domain.Emp;

/**
 * ClassName：
 * Description：
 *
 * @author：yuanjie
 * @date：2022/6/14 9:58
 */
public interface EmpService {
    /**
     * 添加员工(注册)
     */
    int addEmp(Emp emp);

    /**
     * 通过用户名查找员工（登录）
     */
    int login(Emp emp);
}
