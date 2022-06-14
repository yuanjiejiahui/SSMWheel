import com.vector.domain.Emp;
import com.vector.mapper.EmpMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName empController
 * @Description TODO
 * @Author YuanJie
 * @Date 2022/6/14 20:54
 */
public class empController {
    @Autowired
    EmpMapper empMapper;
    @Test
    public void getEmpMapper() {
        Emp emp = empMapper.findEmpByUsername("张三");
        System.out.println(emp);
    }
}
