package com.douwe.banque.gui.admin;

import com.douwe.banque.data.RoleType;
import com.douwe.banque.data.User;
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Vincent Douwe<douwevincent@yahoo.fr>
 */
public class NouveauUtilisateurPanel extends JPanel {

    private JTextField loginText;
    private JPasswordField passwdText1;
    private JPasswordField passwdText2;
    private JComboBox<RoleType> role;
    private JButton enregistrer;
    private IBankService bankService;
    private MainMenuPanel parent;

    public NouveauUtilisateurPanel(MainMenuPanel parentFrame) {
        bankService = new BankServiceImpl();
        setLayout(new BorderLayout(20, 20)); 
        this.parent = parentFrame;
        JPanel haut = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl;
        haut.add(lbl = new JLabel("AJOUT D'UN NOUVEAU UTILISATEUR DANS MA BANQUE POPULAIRE"));
        lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        add(BorderLayout.BEFORE_FIRST_LINE, haut);
        DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 180dlu:",""));
        builder.append("Login", loginText = new JTextField());
        builder.append("Mot de Passe", passwdText1 = new JPasswordField());
        builder.append("Retapez mot de passe",passwdText2 = new JPasswordField());
        builder.append("Role", role = new JComboBox<>());
        role.addItem(null);
        role.addItem(RoleType.admin);
        role.addItem(RoleType.employee);
        builder.append(enregistrer = new JButton("Enregistrer"));
        add(BorderLayout.CENTER,builder.getPanel());
        enregistrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    String login = loginText.getText();
                    String pwd = new String(passwdText1.getPassword());
                    String pwd2 = new String(passwdText2.getPassword());
                    RoleType ro = (RoleType) role.getSelectedItem();
                    if((login == null) || ("".equals(login))){
                        JOptionPane.showMessageDialog(null, "Le login est obligatoire");
                        return;
                    }
                    if("".equals(pwd)){
                        JOptionPane.showMessageDialog(null, "Le password est obligatoire");
                        return;
                    }
                    if(!(pwd.equals(pwd2))){
                        JOptionPane.showMessageDialog(null, "Les mots de passe ne sont pas identiques");
                        return;
                    }
                    if(ro == null){
                        JOptionPane.showMessageDialog(null, "Le role est obligatoire");
                        return;
                    }
                    User user = new User();
                    user.setLogin(login.toLowerCase());
                    user.setPassword(pwd);
                    user.setRole(ro);
                    bankService.saveOrUpdateUser(user);
                    parent.setContenu(new UtilisateurPanel(parent));
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null, "Impossible de créer le compte");
                    Logger.getLogger(NouveauUtilisateurPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
