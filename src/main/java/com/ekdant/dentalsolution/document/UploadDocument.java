/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.document;

import com.ekdant.dentalsolution.dao.DocumentsDao;
import com.ekdant.dentalsolution.dao.PatientsDao;
import com.ekdant.dentalsolution.dao.SettingsDao;
import com.ekdant.dentalsolution.domain.PatientBean;
import com.ekdant.dentalsolution.masters.DocumentType;
import com.ekdant.dentalsolution.utilities.PropertiesCache;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Sushant Raut
 */
public class UploadDocument extends javax.swing.JFrame {

    private int patientId; 
    DocumentsDao documentsDao;
    PatientsDao patientsDao;
    SettingsDao settingsDao;
    String baseLocation = "";
    final static Logger logger = Logger.getLogger(UploadDocument.class);
    
    public UploadDocument(int patientId) {
        settingsDao = new SettingsDao();
        baseLocation = settingsDao.getMySQLPath() + File.separator + PropertiesCache.getInstance().getProperty("folder.document");
        
        this.patientId = patientId;        
        documentsDao = new DocumentsDao();
        patientsDao = new PatientsDao();
        initComponents();
        populateDocumentTypes();
    }
    
    private void populateDocumentTypes(){
        List<String> documentTypes = documentsDao.fetchDocumentTypes();
        for (String documentType : documentTypes){
            documentTypeCB.addItem(documentType);
        }
    }
    
    private void loadDocumentTypes(boolean prePopulateWithLatestCity) {
        documentsDao.getDocumentTypes(documentTypeCB);
        if(prePopulateWithLatestCity){
            String newlyAddedDocumentType = documentsDao.fetchLatestDocumentType();
            documentTypeCB.setSelectedItem(newlyAddedDocumentType);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        documentTypeCB = new javax.swing.JComboBox();
        documentUploaderFC = new javax.swing.JFileChooser();
        newDocumentTypeBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Upload Documents");

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Please upload document");

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("Please select document type");

        documentUploaderFC.setCurrentDirectory(new java.io.File("C:\\"));
            documentUploaderFC.setOpaque(true);
            documentUploaderFC.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    documentUploaderFCActionPerformed(evt);
                }
            });

            newDocumentTypeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/plus-icon.png"))); // NOI18N
            newDocumentTypeBtn.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    newDocumentTypeBtnActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(documentUploaderFC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(documentTypeCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(newDocumentTypeBtn))
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(newDocumentTypeBtn)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(documentTypeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(18, 18, 18)
                    .addComponent(documentUploaderFC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {documentTypeCB, jLabel1, jLabel2, newDocumentTypeBtn});

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void documentUploaderFCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentUploaderFCActionPerformed
        String documentType = documentTypeCB.getSelectedItem().toString();
        PatientBean patient = patientsDao.fetchPatientById(patientId);
        String newFileDirLocation = baseLocation + File.separator+ patient.getCaseId() + File.separator + documentType;
        try{
            File file = documentUploaderFC.getSelectedFile();
            File newFileDir = new File(newFileDirLocation);
            if(!newFileDir.exists()){
                newFileDir.mkdirs();
            }
            
            InputStream in = new FileInputStream(file);
            OutputStream out = new FileOutputStream(newFileDirLocation + File.separator +file.getName());
            
            byte[] buff = new byte[1024];
            int len;
            while((len = in.read(buff)) > 0){
                out.write(buff, 0, len);
            }
            in.close();
            out.close();
        } catch(Exception e){ logger.error(e);}
        this.dispose();
    }//GEN-LAST:event_documentUploaderFCActionPerformed

    private void newDocumentTypeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newDocumentTypeBtnActionPerformed
        new DocumentType(true).setVisible(true);
        loadDocumentTypes(true);
    }//GEN-LAST:event_newDocumentTypeBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox documentTypeCB;
    private javax.swing.JFileChooser documentUploaderFC;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton newDocumentTypeBtn;
    // End of variables declaration//GEN-END:variables
}
