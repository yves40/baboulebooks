package org.mouserabbit.baboulebooks.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import org.apache.logging.log4j.Logger;

public class Editor {

  private static String version = "Editor, Sep 27 2025 : 1.01";
  protected int _id ;
  protected String _name;
  protected static Connection _dbconn = null;
  protected static Logger _logger = null;

  public Editor(String _name) {
    this._name = Normalize(_name);
  }
    // ---------------------------------------------------------------------
  // Insert Editor data
  // Called after the object is created and populated
  // ---------------------------------------------------------------------
  public void Insert() throws SQLException, Exception {

    if(_dbconn == null) throw new Exception("DB connection is not initialized");
    Statement stmt = _dbconn.createStatement();
    try {
      stmt.executeUpdate("insert into editors ( ed_name ) " + 
            "values ( '" + this._name + "' )",
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
    _logger.info("Delete all editors");
    PreparedStatement stmt = _dbconn.prepareStatement("delete from editors");
    try {
      int i = stmt.executeUpdate();
      _logger.info("\tDeleted " + i + " Editors record(s)");
      stmt.executeUpdate("commit");
    }
    catch(SQLException sqle) {
      // Already inserted, no action
      _logger.error("Cannot delete all editors : " + sqle.getMessage());
    }
  }
  // ---------------------------------------------------------------------
  // Setters & Getters
  // ---------------------------------------------------------------------
  public static void set_dbconn(Connection _dbconn) {
    Editor._dbconn = _dbconn;
  }
  public static void set_logger(Logger _logger) {
    Editor._logger = _logger;
  }
  public int get_id() {
    return _id;
  }
  public void set_id(int _id) {
    this._id = _id;
  }
  public String get_name() {
    return _name;
  }
  public void set_name(String _name) {
    this._name = _name;
  }
  // ---------------------------------------------------------------------
  // Normalize editor name
  // ---------------------------------------------------------------------
  protected String Normalize(String name) {
    try {
      return name.strip().toUpperCase();
    }
    catch(Exception e) {
      System.out.println(e.getMessage());
      return "";
    }
  }    
}
