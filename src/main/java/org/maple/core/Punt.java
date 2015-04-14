package org.maple.core;

public class Punt extends Action {
  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    Punt other2 = (Punt) other;
    return true;
  }
}
