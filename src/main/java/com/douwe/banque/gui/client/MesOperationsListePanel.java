package com.douwe.banque.gui.client;

import com.douwe.banque.data.Account;
import com.douwe.banque.data.OperationType;
import com.douwe.banque.gui.common.UserInfo;
import com.douwe.banque.projection.AccountOperation;
import com.douwe.banque.service.IBankService;
import com.douwe.banque.service.ServiceException;
import com.douwe.banque.service.impl.BankServiceImpl;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
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
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author Vincent Douwe<douwevincent@yahoo.fr>
 */
public class MesOperationsListePanel extends JPanel {

    private JComboBox<String> comptes;
    private JComboBox<String> operations;
    private JXDatePicker startDate;
    private JXDatePicker endDate;
    private JTable operationTable;
    private DefaultTableModel tableModel;
    private JButton filtreBtn;
    private IBankService bankService;

    public MesOperationsListePanel() throws Exception {
        bankService = new BankServiceImpl();
        setLayout(new BorderLayout());
        JPanel hautPanel = new JPanel(new GridLayout(2, 1));
        JPanel pan = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Label lbl;
        pan.add(lbl = new Label("LA LISTE DE MES OPERATIONS"));
        lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        hautPanel.add(pan);
        JPanel filtrePanel = new JPanel();
        filtrePanel.setLayout(new FlowLayout());
        filtreBtn = new JButton("Filtrer");
        comptes = new JComboBox<>();
        operations = new JComboBox<>();
        operations.addItem("");
        operations.addItem(OperationType.credit.toString());
        operations.addItem(OperationType.debit.toString());
        operations.addItem(OperationType.transfer.toString());
        operations.addItem(OperationType.cloture.toString());
        startDate = new JXDatePicker();
        endDate = new JXDatePicker();
        filtrePanel.add(new JLabel("Compte"));
        filtrePanel.add(comptes);
        filtrePanel.add(new JLabel("Opération"));
        filtrePanel.add(operations);
        filtrePanel.add(new JLabel("Date Début"));
        filtrePanel.add(startDate);
        filtrePanel.add(new JLabel("Date Fin"));
        filtrePanel.add(endDate);
        filtrePanel.add(filtreBtn);
        hautPanel.add(filtrePanel);
        tableModel = new DefaultTableModel(new Object[]{"Opération", "Compte", "Date", "Utilisateur", "Description"}, 0);
        operationTable = new JTable(tableModel);
        add(BorderLayout.BEFORE_FIRST_LINE, hautPanel);
        add(BorderLayout.CENTER, new JScrollPane(operationTable));
        filtreBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    String selectedCompte = (String) comptes.getSelectedItem();
                    String selectedOperation = (String) operations.getSelectedItem();
                    Date debut = startDate.getDate();
                    Date fin = endDate.getDate();
                    List<AccountOperation> result = bankService.findOperationByCriteria(selectedCompte, UserInfo.getUsername(), OperationType.valueOf(selectedOperation), debut, fin);
                    tableModel.setNumRows(0);
                    for (AccountOperation accountOperation : result) {
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
        comptes.addItem("");
        List<Account> accounts = bankService.findAccountByCustomerId(UserInfo.getCustomerId());
        for (Account account : accounts) {
            comptes.addItem(account.getAccountNumber());
        }
        List<AccountOperation> values = bankService.findOperationFromCustomerAccounts(UserInfo.getCustomerId());
        for (AccountOperation accountOperation : values) {
            tableModel.addRow(new Object[]{accountOperation.getType(),
                accountOperation.getAccountNumber(),
                accountOperation.getDateOperation(),
                accountOperation.getUsername(),
                accountOperation.getDescription()});
        }
    }
}
