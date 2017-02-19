package cn.mybatis.jdbc;

import cn.mybatis.domain.User;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.junit.Test;

import java.util.Random;

/**
 * Created by free on 17-2-16.
 */
public class MetaObjectTest {

    /**
     * property  editor
     */
    @Test
    public void testProperty(){
        User user=new User();
        //MetaObject metaDataSource = SystemMetaObject.forObject(user);

        ObjectFactory objectFactory = new DefaultObjectFactory();
        ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
        MetaObject metaDataSource=MetaObject.forObject(user,objectFactory,objectWrapperFactory);
        //BeanWrapper beanWrapper=(BeanWrapper)metaDataSource.getObjectWrapper();
        //System.out.println(beanWrapper.getSetterNames().length);
        //Class clazz=metaDataSource.getSetterType("username");
        metaDataSource.setValue("username","nihao");
        System.out.println(metaDataSource.getValue("username"));
        metaDataSource.getGetterNames();
        System.out.println(user);


    }




    @Test
    public void testSort(){
        Random random=new Random();
        //System.out.println(random.nextInt(10));
        int[] array=null;
        for (int d=random.nextInt(30),i=0;i<d;i++){
            if(array==null){
                if(d==0){
                    return;
                }
                array=new int[d];
                System.out.println(d);
            }

            array[i]=random.nextInt(10);
        }
        System.out.println(array.length);
        for (int j=0;j<array.length;j++){
            System.out.print(array[j]+" ");
        }
        System.out.println();
        System.out.println("sort after ...");

        int tmp=0;
        for (int n=0;n<array.length -1 ;n++){
            for (int m=n+1;m<array.length;m++){
                if(array[n] > array[m]){
                    tmp=array[n];
                    array[n]=array[m];
                    array[m]=tmp;
                }
            }
        }

        for (int k=0;k<array.length;k++){
            System.out.print(array[k]+" ");
        }
    }
}
