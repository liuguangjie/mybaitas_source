package cn.mybatis.jdbc;

import cn.mybatis.domain.User;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by free on 17-2-15.
 */
public class SourceTest {

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
         */
        Configuration configuration = parser.parse();

        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("first.findUserById", 1);

        System.out.println(user);
        sqlSession.close();


    }
}
