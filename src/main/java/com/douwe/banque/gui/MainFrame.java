package com.douwe.banque.gui;

import com.douwe.banque.dao.jdbc.JDBCConnectionFactory;
import com.douwe.banque.gui.common.LoginPanel;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class MainFrame extends JFrame {
    
    private HeaderPanel headerPanel;
    private JPanel contentPanel;
    
    public MainFrame() {
        setTitle("Ma banque populaire vraiment populaire");
        getContentPane().setLayout(new BorderLayout(10, 10));
        headerPanel = new HeaderPanel() {
            @Override
            public void deconnexion() {
                contentPanel.removeAll();
                contentPanel.add(BorderLayout.CENTER, new LoginPanel() {
                    @Override
                    public void success() {
                        contentPanel.removeAll();
                        contentPanel.add(BorderLayout.CENTER, new MainMenuPanel());
                        contentPanel.validate();
                        headerPanel.setEnabledHeader(true);
                    }
                });
                contentPanel.validate();
            }
        };
        getContentPane().add(headerPanel, BorderLayout.BEFORE_FIRST_LINE);
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        JPanel login = new LoginPanel() {
            @Override
            public void success() {
                contentPanel.removeAll();
                contentPanel.add(BorderLayout.CENTER, new MainMenuPanel());
                contentPanel.validate();
                headerPanel.setEnabledHeader(true);
            }
        };
        contentPanel.add(login, BorderLayout.CENTER);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    JDBCConnectionFactory.getConnection().close();
                    System.exit(0);
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
    }
}
