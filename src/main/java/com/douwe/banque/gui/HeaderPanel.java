package com.douwe.banque.gui;

import com.douwe.banque.gui.common.UserInfo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXHyperlink;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public abstract class HeaderPanel extends JPanel{

    private final JXHyperlink deconnexion;
    private final JLabel username;
    public HeaderPanel(){
        setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));        
        setBackground(new Color(166, 202, 240));
        //JPanel pan = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
        //pan.setBorder(new EmptyBorder(0, 0, 0, 10));
        setPreferredSize(new Dimension(800,180));
        username = new JLabel("Bonjour,");
        deconnexion = new JXHyperlink();
        deconnexion.setUnclickedColor(Color.blue);
        deconnexion.setClickedColor(Color.blue);
        deconnexion.setText("Deconnexion");
        deconnexion.setVisible(false);
        username.setVisible(false);
        add(username);
        add(deconnexion);
        //add(BorderLayout.CENTER,pan);
        deconnexion.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                deconnexion();
                setEnabledHeader(false);
            }
        });
    }
    
    public abstract void deconnexion();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font font = new Font("Serif", Font.PLAIN, 32);
        GradientPaint gp = new GradientPaint(0f,0f,Color.blue,0f,30f,Color.green);
        ((Graphics2D)g).setPaint(gp);
        g.setFont(font);
        g.setColor(Color.yellow);
        g.drawString("Ma banque vraiment populaire",340,80);
        g.setColor(Color.ORANGE);
        g.drawString("Je suis trop fort en design et en choix de couleur",260,120);
    }

    public void setEnabledHeader(boolean status){
        username.setVisible(status);
        deconnexion.setVisible(status);
        if(status){
              username.setText("Bonjour "+UserInfo.getUsername()+", ");
        }
    }

    
}
