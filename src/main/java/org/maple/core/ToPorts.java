package org.maple.core;

public class ToPorts extends Action {
  public int[] portIDs;
  public ToPorts(int... portIDs) {
    this.portIDs = portIDs;
  }
  
  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    if (other instanceof ToPorts) {
      ToPorts other2 = (ToPorts) other;
      if (portIDs.length != other2.portIDs.length) { return false; }
      for (int i = 0; i < portIDs.length; i++) {
        if (portIDs[i] != other2.portIDs[i]) { return false; }
      }
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    final int prime = 5557;
    int result = 1;
    for (int p : portIDs) {
      result = prime * result + p;
    }
    return result;
  }

  // TODO: This is quite inefficient; it would be better to use
  // StringBuilder here.
  @Override
  public String toString() {

    String start = "ToPorts: [";
    String end = "]";
    String middle = "";

    for (int i = 0; i < portIDs.length; i++) {
      middle += portIDs[i];
      if (i < portIDs.length - 1) { middle += ", "; }
    }
    
    return start + middle + end;
  }
}
