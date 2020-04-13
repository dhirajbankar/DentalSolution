package com.ekdant.dentalsolution.utilities;
import java.sql.*;
import javax.swing.*;
import org.apache.log4j.Logger;
/**
 *
 * @author Sushant
 */
public class ConnectionPool {
    private static ConnectionPool connection;
    private static Connection conn = null;
    public Statement stmt = null;
    final static Logger logger = Logger.getLogger(ConnectionPool.class);
    
    public static ConnectionPool getInstance(){
        if(connection == null){
            try {
                Class.forName(PropertiesCache.getInstance().getProperty("db.driver"));
                connection = new ConnectionPool();
                
                connection.conn = DriverManager.getConnection(PropertiesCache.getInstance().getProperty("db.url")+PropertiesCache.getInstance().getProperty("db.dbname"), PropertiesCache.getInstance().getProperty("db.username"), PropertiesCache.getInstance().getProperty("db.password"));
            } catch (ClassNotFoundException ex) {
                logger.error(ex);
            } 
            catch (SQLException ex) {
                logger.error(ex);
            }
            logger.debug("Connected :D");
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
             logger.error("The SQL command not work: "+erroSQL+",  SQL past: "+sql);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"The SQL command not work: "+e+",  SQL past: "+sql);
            logger.error("The SQL command not work: "+e+",  SQL past: "+sql);
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
             logger.error("The SQL command not work: "+erroSQL+",  SQL past: "+sql);
        }
    }
}