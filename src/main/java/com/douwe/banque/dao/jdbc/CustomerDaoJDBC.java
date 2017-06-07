package com.douwe.banque.dao.jdbc;

import com.douwe.banque.dao.DataAccessException;
import com.douwe.banque.dao.ICustomerDao;
import com.douwe.banque.data.Customer;
import com.douwe.banque.data.RoleType;
import com.douwe.banque.data.User;
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
public class CustomerDaoJDBC implements ICustomerDao {

    @Override
    public Customer save(Customer customer) throws DataAccessException {
        try {
            Connection conn = JDBCConnectionFactory.getConnection();
            try (PreparedStatement psmt = conn.prepareStatement("insert into customer(name, emailAddress, phoneNumber, user_id, status) values (?,?,?,?,?)")) {
                psmt.setString(1, customer.getName());
                psmt.setString(2, customer.getEmailAddress());
                psmt.setString(3, customer.getPhoneNumber());
                psmt.setLong(4, customer.getUser().getId());
                psmt.setInt(5, 0);
                psmt.executeUpdate();
                ResultSet res = psmt.getGeneratedKeys();
                if (res.next()) {
                    customer.setId(res.getInt(1));
                }
                
            }
            return customer;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void delete(Customer customer) throws DataAccessException {
        try {
            Connection conn = JDBCConnectionFactory.getConnection();
            try (PreparedStatement psmt = conn.prepareStatement("delete from customer where id = ?")) {
                psmt.setLong(1, customer.getId());
                psmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException(ex);
        }
    }

    @Override
    public Customer update(Customer customer) throws DataAccessException {
        try {
            Connection conn = JDBCConnectionFactory.getConnection();
            try (PreparedStatement psmt = conn.prepareStatement("update customer set name = ?, emailAddress = ?, phoneNumber = ?, status = ? where id = ?")) {
                psmt.setString(1, customer.getName());
                psmt.setString(2, customer.getEmailAddress());
                psmt.setString(3, customer.getPhoneNumber());
                psmt.setInt(4, customer.getStatus());
                psmt.setInt(5, customer.getId());
                psmt.executeUpdate();
            }
            return customer;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException(ex);
        }
    }

    @Override
    public Customer findById(Integer id) throws DataAccessException {
        try {
            Customer res = null;
            Connection conn = JDBCConnectionFactory.getConnection();
            try (PreparedStatement psmt = conn.prepareStatement("select c.name, c.emailAddress, c.id as cid, c.phoneNumber, c.status as cstatus, u.* from customer c, users u where  c.user_id = u.id and c.id = ?")) {
                psmt.setInt(1, id);
                try (ResultSet rs = psmt.executeQuery()) {
                    if (rs.next()) {
                        res = new Customer();
                        res.setName(rs.getString("name"));
                        res.setEmailAddress(rs.getString("emailAddress"));
                        res.setId(rs.getInt("cid"));
                        res.setStatus(rs.getInt("cstatus"));
                        res.setPhoneNumber(rs.getString("phoneNumber"));
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setLogin(rs.getString("username"));
                        user.setPassword(rs.getString("passwd"));
                        user.setRole(RoleType.values()[rs.getInt("role")]);
                        user.setStatus(rs.getInt("status"));
                        res.setUser(user);
                    }
                }
            }
            return res;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException(ex);
        }
    }

    @Override
    public List<Customer> findAll() throws DataAccessException {
        try {
            List<Customer> result = new ArrayList<>();
            Connection conn = JDBCConnectionFactory.getConnection();
            try (PreparedStatement psmt = conn.prepareStatement("select * from customer"); ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    Customer res = new Customer();
                    res.setName(rs.getString("name"));
                    res.setEmailAddress(rs.getString("emailAddress"));
                    res.setId(rs.getInt("id"));
                    res.setStatus(rs.getInt("status"));
                    res.setPhoneNumber(rs.getString("phoneNumber"));
                    result.add(res);
                }
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException(ex);
        }
    }

    @Override
    public Customer findByUser(User us) throws DataAccessException {
        Customer customer = null;
        try {
            Connection conn = JDBCConnectionFactory.getConnection();
            try (PreparedStatement psmt = conn.prepareStatement("select c.id as cid, c.emailAddress, c.phoneNumber, c.name,c.status as cstatus, u.id, u.username, u.passwd, u.status, u.role from customer c, users u where c.user_id=u.id and u.username = ?")) {
                psmt.setString(1, us.getLogin());
                try (ResultSet rs = psmt.executeQuery()) {
                    if (rs.next()) {
                        customer = new Customer();
                        User user = new User();
                        customer.setId(rs.getInt("cid"));
                        customer.setEmailAddress(rs.getString("emailAddress"));
                        customer.setPhoneNumber(rs.getString("phoneNumber"));
                        customer.setStatus(rs.getInt("cstatus"));
                        customer.setName(rs.getString("name"));
                        user.setId(rs.getInt("id"));
                        user.setLogin(rs.getString("username"));
                        user.setPassword(rs.getString("passwd"));
                        user.setRole(RoleType.values()[rs.getInt("role")]);
                        user.setStatus(rs.getInt("status"));
                        customer.setUser(user);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException(ex);
        }
        return customer;
    }

    @Override
    public Customer findByName(String customerName) throws DataAccessException {
        Customer customer = null;
        try {
            Connection conn = JDBCConnectionFactory.getConnection();
            try (PreparedStatement psmt = conn.prepareStatement("select c.id as cid, c.emailAddress, c.phoneNumber, c.name,c.status as cstatus, u.id, u.username, u.passwd, u.status, u.role from customer c, users u where c.user_id=u.id and c.name = ?")) {
                psmt.setString(1, customerName);
                try (ResultSet rs = psmt.executeQuery()) {
                    if (rs.next()) {
                        customer = new Customer();
                        User user = new User();
                        customer.setId(rs.getInt("cid"));
                        customer.setEmailAddress(rs.getString("emailAddress"));
                        customer.setPhoneNumber(rs.getString("phoneNumber"));
                        customer.setStatus(rs.getInt("cstatus"));
                        customer.setName(rs.getString("name"));
                        user.setId(rs.getInt("id"));
                        user.setLogin(rs.getString("username"));
                        user.setPassword(rs.getString("passwd"));
                        user.setRole(RoleType.values()[rs.getInt("role")]);
                        user.setStatus(rs.getInt("status"));
                        customer.setUser(user);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException(ex);
        }
        return customer;
    }
}
