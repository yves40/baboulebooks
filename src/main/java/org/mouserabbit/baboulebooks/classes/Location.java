package org.mouserabbit.baboulebooks.classes;

public class Location {

  protected int _id ;
  protected String _city ;

  public Location(String _city) {
    this._city = _city;
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
