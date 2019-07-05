package test.mytatis.zsl.com.dao;

import test.mytatis.zsl.com.entity.User;

public interface UserDao {

    User queryUserById(Integer id);

    User queryUserByName(String name);
}
