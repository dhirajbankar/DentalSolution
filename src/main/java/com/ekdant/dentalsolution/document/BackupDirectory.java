/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.document;


import com.ekdant.dentalsolution.dao.SettingsDao;
import com.ekdant.dentalsolution.domain.SettingsBean;
import java.io.File;
import java.net.URLDecoder;
import org.apache.log4j.Logger;

/**
 *
 * @author Sushant Raut
 */
public class BackupDirectory extends javax.swing.JFrame {

    String baseLocation = "";
    SettingsDao settingsDao;
    final static Logger logger = Logger.getLogger(BackupDirectory.class);

    public BackupDirectory() {
        settingsDao = new SettingsDao();
        initComponents();
        currentPathLbl.setText("Current backup path : " + settingsDao.getSettingValue("DB_PATH"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        msgLbl = new javax.swing.JLabel();
        documentUploaderFC = new javax.swing.JFileChooser();
        currentPathLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Upload Documents");

        msgLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        msgLbl.setForeground(new java.awt.Color(51, 51, 255));
        msgLbl.setText("Please select directory for database backup");

        documentUploaderFC.setCurrentDirectory(new java.io.File("C:\\"));
            documentUploaderFC.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
            documentUploaderFC.setOpaque(true);
            documentUploaderFC.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    documentUploaderFCActionPerformed(evt);
                }
            });

            currentPathLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
            currentPathLbl.setForeground(new java.awt.Color(51, 51, 255));

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(documentUploaderFC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(msgLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(currentPathLbl))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {currentPathLbl, msgLbl});

            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(msgLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(currentPathLbl)
                    .addGap(25, 25, 25)
                    .addComponent(documentUploaderFC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {currentPathLbl, msgLbl});

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void documentUploaderFCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentUploaderFCActionPerformed
        String databaseBackupPath = "";
        try {
            File file = documentUploaderFC.getSelectedFile();
            databaseBackupPath = URLDecoder.decode(file.getPath(), "UTF-8");
            settingsDao.updateSettings(new SettingsBean("DB_PATH", databaseBackupPath));
        } catch (Exception e) {
            logger.error(e);
        }
        this.dispose();
    }//GEN-LAST:event_documentUploaderFCActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel currentPathLbl;
    private javax.swing.JFileChooser documentUploaderFC;
    private javax.swing.JLabel msgLbl;
    // End of variables declaration//GEN-END:variables
}
