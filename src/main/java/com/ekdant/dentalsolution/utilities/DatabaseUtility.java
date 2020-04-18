/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;

/**
 *
 * @author raut.sushant
 */
public class DatabaseUtility {
    final static Logger logger = Logger.getLogger(DatabaseUtility.class);
    
    public DatabaseUtility() {
        
        try {
            Class.forName(PropertiesCache.getInstance().getProperty("db.driver"));
            Connection con = DriverManager.getConnection(PropertiesCache.getInstance().getProperty("db.url")+PropertiesCache.getInstance().getProperty("db.dbname"), PropertiesCache.getInstance().getProperty("db.username"), PropertiesCache.getInstance().getProperty("db.password"));
            Statement st = con.createStatement();
            ScriptRunner sr = new ScriptRunner(con);           
            sr.runScript(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/"+PropertiesCache.getInstance().getProperty("db.dbtemplatepath")))));
            logger.debug("Database Creation completed");
        } catch (ClassNotFoundException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }
     
    private static boolean tablePresent(String tableName) {
        String result = "";
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='users'";
        ConnectionPool con = ConnectionPool.getInstance();
        ResultSet rs = con.getResult(sql);
        try {
            while(rs.next()){
                result = rs.getString("name");
            }
        } catch (SQLException ex) {
            logger.error("The SQL command not work: "+ex+",  SQL past: "+sql);
        }
        return result.equalsIgnoreCase(tableName);
    }

}
