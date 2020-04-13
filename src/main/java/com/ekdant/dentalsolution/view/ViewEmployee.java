package com.ekdant.dentalsolution.view;
import com.ekdant.dentalsolution.dao.CityDao;
import com.ekdant.dentalsolution.dao.EmployeeDao;
import com.ekdant.dentalsolution.domain.EmployeeBean;
import com.ekdant.dentalsolution.masters.Cities;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import com.ekdant.dentalsolution.principal.Employees;
import com.ekdant.dentalsolution.utilities.PropertiesCache;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
/**
 *
 * @author Sushant
 */
public class ViewEmployee extends javax.swing.JFrame {
    Employees employee;
    CityDao cityDao;
    EmployeeDao employeeDao;
    DateFormat displayDateFormat = new SimpleDateFormat(PropertiesCache.getInstance().getProperty("format.displaydate"));
    DateFormat databaseDateFormat = new SimpleDateFormat(PropertiesCache.getInstance().getProperty("format.dbdate"));
    DateFormat inputDateFormat = new SimpleDateFormat("d MMM, yyyy");
    List<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
    boolean newCityAdded;
    final static Logger logger = Logger.getLogger(ViewEmployee.class);
    
    /** Creates new form JF_AlterEmployee
     * @param employeeFrm
     * @param employeeId
     * @param name
     * @param birthDay
     * @param gender
     * @param address
     * @param city
     * @param telephone
     * @param email
     * @param age */
    public ViewEmployee(Employees employeeFrm,String employeeId, String name, String birthDay, String gender, String address, String city, String telephone, String email, String age) {
        employee = employeeFrm;
        initComponents();
        cityDao = new CityDao();
        employeeDao = new EmployeeDao();
        cityDao.getCities(cityCB);
        if(!"".equalsIgnoreCase(birthDay)){
            try {
                birthdayDC.setDate(displayDateFormat.parse(birthDay));
            } catch (ParseException ex) {logger.error(ex);}
        }
        employeeIdTxt.setText(employeeId);
        nameTxt.setText(name);
        genderCB.setSelectedItem(gender);
        addressTxt.setText(address);
        cityCB.setSelectedItem(city);
        mobileTxt.setText(telephone);
        emailTxt.setText(email);
        ageTxt.setText(age);
        newCityAdded = false;
    }   
   
