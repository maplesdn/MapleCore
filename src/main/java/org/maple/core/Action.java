package org.maple.core;

import java.util.HashSet;

public class Action {
  private static Punt punt = new Punt();
  
  public static Action Punt() { return punt; }
  public static Action ToPort(int portID) { return new ToPort(portID); }
}
