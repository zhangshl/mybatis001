package test.mytatis.zsl.com.dao;


import mybatis.framework.zsl.com.sqlsession.SqlSession;
import mybatis.framework.zsl.com.sqlsession.SqlSessionFactory;
import test.mytatis.zsl.com.entity.User;

public class UserDaoImpl implements UserDao{

    private SqlSessionFactory sqlSessionFactory;

    /**
     * 注入一个sqlSessionFactory，为什么不直接new？
     */
    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public User queryUserById(Integer id) {
        /**
         * 调用自己写的框架实现CURD
         */
        SqlSession sqlSession = sqlSessionFactory.openSession();
        String statementId = "test.findUserById";
        User user = sqlSession.selectOne(statementId,id);
        return user;
    }

    @Override
    public User queryUserByName(String name) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        String statementId = "test.findUserByName";
        User result = sqlSession.selectOne(statementId,name);
        return  result;
    }
}
