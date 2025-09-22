package org.mouserabbit.baboulebooks.classes;

public class Editor {

  protected int _id ;
  protected String _name ;

  public Editor(String _name) {
    this._name = _name;
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
  
}
