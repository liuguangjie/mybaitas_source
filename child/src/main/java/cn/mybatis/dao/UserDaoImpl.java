package cn.mybatis.dao;

import cn.mybatis.domain.QueryVo;
import cn.mybatis.domain.User;
import cn.mybatis.domain.UserCustom;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by free on 17-2-16.
 */
public class UserDaoImpl implements UserDao {

    private SqlSessionFactory sqlSessionFactory;

    public UserDaoImpl(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory=sqlSessionFactory;
    }

    public User findUserById(int userId) throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        User user=sqlSession.selectOne("cn.mybatis.dao.UserDao.findUserById",userId);
        sqlSession.close();
        return user;
    }

    public void updateUser(User user) throws Exception {

    }

    public void deleteUserByid(int userId) throws Exception {

    }

    public List<User> queryUserListBuName(String userName) throws Exception {
        return null;
    }

    @Override
    public void addUser(User user) throws Exception {

    }

    @Override
    public List<UserCustom> findUserList(QueryVo queryVo) throws Exception {
        return null;
    }
}
