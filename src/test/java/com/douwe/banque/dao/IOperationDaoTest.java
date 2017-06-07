package com.douwe.banque.dao;

import com.douwe.banque.dao.jdbc.JDBCConnectionFactory;
import com.douwe.banque.data.Account;
import com.douwe.banque.data.AccountType;
import com.douwe.banque.data.Customer;
import com.douwe.banque.data.Operation;
import com.douwe.banque.data.OperationType;
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
public class IOperationDaoTest {
    
    private static DaoFactory daoFactory;
    private IOperationDao instance;
    private static Integer val, value;
    public IOperationDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        try {
            Class.forName("org.sqlite.JDBC");
            daoFactory = new DaoFactory();
            ICustomerDao customerDao = daoFactory.getCustomerDao();
            IUserDao userDao = daoFactory.getUserDao();
            IAccountDao accountDao = daoFactory.getAccountDao();
            IOperationDao operationDao = daoFactory.getOperationDao();
            User user = null;
            Account account = null;
            for (int i = 0; i < 2; i++) {
                try {
                    user = new User();
                    user.setLogin("valeur"+i);
                    user.setPassword("password"+i);
                    user.setRole(RoleType.admin);
                    user.setStatus(1);
                    user = userDao.save(user);
                    Customer customer = new Customer();
                    customer.setUser(user);
                    customer.setStatus(1);
                    customer.setPhoneNumber("9408712"+i);
                    customer.setName("version"+i);
                    customer.setEmailAddress("douwevincent"+i+"@gmail.com");
                    customer.setUser(user);
                    customer = customerDao.save(customer);
                    account = new Account();
                    account.setAccountNumber("12345"+i);
                    account.setBalance(6500* (i + 1));
                    account.setCustomer(customer);
                    account.setType(AccountType.values()[i % 2]);
                    account.setDateDeCreation(new Date());
                    account.setStatus(1);
                    account = accountDao.save(account);
                } catch (DataAccessException ex) {
                    Logger.getLogger(IOperationDaoTest.class.getName()).log(Level.SEVERE, null, ex);
                }            
            }
            for (int i = 0; i < 10; i++) {
                Operation op = new Operation();
                op.setType(OperationType.values()[i % 4]);
                op.setUser(user);
                op.setDescription("operation "+ i);
                op.setDateOperation(new Date());
                op.setAccount(account);
                op = operationDao.save(op);
                if(i == 3)
                    val = op.getId();
                if (i == 5)
                    value = op.getId();
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(IOperationDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IOperationDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        try {
            IOperationDao operationDao = daoFactory.getOperationDao();
            IAccountDao accountDao = daoFactory.getAccountDao();
            ICustomerDao customerDao = daoFactory.getCustomerDao();
            IUserDao userDao = daoFactory.getUserDao();
            for (Operation operation : operationDao.findAll()) {
                operationDao.delete(operation);
            }            
            for (Account account : accountDao.findAll()) {
                accountDao.delete(account);                
            }
            for (Customer customer : customerDao.findAll()) {
                customerDao.delete(customer);
            }
            for (User user : userDao.findAll()) {
                userDao.delete(user);
            }            
            JDBCConnectionFactory.getConnection().close();
        } catch (SQLException | DataAccessException ex) {
            Logger.getLogger(IOperationDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Before
    public void setUp() {
        instance = daoFactory.getOperationDao();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        Account account = daoFactory.getAccountDao().findByAccountNumber("123450");
        User user = daoFactory.getUserDao().findByLogin("valeur0");
        Operation operation = new Operation();
        operation.setDateOperation(new Date());
        operation.setDescription("Une operation simple");
        operation.setType(OperationType.debit);
        operation.setAccount(account);
        operation.setUser(user);
        Operation result = instance.save(operation);
        assertNotNull(result.getId());
    }

    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Operation operation = instance.findById(val);
        assertNotNull(operation);
        instance.delete(operation);
        Operation op = instance.findById(val);
        assertNull(op);
    }

    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Operation operation = instance.findById(value);
        assertNotNull(operation);
        operation.setDescription("Bonjour mon cher");
        operation.setType(OperationType.transfer);
        Operation result = instance.update(operation);
        assertEquals("Bonjour mon cher", result.getDescription());
        assertEquals(OperationType.transfer, result.getType());
    }

    @Test
    public void testFindById() throws Exception {
        System.out.println("findById");
        Operation result = instance.findById(value);
        assertNotNull(result);
    }

    @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        List result = instance.findAll();
        assertEquals(10, result.size());
    }

    @Test
    public void testFindForCustomer() throws Exception {
        System.out.println("findForCustomer");
        Customer customer = daoFactory.getCustomerDao().findByName("version1");
        assertNotNull(customer);
        List result = instance.findForCustomer(customer);
        assertEquals(9, result.size());
    }
}