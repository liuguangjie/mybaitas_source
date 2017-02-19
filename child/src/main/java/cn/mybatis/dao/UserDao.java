package cn.mybatis.dao;

import cn.mybatis.domain.QueryVo;
import cn.mybatis.domain.User;
import cn.mybatis.domain.UserCustom;

import java.util.List;

/**
 * Created by free on 17-2-16.
 */
public interface UserDao {

    public User findUserById(int userId) throws Exception;

    public void updateUser( User user) throws  Exception;

    public void deleteUserByid(int userId) throws Exception;

    public List<User> queryUserListBuName(String userName)throws Exception;

    public void addUser(User user) throws Exception;
    public List<UserCustom> findUserList(QueryVo queryVo) throws Exception;

}
