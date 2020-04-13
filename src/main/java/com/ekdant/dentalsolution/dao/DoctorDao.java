/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.DoctorBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import com.ekdant.dentalsolution.utilities.PropertiesCache;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Sushant Raut
 */
public class DoctorDao {
    ConnectionPool connection;
    DateFormat databaseDateFormat = new SimpleDateFormat(PropertiesCache.getInstance().getProperty("format.dbdate"));
    final static Logger logger = Logger.getLogger(DoctorDao.class);
    
    public DoctorDao(){
        connection = ConnectionPool.getInstance();
    }
    
    public List<DoctorBean> fetchDoctors(){
        List<DoctorBean> doctors = new ArrayList<DoctorBean>();
        String sql = "SELECT * FROM DOCTORS WHERE ACTIVEIND = 1";
        ResultSet rs = connection.getResult(sql);
        try{
            while (rs.next()) {
                DoctorBean doctor = new DoctorBean();
                doctor.setDoctorId(rs.getInt("DOCTORID"));
                doctor.setName(rs.getString("NAME"));
                doctor.setType(rs.getString("TYPE"));
                doctor.setAge(rs.getInt("AGE"));
                doctor.setGender(rs.getString("GENDER"));
                doctor.setAddress(rs.getString("ADDRESS"));
                doctor.setCity(rs.getString("CITY"));
                doctor.setTelephone(rs.getString("TELEPHONE"));
                doctor.setMobile(rs.getString("MOBILE"));
                doctor.setEmail(rs.getString("EMAIL"));
                if(rs.getString("BIRTHDAY") != null && !rs.getString("BIRTHDAY").isEmpty())
                    doctor.setBirthDate(databaseDateFormat.parse(rs.getString("BIRTHDAY")));
                doctor.setDegree(rs.getString("DEGREE"));
                doctor.setRegistrationNo(rs.getString("REGISTRATIONNO"));
                doctor.setSpecialization(rs.getString("SPECIALIZATION"));
                doctors.add(doctor);
            }
        }catch(Exception e){logger.error(e);}
        return doctors;
    }
    
    public List<DoctorBean> fetchDoctors(String searchText){
        List<DoctorBean> doctors = new ArrayList<DoctorBean>();
        String sql = "SELECT * FROM DOCTORS WHERE ACTIVEIND = 1 ORDER BY NAME";
        if(searchText != null && !searchText.isEmpty()){
            sql = "SELECT * FROM DOCTORS WHERE (NAME LIKE '%"+searchText+"%' OR TELEPHONE LIKE '%"+searchText+"%' OR CITY LIKE '%"+searchText+"%') AND ACTIVEIND = 1 ORDER BY NAME";
        }
        ResultSet rs = connection.getResult(sql);
        try{
            while (rs.next()) {
                DoctorBean doctor = new DoctorBean();
                doctor.setDoctorId(rs.getInt("DOCTORID"));
                doctor.setName(rs.getString("NAME"));
                doctor.setType(rs.getString("TYPE"));
                doctor.setAge(rs.getInt("AGE"));
                doctor.setGender(rs.getString("GENDER"));
                doctor.setAddress(rs.getString("ADDRESS"));
                doctor.setCity(rs.getString("CITY"));
                doctor.setTelephone(rs.getString("TELEPHONE"));
                doctor.setMobile(rs.getString("MOBILE"));
                doctor.setEmail(rs.getString("EMAIL"));
                if(rs.getString("BIRTHDAY") != null && !rs.getString("BIRTHDAY").isEmpty())
                    doctor.setBirthDate(databaseDateFormat.parse(rs.getString("BIRTHDAY")));
                doctor.setDegree(rs.getString("DEGREE"));
                doctor.setRegistrationNo(rs.getString("REGISTRATIONNO"));
                doctor.setSpecialization(rs.getString("SPECIALIZATION"));
                doctors.add(doctor);
            }
        }catch(Exception e){logger.error(e);}
        return doctors;
    }
    
    public DoctorBean fetchDoctor(int doctorId){
        String sql = "SELECT * FROM DOCTORS WHERE ACTIVEIND = 1 AND DOCTORID = " + doctorId;
        DoctorBean doctor = new DoctorBean();
        
        ResultSet rs = connection.getResult(sql);
        try{
            while (rs.next()) {
                doctor.setDoctorId(rs.getInt("DOCTORID"));
                doctor.setName(rs.getString("NAME"));
                doctor.setType(rs.getString("TYPE"));
                doctor.setAge(rs.getInt("AGE"));
                doctor.setGender(rs.getString("GENDER"));
                doctor.setAddress(rs.getString("ADDRESS"));
                doctor.setCity(rs.getString("CITY"));
                doctor.setTelephone(rs.getString("TELEPHONE"));
                doctor.setMobile(rs.getString("MOBILE"));
                doctor.setEmail(rs.getString("EMAIL"));
                if(rs.getString("BIRTHDAY") != null && !rs.getString("BIRTHDAY").isEmpty())
                    doctor.setBirthDate(databaseDateFormat.parse(rs.getString("BIRTHDAY")));
                doctor.setDegree(rs.getString("DEGREE"));
                doctor.setRegistrationNo(rs.getString("REGISTRATIONNO"));
                doctor.setSpecialization(rs.getString("SPECIALIZATION"));
                
            }
        }catch(Exception e){logger.error(e);}
        return doctor;
    }
    
