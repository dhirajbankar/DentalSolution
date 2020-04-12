package com.ekdant.dentalsolution.utilities;
import java.sql.*;
import javax.swing.*;
/**
 *
 * @author Sushant
 */
public class ConnectionPool {
    final private static String DRIVER = "org.sqlite.JDBC"; 
    final private static String URL = "jdbc:sqlite:DentalSolution.sqlite";
    final private static String USERNAME = "root";
    final private static String PASSWORD = "root";
    private static ConnectionPool connection;
    private static Connection conn = null;
    public Statement stmt = null;
    
    public static ConnectionPool getInstance(){
        if(connection == null){
            try {
                Class.forName(DRIVER);
                connection = new ConnectionPool();
                
                connection.conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            } 
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("Connected :D"+PropertiesCache.getInstance().getProperty(URL));
        }
        return connection;
    }
    
    public void CloseDB(){

    }
    
    public ResultSet getResult(String sql){
        ResultSet result = null;
        try{           
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            result   = stmt.executeQuery(sql);
        }
        catch(SQLException erroSQL){
             JOptionPane.showMessageDialog(null,"The SQL command not work: "+erroSQL+",  SQL past: "+sql);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"The SQL command not work: "+e+",  SQL past: "+sql);
        }
        return result;
    }

    public void QuerySQL(String sql){
        ResultSet rs;
        try{           
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs   = stmt.executeQuery(sql);
        }
        catch(SQLException erroSQL){
             JOptionPane.showMessageDialog(null,"The SQL command not work: "+erroSQL+",  SQL past: "+sql);
        }
    }
}