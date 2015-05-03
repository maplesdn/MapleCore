package org.maple.core;

public class Drop extends Action {
  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    if (other instanceof Drop) {
      Drop other2 = (Drop) other;
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
    return "Drop; ";
  }
}
