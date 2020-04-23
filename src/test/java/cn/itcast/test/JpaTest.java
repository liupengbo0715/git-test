package cn.itcast.test;

import cn.itcast.domain.Customer;
import cn.itcast.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTest {
    /**
     * 测试JPA的保存
     *    案例：保存一个客户到数据库
     *
     * Jpa的操作步骤：
     *      1.加载配置文件创建工厂（实体管理类工厂）对象
     *      2.通过实体管理器工厂获取实体管理器
     *      3.获取事务对象，开启事务
     *      4.完成增删改查操作
     *      5.提交事务（回滚事务）
     *      6.释放资源
     */
    @Test
    public void testSave(){
      /*  //1.加载配置文件创建工厂（实体管理类工厂）对象
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
        //2.通过实体管理器工厂获取实体管理器
        EntityManager em = factory.createEntityManager();*/

      EntityManager em= JpaUtils.getEntityManager();
        //3.获取事务对象，开启事务
        EntityTransaction tx = em.getTransaction();//获取事务对象
        tx.begin();
        //4.完成增删改查操作:保存一个客户到数据库中
        Customer customer=new Customer();
        customer.setCustName("刘蓬勃");
        customer.setCustIndustry("科技");
        //保存
        em.persist(customer);//保存操作
        //5.提交事务（回滚事务）
        tx.commit();
        //6.释放资源
        em.close();
        //factory.close();
    }


    /**
     * 根据id查询客户
     *    使用find方法查询：
     *       1.查询对象就是客户对象本身
     *       2.在调用find方法的时候，就会发送sql语句查询数据库
     *
     *       立即加载
     *
     */
    //根据id查询客户
    @Test
    public void testFind(){
        //1.通过工具类获取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.增删改查--根据id查询客户
        /**
         * find:根据id查询的数据
         *    class：查询数据的结果需要的实体类类型的字节码
         *    id:查询主键的取值
         */
        Customer customer = entityManager.find(Customer.class, 1l);
        //System.out.print(customer);
        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 根据id查询客户：
     *      getReference方法：
     *           1.获取一个动态代理对象
     *           2.调用getReference方法不会立即发送sql查询数据库
     *              当调用查询结果对象的时候，才会发送查询的sql语句（什么时候用，什么时候发）
     *
     *              延迟加载（懒加载）：、
     *                  得到的是动态代理对象
     *                  什么时候用什么时候加载
     *
     */
    @Test
    public void testReference(){
        //1.通过工具类获取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3.增删改查--根据id查询客户
        /**
         * getReference:根据id查询的数据
         *    class：查询数据的结果需要的实体类类型的字节码
         *    id:查询主键的取值
         */
        Customer customer = entityManager.getReference(Customer.class, 1l);
        //System.out.print(customer);
        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 删除客户
     */
    @Test
    public void testRemove(){
        //1.通过工具类获取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        //3.增删改查--删除客户
        //1）根据id查询客户
        Customer customer = entityManager.find(Customer.class, 1l);
        //2）调用remove方法完成删除操作
        entityManager.remove(customer);

        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 更新客户操作
     *     merge（Object）
     */
    @Test
    public void testUpdate(){
        //1.通过工具类获取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        //3.增删改查--更新客户
        //1）查询客户
        Customer customer = entityManager.find(Customer.class, 1l);
        //2）更新客户
        customer.setCustIndustry("软件");
        entityManager.merge(customer);

        //4.提交事务
        tx.commit();
        //5.释放资源
        entityManager.close();
    }
}
