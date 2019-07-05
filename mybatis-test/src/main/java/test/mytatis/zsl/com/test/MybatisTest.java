package test.mytatis.zsl.com.test;

import mybatis.framework.zsl.com.sqlsession.SqlSessionFactory;
import mybatis.framework.zsl.com.sqlsession.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import test.mytatis.zsl.com.dao.UserDao;
import test.mytatis.zsl.com.dao.UserDaoImpl;
import test.mytatis.zsl.com.entity.User;

import java.io.InputStream;

public class MybatisTest {

    /**
     * SqlSessionFactory 加载依赖于全局配置文件的加载方式
     * 所以我们需要定制哪一种加载方式去创建SqlSessionFactory
     * 使用构建者模式去定制SqlSessionFactory
     */
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() {
        String resource = "SqlMap-Config.xml";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testQueryUserById() {
        UserDao userDao = new UserDaoImpl(sqlSessionFactory);
        User user = userDao.queryUserById(1);
        System.out.println(user);
    }

    @Test
    public void testQueryUserByName() {
        UserDao userDao = new UserDaoImpl(sqlSessionFactory);
//        User user = new User();
//        user.setName("张三");
        User result = userDao.queryUserByName("张三");
        System.out.println(result);
    }
}
