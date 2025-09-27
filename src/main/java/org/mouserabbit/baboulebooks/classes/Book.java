package org.mouserabbit.baboulebooks.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

import org.apache.logging.log4j.Logger;

public class Book {

  private static String version = "Book, Sep 27 2025 : 1.01";

  protected int _id ;
  protected String _title ;
  protected int _author ;
  protected int _location ;
  protected int _editor ;

  protected static Connection _dbconn = null;
  protected static Logger _logger = null;

  public Book(String _title) {
    this._title = Normalize(_title);
  }
  // ---------------------------------------------------------------------
  // Insert Editor data
  // Called after the object is created and populated
  // ---------------------------------------------------------------------
  public void Insert() throws SQLException, Exception {

    if(_dbconn == null) throw new Exception("DB connection is not initialized");
    Statement stmt = _dbconn.createStatement();
    String sqlstatement = "";
    try {
      sqlstatement = "insert into books ( bk_author, bk_editor, bk_location, bk_title ) " + 
            "values ( " + this._author + 
                "," + this._editor +
                "," + this._location + 
                ",\"" + this._title + "\"" +
            " )";
      stmt.executeUpdate(sqlstatement, Statement.RETURN_GENERATED_KEYS);   
      ResultSet rs = stmt.getGeneratedKeys();
      if(rs.next()) {
        this._id = rs.getInt(1);
      }
      stmt.executeUpdate("commit");
    }
    catch(SQLSyntaxErrorException sqlsyn) {
      _logger.error(sqlsyn.getMessage());
      // _logger.error(sqlstatement);
    }
    catch(SQLIntegrityConstraintViolationException sqli) {
      // Already inserted, no action
    }
  }
  // ---------------------------------------------------------------------
  // Normalize author data
  // ---------------------------------------------------------------------
  protected String Normalize(String title) {
    try {
      String normalized = title.strip();
      return normalized;
    }
    catch(Exception e) {
      System.out.println(e.getMessage());
      return "";
    }
  }
  // ---------------------------------------------------------------------
  // Cleanup DB table
  // ---------------------------------------------------------------------
  public static void DeleteAll() throws Exception {
    if(_dbconn == null) throw new Exception("DB connection is not initialized");
    _logger.info("Delete all books");
    PreparedStatement stmt = _dbconn.prepareStatement("delete from books");
    try {
      int i = stmt.executeUpdate();
      _logger.info("\tDeleted " + i + " Books record(s)");
      stmt.executeUpdate("commit");
    }
    catch(SQLException sqle) {
      // Already inserted, no action
      _logger.error("Cannot delete all books : " + sqle.getMessage());
    }
  }
  // ---------------------------------------------------------------------
  // Setters & Getters
  // ---------------------------------------------------------------------
  public int get_id() {
    return _id;
  }

  public void set_id(int _id) {
    this._id = _id;
  }

  public String get_title() {
    return _title;
  }

  public void set_title(String _title) {
    this._title = _title;
  }

  public int get_author() {
    return _author;
  }

  public static void set_dbconn(Connection _dbconn) {
    Book._dbconn = _dbconn;
  }
  public static void set_logger(Logger _logger) {
    Book._logger = _logger;
  }
  public void set_author(int _author) {
    this._author = _author;
  }

  public int get_location() {
    return _location;
  }

  public void set_location(int _location) {
    this._location = _location;
  }

  public int get_editor() {
    return _editor;
  }

  public void set_editor(int _editor) {
    this._editor = _editor;
  }
}
