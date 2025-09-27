package org.mouserabbit.baboulebooks.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.ResultSet;
import org.apache.logging.log4j.Logger;

public class Location {

  private static String version = "Location, Sep 25 2025 : 1.12";
  protected int _id ;
  protected String _city;
  protected static Connection _dbconn = null;
  protected static Logger _logger = null;

  public Location(String _city) {
    this._city = Normalize(_city);
  }

  // ---------------------------------------------------------------------
  // Insert location data
  // Called after the object is created and populated
  // ---------------------------------------------------------------------
  public void Insert() throws SQLException, Exception {

    if(_dbconn == null) throw new Exception("DB connection is not initialized");
    Statement stmt = _dbconn.createStatement();
    try {
      stmt.executeUpdate("insert into locations ( loc_city ) " + 
            "values ( '" + this._city + "' )",
            Statement.RETURN_GENERATED_KEYS);   
      ResultSet rs = stmt.getGeneratedKeys();
      if(rs.next()) {
        this._id = rs.getInt(1);
      }
      stmt.executeUpdate("commit");
    }
    catch(SQLIntegrityConstraintViolationException sqli) {
      // Already inserted, no action
    }
  }
  // ---------------------------------------------------------------------
  // Cleanup DB table
  // ---------------------------------------------------------------------
  public static void DeleteAll() throws Exception {
    if(_dbconn == null) throw new Exception("DB connection is not initialized");
    _logger.info("Delete all locations");
    PreparedStatement stmt = _dbconn.prepareStatement("delete from locations");
    try {
      int i = stmt.executeUpdate();
      _logger.info("\tDeleted " + i + " Locations record(s)");
      stmt.executeUpdate("commit");
    }
    catch(SQLException sqle) {
      // Already inserted, no action
      _logger.error("Cannot delete all Locations : " + sqle.getMessage());
    }
  }
  // ---------------------------------------------------------------------
  // Setters & Getters
  // ---------------------------------------------------------------------
  public static void set_dbconn(Connection _dbconn) {
    Location._dbconn = _dbconn;
  }
  public static void set_logger(Logger _logger) {
    Location._logger = _logger;
  }
  public int get_id() {
    return _id;
  }
  public static String getVersion() {
    return version;
  }
  public void set_id(int _id) {
    this._id = _id;
  }
  public String get_city() {
    return _city;
  }
  public void set_city(String _city) {
    this._city = _city;
  }
  // ---------------------------------------------------------------------
  // Normalize location city
  // ---------------------------------------------------------------------
  protected String Normalize(String city) {
    try {
      String normalized = city.strip();
      return normalized.substring(0, 1).toUpperCase() +  normalized.substring(1).toLowerCase();
    }
    catch(Exception e) {
      System.out.println(e.getMessage());
      return "";
    }
  }  
}
