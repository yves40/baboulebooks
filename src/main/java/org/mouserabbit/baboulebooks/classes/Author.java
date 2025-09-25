package org.mouserabbit.baboulebooks.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.apache.logging.log4j.Logger;

public class Author {
  
  private static String version = "Location, Sep 25 2025 : 1.11";
  protected int _id ;
  protected String _firstname ;
  protected String _lastname ;
  protected static Connection _dbconn = null;
  protected static Logger _logger = null;

  
  public Author(String _firstname, String _lastname) {
    String concat = Normalize( _firstname, _lastname);
  }
  // ---------------------------------------------------------------------
  // Insert Author data
  // Called after the object is created and populated
  // ---------------------------------------------------------------------
  public void Insert() throws SQLException, Exception {

    if(_dbconn == null) throw new Exception("DB connection is not initialized");
    PreparedStatement pstmt = _dbconn.prepareStatement("insert into authors ( auth_fname, auth_lname ) values ( ?, ? )");
    pstmt.setString(1, this._firstname);
    pstmt.setString(2, this._lastname);
    try {
      pstmt.execute();
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
    _logger.info("Delete all authors");
    PreparedStatement stmt = _dbconn.prepareStatement("delete from authors");
    try {
      int i = stmt.executeUpdate();
      _logger.info("\tDeleted " + i + " Authors record(s)");
      stmt.executeUpdate("commit");
    }
    catch(SQLException sqle) {
      // Already inserted, no action
      _logger.error("Cannot delete all Locations : " + sqle.getMessage());
    }

  }
  // ---------------------------------------------------------------------
  // Normalize author data
  // ---------------------------------------------------------------------
  protected String Normalize(String firstname, String lastname) {
    try {
      String normalized = firstname.strip();
      _firstname = normalized.substring(0, 1).toUpperCase() +  normalized.substring(1).toLowerCase();
      normalized = lastname.strip();
      _lastname = normalized.toUpperCase();
      return _firstname + "." + _lastname;
    }
    catch(Exception e) {
      System.out.println(e.getMessage());
      return "";
    }
  }
  // ---------------------------------------------------------------------
  // Setters & Getters
  // ---------------------------------------------------------------------
  public String get_firstname() {
    return _firstname;
  }
  public static String getVersion() {
    return version;
  }
  public int get_id() {
    return _id;
  }
  public String get_lastname() {
    return _lastname;
  }
  public static void set_dbconn(Connection _dbconn) {
    Author._dbconn = _dbconn;
  }
  public static void set_logger(Logger _logger) {
    Author._logger = _logger;
  }
}
