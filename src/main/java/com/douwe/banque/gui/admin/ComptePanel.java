package com.douwe.banque.gui.admin;

import com.douwe.banque.data.Account;
import com.douwe.banque.data.AccountType;
import com.douwe.banque.gui.MainMenuPanel;
import com.douwe.banque.projection.AccountCustomer;
import com.douwe.banque.service.IBankService;
import com.douwe.banque.service.ServiceException;
import com.douwe.banque.service.impl.BankServiceImpl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

/**
 *
 * @author Vincent Douwe<douwevincent@yahoo.fr>
 */
public class ComptePanel extends JPanel {

    private JButton nouveauBtn;
    private JButton supprimerBtn;
    private JButton modifierBtn;
    private JButton filtreBtn;
    private JTable compteTable;
    private DefaultTableModel tableModel;
    private JTextField nameText;
    private JTextField numberText;
    private JComboBox<AccountType> type;
    private IBankService bankService;
    private MainMenuPanel parent;

    public ComptePanel(MainMenuPanel parentFrame) {
        try {
            bankService = new BankServiceImpl();
            this.parent = parentFrame;
            setLayout(new BorderLayout());
            JPanel haut = new JPanel();
            haut.setLayout(new FlowLayout(FlowLayout.CENTER));
            JLabel lbl;
            haut.add(lbl = new JLabel("LA LISTE DES COMPTES DE MA BANQUE POPULAIRE"));
            lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
            add(BorderLayout.BEFORE_FIRST_LINE, haut);
            JPanel contenu = new JPanel();
            contenu.setLayout(new BorderLayout());
            JPanel bas = new JPanel();
            bas.setLayout(new FlowLayout());
            nouveauBtn = new JButton("Nouveau");
            supprimerBtn = new JButton("Supprimer");
            modifierBtn = new JButton("Modifier");
            filtreBtn = new JButton("Filtrer");
            filtreBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        String client = nameText.getText();
                        String accountNumber = numberText.getText();
                        AccountType ty = (AccountType) type.getSelectedItem();
                        List<AccountCustomer> values = bankService.findAccountByCriteria(client, accountNumber, ty);                        
                        tableModel.setRowCount(0);
                        for (AccountCustomer accountCustomer : values) {                            
                            tableModel.addRow(new Object[]{accountCustomer.getId(),
                                accountCustomer.getAccountNumber(),
                                accountCustomer.getBalance(),
                                accountCustomer.getDateDeCreation(),
                                accountCustomer.getType(),
                                accountCustomer.getCustomerName()});
                        }
                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(null, "Impossible d'appliquer le filtre");
                        Logger.getLogger(ComptePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            nouveauBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    parent.setContenu(new NouveauComptePanel(parent));
                }
            });
            modifierBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    int selected = compteTable.getSelectedRow();
                    if (selected >= 0) {
                        parent.setContenu(new NouveauComptePanel(parent,(Integer)tableModel.getValueAt(selected, 0)));
                    } else {
                        JOptionPane.showMessageDialog(null, "Aucun compte selectionné");
                    }
                }
            });
            supprimerBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    int selected = compteTable.getSelectedRow();
                    if (selected >= 0) {
                        try {
                            String accountNumber = (String) tableModel.getValueAt(selected, 1);
                            Account acc = bankService.findAccountByNumber(accountNumber);
                            bankService.deleteAccount(acc.getId());
                            tableModel.removeRow(selected);
                        } catch (ServiceException ex) {
                            JOptionPane.showMessageDialog(null, "Impossible de supprimer ce compte");
                            Logger.getLogger(ComptePanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Aucune ligne de la table n'est selectionnee");
                    }

                }
            });
            bas.add(nouveauBtn);
            bas.add(modifierBtn);
            bas.add(supprimerBtn);
            JPanel filtrePanel = new JPanel();
            filtrePanel.setLayout(new FlowLayout());
            filtrePanel.add(new JLabel("Nom Client"));
            filtrePanel.add(nameText = new JTextField());
            nameText.setPreferredSize(new Dimension(100, 25));
            filtrePanel.add(new JLabel("Numero Compte"));
            filtrePanel.add(numberText = new JTextField());
            numberText.setPreferredSize(new Dimension(100, 25));
            filtrePanel.add(new JLabel("Type Compte"));
            filtrePanel.add(type = new JComboBox<>());
            type.setPreferredSize(new Dimension(100, 25));
            type.addItem(null);
            type.addItem(AccountType.deposit);
            type.addItem(AccountType.saving);
            filtrePanel.add(filtreBtn);
            contenu.add(BorderLayout.AFTER_LAST_LINE, bas);
            contenu.add(BorderLayout.BEFORE_FIRST_LINE, filtrePanel);
            tableModel = new DefaultTableModel(new Object[]{"id", "Numero Compte", "Solde", "Date création", "Type", "Client"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            compteTable = new JTable(tableModel);
            compteTable.removeColumn(compteTable.getColumnModel().getColumn(0));
            contenu.add(BorderLayout.CENTER, new JScrollPane(compteTable));
            add(BorderLayout.CENTER, contenu);
            List<AccountCustomer> values = bankService.findAllAccountCustomer();
            for (AccountCustomer accountCustomer : values) {                
                tableModel.addRow(new Object[]{accountCustomer.getId(),
                    accountCustomer.getAccountNumber(),
                    accountCustomer.getBalance(),
                    accountCustomer.getDateDeCreation(),
                    accountCustomer.getType(),
                    accountCustomer.getCustomerName()});
            }
        } catch (ServiceException ex) {
            Logger.getLogger(ComptePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
