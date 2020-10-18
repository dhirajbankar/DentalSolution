/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.principal;

import com.ekdant.dentalsolution.dao.CityDao;
import com.ekdant.dentalsolution.dao.ClinicDao;
import com.ekdant.dentalsolution.dao.SettingsDao;
import com.ekdant.dentalsolution.dao.UserDao;
import com.ekdant.dentalsolution.domain.ClinicBean;
import com.ekdant.dentalsolution.domain.SettingsBean;
import com.ekdant.dentalsolution.domain.UserBean;
import com.ekdant.dentalsolution.masters.Cities;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author Sushant Raut
 */
public class InitialClinicSettings extends javax.swing.JFrame {
    
    ClinicDao clinicDao;
    UserDao userDao;
    CityDao cityDao;
    SettingsDao settingsDao;
    int clinicId = 0;
    int staffId = 0;
    boolean newCityAdded;
    final static Logger logger = Logger.getLogger(InitialClinicSettings.class);

    /**
     * Creates new form initialSettings
     */
    public InitialClinicSettings() {
        userDao = new UserDao();
        clinicDao = new ClinicDao();
        cityDao = new CityDao();
        settingsDao = new SettingsDao();
        initComponents();
        cityDao.getCities(cityCB); 
        populateClinic();
        newCityAdded = false;
    }
    
    private void populateClinic(){
        List<ClinicBean> clinics = clinicDao.fetchClinics();
        UserBean staff = userDao.fetchStaff();
        staffId = staff.getUserId();
        ClinicBean clinic = clinics.isEmpty() ? new ClinicBean() : clinics.get(0);
        clinicId = clinic.getId();
        clinicNameTxt.setText(clinic.getName());
        clinicAddressTxt.setText(clinic.getAddress());
        clinicContactTxt.setText(clinic.getContact());
        cityCB.setSelectedItem(clinic.getCity());
        morningStartTimeCB.setSelectedItem(clinic.getMorningStartTime());
        morningEndTimeCB.setSelectedItem(clinic.getMorningEndTime());
        eveningStartTimeCB.setSelectedItem(clinic.getEveningStartTime());
        eveningEndTimeCB.setSelectedItem(clinic.getEveningEndTime());
       
    }
    
