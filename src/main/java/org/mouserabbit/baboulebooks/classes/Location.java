package org.mouserabbit.baboulebooks.classes;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.logging.log4j.Logger;

public class Location {

  protected int _id ;
  protected String _city;
  protected Connection _dbconn = null;
  protected Logger _logger = null;

  public Location(String _city) {
    this._city = _city;
  }
  public Location(String _city, Connection _dbconn, Logger _logger) {
    this._city = _city;
    this._dbconn = _dbconn;
  }

  // Insert or Update location data
  // Called after object is created and populated
  public void Save() throws SQLException, Exception {

    if(_dbconn == null) throw new Exception("DB connection is not initialized");
    PreparedStatement pstmt = _dbconn.prepareStatement("insert into locations ( loc_city ) values ( ? )");
    pstmt.setString(1, this._city);
    try {
      pstmt.execute();
    }
    catch(SQLIntegrityConstraintViolationException sqli) {
      _logger.warn(this._city + " already exists");
    }
  }

  public int get_id() {
    return _id;
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

  
  
  
}
