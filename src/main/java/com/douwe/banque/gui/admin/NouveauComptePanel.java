package com.douwe.banque.gui.admin;

import com.douwe.banque.data.Account;
import com.douwe.banque.data.AccountType;
import com.douwe.banque.data.Customer;
import com.douwe.banque.gui.MainMenuPanel;
import com.douwe.banque.projection.AccountCustomer;
import com.douwe.banque.service.IBankService;
import com.douwe.banque.service.ServiceException;
import com.douwe.banque.service.impl.BankServiceImpl;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Vincent Douwe<douwevincent@yahoo.fr>
 */
public class NouveauComptePanel extends JPanel {

    private JTextField numberText;
    private JTextField balanceText;
    private JComboBox<AccountType> typeText;
    private JTextField customerText;
    private JButton btnEnregistrer;
    private int id = -1;
    private IBankService bankService;
    private MainMenuPanel parent;

    public NouveauComptePanel(MainMenuPanel parentFrame, int account_id) {
        this(parentFrame);
        this.id = account_id;
        if (this.id > 0) {
            btnEnregistrer.setText("Modifier");
            try {
                //conn = DriverManager.getConnection("jdbc:sqlite:banque.db");
                AccountCustomer result =  bankService.findAccountCustomerById(id);                
                if (result != null) {
                    numberText.setText(result.getAccountNumber());
                    balanceText.setText(String.valueOf(result.getBalance()));
                    balanceText.setEnabled(false);
                    customerText.setEnabled(false);
                    customerText.setText(result.getCustomerName());
                    typeText.setSelectedItem(result.getType());
                }
            } catch (ServiceException ex) {
                Logger.getLogger(NouveauComptePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public NouveauComptePanel(MainMenuPanel parentFrame) {
        this.parent = parentFrame;
        bankService = new BankServiceImpl();
        setLayout(new BorderLayout(10, 10));
        JPanel haut = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl;
        haut.add(lbl = new JLabel("AJOUT D'UN NOUVEAU COMPTE DANS MA BANQUE POPULAIRE"));
        lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        add(BorderLayout.BEFORE_FIRST_LINE, haut);
        DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 180dlu:", ""));
        builder.append("Numéro Compte", numberText = new JTextField());
        builder.append("Solde initial", balanceText = new JTextField());
        builder.append("Type de Compte", typeText = new JComboBox<>());
        typeText.addItem(AccountType.deposit);
        typeText.addItem(AccountType.saving);
        builder.append("Titulaire", customerText = new JTextField());
        builder.append(btnEnregistrer = new JButton("Enrégistrer"));
        add(BorderLayout.CENTER, builder.getPanel());
        btnEnregistrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (id > 0) {
                    try {
                        // here I'm updating an account
                        String number = numberText.getText();
                        AccountType type = (AccountType) typeText.getSelectedItem();
                        if ((number == null) || ("".equals(number))) {
                            JOptionPane.showMessageDialog(null, "Le numéro du compte n'est pas specifie");
                            return;
                        }
                        if (type == null) {
                            JOptionPane.showMessageDialog(null, "Le type du compte n'est pas specifie");
                            return;
                        }
                        Account acc = bankService.findAccountById(id);
                        acc.setType(type);
                        acc.setAccountNumber(number);
                        bankService.saveOrUpdateAccount(acc);
                        //conn.close();
                        parent.setContenu(new ComptePanel(parent));
                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(null, "Impossible de mettre à jour le compte");
                        Logger.getLogger(NouveauComptePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    try {
                        // I need to get the customer_id
                        String customer = customerText.getText();
                        String number = numberText.getText();
                        String balanceT = balanceText.getText();
                        AccountType type = (AccountType) typeText.getSelectedItem();
                        double balance;
                        if ((customer == null) || ("".equals(customer))) {
                            JOptionPane.showMessageDialog(null, "Le nom du client n'est pas specifie");
                            return;
                        }
                        if ((number == null) || ("".equals(number))) {
                            JOptionPane.showMessageDialog(null, "Le numéro du compte n'est pas specifie");
                            return;
                        }
                        if ((balanceT == null) || ("".equals(balanceT))) {
                            JOptionPane.showMessageDialog(null, "Le solde initial du compte n'est pas specifie");
                            return;
                        }
                        if (type == null) {
                            JOptionPane.showMessageDialog(null, "Le type du compte n'est pas specifie");
                            return;
                        }
                        try {
                            balance = Double.valueOf(balanceT);
                        } catch (NumberFormatException nfe) {
                            JOptionPane.showMessageDialog(null, "Le solde compte doit être un nombre positif");
                            return;
                        }
                        if (balance < 0) {
                            JOptionPane.showMessageDialog(null, "Le solde compte doit être un nombre positif");
                            return;
                        }
                        Customer cc = bankService.getSingleCustomerByName(customer);
                        if (cc != null) { 
                            Account acc = new Account(number,balance,new java.util.Date(),type, cc,0);
                            bankService.saveOrUpdateAccount(acc);
                        } else {
                            JOptionPane.showMessageDialog(null, "Le client spécifié n'existe pas");
                            return;
                        }
                        parent.setContenu(new ComptePanel(parent));
                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(null, "Impossible d'enregistrer le compte");
                        Logger.getLogger(NouveauComptePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
}
