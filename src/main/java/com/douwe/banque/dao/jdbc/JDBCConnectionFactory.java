package com.douwe.banque.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class JDBCConnectionFactory {
    private static final Connection conn = createConnection();
    
    private static Connection createConnection(){
        try {
            return DriverManager.getConnection("jdbc:sqlite:banque.db");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Connection getConnection() {
        return conn;
    }
}
