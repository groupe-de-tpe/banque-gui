package com.douwe.banque.dao;

import com.douwe.banque.dao.jdbc.JDBCConnectionFactory;
import com.douwe.banque.data.Customer;
import com.douwe.banque.data.RoleType;
import com.douwe.banque.data.User;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class ICustomerDaoTest {

    private static DaoFactory daoFactory;
    private ICustomerDao instance;
    private static Integer val;

    public ICustomerDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            Class.forName("org.sqlite.JDBC");
            daoFactory = new DaoFactory();
            ICustomerDao customerDao = daoFactory.getCustomerDao();
            IUserDao userDao = daoFactory.getUserDao();
            User us = new User();
            us.setLogin("vincente");
            us.setPassword("douwe");
            us.setRole(RoleType.admin);
            us.setStatus(0);
            userDao.save(us);
            for (int i = 0; i < 10; i++) {
                try {
                    User user = new User();
                    user.setLogin("vincente" + i);
                    user.setPassword("douwe" + i);
                    user.setRole(RoleType.admin);
                    user.setStatus(0);
                    Customer customer = new Customer();
                    customer.setEmailAddress("zozo" + i + "@toto.com");
                    customer.setName("Zozo " + i);
                    customer.setPhoneNumber("7283813" + i);
                    customer.setStatus(i % 2);
                    user = userDao.save(user);
                    customer.setUser(user);
                    Customer c = customerDao.save(customer);
                    if (i == 3) {
                        val = c.getId();
                    }
                } catch (DataAccessException ex) {
                    Logger.getLogger(ICustomerDaoTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ICustomerDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ICustomerDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            ICustomerDao customerDao = daoFactory.getCustomerDao();
            IUserDao userDao = daoFactory.getUserDao();
            for (Customer cust : customerDao.findAll()) {
                customerDao.delete(cust);
            }
            for (User us : userDao.findAll()) {
                userDao.delete(us);
            }
            JDBCConnectionFactory.getConnection().close();
        } catch (DataAccessException | SQLException ex) {
            Logger.getLogger(ICustomerDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Before
    public void setUp() {
        instance = daoFactory.getCustomerDao();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        Customer customer = new Customer();
        customer.setEmailAddress("zozo@toto.com");
        customer.setName("Zozo Toto");
        customer.setPhoneNumber("623451");
        customer.setStatus(0);
        User user = daoFactory.getUserDao().findByLogin("vincente");
        assertNotNull(user);
        customer.setUser(user);
        Customer result = instance.save(customer);
        assertNotNull(result.getId());
    }

    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Customer customer = instance.findByName("Zozo 1");
        instance.delete(customer);
        Customer cc = instance.findByName("Zozo 1");
        assertNull(cc);
    }

    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Customer customer = instance.findByName("Zozo 8");
        customer.setEmailAddress("zozo@example.com");
        customer.setStatus(1);
        Customer result = instance.update(customer);
        assertEquals("zozo@example.com", result.getEmailAddress());
        assertEquals(1, result.getStatus());

    }

    @Test
    public void testFindById() throws Exception {
        System.out.println("findById");
        Customer result = instance.findById(val);
        System.out.println("The original id is "+ val);
        System.out.println("The result is null "+ (result == null));
        assertNotNull(result);
        System.out.println("The id is "+ result.getId());
        System.out.println("The email is "+result.getEmailAddress());
        assertEquals("zozo3@toto.com", result.getEmailAddress());

    }

    @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        List result = instance.findAll();
        assertEquals(10, result.size());

    }

    @Test
    public void testFindByUser() throws Exception {
        System.out.println("findByUser");
        User user = daoFactory.getUserDao().findByLogin("vincente6");
        Customer result = instance.findByUser(user);
        assertEquals("zozo6@toto.com", result.getEmailAddress());
        assertEquals("Zozo 6", result.getName());
    }

    @Test
    public void testFindByName() throws Exception {
        System.out.println("findByName");
        String name = "Zozo 7";
        Customer result = instance.findByName(name);
        assertEquals("zozo7@toto.com", result.getEmailAddress());
    }
}