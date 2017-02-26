package cn.mybatis.jdbc;

import cn.mybatis.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

/**
 * Created by free on 17-2-13.
 */
public class MybatisTest {

    @Test
    public void testFist() throws IOException {
        // build  inputStream
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        //build SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //build Sqlsession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // query database  Object
        User user = sqlSession.selectOne("test.findUserById", 1);

        System.out.println(user);
        sqlSession.close();

    }

    @Test
    public void testFindUserByName() throws IOException {

        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        List<User> userList = sqlSession.selectList("first.findUserByName", "小明");

        System.out.println(userList);

        sqlSession.close();
    }

    @Test
    public void testInsertUser() throws IOException {

        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setAddress("beijing");

        user.setBirthday(new Date());
        user.setSex("1");
        user.setUsername("liujie");
        sqlSession.insert("first.addUser", user);
        sqlSession.commit();
        sqlSession.close();

    }

    // update user
    @Test
    public void testUpdatetUser() throws IOException {

        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setAddress("北京");

        user.setBirthday(new Date());
        user.setSex("1");
        user.setUsername("刘介");
        user.setId(27);
        sqlSession.update("first.updateUser", user);
        sqlSession.commit();
        sqlSession.close();

    }

    //delete  user By id
    @Test
    public void testDeleteUser() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        sqlSession.delete("first.deleteUser",26);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testClassLoader() {
        String path = "org/apache/ibatis/builder/xml/mybatis-3-config.dtd";
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder bufferBuilder = null;
        try {
            bufferBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                bufferBuilder.append(line + "\n");
            }
            System.out.println(bufferBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
