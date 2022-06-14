## 一.spring xml配置

#### 注意:

1.applicationContext.xmlname值必须与连接池方法名相同.

可以使用DruidDataSource dataSource = new DruidDataSource(); 

dataSource .setxxxx;寻找name值.

<img src="C:\Users\78235\AppData\Roaming\Typora\typora-user-images\image-20211107153222208.png" alt="image-20211107153222208" style="zoom:50%;" />

2.![image-20211107164109144](C:\Users\78235\AppData\Roaming\Typora\typora-user-images\image-20211107164109144.png)

使用了spring，配置文件中的username不能叫username，spring会默认username是你的计算机名，解决办法，把`username`改个名字即可。
或在每个配置信息前面加上 jdbc. 即可（jdbc.username）。

```xml
<?applicationContext.xml version="1.0" encoding="UTF-8"?>
   <!-- 引入context命名空间和约束地址-->
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation=
               "http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

        <!--    加载外部的properties文件-->
        <context:property-placeholder location="classpath:druid.properties"/>
        <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
            <property name="DriverClassName" value="${jdbc.driverClassName}"/>
            <property name="Url" value="${jdbc.url}"/>
            <property name="Username" value="${jdbc.username}"/>
            <property name="Password" value="${jdbc.password}"/>
            <!-- 统计监控过滤器 -->
            <property name="filters" value="stat"/>
            <!-- 连接池设置 -->
            <property name="maxActive" value="20"/>
            <property name="initialSize" value="1"/>
            <property name="maxWait" value="60000"/>
            <property name="minIdle" value="1"/>
            <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
            <property name="timeBetweenEvictionRunsMillis" value="60000"/>
            <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
            <property name="minEvictableIdleTimeMillis" value="300000"/>
            <property name="validationQuery" value="SELECT 'x'"/>
            <property name="testWhileIdle" value="true"/>
            <property name="testOnBorrow" value="false"/>
            <property name="testOnReturn" value="false"/>

            <property name="poolPreparedStatements" value="true"/>
            <property name="maxOpenPreparedStatements" value="20"/>
        </bean>
</beans>

```

```
//druid.properties文件
jdbc.driverClassName=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true
jdbc.username=root
jdbc.password=WJHwjh20010825.
filters=stat
initialSize=2
maxActive=300
maxWait=60000
timeBetweenEvictionRunsMillis=60000
minEvictableIdleTimeMillis=300000
validationQuery=SELECT 1
testWhileIdle=true
testOnBorrow=false
testOnReturn=false
poolPreparedStatements=false
maxPoolPreparedStatementPerConnectionSize=200
```

```java
@Test
/**
 * spring代理druid配置文件连接池
 */
public void getDruidProerties() throws Exception {
    ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
    DataSource dataSource = (DataSource) app.getBean("dataSource");
    Connection conn = dataSource.getConnection();
    System.out.println(conn);
    conn.close();
}
```

## 二.spring原始注解

![image-20211107220906064](C:\Users\78235\AppData\Roaming\Typora\typora-user-images\image-20211107220906064.png)



```xml
<!--    配置组件扫描  扫描路径包及其子包-->
    <context:component-scan base-package="com.vector"/>
```

```java
//<bean id="userDao" class="com.vector.dao.impl.UserDaoImpl"/>
//@Component("userDao")
@Repository("userDao")
public class UserDaoImpl implements UserDao {
    @Override
    public void save() {
        System.out.println("save running...");
    }
}
```

```java
//<bean id="userService" class="com.vector.service.impl.UserServiceImpl">
//@Component("userService")
@Service("userService")
//@Scope("prototype") //多例
@Scope("singleton") //单例
public class UserServiceImpl implements UserService {
//    <property name="userDao" ref="userDao"/>
//    @Autowired //自动注入 按照数据类型从Spring容器中进行匹配的
//    @Qualifier("userDao") //要被注入的对象 按照id的名称从容器中进行匹配@Qualifier要结合@Autowired一起使用
    @Resource(name = "userDao") //相当于@Qualifier+@Autowired
    private UserDao userDao; //以反射调用无参构造器赋值
//    public void setUserDao(UserDao userDao){
//        this.userDao = userDao;
//    }
    @Override
    public void save() {
        userDao.save();
    }
}
```

```java
//测试
public class UserController {
    public static void main(String[] args) {
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) app.getBean("userService");
        userService.save();
    }
}
```

