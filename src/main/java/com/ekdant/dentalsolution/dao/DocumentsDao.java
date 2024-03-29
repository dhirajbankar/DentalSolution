/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.DocumentTypeBean;
import com.ekdant.dentalsolution.domain.PatientBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import com.ekdant.dentalsolution.utilities.PropertiesCache;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;
import org.apache.log4j.Logger;

/**
 *
 * @author Sushant Raut
 */
public class DocumentsDao {
    
    ConnectionPool connection;
    String caseId;
    JTree jTree1;
    String baseLocation = "";
    SettingsDao settingsDao;
    final static Logger logger = Logger.getLogger(DocumentsDao.class);
    public DocumentsDao(){
        connection = ConnectionPool.getInstance();
        settingsDao = new SettingsDao();
    }
    
    public List<String> fetchDocumentTypes(){
        ResultSet rs = connection.getResult("SELECT * FROM DOCUMENTTYPE WHERE ACTIVEIND = 1 ORDER BY DOCUMENTTYPE");
        List<String> documentTypes = new ArrayList<String>();
        try {
            while (rs.next()) {                
                documentTypes.add(rs.getString("DOCUMENTTYPE"));
            }
        } catch (SQLException e) {
            logger.error(e);
        } 
        return documentTypes;
    }
    
    public boolean documentTypeNotPresent(String documentType) {
        int documentTypeCount = 0;
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM DOCUMENTTYPE WHERE DOCUMENTTYPE = '"+documentType+"' AND ACTIVEIND = 1");
        try {
            while(rs.next()){
                documentTypeCount = rs.getInt(1);
            }
        } catch (SQLException ex) { logger.error(ex);}
        return documentTypeCount == 0;
    }
    
    public List<DocumentTypeBean> fetchDocumentTypes(String searchText){
        String sql = "SELECT * FROM DOCUMENTTYPE WHERE ACTIVEIND = 1 ORDER BY DOCUMENTTYPE";
        if(searchText != null && searchText.trim().length() > 0){
            sql = "SELECT * FROM DOCUMENTTYPE WHERE DOCUMENTTYPE LIKE '%"+searchText+"%' AND ACTIVEIND = 1 ORDER BY DOCUMENTTYPE";
        }
        ResultSet rs = connection.getResult(sql);
        List<DocumentTypeBean> documentTypes = new ArrayList<DocumentTypeBean>();
        try {
            while (rs.next()) {
                DocumentTypeBean documentType = new DocumentTypeBean();
                documentType.setId(rs.getInt("ID"));
                documentType.setName(rs.getString("DOCUMENTTYPE"));
                documentTypes.add(documentType);
            }
        } catch (SQLException e) {logger.error(e);} 
        return documentTypes;
    }
    
    public boolean addDocumentType(DocumentTypeBean documentType) {
        boolean success = true;
        try {
            connection.stmt.execute("INSERT INTO DOCUMENTTYPE ( DOCUMENTTYPE, ACTIVEIND) VALUES( '" + documentType.getName() + "', 1)");
        } catch (SQLException ex) { 
            success = false;
            logger.error(ex);
        }
        return success;
    }
    
    public boolean updateDocumentType(DocumentTypeBean documentType){
        boolean success = true;
        String updateDocumentTypeSQL = "UPDATE DOCUMENTTYPE SET DOCUMENTTYPE = '"+documentType.getName()+"' WHERE ID = "+documentType.getId();
        try {
            connection.stmt.execute(updateDocumentTypeSQL);
        } catch (SQLException ex) {
            success = false;
            logger.error(ex);
        }
        return success;
    }
    
    public boolean deleteDocumentType(DocumentTypeBean documentType){
        boolean success = true;
        String updateDocumentTypeSQL = "UPDATE DOCUMENTTYPE SET ACTIVEIND = 0 WHERE ID = "+documentType.getId();
        try {
            connection.stmt.execute(updateDocumentTypeSQL);
        } catch (SQLException ex) {
            success = false;
            logger.error(ex);
        }
        return success;
    }
    
    public String fetchLatestDocumentType(){
        String newlyAddedDocumentType = "";
        try {
            ResultSet rs = connection.getResult("SELECT DOCUMENTTYPE FROM DOCUMENTTYPE ORDER BY ID DESC");
            while (rs.next()) {
                newlyAddedDocumentType = rs.getString("DOCUMENTTYPE");
                break;
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return newlyAddedDocumentType;
    }
    
    public void getDocumentTypes(JComboBox documentTypeCB) {        
        List<String> documentTypes = fetchDocumentTypes();
        for (String documentType : documentTypes){
                documentTypeCB.addItem(documentType);
        }
    }
    
    public void displayTree(PatientBean patient, JTree jTree){
        this.caseId = patient.getCaseId();
        this.jTree1 = jTree;
        String location = settingsDao.getMySQLPath()+ File.separator + PropertiesCache.getInstance().getProperty("folder.document") + File.separator + patient.getCaseId();
        String rootDirName = patient.getName() + "  " + patient.getAge() + File.separator + patient.getGender().charAt(0) + "  " + patient.getCaseId();
        File rootDir = new File(location);
        DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
        jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        TreeNode node = new DefaultMutableTreeNode(rootDirName);        
        model.setRoot(node);        
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        displayDirectoryContents(rootDir, root, jTree);
        
    }
    
    public void displayDirectoryContents(File dir, DefaultMutableTreeNode root, JTree jTree) {
        DefaultMutableTreeNode newdir;
        File[] files = dir.listFiles();  
        if(files != null){
            for (File file : files) {
                if (file == null) {
                    logger.debug("NUll directory found ");
                    continue;
                }
                if (file.isDirectory()) {
                    if (file.listFiles() == null) {
                        continue;
                    }
                    DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
                    newdir = new DefaultMutableTreeNode(file.getName());
                    root.add(newdir);
                    model.reload();
                    displayDirectoryContents(file, newdir, jTree);
                } else if(!file.getName().startsWith(".")){
                    DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
                    DefaultMutableTreeNode selectednode = root;
                    DefaultMutableTreeNode newfile = new DefaultMutableTreeNode(file.getName());
                    model.insertNodeInto(newfile, selectednode, selectednode.getChildCount());
                    model.reload();
                }
            }
        }
    }
    
    public void downloadFile(){
        if(jTree1.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(null, "Please Select file to download !!", "Error!", JOptionPane.ERROR_MESSAGE);
            logger.debug("Please Select file to download !!");
        }
        Object lastPathComponent = jTree1.getSelectionModel().getSelectionPath().getLastPathComponent();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) lastPathComponent;
        if(!node.isLeaf()){
            logger.error("Please select file to download");
        }else{
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
            File file = new File(settingsDao.getMySQLPath()+ File.separator + PropertiesCache.getInstance().getProperty("folder.document") + File.separator + this.caseId + File.separator + parentNode.getUserObject() + File.separator + node.getUserObject());
            if(file.isDirectory()){
                JOptionPane.showMessageDialog(null, "Please Select file to download !!", "Error!", JOptionPane.ERROR_MESSAGE);
                logger.debug("Please Select file to download !!");
            }else{
                try {
                    java.awt.Desktop.getDesktop().open(file);
                } catch (IOException ex) {logger.error(ex);}
            }
        }
    }
   
}