    private void populateNewCity(){
        cityDao.getCities(cityCB);
        if(newCityAdded){
            String newlyAddedCity = cityDao.fetchLatestCity();
            cityCB.setSelectedItem(newlyAddedCity);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        employeeDetailsPanel = new javax.swing.JPanel();
        nameLbl = new javax.swing.JLabel();
        addressTxt = new javax.swing.JTextField();
        birthDayLbl = new javax.swing.JLabel();
        genderLbl = new javax.swing.JLabel();
        genderCB = new javax.swing.JComboBox();
        addressLbl = new javax.swing.JLabel();
        nameTxt = new javax.swing.JTextField();
        cityLbl = new javax.swing.JLabel();
        mobileLbl = new javax.swing.JLabel();
        emailLbl = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        mobileTxt = new javax.swing.JTextField();
        employeeIdTxt = new javax.swing.JTextField();
        cityCB = new javax.swing.JComboBox();
        ageLbl = new javax.swing.JLabel();
        ageTxt = new javax.swing.JTextField();
        newCityBtn = new javax.swing.JButton();
        birthdayDC = new com.toedter.calendar.JDateChooser();
        saveBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("View Patients");
        setName("Patients"); // NOI18N
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jTabbedPane.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        employeeDetailsPanel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        nameLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        nameLbl.setForeground(new java.awt.Color(51, 51, 255));
        nameLbl.setText("* Name:");

        addressTxt.setEnabled(false);
        addressTxt.setNextFocusableComponent(cityCB);

        birthDayLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        birthDayLbl.setForeground(new java.awt.Color(51, 51, 255));
        birthDayLbl.setText("   Date of birth:");

        genderLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        genderLbl.setForeground(new java.awt.Color(51, 51, 255));
        genderLbl.setText("* Gender:");

        genderCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female" }));
        genderCB.setEnabled(false);
        genderCB.setNextFocusableComponent(ageTxt);

        addressLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        addressLbl.setForeground(new java.awt.Color(51, 51, 255));
        addressLbl.setText("   Address:");

        nameTxt.setEnabled(false);
        nameTxt.setNextFocusableComponent(genderCB);

        cityLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        cityLbl.setForeground(new java.awt.Color(51, 51, 255));
        cityLbl.setText("   City:");

        mobileLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        mobileLbl.setForeground(new java.awt.Color(51, 51, 255));
        mobileLbl.setText("   Mobile:");

        emailLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        emailLbl.setForeground(new java.awt.Color(51, 51, 255));
        emailLbl.setText("   Email:");

        emailTxt.setEnabled(false);
        emailTxt.setNextFocusableComponent(saveBtn);

        mobileTxt.setEnabled(false);
        mobileTxt.setNextFocusableComponent(emailTxt);

        employeeIdTxt.setEditable(false);
        employeeIdTxt.setMinimumSize(new java.awt.Dimension(0, 0));
        employeeIdTxt.setPreferredSize(new java.awt.Dimension(0, 0));

        cityCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select" }));
        cityCB.setEnabled(false);
        cityCB.setNextFocusableComponent(mobileTxt);

        ageLbl.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ageLbl.setForeground(new java.awt.Color(51, 51, 255));
        ageLbl.setText("* Age:");

        ageTxt.setEnabled(false);
        ageTxt.setNextFocusableComponent(addressTxt);

        newCityBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/plus-icon.png"))); // NOI18N
        newCityBtn.setEnabled(false);
        newCityBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCityBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout employeeDetailsPanelLayout = new javax.swing.GroupLayout(employeeDetailsPanel);
        employeeDetailsPanel.setLayout(employeeDetailsPanelLayout);
        employeeDetailsPanelLayout.setHorizontalGroup(
            employeeDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(employeeDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(employeeDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cityLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addressLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ageLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(birthDayLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                        .addComponent(emailLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mobileLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(genderLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(employeeDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(employeeDetailsPanelLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(employeeIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(666, Short.MAX_VALUE))
                    .addGroup(employeeDetailsPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(employeeDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mobileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(genderCB, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(employeeDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(employeeDetailsPanelLayout.createSequentialGroup()
                                    .addComponent(cityCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(newCityBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(addressTxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(birthdayDC, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        employeeDetailsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addressLbl, ageLbl, birthDayLbl, cityLbl, emailLbl, genderLbl, mobileLbl, nameLbl});

        employeeDetailsPanelLayout.setVerticalGroup(
            employeeDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, employeeDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(employeeIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(employeeDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(employeeDetailsPanelLayout.createSequentialGroup()
                        .addComponent(nameLbl)
                        .addGap(18, 18, 18)
                        .addComponent(genderLbl)
                        .addGap(23, 23, 23)
                        .addComponent(ageLbl)
                        .addGap(18, 18, 18)
                        .addComponent(birthDayLbl)
                        .addGap(18, 18, 18)
                        .addComponent(addressLbl)
                        .addGap(18, 18, 18)
                        .addComponent(cityLbl)
                        .addGap(18, 18, 18)
                        .addComponent(mobileLbl)
                        .addGap(18, 18, 18)
                        .addComponent(emailLbl))
                    .addGroup(employeeDetailsPanelLayout.createSequentialGroup()
                        .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(genderCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(ageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(birthdayDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(employeeDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cityCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(newCityBtn))
                        .addGap(18, 18, 18)
                        .addComponent(mobileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(391, 391, 391))
        );

        employeeDetailsPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addressLbl, addressTxt, ageLbl, ageTxt, birthDayLbl, cityCB, cityLbl, emailLbl, emailTxt, genderCB, genderLbl, mobileLbl, mobileTxt, nameLbl, nameTxt, newCityBtn});

        jTabbedPane.addTab("Employee Details", new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1.png")), employeeDetailsPanel); // NOI18N

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Check.png"))); // NOI18N
        saveBtn.setMnemonic('U');
        saveBtn.setText("Update");
        saveBtn.setEnabled(false);
        saveBtn.setNextFocusableComponent(cancelBtn);
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Stop.png"))); // NOI18N
        cancelBtn.setMnemonic('C');
        cancelBtn.setText("Cancel");
        cancelBtn.setToolTipText("");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        editBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EkDant/icones/Patient Boy 1 Edit 1.png"))); // NOI18N
        editBtn.setMnemonic('E');
        editBtn.setText("Edit");
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jTabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelBtn)
                    .addComponent(saveBtn)
                    .addComponent(editBtn))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed

        String name = nameTxt.getText();
        String age = ageTxt.getText();
        if (name.length() < 3) {
            JOptionPane.showMessageDialog(null, "Please enter Employee name!", "Attention", JOptionPane.WARNING_MESSAGE);
            logger.debug("Please enter Employee name!");
        } else if (age.length() < 1) {
            JOptionPane.showMessageDialog(null, "Please enter age!", "Attention", JOptionPane.WARNING_MESSAGE);
            logger.debug("Please enter age!");
        } else {

            String city = cityCB.getSelectedItem().toString().equalsIgnoreCase("select") ? "" : cityCB.getSelectedItem().toString();

            EmployeeBean employeeBean = new EmployeeBean();
            employeeBean.setEmployeeId(Integer.parseInt(employeeIdTxt.getText()));
            employeeBean.setName(name);
            employeeBean.setBirthDate(birthdayDC.getDate());
            employeeBean.setAge(Integer.parseInt(age));
            employeeBean.setAddress(addressTxt.getText());
            employeeBean.setCity(city);
            employeeBean.setGender(genderCB.getSelectedItem().toString());
            employeeBean.setEmail(emailTxt.getText());
            employeeBean.setTelephone(mobileTxt.getText());

            if (employeeDao.updateEmployee(employeeBean)) {
                JOptionPane.showMessageDialog(null, "Employee successfully changed!", "Joined!", JOptionPane.INFORMATION_MESSAGE);
                logger.debug("Employee successfully changed!");
            } else {
                JOptionPane.showMessageDialog(null, "Unable to change employee data!", "Error!", JOptionPane.ERROR_MESSAGE);
                logger.error("Unable to change employee data!");
            }

            this.dispose();
            employee.dispose();
            new Employees().setVisible(true);
        }
    }//GEN-LAST:event_saveBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        nameTxt.setEnabled(true);
        genderCB.setEnabled(true);
        ageTxt.setEnabled(true);
        birthdayDC.setEnabled(true);
        addressTxt.setEnabled(true);
        cityCB.setEnabled(true);
        mobileTxt.setEnabled(true);
        emailTxt.setEnabled(true);
        saveBtn.setEnabled(true);
        birthdayDC.setEnabled(true);
        newCityBtn.setEnabled(true);
        panel.setEnabled(true);
        for(JCheckBox checkBox: checkBoxList){
            checkBox.setEnabled(true);
        }       
    }//GEN-LAST:event_editBtnActionPerformed

    private void newCityBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCityBtnActionPerformed
        newCityAdded = true;
        new Cities(true).setVisible(true);
    }//GEN-LAST:event_newCityBtnActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        populateNewCity();
    }//GEN-LAST:event_formWindowGainedFocus

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLbl;
    private javax.swing.JTextField addressTxt;
    private javax.swing.JLabel ageLbl;
    private javax.swing.JTextField ageTxt;
    private javax.swing.JLabel birthDayLbl;
    private com.toedter.calendar.JDateChooser birthdayDC;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JComboBox cityCB;
    private javax.swing.JLabel cityLbl;
    private javax.swing.JButton editBtn;
    private javax.swing.JLabel emailLbl;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JPanel employeeDetailsPanel;
    private javax.swing.JTextField employeeIdTxt;
    private javax.swing.JComboBox genderCB;
    private javax.swing.JLabel genderLbl;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JLabel mobileLbl;
    private javax.swing.JTextField mobileTxt;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JButton newCityBtn;
    private javax.swing.JButton saveBtn;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JPanel panel;
}
