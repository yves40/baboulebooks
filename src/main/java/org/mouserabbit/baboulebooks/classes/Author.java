package org.mouserabbit.baboulebooks.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import org.mouserabbit.baboulebooks.classes.DBsingleton;
import org.mouserabbit.baboulebooks.classes.LoggerSingleton;

import org.apache.logging.log4j.Logger;

public class Author {
  
  private static String version = "Location, Oct 07 2025 : 1.16";
  protected int _id ;
  protected String _firstname ;
  protected String _lastname ;
  protected Connection _dbconn = null;
  protected Logger _logger = null;

  
  public Author() throws Exception { 
    _logger = LoggerSingleton.getInstance().get_logger();
    try {
      _dbconn = DBsingleton.getInstance().get_dbconn();
      Normalize( _firstname, _lastname);
    }
    catch (Exception e) {
      _logger.error(e.getMessage());
    }
  }
  public Author(String _firstname, String _lastname) throws Exception {
    _logger = LoggerSingleton.getInstance().get_logger();
    try {
      _dbconn = DBsingleton.getInstance().get_dbconn();
      Normalize( _firstname, _lastname);
    }
    catch (Exception e) {
      _logger.error(e.getMessage());
    }
  }
  // ---------------------------------------------------------------------
  // Insert Author data
  // Called after the object is created and populated
  // ---------------------------------------------------------------------
  public void Insert() throws SQLException, Exception {

    if(_dbconn == null) throw new Exception("DB connection is not initialized");
    Statement stmt = _dbconn.createStatement();
    String sqlstatement = "insert into authors ( auth_fname, auth_lname ) values ( \"" + 
      this._firstname + "\",\"" +  this._lastname + "\" )";
    try {
      stmt.executeUpdate(sqlstatement,Statement.RETURN_GENERATED_KEYS);
      ResultSet rs = stmt.getGeneratedKeys();
      if(rs.next()) {
        this._id = rs.getInt(1);
      }
      // stmt.executeUpdate("commit");
    }
    catch(SQLIntegrityConstraintViolationException sqli) {
      // Already inserted, get exisiting data
      Author alreadyexists = getAuthorByName(this._firstname, this._lastname);
      this.set_id(alreadyexists.get_id());
    }
    catch(SQLSyntaxErrorException sqlse) {
      _logger.error(sqlse.getMessage());
      _logger.error(sqlstatement);
    }
  }
  // ---------------------------------------------------------------------
  // Get Location data
  // ---------------------------------------------------------------------
  public Author getAuthorByName(String firstname, String lastname) throws Exception {
    if(_dbconn == null) throw new Exception("DB connection is not initialized");
    Statement stmt = _dbconn.createStatement();
    try {
      stmt.executeQuery("select * from authors where auth_fname = \"" +  firstname + "\"" +
                        " and auth_lname = \"" +  lastname + "\"");   
      ResultSet rs = stmt.getResultSet();
      Author author = null;
      if(rs.next()) {
        author = new Author(rs.getString("auth_fname"), rs.getString("auth_lname"));
        author.set_id(rs.getInt("auth_id"));
      }
      return author;
    }
    catch(SQLException sqle) {
      _logger.error(sqle.getMessage());
      return new Author("","");
    }
  }
  public Author getAuthorByid(int id) throws Exception {
    if(_dbconn == null) throw new Exception("DB connection is not initialized");
    Statement stmt = _dbconn.createStatement();
    try {
      stmt.executeQuery("select * from authors where auth_id = " +  id );   
      ResultSet rs = stmt.getResultSet();
      Author author = null;
      if(rs.next()) {
        author = new Author(rs.getString("auth_fname"), rs.getString("auth_lname"));
        author.set_id(rs.getInt("auth_id"));
      }
      return author;
    }
    catch(SQLException sqle) {
      _logger.error(sqle.getMessage());
      return new Author("","");
    }
  }
  // ---------------------------------------------------------------------
  // Cleanup DB table
  // ---------------------------------------------------------------------
  public void DeleteAll() throws Exception {
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
  protected void Normalize(String firstname, String lastname) {
    try {
      String normalized = firstname.strip();
      _firstname = normalized.substring(0, 1).toUpperCase() +  normalized.substring(1).toLowerCase();
      normalized = lastname.strip();
      _lastname = normalized.toUpperCase();
      return;
    }
    catch(Exception e) {
      System.out.println(e.getMessage());
    }
  }
  // ---------------------------------------------------------------------
  // Setters & Getters
  // ---------------------------------------------------------------------
  public String get_firstname() {
    return _firstname;
  }
  public String getVersion() {
    return version;
  }
  public int get_id() {
    return _id;
  }
  public String get_lastname() {
    return _lastname;
  }
  
  public void set_id(int _id) {
    this._id = _id;
  }
  public void set_firstname(String _firstname) {
    this._firstname = _firstname;
  }
  public void set_lastname(String _lastname) {
    this._lastname = _lastname;
  }
}
