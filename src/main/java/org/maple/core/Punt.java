package org.maple.core;

public class Punt extends Action {
  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    if (other instanceof Punt) {
      Punt other2 = (Punt) other;
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    int result = 1;
    return result;
  }

  @Override
  public String toString() {
    return "Punt; ";
  }
}
