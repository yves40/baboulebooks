package org.mouserabbit.baboulebooks.classes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerSingleton {

    private  String version = "LoggerSingleton, Oct 08 2025: 1.01 == ";
    private Logger _logger = null;
    private static LoggerSingleton _instance = null;

    private LoggerSingleton() throws Exception {
      // log4j2 parameters:  Uncomment this line in case tracing log4j is required
      // System.setProperty("log4j2.debug", "true");
      // Point to the configuration file. We'll take the name from ENV which is defined in 
      // settings.json
      String log4jProperties = System.getenv("log4j2.configurationFile");
      if(log4jProperties == null) {
        throw new Exception("log4j2.configurationFile environment variable not set");
      }
      System.setProperty("log4j2.configurationFile", System.getenv("log4j2.configurationFile"));
      _logger = LogManager.getLogger();
      _logger.debug(version + "Instantiation");
    }
    public static LoggerSingleton getInstance() throws Exception{
      if(_instance == null) {
        _instance = new LoggerSingleton();
      }
      return _instance;
    }
    public Logger get_logger() {
      return _logger;
    }
}
