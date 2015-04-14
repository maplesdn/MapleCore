package org.maple.core;

public class ToPort extends Action {
  public int portID;
  public ToPort(int portID) {
    this.portID = portID;
  }
  
  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    ToPort other2 = (ToPort) other;
    return portID == other2.portID;
  }
}
