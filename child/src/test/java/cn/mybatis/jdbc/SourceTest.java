package cn.mybatis.jdbc;

import cn.mybatis.dao.UserDao;
import cn.mybatis.dao.UserMapper;
import cn.mybatis.domain.QueryVo;
import cn.mybatis.domain.User;
import cn.mybatis.domain.UserCustom;
import cn.mybatis.jdkproxy.ProxyConnection;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.util.*;

/**
 * Created by free on 17-2-15.
 */
public class SourceTest {
    // jdk logger  public Logger log = Logger.getLogger(SourceTest.class.toString());
    @Test
    public void testBase() throws Exception {
        String environment = null;
        Properties properties = null;
        InputStream inputStream = Resources.getResourceAsStream("mybatis-comfig.xml");

        /**
         * 这行代码初始化如下
         * 1.初始化 XPath 并且生成 Document
         * 2.初始化 Configuration 注册TypeHandlerRegistry 和 TypeAliasRegistry
         * 3.设置成员变量
         */
        XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);
        /**
         * 这行代码做了那些事情
         * 1.解析Document并且构建XNode
         * 2.解析子标签
         * 3.构建XMLMapperBuilder 并且 解析mapper配置文件
         * 4.解析动态sql
         * 5.还的看源码不够深入
         */
        Configuration configuration = parser.parse();

        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        /*User user = sqlSession.selectOne("cn.mybatis.dao.UserDao.findUserById", 1);
        System.out.println(user);*/
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        User user=userDao.findUserById(1);
        System.out.println(user);
        //User user = new User();
        //user.setAddress("shanghai");
        //user.setBirthday(new Date());
        //user.setSex("2");
        //user.setUsername("水水水");
        //sqlSession.insert("cn.mybatis.dao.UserDao.addUser",user);
        //sqlSession.getMapper(UserDao.class).addUser(user);
        //sqlSession.commit();
        sqlSession.close();


    }

    @Test
    public void testFindUserList() throws Exception{
        String environment = null;
        Properties properties = null;
        InputStream inputStream = Resources.getResourceAsStream("mybatis-comfig.xml");
        XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);
        Configuration configuration = parser.parse();
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        UserCustom userCustom=new UserCustom();
        userCustom.setSex("1");
        userCustom.setUsername("陈小明");
        QueryVo queryVo=new QueryVo();
        queryVo.setUserCustom(userCustom);
        List<UserCustom> userCustoms=userDao.findUserList(queryVo);
        System.out.println(userCustoms);
        sqlSession.close();
    }

    public SqlSession getSqlSession()throws Exception{
        String environment = null;
        Properties properties = null;
        InputStream inputStream = Resources.getResourceAsStream("mybatis-comfig.xml");
        XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);
        Configuration configuration = parser.parse();
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }

    @Test
    public void testFindUserCount()throws Exception{
        SqlSession sqlSession=getSqlSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        UserCustom userCustom=new UserCustom();
        userCustom.setSex("1");
        userCustom.setUsername("陈小明");
        QueryVo queryVo=new QueryVo();
        queryVo.setUserCustom(userCustom);
        int count=userMapper.findUserCount(queryVo);
        //sqlSession.clearCache();
        int count2=userMapper.findUserCount(queryVo);
        System.out.println(count);
        System.out.println(count2);
    }

    // resultMap  test  and  look  source
    @Test
    public void testFindUserResultMap() throws Exception{
        SqlSession sqlSession=getSqlSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        UserCustom userCustom=new UserCustom();
        userCustom.setSex("1");
        userCustom.setUsername("陈小明");
        QueryVo queryVo=new QueryVo();
        queryVo.setUserCustom(userCustom);
        List<Integer> ids=new ArrayList<Integer>();
        ids.add(16);
        ids.add(25);
        queryVo.setIds(ids);
        List <User> userList=userMapper.findUserResultMap(queryVo);
        System.out.println(userList);
        sqlSession.close();

    }


    @Test
    public void testDataSource() throws Exception {
        UnpooledDataSource dataSource=new UnpooledDataSource();
        MetaObject metaObject=SystemMetaObject.forObject(dataSource);

        Map<String,String> names=new HashMap<String, String>(4);
        names.put("username","root");
        names.put("password","root");
        names.put("driver","com.mysql.jdbc.Driver");
        names.put("url","jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8");
        for (Map.Entry<String,String> entry : names.entrySet()){
            if(metaObject.hasSetter(entry.getKey())){
                metaObject.setValue(entry.getKey(),entry.getValue());
            }
        }

        ProxyConnection proxyConnection=new ProxyConnection(dataSource.getConnection(),dataSource);

        Connection connection=proxyConnection.getProxyConnection();
        System.out.println(connection);
        connection.close();
        /*Transaction transaction=new JdbcTransaction(pooledDataSource,null,false);
        Connection connection=transaction.getConnection();
        System.out.println(connection);
        connection.close();*/
        /*Class.forName("com.mysql.jdbc.Driver");
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8","root","root");*/

    }

}
