/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.utilities;

/**
 *
 * @author raut.sushant
 */
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mailer {


    public void sendEmail(String toEmail, String subject, String filePath) {
        
        Personalization personalization = new Personalization();
        Email from = new Email(PropertiesCache.getInstance().getProperty("email.from"),PropertiesCache.getInstance().getProperty("email.name"));        
        Email to = new Email(toEmail);
        personalization.addTo(to);        
        personalization.addBcc(from);        
        personalization.setSubject(subject);
        Content content = new Content("text/html", PropertiesCache.getInstance().getProperty("email.body"));
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.addContent(content);
        mail.addPersonalization(personalization);
        Path file = Paths.get(filePath);
        Attachments attachments = new Attachments();
        attachments.setFilename(file.getFileName().toString());
        attachments.setType("application/octet-stream");
        attachments.setDisposition("attachment");

        byte[] attachmentContentBytes;
        try {
            attachmentContentBytes = Files.readAllBytes(file);
            String attachmentContent = Base64.getMimeEncoder().encodeToString(attachmentContentBytes);
            attachments.setContent(attachmentContent);
            mail.addAttachments(attachments);
        } catch (IOException ex) {
            Logger.getLogger(Mailer.class.getName()).log(Level.SEVERE, null, ex);
        }
               
        SendGrid sg = new SendGrid(PropertiesCache.getInstance().getProperty("sendgrid.key"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (IOException ex) { }
    }
    
    public void sendEmailPassword(String toEmail, String subject, String body) {
        
        Personalization personalization = new Personalization();
        Email from = new Email(PropertiesCache.getInstance().getProperty("email.from"),PropertiesCache.getInstance().getProperty("email.name"));        
        Email to = new Email(toEmail);
        personalization.addTo(to);        
        personalization.addBcc(from);        
        personalization.setSubject(subject);
        Content content = new Content("text/html", body);
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.addContent(content);
        mail.addPersonalization(personalization);
        SendGrid sg = new SendGrid(PropertiesCache.getInstance().getProperty("sendgrid.key"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (IOException ex) { }
    }

}
