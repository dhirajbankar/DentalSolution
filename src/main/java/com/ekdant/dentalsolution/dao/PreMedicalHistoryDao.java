/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.PreMedicalHistoryBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Sushant Raut
 */
public class PreMedicalHistoryDao {
    
    ConnectionPool connection;
    final static Logger logger = Logger.getLogger(PreMedicalHistoryDao.class);
    public PreMedicalHistoryDao(){
        connection = ConnectionPool.getInstance();
    }
    
    public List<PreMedicalHistoryBean> fetchPreMedicalHistory(){
        List<PreMedicalHistoryBean> preMedicalHistorys = new ArrayList<PreMedicalHistoryBean>();
        ResultSet rs = connection.getResult("SELECT * FROM PREMEDICALHISTORY WHERE ACTIVEIND = 1");
        try{
            while(rs.next()){
                PreMedicalHistoryBean preMedicalHistory = new PreMedicalHistoryBean();
                preMedicalHistory.setId(rs.getInt("ID"));
                preMedicalHistory.setDiscription(rs.getString("DISCRIPTION"));
                preMedicalHistorys.add(preMedicalHistory);
            }
        }catch(SQLException ex){logger.error(ex);}
        return preMedicalHistorys;
    }
    
    public List<PreMedicalHistoryBean> fetchPreMedicalHistory(String searchText){        
        String sql = "";
        if(searchText == null || searchText.isEmpty()){
            sql = "SELECT * FROM PREMEDICALHISTORY WHERE ACTIVEIND = 1 ORDER BY DISCRIPTION";
        }else{
            sql = "SELECT * FROM PREMEDICALHISTORY WHERE DISCRIPTION LIKE '%"+searchText+"%' AND ACTIVEIND = 1 ORDER BY DISCRIPTION";
        }
        
        List<PreMedicalHistoryBean> preMedicalHistorys = new ArrayList<PreMedicalHistoryBean>();
        ResultSet rs = connection.getResult(sql);
        try{
            while(rs.next()){
                PreMedicalHistoryBean preMedicalHistory = new PreMedicalHistoryBean();
                preMedicalHistory.setId(rs.getInt("ID"));
                preMedicalHistory.setDiscription(rs.getString("DISCRIPTION"));
                preMedicalHistorys.add(preMedicalHistory);
            }
        }catch(SQLException ex){logger.error(ex);}
        return preMedicalHistorys;
    }
    
    public boolean preMedicalHistoryNotPresent(String newPreMedicalhistory) {
        int preMedicalHistoryCount = 0;
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM PREMEDICALHISTORY WHERE DISCRIPTION = '"+newPreMedicalhistory+"' AND ACTIVEIND = 1");
        try {
            while(rs.next()){
                preMedicalHistoryCount = rs.getInt(1);
            }
        } catch (SQLException ex) {logger.error(ex);}
        return preMedicalHistoryCount == 0;
    }
    
    public boolean addPreMedicalHistory(PreMedicalHistoryBean preMedicalHistory) {
        boolean success = true;
        String sql = "INSERT INTO PREMEDICALHISTORY ( DISCRIPTION, ACTIVEIND) VALUES( '"+preMedicalHistory.getDiscription()+"', 1)";
        
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
            logger.error(ex);
        }
         
        return success;
    }
    
    public boolean updatePreMedicalHistory(PreMedicalHistoryBean preMedicalHistory) {
        boolean success = true;
        String sql = "UPDATE PREMEDICALHISTORY SET DISCRIPTION = '"+preMedicalHistory.getDiscription()+"' WHERE ID = "+preMedicalHistory.getId();
        
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
            logger.error(ex);
        }
         
        return success;
    }
    
    public boolean deletePreMedicalHistory(PreMedicalHistoryBean preMedicalHistory) {
        boolean success = true;
        String sql = "UPDATE PREMEDICALHISTORY SET ACTIVEIND = 0 WHERE ID = "+preMedicalHistory.getId();
        
        try {
            connection.stmt.execute(sql);
        } catch (SQLException ex) {
            success = false;
            logger.error(ex);
        }
         
        return success;
    }
}
