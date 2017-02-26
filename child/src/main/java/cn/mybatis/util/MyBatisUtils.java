package cn.mybatis.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by free on 17-2-13.
 */
public class MyBatisUtils {

    public static InputStream inputStream=null;
    private static SqlSessionFactory sqlSessionFactory=null;
    private static SqlSession sqlSession=null;
    static {
        try {
            inputStream= Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

    public static void closeSqlSession(){
        if(inputStream!=null && sqlSession!=null){
            try {
                inputStream.close();
                sqlSession.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
