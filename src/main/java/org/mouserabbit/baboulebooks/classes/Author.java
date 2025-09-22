package org.mouserabbit.baboulebooks.classes;

public class Author {
  
  protected int _id ;
  protected String _firstname ;
  protected String _lastname ;
  
  public Author(String _firstname, String _lastname) {
    this._firstname = _firstname;
    this._lastname = _lastname;
  }

  public String get_firstname() {
    return _firstname;
  }

  public String get_lastname() {
    return _lastname;
  }
  
  
}
