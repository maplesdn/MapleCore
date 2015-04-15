package org.maple.core;

public class Drop extends Action {
  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    Drop other2 = (Drop) other;
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    return result;
  }
}
