/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author raut.sushant
 */
public class PropertiesCache {
    private final Properties configProp = new Properties();
    final static Logger logger = Logger.getLogger(PropertiesCache.class);
    
   private PropertiesCache()
   {
      InputStream in = this.getClass().getClassLoader().getResourceAsStream("config.properties");
      logger.debug("Read all properties from file");
      try {
          configProp.load(in);
      } catch (IOException e) {
          logger.error(e);
      }
   }
 
   private static class LazyHolder
   {
      private static final PropertiesCache INSTANCE = new PropertiesCache();
   }
 
   public static PropertiesCache getInstance()
   {
      return LazyHolder.INSTANCE;
   }
    
   public String getProperty(String key){
      return configProp.getProperty(key).trim();
   }
    
   public Set<String> getAllPropertyNames(){
      return configProp.stringPropertyNames();
   }
    
   public boolean containsKey(String key){
      return configProp.containsKey(key);
   }
    
}
