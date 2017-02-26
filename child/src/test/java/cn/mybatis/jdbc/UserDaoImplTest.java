package cn.mybatis.jdbc;

import cn.mybatis.dao.UserDao;
import cn.mybatis.dao.UserDaoImpl;
import cn.mybatis.dao.UserMapper;
import cn.mybatis.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by free on 17-2-16.
 */
public class UserDaoImplTest {


    private SqlSessionFactory sqlSessionFactory=null;
    @Before
    public void createSqlSessionFactory() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void findUserById() throws Exception {
        UserDao userDao=new UserDaoImpl(sqlSessionFactory);

        User user=userDao.findUserById(27);
        System.out.println(user);
    }
    @Test
    public void testMapper() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        System.out.println(userMapper.findUserById(1));
        sqlSession.close();
    }

}