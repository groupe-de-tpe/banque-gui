package com.douwe.banque.gui.admin;

import com.douwe.banque.data.Customer;
import com.douwe.banque.gui.MainMenuPanel;
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
public class ClientPanel extends JPanel {

    private JButton nouveauBtn;
    private JButton supprimerBtn;
    private JButton modifierBtn;
    private JButton filtreBtn;
    private JTable clientTable;
    private DefaultTableModel tableModel;
    private JTextField nameText;
    private IBankService bankService;
    private MainMenuPanel parent;

    public ClientPanel(MainMenuPanel parentFrame) {
        try {
            bankService = new BankServiceImpl();
            setLayout(new BorderLayout());
            this.parent = parentFrame;
            JPanel haut = new JPanel();
            haut.setLayout(new FlowLayout(FlowLayout.CENTER));
            JLabel lbl;
            haut.add(lbl = new JLabel("LA LISTE DES CLIENTS DE MA BANQUE POPULAIRE"));
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
                    String name = nameText.getText();
                    //if ((name != null) && !("".equals(name))) {
                        try {
                            List<Customer> customers = bankService.findCustomerByName(name);                            
                            tableModel.setRowCount(0);
                            for (Customer customer : customers) {                                
                                tableModel.addRow(new Object[]{customer.getId(),
                                    customer.getName(),
                                    customer.getEmailAddress(),
                                    customer.getPhoneNumber()
                                });
                            }
                        } catch (ServiceException ex) {
                            JOptionPane.showMessageDialog(null, "Impossible de filtrer vos données");
                            Logger.getLogger(ClientPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                //}
            });
            nouveauBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    parent.setContenu(new NouveauClientPanel(parent));
                }
            });
            modifierBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    int selected = clientTable.getSelectedRow();
                    if (selected >= 0){
                        parent.setContenu(new NouveauClientPanel(parent, (Integer)tableModel.getValueAt(selected, 0)));
                    }else{
                        JOptionPane.showMessageDialog(null, "Aucun client n'est selectionné");
                    }
                }
            });
            supprimerBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    int selected = clientTable.getSelectedRow();
                    if (selected >=0){
                        try {
                            bankService.deleteCustomer((Integer)tableModel.getValueAt(selected, 0));                            
                            tableModel.removeRow(selected);
                           
                        } catch (ServiceException ex) {
                            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du client "+ tableModel.getValueAt(selected, 1));
                            Logger.getLogger(ClientPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Aucune donnée n'est selectionnée");
                    }
                }
            });
            bas.add(nouveauBtn);
            bas.add(modifierBtn);
            bas.add(supprimerBtn);
            JPanel filtrePanel = new JPanel();
            filtrePanel.setLayout(new FlowLayout());
            filtrePanel.add(new JLabel("Nom"));
            filtrePanel.add(nameText = new JTextField());
            nameText.setPreferredSize(new Dimension(100, 25));
            filtrePanel.add(filtreBtn);
            contenu.add(BorderLayout.AFTER_LAST_LINE, bas);
            contenu.add(BorderLayout.BEFORE_FIRST_LINE, filtrePanel);
            tableModel = new DefaultTableModel(new Object[]{"id", "Nom", "Adresse Email", "Téléphone"}, 0);
            clientTable = new JTable(tableModel);
            clientTable.removeColumn(clientTable.getColumnModel().getColumn(0));
            contenu.add(BorderLayout.CENTER, new JScrollPane(clientTable));
            add(BorderLayout.CENTER, contenu);
            List<Customer> customers = bankService.findAllCustomer();
            for (Customer customer : customers) {
                tableModel.addRow(new Object[]{customer.getId(),
                    customer.getName(),
                    customer.getEmailAddress(),
                    customer.getPhoneNumber()
                });
            }
        } catch (ServiceException ex) {
            Logger.getLogger(ClientPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}