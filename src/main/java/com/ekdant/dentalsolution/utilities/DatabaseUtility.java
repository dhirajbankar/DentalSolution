/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
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
    final private static String DRIVER = "org.sqlite.JDBC"; 
    final private static String URL = "jdbc:sqlite:DentalSolution.sqlite";
    final private static String USERNAME = "root";
    final private static String PASSWORD = "root";
    final private static String DBTEMPLATEPATH = "/Users/raut.sushant/Downloads/DentalSolution-master/DentalSolution/src/main/java/com/ekdant/dentalsolution/utilities/newSQLTemplate.sql";
    final static Logger logger = Logger.getLogger(DatabaseUtility.class);
    
    public DatabaseUtility() {
        
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement st = con.createStatement();
            ScriptRunner sr = new ScriptRunner(con);
            Reader reader;
        
            reader = new BufferedReader(new FileReader(DBTEMPLATEPATH));
            sr.runScript(reader);
            logger.debug("Database Creation completed");
        }catch (FileNotFoundException ex) {
            logger.error(ex);
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