    public DoctorBean fetchMainDoctor(){
        String sql = "SELECT * FROM DOCTORS WHERE ACTIVEIND = 1 AND TYPE = 'Main'";
        DoctorBean doctor = new DoctorBean();
        
        ResultSet rs = connection.getResult(sql);
        try{
            while (rs.next()) {
                doctor.setDoctorId(rs.getInt("DOCTORID"));
                doctor.setName(rs.getString("NAME"));
                doctor.setType(rs.getString("TYPE"));
                doctor.setAge(rs.getInt("AGE"));
                doctor.setGender(rs.getString("GENDER"));
                doctor.setAddress(rs.getString("ADDRESS"));
                doctor.setCity(rs.getString("CITY"));
                doctor.setTelephone(rs.getString("TELEPHONE"));
                doctor.setMobile(rs.getString("MOBILE"));
                doctor.setEmail(rs.getString("EMAIL"));
                if(rs.getString("BIRTHDAY") != null && !rs.getString("BIRTHDAY").isEmpty())
                    doctor.setBirthDate(databaseDateFormat.parse(rs.getString("BIRTHDAY")));
                doctor.setDegree(rs.getString("DEGREE"));
                doctor.setRegistrationNo(rs.getString("REGISTRATIONNO"));
                doctor.setSpecialization(rs.getString("SPECIALIZATION"));
                
            }
        }catch(Exception e){logger.error(e);}
        return doctor;
    }
    
    public int fetchDoctorId(String doctorName){
        int doctorId = 0;
        ResultSet rs = connection.getResult("SELECT DOCTORID FROM DOCTORS WHERE NAME = '"+doctorName+"' AND ACTIVEIND = 1");
        try {
            while(rs.next()){
                doctorId = rs.getInt(1);
            }
        } catch (SQLException ex) { logger.error(ex);}
        return doctorId;
    }
    
    public int populateTotalDoctorsCount() {
        int totalCount = 0; 
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM DOCTORS WHERE ACTIVEIND = 1");
        try {
            while(rs.next()){
                totalCount = rs.getInt(1);
            }
        } catch (SQLException ex) {logger.error(ex);}
        return totalCount;
    }
    
    public int addDoctor(DoctorBean doctor){
        int doctorId = 0;
        
        String birthDateStr = "";
        if(doctor.getBirthDate() != null && !doctor.getBirthDate().toString().isEmpty()){
        try{
            birthDateStr = "'" + databaseDateFormat.format(doctor.getBirthDate()) + "'";            
        }catch(Exception e){ logger.error(e);} 
        
        }       
        
        String doctorInsertQuery = "INSERT INTO DOCTORS (NAME,TYPE,BIRTHDAY,GENDER,PHOTO,ADDRESS,CITY,TELEPHONE,MOBILE,EMAIL,AGE,DEGREE,REGISTRATIONNO,SPECIALIZATION,ACTIVEIND) "+
                "VALUES ('"+doctor.getName()+"','"+doctor.getType()+"','"+birthDateStr+"','"+doctor.getGender()+"', NULL,'"+doctor.getAddress()+"','"+doctor.getCity()+"','"+doctor.getTelephone()+"','"+doctor.getMobile()+"','"+doctor.getEmail()+"',"+doctor.getAge()+",'"+doctor.getDegree()+"','"+doctor.getRegistrationNo()+"','"+doctor.getSpecialization()+"',1)";
        
        try {
            connection.stmt.executeUpdate(doctorInsertQuery);
            ResultSet rs = connection.stmt.executeQuery("select last_insert_rowid();");
            
            if (rs.next()) {
                doctorId = rs.getInt(1);
            }
        } catch (SQLException ex) {logger.error(ex);}

        return doctorId;
    }
    
    public boolean updateDoctor(DoctorBean doctor){
        
        String birthDateStr = null;
        try{
            birthDateStr = "'" + databaseDateFormat.format(doctor.getBirthDate()) + "'";        
        }catch(Exception e){ }
        String doctorUpdateQuery = "UPDATE DOCTORS SET NAME = '"+doctor.getName()+"', BIRTHDAY = "+birthDateStr+"," +
                                        " GENDER  = '"+doctor.getGender()+"', PHOTO = NULL, ADDRESS = '"+doctor.getAddress()+"', " +
                                        " CITY = '"+doctor.getCity()+"', TELEPHONE = '"+doctor.getMobile()+"',  EMAIL = '"+doctor.getEmail()+"'," +
                                        " AGE = "+doctor.getAge()+", DEGREE = '"+doctor.getDegree()+"', REGISTRATIONNO = '"+doctor.getRegistrationNo()+"', " +
                                        " SPECIALIZATION = '"+doctor.getSpecialization()+"' WHERE DOCTORID = "+doctor.getDoctorId();
            
        try {
            connection.stmt.executeUpdate(doctorUpdateQuery);
        } catch (SQLException ex) {
            logger.error(ex);
            return false;
        }
        return true;
    }
    
    public boolean deleteDoctorById(int doctorId) {
        boolean success = true;
        String deleteDoctorQuery = "UPDATE DOCTORS SET ACTIVEIND = 0 WHERE DOCTORID = " + doctorId;
        try {
            connection.stmt.executeUpdate(deleteDoctorQuery);
        } catch (SQLException ex) {
            success = false;
            logger.error(ex);
        }
        return success;
    }
    
    public boolean doctorSettingsRequired() {
        int count = 0;        
        
        try {
            ResultSet rs = connection.getResult("SELECT COUNT(*) FROM USERS WHERE USERTYPE = 'Doctor' AND ACTIVEIND = 1");
            while(rs.next()){
                count = rs.getInt(1);
            }
        } catch (Exception ex) {logger.error(ex);}
        return count == 0;
    }
}
