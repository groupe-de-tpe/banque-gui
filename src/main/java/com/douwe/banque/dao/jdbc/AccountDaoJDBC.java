package com.douwe.banque.dao.jdbc;

import com.douwe.banque.dao.DataAccessException;
import com.douwe.banque.dao.IAccountDao;
import com.douwe.banque.data.Account;
import com.douwe.banque.data.AccountType;
import com.douwe.banque.data.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class AccountDaoJDBC implements IAccountDao {

    @Override
    public Account save(Account account) throws DataAccessException {
        try {
            Connection conn = JDBCConnectionFactory.getConnection();
            try (PreparedStatement psmt = conn.prepareStatement("insert into account(accountNumber, balance,dateCreation, customer_id, status, type) values(?,?,?,?,?,?)")) {
                psmt.setString(1, account.getAccountNumber());
                psmt.setDouble(2, account.getBalance());
                psmt.setDate(3, new java.sql.Date(account.getDateDeCreation().getTime()));
                psmt.setLong(4, account.getCustomer().getId());
                psmt.setInt(5, 0);
                psmt.setInt(6, account.getType().ordinal());
                psmt.executeUpdate();
                ResultSet res = psmt.getGeneratedKeys();
                if (res.next()) {
                    account.setId(res.getInt(1));
                }
                psmt.close();
            }
            return account;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void delete(Account account) throws DataAccessException {
        try {
            Connection conn = JDBCConnectionFactory.getConnection();
            try (PreparedStatement psmt = conn.prepareStatement("delete from account where id = ?")) {
                psmt.setLong(1, account.getId());
                psmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException(ex);
        }
    }

    @Override
    public Account update(Account account) throws DataAccessException {
        try {
            Connection conn = JDBCConnectionFactory.getConnection();
            try (PreparedStatement psmt = conn.prepareStatement("update account set accountNumber = ?, balance = ?, dateCreation = ?, type = ?, status = ? where id=?")) {
                psmt.setString(1, account.getAccountNumber());
                psmt.setDouble(2, account.getBalance());
                psmt.setDate(3, new java.sql.Date(account.getDateDeCreation().getTime()));
                psmt.setInt(4, account.getType().ordinal());
                psmt.setInt(5, account.getStatus());
                psmt.setInt(6, account.getId());
                psmt.executeUpdate();
            }
            return account;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException(ex);
        }
    }

    @Override
    public List<Account> findAll() throws DataAccessException {
        List<Account> result = new ArrayList<>();
        try {
            Connection conn = JDBCConnectionFactory.getConnection();
            try (PreparedStatement psmt = conn.prepareStatement("select a.id as aid, a.accountNumber, a.balance, a.status as astatus, a.type as atype, a.dateCreation, c.* from account a, customer c where a.customer_id = c.id"); 
                    ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    Account acc = new Account();
                    acc.setId(rs.getInt("aid"));
                    acc.setAccountNumber(rs.getString("accountNumber"));
                    acc.setBalance(rs.getDouble("balance"));
                    acc.setStatus(rs.getInt("astatus"));
                    acc.setType(AccountType.values()[rs.getInt("atype")]);
                    acc.setDateDeCreation(rs.getDate("dateCreation"));
                    Customer customer = new Customer();
                    customer.setId(rs.getInt("id"));
                    customer.setName(rs.getString("name"));
                    customer.setPhoneNumber(rs.getString("phoneNumber"));
                    customer.setEmailAddress(rs.getString("emailAddress"));
                    customer.setStatus(rs.getInt("status"));
                    acc.setCustomer(customer);
                    result.add(acc);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException(ex);
        }
        return result;
    }

    @Override
    public Account findById(Integer id) throws DataAccessException {
        try {
            Account result = null;
            Connection conn = JDBCConnectionFactory.getConnection();
            try (PreparedStatement psmt = conn.prepareStatement("select a.id as aid, a.accountNumber, a.balance, a.status as astatus, a.type as atype, a.dateCreation, c.* from account a, customer c where a.customer_id = c.id and a.id = ?")) {
                psmt.setInt(1, id);
                try (ResultSet rs = psmt.executeQuery()) {
                    if (rs.next()) {
                        result = new Account();
                        result.setId(rs.getInt("aid"));
                        result.setAccountNumber(rs.getString("accountNumber"));
                        result.setBalance(rs.getDouble("balance"));
                        result.setStatus(rs.getInt("astatus"));
                        result.setType(AccountType.values()[rs.getInt("atype")]);
                        result.setDateDeCreation(rs.getDate("dateCreation"));
                        Customer customer = new Customer();
                        customer.setId(rs.getInt("id"));
                        customer.setName(rs.getString("name"));
                        customer.setPhoneNumber(rs.getString("phoneNumber"));
                        customer.setEmailAddress(rs.getString("emailAddress"));
                        customer.setStatus(rs.getInt("status"));
                        result.setCustomer(customer);
                    }
                }
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException(ex);
        }
    }

    @Override
    public Account findByAccountNumber(String accountNumber) throws DataAccessException {
        try {
            Account result = null;
            Connection conn = JDBCConnectionFactory.getConnection();
            try (PreparedStatement psmt = conn.prepareStatement("select a.id as aid, a.accountNumber, a.balance, a.status as astatus, a.type as atype, a.dateCreation, c.* from account a, customer c where a.customer_id = c.id and accountNumber = ?")) {
                psmt.setString(1, accountNumber);
                try (ResultSet rs = psmt.executeQuery()) {
                    if (rs.next()) {
                        result = new Account();
                        result.setId(rs.getInt("aid"));
                        result.setAccountNumber(rs.getString("accountNumber"));
                        result.setBalance(rs.getDouble("balance"));
                        result.setStatus(rs.getInt("astatus"));
                        result.setType(AccountType.values()[rs.getInt("atype")]);
                        result.setDateDeCreation(rs.getDate("dateCreation"));
                        Customer customer = new Customer();
                        customer.setId(rs.getInt("id"));
                        customer.setName(rs.getString("name"));
                        customer.setPhoneNumber(rs.getString("phoneNumber"));
                        customer.setEmailAddress(rs.getString("emailAddress"));
                        customer.setStatus(rs.getInt("status"));
                        result.setCustomer(customer);
                    }
                }
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException(ex);
        }
    }

    @Override
    public List<Account> findByCustomer(Customer cust) throws DataAccessException {
        List<Account> result = new ArrayList<>();
        try {            
            Connection conn = JDBCConnectionFactory.getConnection();
            PreparedStatement psmt = conn.prepareStatement("select a.id as aid, a.accountNumber, a.balance, a.status as astatus, a.type as atype, a.dateCreation, c.* from account a, customer c where a.customer_id = c.id and c.id = ?");
            psmt.setInt(1, cust.getId());
            ResultSet rs  = psmt.executeQuery();
            while(rs.next()){
                Account account = new Account();
                account.setId(rs.getInt("aid"));
                account.setAccountNumber(rs.getString("accountNumber"));
                account.setBalance(rs.getDouble("balance"));
                account.setStatus(rs.getInt("astatus"));
                account.setType(AccountType.values()[rs.getInt("atype")]);
                account.setDateDeCreation(rs.getDate("dateCreation"));
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhoneNumber(rs.getString("phoneNumber"));
                customer.setEmailAddress(rs.getString("emailAddress"));
                customer.setStatus(rs.getInt("status"));
                account.setCustomer(customer);
                result.add(account);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException(ex);
        }
        return result;
    }
}
