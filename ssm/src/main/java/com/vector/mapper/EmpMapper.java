package com.vector.mapper;

import com.vector.domain.Emp;
import org.springframework.stereotype.Repository;

/**
 * ClassName：
 * Description：
 *
 * @author：yuanjie
 * @date：2022/6/14 9:21
 */
@Repository
public interface EmpMapper {
    /**
     * 注册（添加员工）
     */
    int addEmp(Emp emp);

    /**
     * 登录（通过用户名查找员工）
     */
    Emp findEmpByUsername(String uersname);
}
