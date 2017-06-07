package com.douwe.banque.gui.admin;

import com.douwe.banque.data.Customer;
import com.douwe.banque.gui.MainMenuPanel;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Vincent Douwe<douwevincent@yahoo.fr>
 */
public class NouveauClientPanel extends JPanel {

    private JTextField nameText;
    private JTextField emailText;
    private JTextField phoneText;
    private JButton btnEnregistrer;
    private int id = -1;
    private MainMenuPanel parent;
    private IBankService bankService;
    private Customer customer;

    public NouveauClientPanel(MainMenuPanel parentFrame, int id) {
        this(parentFrame);
        this.id = id;
        if (id > 0) {
            try {
                customer = bankService.findCustomerById(id);
                if (customer != null) {
                    nameText.setText(customer.getName());
                    emailText.setText(customer.getEmailAddress());
                    phoneText.setText(customer.getPhoneNumber());
                }
            } catch (ServiceException ex) {
                Logger.getLogger(NouveauClientPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public NouveauClientPanel(MainMenuPanel parentFrame) {
        this.parent = parentFrame;
        bankService = new BankServiceImpl();
        setLayout(new BorderLayout(10, 10));
        JPanel haut = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl;
        haut.add(lbl = new JLabel("AJOUT D'UN NOUVEAU CLIENT DANS MA BANQUE POPULAIRE"));
        lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        add(BorderLayout.BEFORE_FIRST_LINE, haut);
        DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 180dlu:", ""));
        builder.append("Nom", nameText = new JTextField());
        builder.append("Adresse Email", emailText = new JTextField());
        builder.append("Numéro de Téléphone", phoneText = new JTextField());
        builder.append(btnEnregistrer = new JButton("Enrégistrer"));
        add(BorderLayout.CENTER, builder.getPanel());
        btnEnregistrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (id <= 0) {
                    try {
                        String name = nameText.getText();
                        String email = emailText.getText();
                        String phone = phoneText.getText();
                        if ((name == null) || ("".equals(name))) {
                            JOptionPane.showMessageDialog(null, "Le nom est obligatoire");
                            return;
                        }
                        if ((email == null) || ("".equals(email))) {
                            JOptionPane.showMessageDialog(null, "L'adresse email est obligatoire");
                            return;
                        }
                        if ((phone == null) || ("".equals(phone))) {
                            JOptionPane.showMessageDialog(null, "Le numéro de téléphone est obligatoire");
                            return;
                        }
                        Customer customer = new Customer();
                        customer.setEmailAddress(email);
                        customer.setPhoneNumber(phone);
                        customer.setName(name);
                        Customer t = bankService.saveOrUpdateCustomer(customer);
                        if (t.getUser() != null){
                            String val = t.getUser().getLogin();
                            JOptionPane.showMessageDialog(null, "Un compte avec login " + val + " et mot de passe 'admin' a été créé");
                        }
                        parent.setContenu(new ClientPanel(parent));
                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(null, "Impossible de créer le compte");
                        Logger.getLogger(NouveauClientPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        String name = nameText.getText();
                        String email = emailText.getText();
                        String phone = phoneText.getText();
                        if ((name == null) || ("".equals(name))) {
                            JOptionPane.showMessageDialog(null, "Le nom est obligatoire");
                            return;
                        }
                        if ((email == null) || ("".equals(email))) {
                            JOptionPane.showMessageDialog(null, "L'adresse email est obligatoire");
                            return;
                        }
                        if ((phone == null) || ("".equals(phone))) {
                            JOptionPane.showMessageDialog(null, "Le numéro de téléphone est obligatoire");
                            return;
                        }
                        customer.setName(name);
                        customer.setPhoneNumber(phone);
                        customer.setEmailAddress(email);
                        bankService.saveOrUpdateCustomer(customer);
                        parent.setContenu(new ClientPanel(parent));
                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(null, "Impossible de mettre a jour votre compte");
                        Logger.getLogger(NouveauClientPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
}