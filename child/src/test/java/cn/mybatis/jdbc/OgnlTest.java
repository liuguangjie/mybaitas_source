package cn.mybatis.jdbc;

import cn.mybatis.domain.QueryVo;
import cn.mybatis.domain.UserCustom;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.junit.Test;

/**
 * Created by free on 17-2-17.
 */
public class OgnlTest {

    /**
     *

     ognl   study

     http://www.cnblogs.com/withscorpion/p/5608585.html

     http://www.cnblogs.com/yw-ah/p/5760192.html

     */

    @Test
    public void testValue() throws OgnlException {
        UserCustom userCustom=new UserCustom();
        userCustom.setSex("1");
        userCustom.setUsername("陈小明");

        QueryVo queryVo=new QueryVo();
        queryVo.setUserCustom(userCustom);

        Object o=Ognl.getValue("userCustom.sex",queryVo);

        System.out.println(o);
    }


    @Test
    public void testContext() throws Exception{
        UserCustom userCustom=new UserCustom();
        userCustom.setSex("1");
        userCustom.setUsername("陈小明");
        OgnlContext context=new OgnlContext();

        context.put("userCustom",userCustom);
        context.setRoot(userCustom);

        Object o=Ognl.parseExpression("username");
        Object value=Ognl.getValue(o,context,context.getRoot());
        System.out.println(value);

        value=Ognl.getValue(Ognl.parseExpression("#userCustom.username"),context,context.getRoot());
        System.out.println(value);


    }
}
