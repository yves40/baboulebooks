package org.mouserabbit.baboulebooks.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import org.apache.logging.log4j.Logger;

public class Book {

  private static String version = "Book, Oct 07 2025 : 1.03";

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
    catch(SQLIntegrityConstraintViolationException sqli) {
      // Already inserted, get exisiting data
      Book alreadyexists = getBookByTitle(this._title);
      this.set_id(alreadyexists.get_id());
    }
    catch(SQLException sqle) {
      _logger.error("Book.java, SQL exception : " + this._title);
      _logger.error("Book.java : " + sqle.getMessage());
    }
  }
  // ---------------------------------------------------------------------
  // Get Book data
  // ---------------------------------------------------------------------
  public Book getBookByTitle(String title) throws Exception {
    if(_dbconn == null) throw new Exception("DB connection is not initialized");
    Statement stmt = _dbconn.createStatement();
    try {
      stmt.executeQuery("select * from books where bk_title = \"" +  title + "\"");   
      ResultSet rs = stmt.getResultSet();
      Book book = null;
      if(rs.next()) {
        book = new Book(rs.getString("bk_title"));
        book.set_id(rs.getInt("bk_id"));
      }
      return book;
    }
    catch(SQLException sqle) {
      _logger.error(sqle.getMessage());
      return new Book(title);
    }
  }
  public Book getBookByid(int id) throws Exception {
    if(_dbconn == null) throw new Exception("DB connection is not initialized");
    Statement stmt = _dbconn.createStatement();
    try {
      stmt.executeQuery("select * from books where bk_id = " +  id );   
      ResultSet rs = stmt.getResultSet();
      Book book = null;
      if(rs.next()) {
        book = new Book(rs.getString("bk_title"));
        book.set_id(rs.getInt("bk_id"));
      }
      return book;
    }
    catch(SQLException sqle) {
      _logger.error(sqle.getMessage());
      return new Book("Book ID not found");
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
      // stmt.executeUpdate("commit");
    }
    catch(SQLException sqle) {
      _logger.error("Cannot delete all books : " + sqle.getMessage());
    }
  }
  // ---------------------------------------------------------------------
  // Setters & Getters
  // ---------------------------------------------------------------------
  public int get_id() {
    return _id;
  }
  public static String getVersion() {
    return version;
  }
  public static void setVersion(String version) {
    Book.version = version;
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
