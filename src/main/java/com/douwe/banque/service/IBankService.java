package com.douwe.banque.service;

import com.douwe.banque.data.Account;
import com.douwe.banque.data.AccountType;
import com.douwe.banque.data.Customer;
import com.douwe.banque.data.Operation;
import com.douwe.banque.data.OperationType;
import com.douwe.banque.data.RoleType;
import com.douwe.banque.data.User;
import com.douwe.banque.projection.AccountCustomer;
import com.douwe.banque.projection.AccountOperation;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public interface IBankService {
    public Customer findCustomerById(Integer id) throws ServiceException;
    
    public Customer findCustomerByLogin(String login) throws ServiceException;
    
    public User findUserByLogin(String login) throws ServiceException;
    
    public void transfer(String depart, String destination, double montant, int userId) throws ServiceException;
    
    public List<Account> findAccountByCustomerId(Integer id) throws ServiceException;
    
    public void saveOperation(Operation op) throws ServiceException;
    
    public Account findAccountByNumber(String accountNumber) throws ServiceException;
    
    public List<User> findAllUsers() throws ServiceException;
    
    public Account saveOrUpdateAccount(Account account) throws ServiceException;
    
    public User saveOrUpdateUser(User user) throws ServiceException;
    
    public void deleteUser(Integer userId) throws ServiceException;
    
    public void deleteAccount(Integer accountId) throws ServiceException;
    
    public void credit(String account, double balance, int userId) throws ServiceException;
    
    public void debit(String account, double balance, int userId) throws ServiceException;
    
    public Customer saveOrUpdateCustomer(Customer customer) throws ServiceException;
    
    public void deleteCustomer(Integer customerId) throws ServiceException;
    
    public List<Customer> findAllCustomer() throws ServiceException;
    
    public List<Customer> findCustomerByName(String name) throws ServiceException;
    
    public List<User> findUserByNameAndRole(String name, RoleType type) throws ServiceException;
    
    public List<AccountCustomer> findAccountByCriteria(String customerName, String accountNumber, AccountType type) throws ServiceException;
    
    public List<AccountOperation> findOperationByCriteria(String accountNumber, String userName, OperationType opType, Date debut, Date fin) throws ServiceException;

    public User findUserById(Integer integer) throws ServiceException;

    public List<AccountOperation> findAllOperations() throws ServiceException;

    public User login(String username, String passwd) throws ServiceException;

    public List<AccountCustomer> findAllAccountCustomer() throws ServiceException;

    public AccountCustomer findAccountCustomerById(int id) throws ServiceException;

    public Account findAccountById(int id) throws ServiceException;

    public Customer getSingleCustomerByName(String customer) throws ServiceException;

    public List<AccountOperation> findOperationFromCustomerAccounts(int customerId) throws ServiceException;
}