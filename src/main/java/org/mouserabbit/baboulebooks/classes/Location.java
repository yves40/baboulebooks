package org.mouserabbit.baboulebooks.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.ResultSet;
import org.apache.logging.log4j.Logger;
import org.mouserabbit.baboulebooks.classes.DBsingleton;
import org.mouserabbit.baboulebooks.classes.LoggerSingleton;

public class Location {

  private static String version = "Location, Oct 07 2025 : 1.14";
  protected int _id ;
  protected String _city;
  protected Connection _dbconn = null;
  protected Logger _logger = null;

  public Location() {
    _logger = LoggerSingleton.getInstance().get_logger();
    try {
      _dbconn = DBsingleton.getInstance().get_dbconn();
    }
    catch (Exception e) {
      _logger.error(e.getMessage());
    }
  }

  public Location(String _city) {
    _logger = LoggerSingleton.getInstance().get_logger();
    try {
      _dbconn = DBsingleton.getInstance().get_dbconn();
    }
    catch (Exception e) {
      _logger.error(e.getMessage());
    }
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
            "values ( \"" + this._city + "\" )",
            Statement.RETURN_GENERATED_KEYS);   
      ResultSet rs = stmt.getGeneratedKeys();
      if(rs.next()) {
        this._id = rs.getInt(1);
      }
      // stmt.executeUpdate("commit");
    }
    catch(SQLIntegrityConstraintViolationException sqli) {
      // Already inserted, get exisiting data
      Location alreadyexists = getLocationByCity(this._city);
      this.set_id(alreadyexists.get_id());
    }
  }
  // ---------------------------------------------------------------------
  // Get Location data
  // ---------------------------------------------------------------------
  public Location getLocationByCity(String city) throws Exception {
    if(_dbconn == null) throw new Exception("DB connection is not initialized");
    Statement stmt = _dbconn.createStatement();
    try {
      stmt.executeQuery("select * from locations where loc_city = \"" +  city + "\"");   
      ResultSet rs = stmt.getResultSet();
      Location loc = null;
      if(rs.next()) {
        loc = new Location(rs.getString("loc_city"));
        loc.set_id(rs.getInt("loc_id"));
      }
      return loc;
    }
    catch(SQLException sqle) {
      _logger.error(sqle.getMessage());
      return new Location(city);
    }
  }
  public Location getLocationByid(int id) throws Exception {
    if(_dbconn == null) throw new Exception("DB connection is not initialized");
    Statement stmt = _dbconn.createStatement();
    try {
      stmt.executeQuery("select * from locations where loc_id = " +  id );   
      ResultSet rs = stmt.getResultSet();
      Location loc = null;
      if(rs.next()) {
        loc = new Location(rs.getString("loc_city"));
        loc.set_id(rs.getInt("loc_id"));
      }
      return loc;
    }
    catch(SQLException sqle) {
      _logger.error(sqle.getMessage());
      return new Location("City ID Not found");
    }
  }
  // ---------------------------------------------------------------------
  // Cleanup DB table
  // ---------------------------------------------------------------------
  public void DeleteAll() throws Exception {
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
  public int get_id() {
    return _id;
  }
  public String getVersion() {
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
