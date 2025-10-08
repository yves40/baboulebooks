package org.mouserabbit.baboulebooks.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.cj.jdbc.MysqlDataSource;


public class DBsingleton {

    private  String version = "DBsingleton, Oct 07 2025: 1.01 == ";
    private static DBsingleton _instance = null;
    private  String _host = "localhost";
    private  int _port = 3306;
    private  String _user = "";
    private  String _password = "";
    private  String _database = "";

    private static Connection _dbconn = null;
    private static Logger _logger = null;

    /*
     * No direct instantiation
     */
    private DBsingleton(String host, int port, String database, String user, String password) throws Exception {
        System.setProperty("log4j2.configurationFile", System.getenv("log4j2.configurationFile"));
        _logger = LogManager.getLogger();
        _logger.debug(version + "Instantiation");
        if(_instance == null) {
            _host = host;
            _port = port;
            _database = database;
            _user = user;
            _password = password;
            // -------------------------------------------------------------------------
            // Connect the DB 
            // -------------------------------------------------------------------------
            String connectstring = "jdbc:mysql://" +  _host + ":" + _port + "/" + _database + "?" + "user=" + _user + "&password=" + _password;
            _logger.info("Connect : " + connectstring);
            _dbconn = DriverManager.getConnection(connectstring);
            Statement autocommitoff = _dbconn.createStatement();
            autocommitoff.execute("set autocommit=0"); 
        }
    }
    // ---------------------------------------------------------------------
    // Get singleton
    // ---------------------------------------------------------------------
    public static DBsingleton getInstance(String host, int port, String database, String user, String password) throws Exception {
      if (_instance == null) {
        _instance = new DBsingleton(host, port, database, user, password);
      }
      return _instance;
    }
    public static DBsingleton getInstance() throws Exception {
      if (_instance == null) {
        _logger.error("Dbtool Singleton improperly initialized");
      }
      return _instance;
    }
    // ---------------------------------------------------------------------
    // Setters & Getters
    // ---------------------------------------------------------------------
    public Connection get_dbconn() {
        return _dbconn;
    }
    
}