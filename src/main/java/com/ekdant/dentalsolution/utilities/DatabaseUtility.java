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
    
    public DatabaseUtility() {
        
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement st = con.createStatement();
            ScriptRunner sr = new ScriptRunner(con);
            Reader reader;
        
            reader = new BufferedReader(new FileReader(DBTEMPLATEPATH));
            sr.runScript(reader);
        }catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
     
    private static boolean tablePresent(String tableName) {
        String result = "";
        ConnectionPool con = ConnectionPool.getInstance();
        ResultSet rs = con.getResult("SELECT name FROM sqlite_master WHERE type='table' AND name='users'");
        try {
            while(rs.next()){
                result = rs.getString("name");
            }
        } catch (SQLException ex) {
            
        }
        return result.equalsIgnoreCase(tableName);
    }

}
