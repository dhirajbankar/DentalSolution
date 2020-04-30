/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.printing;

import com.ekdant.dentalsolution.dao.CheckUpDao;
import com.ekdant.dentalsolution.dao.ClinicDao;
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
    DateFormat displayDateFormat = new SimpleDateFormat(PropertiesCache.getInstance().getProperty("format.displaydate"));
    final static Logger logger = Logger.getLogger(Priscription.class);
    
    public Priscription(int treatmentId){
        patientTreatmentId = treatmentId;
        checkUpDao = new CheckUpDao();
        clinicDao = new ClinicDao();
        
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
       
        return PAGE_EXISTS; 
    }
    

    private void printHeader(Graphics g) {
        int pageCenter = 300;
        List<ClinicBean> clinics = clinicDao.fetchClinics();
        ClinicBean clinic = clinics.get(0);
        String clinicName = clinic.getName();
        String clinicAddress = clinic.getAddress() + " , " +clinic.getCity();
        String contactNumber = clinic.getContact();
        URL iconURL = getClass().getResource("/EkDant/icones/priscription.jpg");
        Image img;
        if (iconURL == null) {
            logger.debug("Icon file not found for print.");
        } else {
            try {
                img = ImageIO.read(iconURL);
                
                g.drawImage(img, 15, 0, 65, 50, 0, 0, 50, 50, null);
             } catch (IOException ex) {
                logger.error("file not found");
             }
        }
        Font fontClinicName = new Font("Serif", Font.BOLD, 20);
        Font fontClinicAddress = new Font("Serif", Font.PLAIN, 10);        
        Font fontClinicCont = new Font("Serif", Font.BOLD, 12);
        

        AttributedString clinicNm = new AttributedString(clinicName);
        clinicNm.addAttribute(TextAttribute.FONT, fontClinicName);
        clinicNm.addAttribute(TextAttribute.FOREGROUND, Color.BLUE);
        
        AttributedString clinicAdd = new AttributedString(clinicAddress);
        clinicAdd.addAttribute(TextAttribute.FONT, fontClinicAddress);
        clinicAdd.addAttribute(TextAttribute.FOREGROUND, Color.BLUE);
        
        AttributedString clinicCont = new AttributedString(contactNumber);
        clinicCont.addAttribute(TextAttribute.FONT, fontClinicAddress);
        clinicCont.addAttribute(TextAttribute.FOREGROUND, Color.BLUE);
        
        g.setFont(fontClinicName);
        g.drawString(clinicNm.getIterator(), pageCenter - getLength(clinicName, g) / 2, 20);
        g.setFont(fontClinicAddress);
        g.drawString(clinicAdd.getIterator(), pageCenter - getLength(clinicAddress, g) / 2, 35);
        g.setFont(fontClinicCont);
        g.drawString(clinicCont.getIterator(), pageCenter - getLength(contactNumber, g) / 2, 55);
        g.drawLine(10, 60, 550, 60);
    }

    private int getLength(String str, Graphics g) {
        return (int) (g.getFontMetrics().getStringBounds(str, g).getWidth());
    }

    private void printPatientInfo(Graphics g, CheckupBean checkup) {
        String name = checkup.getPatient().getName();
        String casepapernumber = checkup.getPatient().getCaseId();
        String date = displayDateFormat.format(checkup.getDate());
        String premedicalHistory = checkup.getPatient().getPreMedicalHistory();
        String doctorName = checkup.getDentistName();
        int lableX = 20;
        int valueX = 130;
        
        Font fontHeader = new Font("Serif", Font.BOLD, 10);
        Font fontVal = new Font("Serif", Font.PLAIN, 10);

        AttributedString doctorNmLbl = new AttributedString("Doctor Name  :    ");
        doctorNmLbl.addAttribute(TextAttribute.FONT, fontHeader);
        doctorNmLbl.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        
        AttributedString doctorNm = new AttributedString(doctorName);
        doctorNm.addAttribute(TextAttribute.FONT, fontVal);
        doctorNm.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        
        AttributedString dateLbl = new AttributedString("Date :    ");
        dateLbl.addAttribute(TextAttribute.FONT, fontHeader);
        dateLbl.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        
        AttributedString date1 = new AttributedString(date);
        date1.addAttribute(TextAttribute.FONT, fontVal);
        date1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        
        AttributedString patientNmLbl = new AttributedString("Patient Name :    ");
        patientNmLbl.addAttribute(TextAttribute.FONT, fontHeader);
        patientNmLbl.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        
        AttributedString patientNm = new AttributedString(name);
        patientNm.addAttribute(TextAttribute.FONT, fontVal);
        patientNm.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        
        AttributedString casePaperLbl = new AttributedString("Case Paper No  :    ");
        casePaperLbl.addAttribute(TextAttribute.FONT, fontHeader);
        casePaperLbl.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        
        AttributedString casepep = new AttributedString(casepapernumber);
        casepep.addAttribute(TextAttribute.FONT, fontVal);
        casepep.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        
        AttributedString preMedicalHistLbl = new AttributedString("Pre Medical History :    ");
        preMedicalHistLbl.addAttribute(TextAttribute.FONT, fontHeader);
        preMedicalHistLbl.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        
        AttributedString preMedicalHist = new AttributedString(premedicalHistory);
        preMedicalHist.addAttribute(TextAttribute.FONT, fontVal);
        preMedicalHist.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
        
        g.drawString(doctorNmLbl.getIterator(), lableX, 75);
        g.drawString(doctorNm.getIterator(), valueX, 75);
        g.drawString(dateLbl.getIterator(), 320, 75);
        g.drawString(date1.getIterator(), 420, 75);
        g.drawString(patientNmLbl.getIterator(), lableX, 95);
        g.drawString(patientNm.getIterator(), valueX, 95);
        g.drawString(casePaperLbl.getIterator(), 320, 95);
        g.drawString(casepep.getIterator(), 420, 95);
        g.drawString(preMedicalHistLbl.getIterator(), lableX, 115);
        g.drawString(preMedicalHist.getIterator(), valueX, 115);
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
        int priscriptionRowStart = 150;

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
                String medicineFrequency = priscription.getFrequency();
                String medicineDuration = priscription.getDuration();
                g.drawString(medicineTypeSelected == null ? "" : "(" + medicineTypeSelected + ")", 90, rowXpoint);
                g.drawString(medicineName == null ? "N/A" : medicineName + " " + (medicineStrength == null || medicineStrength.isEmpty() ? "" : " - " + medicineStrength), 140, rowXpoint);
                g.drawString(medicineFrequency == null ? "N/A" : "( " + medicineFrequency + ")", 250, rowXpoint);
                g.drawString(medicineDuration == null ? "N/A" : medicineDuration + " Days", 440, rowXpoint);
            }
        }
        
        g.drawLine(80, rowXpoint + 20, 500, rowXpoint + 20);
    }
  
}
