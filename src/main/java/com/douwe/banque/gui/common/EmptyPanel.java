package com.douwe.banque.gui.common;

import javax.swing.JPanel;

/**
 *
 * @author ${user}
 */
public class EmptyPanel{
    private static final JPanel panneau = new JPanel();
    
    public static JPanel emptyPanel(){
        return panneau;
    }
}