![image-20211107220028877](C:\Users\78235\AppData\Roaming\Typora\typora-user-images\image-20211107220028877.png)



## 三.完全版注解配

![image-20211118144846171](C:\Users\78235\AppData\Roaming\Typora\typora-user-images\image-20211118144846171.png)



```java
@Test
/**
 * spring代理druid配置文件连接池
 */
public void getDruidProerties() throws Exception {
    //ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
    ApplicationContext app = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    DataSource dataSource = (DataSource) app.getBean("dataSource");
    Connection conn = dataSource.getConnection();
    System.out.println(conn);
    conn.close();
}
```



Spring核心配置类

```java
package com.vector.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//标志该类是Spring的核心配置类
@Configuration
//<!--    配置组件扫描  扫描路径包及其子包-->
//    <context:component-scan base-package="com.vector"/>
@ComponentScan("com.vector")

//<!--    <import resource=""/>-->
@Import(DataSourceConfiguration.class)
public class SpringConfiguration {

}
```

Spring数据资源类

```java
package com.vector.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

//<!--    加载外部的properties文件-->
//<context:property-placeholder location="classpath:druid.properties"/>
@PropertySource("classpath:druid.properties")
public class DataSourceConfiguration {
    @Value("${jdbc.driverClassName}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${filters}")
    private String filters;
    @Value("${initialSize}")
    private String initialSize;
    @Value("${maxActive}")
    private String maxActive;
    @Value("${maxWait}")
    private String maxWait;
    @Value("${timeBetweenEvictionRunsMillis}")
    private String timeBetweenEvictionRunsMillis;
    @Value("${minEvictableIdleTimeMillis}")
    private String minEvictableIdleTimeMillis;
    @Value("${validationQuery}")
    private String validationQuery;
    @Value("${testWhileIdle}")
    private String testWhileIdle;
    @Value("${testOnBorrow}")
    private String testOnBorrow;
    @Value("${testOnReturn}")
    private String testOnReturn;
    @Value("${poolPreparedStatements}")
    private String poolPreparedStatements;
    @Value("${maxPoolPreparedStatementPerConnectionSize}")
    private String maxPoolPreparedStatementPerConnectionSize;
    @Bean("dataSource") // Spring会将当前方法的返回值以指定名称存储到Spring容器中
    public DataSource getDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setFilters(filters);
        dataSource.setInitialSize(Integer.parseInt(initialSize));
        dataSource.setMaxActive(Integer.parseInt(maxActive));
        dataSource.setMaxWait(Long.parseLong(maxWait));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(timeBetweenEvictionRunsMillis));
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(minEvictableIdleTimeMillis));
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(Boolean.parseBoolean(testWhileIdle));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(testOnBorrow));
        dataSource.setTestOnReturn(Boolean.parseBoolean(testOnReturn));
        dataSource.setPoolPreparedStatements(Boolean.parseBoolean(poolPreparedStatements));
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(maxPoolPreparedStatementPerConnectionSize));
        
        
        
        return dataSource;
    }
}

```

druid.properties配置文件

```apl
jdbc.driverClassName=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/spring_test?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true
jdbc.username=root
jdbc.password=WJHwjh20010825.
filters=stat
initialSize=2
maxActive=300
maxWait=60000
timeBetweenEvictionRunsMillis=60000
minEvictableIdleTimeMillis=300000
validationQuery=SELECT 1
testWhileIdle=true
testOnBorrow=false
testOnReturn=false
poolPreparedStatements=false
maxPoolPreparedStatementPerConnectionSize=200
```



spring集成web环境



![image-20211212131900214](C:\Users\78235\AppData\Roaming\Typora\typora-user-images\image-20211212131900214.png)



## spring_mvc



![image-20211212145506302](C:\Users\78235\AppData\Roaming\Typora\typora-user-images\image-20211212145506302.png)

![image-20211212145619439](C:\Users\78235\AppData\Roaming\Typora\typora-user-images\image-20211212145619439.png)

![image-20211212152212752](C:\Users\78235\AppData\Roaming\Typora\typora-user-images\image-20211212152212752.png)

![image-20211212152224436](C:\Users\78235\AppData\Roaming\Typora\typora-user-images\image-20211212152224436.png)

![image-20211212152910613](C:\Users\78235\AppData\Roaming\Typora\typora-user-images\image-20211212152910613.png)

