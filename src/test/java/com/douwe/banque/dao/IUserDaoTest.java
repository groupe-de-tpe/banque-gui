package com.douwe.banque.dao;

import com.douwe.banque.data.RoleType;
import com.douwe.banque.data.User;
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
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class IUserDaoTest {

    private static DaoFactory daoFactory;
    private static Integer val;
    private IUserDao instance;

    public IUserDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            Class.forName("org.sqlite.JDBC");
            daoFactory = new DaoFactory();
            IUserDao userDao = daoFactory.getUserDao();
            User us = new User();
            us.setLogin("douwevincent@gmail.com");
            us.setPassword("password");
            us.setRole(RoleType.admin);
            us.setStatus(1);
            userDao.save(us);
            for (int i = 0; i < 10; i++) {
                try {
                    User user = new User();
                    user.setLogin("user" + i);
                    user.setPassword("password" + i);
                    user.setRole(RoleType.values()[i % 3]);
                    user.setStatus(i % 2);
                    User u = userDao.save(user);
                    if (i == 6) {
                        val = u.getId();
                    }
                } catch (DataAccessException ex) {
                    Logger.getLogger(IUserDaoTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(IUserDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IUserDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            IUserDao userDao = daoFactory.getUserDao();
            for (User user : userDao.findAll()) {
                userDao.delete(user);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(IUserDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Before
    public void setUp() {
        instance = daoFactory.getUserDao();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        User user = new User();
        user.setLogin("hallamvincent");
        user.setPassword("hallam");
        user.setRole(RoleType.customer);
        user.setStatus(1);
        instance.save(user);
        assertNotNull(user.getId());
    }

    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        User user = instance.findByLogin("user5");
        assertNotNull(user);
        instance.delete(user);
        User us = instance.findByLogin("user5");
        System.out.println("user est nul "+us);
        assertNull(us);
    }

    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        User user = instance.findByLogin("user8");
        assertNotNull(user);
        user.setRole(RoleType.admin);
        user.setPassword("tralala");
        User result = instance.update(user);
        assertEquals("tralala", result.getPassword());
        assertEquals(RoleType.admin, result.getRole());
    }

    @Test
    public void testFindById() throws Exception {
        System.out.println("findById");
        User result = instance.findById(val);
        assertEquals("user6", result.getLogin());
        assertEquals(0, result.getStatus());
        assertEquals(RoleType.customer, result.getRole());

    }

    @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        List result = instance.findAll();
        assertEquals(11, result.size());

    }

    @Test
    public void testFindByLogin() throws Exception {
        System.out.println("findByLogin");
        String login = "douwevincent@gmail.com";
        User result = instance.findByLogin(login);
        assertNotNull(result);
        assertEquals(RoleType.admin, result.getRole());
    }

    @Test
    public void testFindByStatus() throws Exception {
        System.out.println("findByStatus");
        int status = 0;
        List result = instance.findByStatus(status);
        assertEquals(5, result.size());
    }
}