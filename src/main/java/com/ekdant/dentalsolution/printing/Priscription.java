/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.printing;

import com.ekdant.dentalsolution.dao.CheckUpDao;
import com.ekdant.dentalsolution.dao.ClinicDao;
import com.ekdant.dentalsolution.dao.SettingsDao;
import com.ekdant.dentalsolution.domain.CheckupBean;
import com.ekdant.dentalsolution.domain.ClinicBean;
import com.ekdant.dentalsolution.domain.PriscriptionBean;
import com.ekdant.dentalsolution.utilities.PropertiesCache;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.URL;
import java.text.AttributedString;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;

/**
 *
 * @author dinesh.mali
 */
public class Priscription extends Canvas implements Printable{
    int patientTreatmentId = 0;
    CheckUpDao checkUpDao;
    ClinicDao clinicDao;
    SettingsDao settingsDao;
    int pageCenter = 300;
    DateFormat displayDateFormat = new SimpleDateFormat(PropertiesCache.getInstance().getProperty("format.displaydate"));
    final static Logger logger = Logger.getLogger(Priscription.class);
    
    public Priscription(int treatmentId){
        patientTreatmentId = treatmentId;
        checkUpDao = new CheckUpDao();
        clinicDao = new ClinicDao();
        settingsDao = new SettingsDao();
    }

    public int print(Graphics gx, PageFormat pf, int page) throws PrinterException {
        if (page > 0 || patientTreatmentId == 0) {
            return NO_SUCH_PAGE;
        } 
        Graphics2D g = (Graphics2D) gx; 
        g.translate(pf.getImageableX(), pf.getImageableY()); 
        
        CheckupBean checkup = checkUpDao.fetchCheckup(patientTreatmentId);
        printHeader(g);
        printPatientInfo(g, checkup);
        printMedicine(g, checkup);
        printFooter(g);
        return PAGE_EXISTS; 
    }
    
    private void printFooter(Graphics g){
        
        Font fontClinicName = new Font("Serif", Font.PLAIN, 12);
        List<ClinicBean> clinics = clinicDao.fetchClinics();
        ClinicBean clinic = clinics.get(0);
        String clinicAddr = clinic.getAddress() + " , " +clinic.getCity();

        AttributedString clinicAddress = new AttributedString(clinicAddr);
        clinicAddress.addAttribute(TextAttribute.FONT, fontClinicName);
        clinicAddress.addAttribute(TextAttribute.FOREGROUND, Color.BLUE);
                
        g.setFont(fontClinicName);
        g.drawLine(10, 700, 550, 700);
        g.drawString(clinicAddress.getIterator(), pageCenter - getLength(clinicAddr, g) / 2, 730);
        
    }

    private void printHeader(Graphics g) {
        
        List<ClinicBean> clinics = clinicDao.fetchClinics();
        ClinicBean clinic = clinics.get(0);
        String clinicName = settingsDao.getSettingValue("CLINIC_NAME");
        String doctorNm = settingsDao.getSettingValue("DOCTOR_NAME");
        String doctorContact = settingsDao.getSettingValue("DOCTOR_MOBILE");
        String doctorEmail = settingsDao.getSettingValue("DOCTOR_EMAIL");
        String doctorDegree = settingsDao.getSettingValue("DOCTOR_DEGREES");
        String doctorReg = settingsDao.getSettingValue("DOCTOR_REGISTRATION_NO");
        
        String contactNumber = clinic.getContact();
        URL iconURL = getClass().getResource("/EkDant/icones/priscription.jpg");
        Image img;
        if (iconURL == null) {
            logger.debug("Icon file not found for print.");
        } else {
            try {
                img = ImageIO.read(iconURL);
                
                g.drawImage(img, 480, 10, 530, 60, 0, 0, 50, 50, null);
             } catch (IOException ex) {
                logger.error("file not found");
             }
        }
        Font fontClinicName = new Font("Serif", Font.BOLD, 25);
        Font fontDoctorDetails = new Font("Serif", Font.BOLD, 10); 
        Font fontDoctorNameDetails = new Font("Serif", Font.BOLD, 12);
        

        AttributedString clinicNm = new AttributedString(clinicName);
        clinicNm.addAttribute(TextAttribute.FONT, fontClinicName);
        clinicNm.addAttribute(TextAttribute.FOREGROUND, Color.MAGENTA);
        
        AttributedString doctorDetails = new AttributedString("Dr. " + doctorNm);
        doctorDetails.addAttribute(TextAttribute.FONT, fontDoctorNameDetails);
        doctorDetails.addAttribute(TextAttribute.FOREGROUND, Color.BLUE);
        
        AttributedString doctorRegLbl = new AttributedString(doctorReg);
        doctorRegLbl.addAttribute(TextAttribute.FONT, fontDoctorDetails);
        doctorRegLbl.addAttribute(TextAttribute.FOREGROUND, Color.BLUE);
        
        AttributedString doctorDegreeLbl = new AttributedString(doctorDegree);
        doctorDegreeLbl.addAttribute(TextAttribute.FONT, fontDoctorDetails);
        doctorDegreeLbl.addAttribute(TextAttribute.FOREGROUND, Color.BLUE);
        
        AttributedString doctorContactLbl = new AttributedString(doctorContact + " / " + contactNumber);
        doctorContactLbl.addAttribute(TextAttribute.FONT, fontDoctorDetails);
        doctorContactLbl.addAttribute(TextAttribute.FOREGROUND, Color.BLUE);
        
        AttributedString doctorEmailLbl = new AttributedString(doctorEmail);
        doctorEmailLbl.addAttribute(TextAttribute.FONT, fontDoctorDetails);
        doctorEmailLbl.addAttribute(TextAttribute.FOREGROUND, Color.BLUE);
        
        g.setFont(fontClinicName);
        g.drawString(clinicNm.getIterator(), pageCenter - getLength(clinicName, g) / 2, 20);
        g.drawLine(10, 95, 550, 95);
        g.setFont(fontDoctorNameDetails);
        g.drawString(doctorDetails.getIterator(), 80, 35);
        g.setFont(fontDoctorDetails);
        g.drawString(doctorDegreeLbl.getIterator(), 80, 50);
        g.drawString(doctorRegLbl.getIterator(), 80, 62);
        g.drawString(doctorContactLbl.getIterator(), 80, 74);
        g.drawString(doctorEmailLbl.getIterator(), 80, 86);
        
    }