    private void validateAndSaveInformation(){
        if (validateForm()) {
            ClinicBean clinic = new ClinicBean();
            clinic.setId(clinicId);
            clinic.setName(clinicNameTxt.getText());
            clinic.setAddress(clinicAddressTxt.getText());
            clinic.setContact(clinicContactTxt.getText());
            clinic.setCity(cityCB.getSelectedItem().toString());
            clinic.setMorningStartTime(morningStartTimeCB.getSelectedItem().toString());
            clinic.setMorningEndTime(morningEndTimeCB.getSelectedItem().toString());
            clinic.setEveningStartTime(eveningStartTimeCB.getSelectedItem().toString());
            clinic.setEveningEndTime(eveningEndTimeCB.getSelectedItem().toString());
        
            settingsDao.updateSettings(new SettingsBean("CLINIC_NAME", clinicNameTxt.getText()));
            settingsDao.updateSettings(new SettingsBean("CLINIC_START_TIME", morningStartTimeCB.getSelectedItem().toString()));
            settingsDao.updateSettings(new SettingsBean("CLINIC_END_TIME", eveningEndTimeCB.getSelectedItem().toString()));
            settingsDao.updateSettings(new SettingsBean("MORNING_START_TIME", morningStartTimeCB.getSelectedItem().toString()));
            settingsDao.updateSettings(new SettingsBean("MORNING_END_TIME", morningEndTimeCB.getSelectedItem().toString()));
            settingsDao.updateSettings(new SettingsBean("EVENING_START_TIME", eveningStartTimeCB.getSelectedItem().toString()));
            settingsDao.updateSettings(new SettingsBean("EVENING_END_TIME", eveningEndTimeCB.getSelectedItem().toString()));
            
            if(clinicId == 0){
                clinicDao.addClinic(clinic);
                new EkDant();
                this.dispose();
            }
            else{
                clinicDao.updateClinic(clinic);
            }
            
            
            msgLbl.setText("Data Saved Succefully");
            msgLbl.setFont(new Font("Vardhana", Font.PLAIN, 12));
            msgLbl.setForeground(new Color(51, 51, 255));
        }
    }
    private void loadCities(boolean prePopulateWithLatestCity) {
        cityDao.getCities(cityCB);
        if(newCityAdded && prePopulateWithLatestCity){
            String newlyAddedCity = cityDao.fetchLatestCity();
            cityCB.setSelectedItem(newlyAddedCity);
        }
    }
    private boolean validateForm() {
        boolean valid = true;
        if(clinicNameTxt.getText().isEmpty()){
            valid = false;
            JOptionPane.showMessageDialog(null,"Please Enter Clinic Name","ERROR", JOptionPane.ERROR_MESSAGE);
            logger.debug("Please Enter Clinic Name");
        }
        else if(clinicContactTxt.getText().isEmpty()){
            valid = false;
            JOptionPane.showMessageDialog(null,"Please Enter Contact","ERROR", JOptionPane.ERROR_MESSAGE);
            logger.debug("Please Enter contact");
        }
        
        return valid;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        doctorDetailsPanel = new javax.swing.JPanel();
        clinicNameLbl = new javax.swing.JLabel();
        clinicNameTxt = new javax.swing.JTextField();
        clinicAddressLbl = new javax.swing.JLabel();
        clinicAddressTxt = new javax.swing.JTextField();
        clinicContactLbl = new javax.swing.JLabel();
        clinicContactTxt = new javax.swing.JTextField();
        clinicCityLbl = new javax.swing.JLabel();
        cityCB = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        morningTimeLbl = new javax.swing.JLabel();
        eveningTimeLbl = new javax.swing.JLabel();
        morningStartTimeCB = new javax.swing.JComboBox();
        morningEndTimeCB = new javax.swing.JComboBox();
        eveningStartTimeCB = new javax.swing.JComboBox();
        eveningEndTimeCB = new javax.swing.JComboBox();
        addCityBtn = new javax.swing.JButton();
        buttonPanel = new javax.swing.JPanel();
        saveBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        msgLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Clinic Details");
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        clinicNameLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        clinicNameLbl.setForeground(new java.awt.Color(51, 51, 255));
        clinicNameLbl.setText("Clinic Name:");

        clinicAddressLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        clinicAddressLbl.setForeground(new java.awt.Color(51, 51, 255));
        clinicAddressLbl.setText("Clinic Address:");

        clinicContactLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        clinicContactLbl.setForeground(new java.awt.Color(51, 51, 255));
        clinicContactLbl.setText("Contact:");

        clinicCityLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        clinicCityLbl.setForeground(new java.awt.Color(51, 51, 255));
        clinicCityLbl.setText("City:");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Add Clinic Details");

        morningTimeLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        morningTimeLbl.setForeground(new java.awt.Color(51, 51, 255));
        morningTimeLbl.setText("Morning Time");

        eveningTimeLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        eveningTimeLbl.setForeground(new java.awt.Color(51, 51, 255));
        eveningTimeLbl.setText("Evening Time");

        morningStartTimeCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06:00:00", "06:30:00", "07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00", "21:00:00", "21:30:00", "22:00:00", "22:30:00", "23:00:00", "23:30:00" }));

        morningEndTimeCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06:00:00", "06:30:00", "07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00", "21:00:00", "21:30:00", "22:00:00", "22:30:00", "23:00:00", "23:30:00" }));

        eveningStartTimeCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06:00:00", "06:30:00", "07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00", "21:00:00", "21:30:00", "22:00:00", "22:30:00", "23:00:00", "23:30:00" }));

        eveningEndTimeCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06:00:00", "06:30:00", "07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00", "21:00:00", "21:30:00", "22:00:00", "22:30:00", "23:00:00", "23:30:00" }));

        addCityBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/plus-icon.png"))); // NOI18N
        addCityBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCityBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout doctorDetailsPanelLayout = new javax.swing.GroupLayout(doctorDetailsPanel);
        doctorDetailsPanel.setLayout(doctorDetailsPanelLayout);
        doctorDetailsPanelLayout.setHorizontalGroup(
            doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                        .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, doctorDetailsPanelLayout.createSequentialGroup()
                                .addComponent(clinicContactLbl)
                                .addGap(18, 18, 18)
                                .addComponent(clinicContactTxt))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, doctorDetailsPanelLayout.createSequentialGroup()
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(morningTimeLbl)
                                        .addComponent(clinicCityLbl))
                                    .addComponent(eveningTimeLbl))
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                                .addComponent(morningStartTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(morningEndTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                                .addComponent(cityCB, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(addCityBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(eveningStartTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(eveningEndTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, doctorDetailsPanelLayout.createSequentialGroup()
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(clinicAddressLbl)
                                    .addComponent(clinicNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(clinicNameTxt)
                                    .addComponent(clinicAddressTxt))))
                        .addContainerGap(34, Short.MAX_VALUE))))
        );

        doctorDetailsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {clinicAddressLbl, clinicCityLbl, clinicContactLbl, clinicNameLbl, eveningTimeLbl, morningTimeLbl});

        doctorDetailsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {eveningEndTimeCB, eveningStartTimeCB, morningEndTimeCB, morningStartTimeCB});

        doctorDetailsPanelLayout.setVerticalGroup(
            doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clinicNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clinicNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clinicAddressLbl)
                    .addComponent(clinicAddressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(clinicContactLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clinicContactTxt))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(clinicCityLbl)
                        .addComponent(cityCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(addCityBtn))
                .addGap(18, 18, 18)
                .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                        .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(morningTimeLbl)
                            .addComponent(morningStartTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(doctorDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(eveningTimeLbl)
                            .addComponent(eveningStartTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(doctorDetailsPanelLayout.createSequentialGroup()
                        .addComponent(morningEndTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(eveningEndTimeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        doctorDetailsPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cityCB, clinicAddressLbl, clinicAddressTxt, clinicCityLbl, clinicContactLbl, clinicContactTxt, clinicNameLbl, clinicNameTxt, eveningEndTimeCB, eveningStartTimeCB, eveningTimeLbl, jLabel3, morningEndTimeCB, morningStartTimeCB, morningTimeLbl});

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Chief of Staff 2b Add.png"))); // NOI18N
        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Chief of Staff 2b Stop.png"))); // NOI18N
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cancelBtn)
                .addGap(18, 18, 18)
                .addComponent(msgLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelBtn, saveBtn});

        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtn)
                    .addComponent(cancelBtn)
                    .addComponent(msgLbl))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        buttonPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cancelBtn, msgLbl, saveBtn});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(doctorDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(444, 444, 444))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(doctorDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        validateAndSaveInformation();
    }//GEN-LAST:event_saveBtnActionPerformed

    private void addCityBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCityBtnActionPerformed
        newCityAdded = true;
        new Cities(true).setVisible(true);
    }//GEN-LAST:event_addCityBtnActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        loadCities(true);
    }//GEN-LAST:event_formWindowGainedFocus
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCityBtn;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JComboBox cityCB;
    private javax.swing.JLabel clinicAddressLbl;
    private javax.swing.JTextField clinicAddressTxt;
    private javax.swing.JLabel clinicCityLbl;
    private javax.swing.JLabel clinicContactLbl;
    private javax.swing.JTextField clinicContactTxt;
    private javax.swing.JLabel clinicNameLbl;
    private javax.swing.JTextField clinicNameTxt;
    private javax.swing.JPanel doctorDetailsPanel;
    private javax.swing.JComboBox eveningEndTimeCB;
    private javax.swing.JComboBox eveningStartTimeCB;
    private javax.swing.JLabel eveningTimeLbl;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JComboBox morningEndTimeCB;
    private javax.swing.JComboBox morningStartTimeCB;
    private javax.swing.JLabel morningTimeLbl;
    private javax.swing.JLabel msgLbl;
    private javax.swing.JButton saveBtn;
    // End of variables declaration//GEN-END:variables
}
