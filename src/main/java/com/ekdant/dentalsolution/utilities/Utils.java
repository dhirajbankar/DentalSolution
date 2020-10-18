/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.utilities;

import com.ekdant.dentalsolution.dao.TokensDao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.log4j.Logger;

/**
 *
 * @author dinesh.mali
 */
public class Utils {
    static TokensDao tokensDao = new TokensDao();
    private static final DateFormat displayDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    final static Logger logger = Logger.getLogger(Utils.class);

    public static byte[] getByteArray(String string) {
        String str[] = string.split(" ");
        byte[] decryptedBytArr = new byte[str.length];

        for (int i = 0; i < str.length; i++) {
            decryptedBytArr[i] = new Byte(str[i]);
        }

        return decryptedBytArr;
    }

    public static String getValidString(String str) {
        String validStringAppender = "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";
        return (str + validStringAppender).substring(0, 16);
    }

    public static boolean demoVersion() {
        boolean demoVersion;
        try {
            String motherboardSN = getMotherboardSN();
            byte[] decryptedBytArr = tokensDao.getTokenByte("K2");
            String databaseSecret = AES.decrypt(decryptedBytArr);
            demoVersion = !motherboardSN.contains(databaseSecret.trim());
        } catch (Exception ex) {
            logger.error(ex);
            demoVersion = true;
        }
        return demoVersion;
    }
    
    public static boolean activationExpired() {
        boolean activationExpired = false;
        try {
            byte[] decryptedBytArr = tokensDao.getTokenByte("K4");
            String databaseSecret = AES.decrypt(decryptedBytArr);
            Date activationExpirationDate = displayDateFormat.parse(databaseSecret);
            if(activationExpirationDate.before(new Date())){
                activationExpired = true;
            }
        } catch (Exception ex) {
            logger.error(ex);
            activationExpired = true;
        }
        return activationExpired;
    }
    
    public static boolean activationExpiring(int days) {
        boolean activationExpiring = false;
        try {
            byte[] decryptedBytArr = tokensDao.getTokenByte("K4");
            String databaseSecret = AES.decrypt(decryptedBytArr);
            Date activationExpirationDate = displayDateFormat.parse(databaseSecret);
            
            Calendar activationExpirationCal = new GregorianCalendar();
            activationExpirationCal.setTime(new Date());
            activationExpirationCal.add(Calendar.DATE, days);
            
            if(activationExpirationDate.before(new Date(activationExpirationCal.getTimeInMillis()))){
                activationExpiring = true;
            }
        } catch (Exception ex) {
            logger.error(ex);
            activationExpiring = true;
        }
        return activationExpiring;
    }
    
    public static int activationRemaining() {
        int activationRemaining = 0;
        try {
            byte[] decryptedBytArr = tokensDao.getTokenByte("K4");
            String databaseSecret = AES.decrypt(decryptedBytArr);
            Date activationExpirationDate = displayDateFormat.parse(databaseSecret);
            activationRemaining = (int)((activationExpirationDate.getTime() - new Date().getTime()) / (1000*60*60*24));
        } catch (Exception ex) {logger.error(ex);}
        return activationRemaining;
    }

    public static String getMotherboardSN() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs
                    = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n"
                    + "Next \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            logger.debug(e);
        }
        result = result.trim().isEmpty() ? getSerialNumberForMac().trim() : result.trim();
        return getValidString(formatFileName(result)).trim();
    }
    
    public static final String getSerialNumberForMac() {
        String sn = "";
		
		OutputStream os = null;
		InputStream is = null;

		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
                    process = runtime.exec(new String[] { "/usr/sbin/system_profiler", "SPHardwareDataType" });
		} catch (IOException e) {
                    logger.error(e);
                    throw new RuntimeException(e);
		}

		os = process.getOutputStream();
		is = process.getInputStream();

		try {
                    os.close();
		} catch (IOException e) {
                    logger.error(e);
                    throw new RuntimeException(e);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		String marker = "Serial Number";
		try {
			while ((line = br.readLine()) != null) {
				if (line.contains(marker)) {
					sn = line.split(":")[1].trim();
					break;
				}
			}
		} catch (IOException e) {
                    logger.error(e);
                    throw new RuntimeException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
                            logger.error(e);
                            throw new RuntimeException(e);
			}
		}

		if (sn == null) {
                    logger.error("Cannot find computer SN");
                    throw new RuntimeException("Cannot find computer SN");
		}

		return sn;
	}

    private static String formatFileName(String fileName){
        String normalized = Normalizer.normalize(fileName, Normalizer.Form.NFD);
        String result = normalized.replaceAll("[^A-Za-z0-9]", "");
        return result;
    }
    
    public String getPath(){
        
        String path = ""; 
        try {
            path = URLDecoder.decode(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
            if(path.contains(".jar"))
                path = path.substring(0, path.lastIndexOf(".jar"));
            else if(path.contains("target/classes"))
                path = path.substring(0, path.lastIndexOf("target/classes"));
            path = path.substring(0, path.lastIndexOf("/"));
            if(path.startsWith("file:")){
                path = path.substring(5);
            }
        } catch (UnsupportedEncodingException ex) {logger.error(ex);}
        return path;
    } 

}