    private int getLength(String str, Graphics g) {
        return (int) (g.getFontMetrics().getStringBounds(str, g).getWidth());
    }

    private void printPatientInfo(Graphics g, CheckupBean checkup) {
        String name = checkup.getPatient().getName() + "    ( " + checkup.getPatient().getAge() + " / " + checkup.getPatient().getGender() + " )";
        String date = displayDateFormat.format(checkup.getDate());
        int lableX = 20;
        int valueX = 130;
        
        Font fontHeader = new Font("Serif", Font.BOLD, 10);
        Font fontVal = new Font("Serif", Font.PLAIN, 10);
        
        AttributedString patientNmLbl = new AttributedString("Patient Name :    ");
        patientNmLbl.addAttribute(TextAttribute.FONT, fontHeader);
        patientNmLbl.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        
        AttributedString patientNm = new AttributedString(name);
        patientNm.addAttribute(TextAttribute.FONT, fontVal);
        patientNm.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);

        AttributedString dateLbl = new AttributedString("Date :    ");
        dateLbl.addAttribute(TextAttribute.FONT, fontHeader);
        dateLbl.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        
        AttributedString date1 = new AttributedString(date);
        date1.addAttribute(TextAttribute.FONT, fontVal);
        date1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        
        
        g.drawString(patientNmLbl.getIterator(), 80, 110);
        g.drawString(patientNm.getIterator(), 150, 110);
        g.drawString(dateLbl.getIterator(), 410, 110);
        g.drawString(date1.getIterator(), 450, 110);
        
    }

    public void printPriscription() {
        PrinterJob job = PrinterJob.getPrinterJob(); 
        job.setPrintable(this); 
        if (job.printDialog() == true) { 
            try {
                job.print();
            } catch (PrinterException ex) {logger.error(ex);}
        }
    }

    private void printMedicine(Graphics2D g, CheckupBean checkup) {
        int priscriptionRowStart = 140;

        Font fontHeader = new Font("Serif", Font.BOLD, 12);
        Font fontVal = new Font("Serif", Font.PLAIN, 10);

        AttributedString rx = new AttributedString("Rx - ");
        rx.addAttribute(TextAttribute.FONT, fontHeader);
        rx.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        
        g.drawString(rx.getIterator(), 80, priscriptionRowStart);
        g.drawLine(80, priscriptionRowStart + 5, 500, priscriptionRowStart + 5);
        int rowXpoint = priscriptionRowStart + 35;
        int row = 0;
        g.setFont(fontVal);
        if(checkup.getPriscriptions() != null && !checkup.getPriscriptions().isEmpty()) {
            for (PriscriptionBean priscription : checkup.getPriscriptions()) {
                rowXpoint = rowXpoint + (row++ * 35);
                String medicineTypeSelected = priscription.getMedicineType();
                String medicineName = priscription.getMedicineName();
                String medicineStrength = priscription.getMedicineStrength();
                String medicineFrequency = getFrequency(priscription.getFrequency());
                String medicineDuration = priscription.getDuration();
                g.drawString(medicineTypeSelected == null ? "" : "(" + medicineTypeSelected + ")", 90, rowXpoint);
                g.drawString(medicineName == null ? "N/A" : medicineName + " " + (medicineStrength == null || medicineStrength.isEmpty() ? "" : " - " + medicineStrength), 140, rowXpoint);
                g.drawString(medicineFrequency == null ? "N/A" : "( " + medicineFrequency + ")", 250, rowXpoint);
                g.drawString(medicineDuration == null ? "N/A" : medicineDuration + " Days", 440, rowXpoint);
            }
        }
        
        g.drawLine(80, rowXpoint + 20, 500, rowXpoint + 20);
    }
    
    public static void main(String args []){
        Priscription canvas = new Priscription(22432);
        canvas.printPriscription();
    }

    private String getFrequency(String frequency) {
        String frq = frequency;
        if(frequency.endsWith("BM")){
            frq = frequency.replaceFirst("BM", "Before Meal");
        } else if (frequency.endsWith("AM")){
            frq = frequency.replaceFirst("AM", "After Meal");
        }
        return frq;
    }
  
}
