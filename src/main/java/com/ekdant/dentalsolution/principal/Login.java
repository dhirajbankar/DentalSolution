/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.principal;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.log4j.Logger;

/**
 *
 * @author user
 */
class Login extends JFrame {

    final static Logger logger = Logger.getLogger(Login.class);
    public Login() {
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException error) {
            JOptionPane.showMessageDialog(null, "Error matching downloads theme : " + error);
            logger.error(error);
        } catch (IllegalAccessException error) {
            JOptionPane.showMessageDialog(null, "Error matching downloads theme : " + error);
            logger.error(error);
        } catch (InstantiationException error) {
            JOptionPane.showMessageDialog(null, "Error matching downloads theme : " + error);
            logger.error(error);
        } catch (UnsupportedLookAndFeelException error) {
            JOptionPane.showMessageDialog(null, "Error matching downloads theme : " + error);
            logger.error(error);
        }
        
    }
    
}
