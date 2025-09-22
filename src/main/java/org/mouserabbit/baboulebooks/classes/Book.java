package org.mouserabbit.baboulebooks.classes;

public class Book {

  protected int _id ;
  protected String _title ;
  protected int _author ;
  protected int _location ;
  protected int _editor ;

  public Book() {

  }
  public Book(String _title) {
    this._title = _title;
  }

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
