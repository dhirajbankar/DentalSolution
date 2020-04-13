/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.utilities;

/**
 *
 * @author dinesh.mali
 */
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import org.apache.log4j.Logger;

public class DiskUtils {
    final static Logger logger = Logger.getLogger(DiskUtils.class);
  private DiskUtils() {  }

  public static String getSerialNumber(String drive) {
  String result = "";
    try {
      File file = File.createTempFile("realhowto",".vbs");
      file.deleteOnExit();
      FileWriter fw = new java.io.FileWriter(file);

      String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                  +"Set colDrives = objFSO.Drives\n"
                  +"Set objDrive = colDrives.item(\"" + drive + "\")\n"
                  +"Wscript.Echo objDrive.SerialNumber";  // see note
      fw.write(vbs);
      fw.close();
      Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
      BufferedReader input =
        new BufferedReader
          (new InputStreamReader(p.getInputStream()));
      String line;
      while ((line = input.readLine()) != null) {
         result += line;
      }
      input.close();
    }
    catch(Exception e){
        logger.error(e);
    }
    return result.trim().isEmpty() ? getSerialNumber() : result.trim();
  }
  
    public static final String getSerialNumber() {
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

  public static void main(String[] args){
//    String sn = DiskUtils.getSerialNumber("D");
//    logger.error("Sr Number : "+sn);
//    javax.swing.JOptionPane.showConfirmDialog((java.awt.Component)
//         null, sn, "Serial Number of C:",
//         javax.swing.JOptionPane.DEFAULT_OPTION);

        logger.debug(PropertiesCache.getInstance().getProperty("db.driver"));

        logger.debug(PropertiesCache.getInstance().getAllPropertyNames());
  }
}
