package com.douwe.banque.gui.admin;

import com.douwe.banque.data.OperationType;
import com.douwe.banque.gui.client.MesOperationsListePanel;
import com.douwe.banque.projection.AccountOperation;
import com.douwe.banque.service.IBankService;
import com.douwe.banque.service.ServiceException;
import com.douwe.banque.service.impl.BankServiceImpl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author Vincent Douwe<douwevincent@yahoo.fr>
 */
public class OperationsPanel extends JPanel {

    private JTable operationTable;
    private DefaultTableModel tableModel;
    private JTextField clientText;
    private JTextField compteText;
    private JComboBox<OperationType> type;
    private JButton filtreBtn;
    private JXDatePicker startDate;
    private JXDatePicker endDate;
    private IBankService bankService;

    public OperationsPanel() {
        try {
            bankService = new BankServiceImpl();
            setLayout(new BorderLayout());
            JPanel haut = new JPanel();
            haut.setLayout(new FlowLayout(FlowLayout.CENTER));
            JLabel lbl;
            haut.add(lbl = new JLabel("LA LISTE DES OPERATIONS DE MA BANQUE POPULAIRE"));
            lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
            add(BorderLayout.BEFORE_FIRST_LINE, haut);
            JPanel contenu = new JPanel();
            contenu.setLayout(new BorderLayout());
            JPanel filtrePanel = new JPanel();
            filtreBtn = new JButton("Filtrer");
            filtreBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        String selectedCompte = compteText.getText();
                        OperationType selectedOperation = (OperationType) type.getSelectedItem();
                        String client = clientText.getText();
                        Date debut = startDate.getDate();
                        Date fin = endDate.getDate();
                        StringBuilder builder = new StringBuilder("select operations.*, account.accountNumber, users.username from operations, account, users where operations.account_id=account.id and operations.user_id=users.id");
                        if ((selectedCompte != null) && !("".equals(selectedCompte))) {
                            builder.append(" and accountNumber like '%");
                            builder.append(selectedCompte);
                            builder.append("%'");
                        }
                        if ((client != null) && !("".equals(client))) {
                            builder.append(" and username like '%");
                            builder.append(selectedCompte);
                            builder.append("%'");
                        }
                        if (selectedOperation != null) {
                            int index = selectedOperation.ordinal();
                            builder.append(" and operationType = ");
                            builder.append(index);
                        }
                        if (debut != null) {
                            builder.append(" and dateOperation >= '");
                            builder.append(debut.getTime());
                            builder.append("'");
                        }
                        if (fin != null) {
                            builder.append(" and dateOperation <= '");
                            builder.append(fin.getTime());
                            builder.append("'");
                        }
                        List<AccountOperation> operations = bankService.findOperationByCriteria(selectedCompte, client, selectedOperation, debut, fin);
                        tableModel.setNumRows(0);
                        for (AccountOperation accountOperation : operations) {
                            tableModel.addRow(new Object[]{accountOperation.getType(),
                                accountOperation.getAccountNumber(),
                                accountOperation.getDateOperation(),
                                accountOperation.getUsername(),
                                accountOperation.getDescription()});
                        }
                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(null, "Impossible de filtrer vos données");
                        Logger.getLogger(MesOperationsListePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            filtrePanel.setLayout(new FlowLayout());
            filtrePanel.add(new JLabel("Nom Client"));
            filtrePanel.add(clientText = new JTextField());
            clientText.setPreferredSize(new Dimension(100, 25));
            filtrePanel.add(new JLabel("Numero Compte"));
            filtrePanel.add(compteText = new JTextField());
            compteText.setPreferredSize(new Dimension(100, 25));
            filtrePanel.add(new JLabel("Type Compte"));
            filtrePanel.add(type = new JComboBox<>());
            type.setPreferredSize(new Dimension(100, 25));
            type.addItem(null);
            type.addItem(OperationType.cloture);
            type.addItem(OperationType.credit);
            type.addItem(OperationType.debit);
            type.addItem(OperationType.transfer);
            filtrePanel.add(new JLabel("Début"));
            filtrePanel.add(startDate = new JXDatePicker());
            filtrePanel.add(new JLabel("Fin"));
            filtrePanel.add(endDate = new JXDatePicker());
            filtrePanel.add(filtreBtn);
            contenu.add(BorderLayout.BEFORE_FIRST_LINE, filtrePanel);
            tableModel = new DefaultTableModel(new Object[]{"Opération", "Compte", "Date", "Utilisateur", "Description"}, 0);
            operationTable = new JTable(tableModel);
            contenu.add(BorderLayout.CENTER, new JScrollPane(operationTable));
            add(BorderLayout.CENTER, contenu);
            /*conn = DriverManager.getConnection("jdbc:sqlite:banque.db");
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("select operations.*, account.accountNumber, users.username from operations, account, users where account.id = operations.account_id and users.id = operations.user_id");
             while (rs.next()) {
             tableModel.addRow(new Object[]{OperationType.values()[rs.getInt("operationType")],
             rs.getString("accountNumber"),
             rs.getDate("dateOperation"),
             rs.getString("username"),
             rs.getString("description")});
             }
             rs.close();
             st.close();
             conn.close();*/
            List<AccountOperation> ops = bankService.findAllOperations();
            for (AccountOperation accountOperation : ops) {
                tableModel.addRow(new Object[]{accountOperation.getType(),
                    accountOperation.getAccountNumber(),
                    accountOperation.getDateOperation(),
                    accountOperation.getUsername(),
                    accountOperation.getDescription()});
            }
        } catch (ServiceException ex) {
            Logger.getLogger(OperationsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}