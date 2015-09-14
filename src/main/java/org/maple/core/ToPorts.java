package org.maple.core;
import java.util.Arrays;
import java.util.HashSet;


public class ToPorts extends Action {
  public SwitchPort[] portIDs;
  public ToPorts(SwitchPort... portIDs) {
    this.portIDs = portIDs;
  }
  
  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    if (other instanceof ToPorts) {
      ToPorts other2 = (ToPorts) other;
      HashSet<SwitchPort> ports = new HashSet<SwitchPort>(Arrays.asList(portIDs));
      HashSet<SwitchPort> ports2 = new HashSet<SwitchPort>(Arrays.asList(other2.portIDs));
      if (ports.equals(ports2)) return true;
      else
      return false;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    final int prime = 5557;
    int result = 1;
    for (SwitchPort p : portIDs) {
      result = prime * result + p.getPort();
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
