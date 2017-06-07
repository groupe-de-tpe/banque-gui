package com.douwe.banque.dao;

import com.douwe.banque.dao.jdbc.JDBCConnectionFactory;
import com.douwe.banque.data.Account;
import com.douwe.banque.data.AccountType;
import com.douwe.banque.data.Customer;
import com.douwe.banque.data.RoleType;
import com.douwe.banque.data.User;
import java.sql.SQLException;
import java.util.Date;
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
public class IAccountDaoTest {

    private static DaoFactory daoFactory;
    private static Integer val;
    private IAccountDao instance;

    public IAccountDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            Class.forName("org.sqlite.JDBC");
            daoFactory = new DaoFactory();
            User user = new User();
            user.setLogin("douwevincent");
            user.setPassword("version");
            user.setRole(RoleType.admin);
            user.setStatus(0);
            user = daoFactory.getUserDao().save(user);
            //System.out.println("Here is the user id "+user.getId());
            ICustomerDao customerDao = daoFactory.getCustomerDao();
            IAccountDao accountDao = daoFactory.getAccountDao();
            Customer cc = new Customer();
            Customer dd = new Customer();
            cc.setName("Douwe Vincent");
            cc.setEmailAddress("douwevincent@yahoo.fr");
            cc.setPhoneNumber("94087120");
            cc.setUser(user);
            cc = customerDao.save(cc);
            //System.out.println("The first customer "+ cc.getId());
            dd.setName("Vincent Douwe");
            dd.setEmailAddress("douwevincent@gmail.com");
            dd.setPhoneNumber("72838130");
            dd.setUser(user);
            dd = customerDao.save(dd);
            //System.out.println("The second customer "+ dd.getId());
//            if(dd.getId().intValue() == cc.getId()){
//                dd.setId(cc.getId() + 1);
//            }
            for (int i = 0; i < 10; i++) {
                Account account = new Account();
                account.setBalance(340 + i);
                account.setDateDeCreation(new Date());
                account.setAccountNumber("14521" + i);
                account.setType(AccountType.saving);
                account.setStatus(0);
                if (i % 3 == 0) {
                    account.setCustomer(dd);
                } else {
                    account.setCustomer(cc);
                }
                Account ac = accountDao.save(account);
                if(i == 3){
                    val = ac.getId();
                }
            }
            for (int i = 0; i < 10; i++) {
                Account account = new Account();
                account.setBalance(340 + i);
                account.setDateDeCreation(new Date());
                account.setAccountNumber("11221" + i);
                account.setType(AccountType.deposit);
                account.setStatus(0);
                if (i % 3 == 0) {
                    account.setCustomer(dd);
                } else {
                    account.setCustomer(cc);
                }
                accountDao.save(account);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(IAccountDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IAccountDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            IAccountDao accountDao = daoFactory.getAccountDao();
            List<Account> accounts = accountDao.findAll();
            for (Account account : accounts) {
                accountDao.delete(account);
            }
            ICustomerDao customerDao = daoFactory.getCustomerDao();
            List<Customer> customers = customerDao.findAll();
            for (Customer customer : customers) {
                customerDao.delete(customer);
            }
            IUserDao userDao = daoFactory.getUserDao();
            List<User> users = userDao.findAll();
            for (User user : users) {
                userDao.delete(user);
            }
            JDBCConnectionFactory.getConnection().close();
        } catch (DataAccessException | SQLException ex) {
            Logger.getLogger(IAccountDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Before
    public void setUp() {
        instance = daoFactory.getAccountDao();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        Account account = new Account();
        account.setAccountNumber("123456");
        account.setBalance(3450f);
        account.setDateDeCreation(new Date());
        account.setStatus(0);
        Customer customer = daoFactory.getCustomerDao().findByName("Vincent Douwe");
        account.setCustomer(customer);
        account.setType(AccountType.deposit);
        assertNull(account.getId());
        Account result = instance.save(account);        
        assertNotNull(result.getId());
        System.out.println("Here is the id "+ result.getId());
        assertEquals(result.getBalance(), account.getBalance(),0);
        assertEquals(result.getAccountNumber(), account.getAccountNumber());
    }

    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Account account = instance.findByAccountNumber("145210");
        assertNotNull(account);
        instance.delete(account);
        Account res = instance.findByAccountNumber("145210");
        assertNull(res);
    }

    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Account account = instance.findByAccountNumber("145211");
        assertNotNull(account);
        account.setBalance(7500);
        account.setType(AccountType.saving);        
        Account result = instance.update(account);
        assertEquals(7500, result.getBalance() ,0);
        assertEquals(AccountType.saving, result.getType());
    }

    @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        List result = instance.findAll();
        assertEquals(20, result.size());        
    }

    @Test
    public void testFindById() throws Exception {
        System.out.println("findById");        
        Account result = instance.findById(val);
        assertNotNull(result);
        assertEquals("145213", result.getAccountNumber());
    }

    @Test
    public void testFindByAccountNumber() throws Exception {
        System.out.println("findByAccountNumber");
        String accountNumber = "112212";
        Account result = instance.findByAccountNumber(accountNumber);
        assertNotNull(result);
        assertTrue(342 == result.getBalance());
        assertEquals(result.getType(), AccountType.deposit);
    }

    @Test
    public void testFindByCustomer() throws Exception {
        System.out.println("findByCustomer");
        Customer customer = daoFactory.getCustomerDao().findByName("Vincent Douwe");
        List<Account> result = instance.findByCustomer(customer);        
        assertEquals(8, result.size());
    }
}