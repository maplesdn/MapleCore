package org.maple.core;

import java.util.HashSet;

public class Action {
  private static Punt punt = new Punt();
  private static Drop drop = new Drop();
  
  public static Action Punt() { return punt; }
  public static Action ToPorts(SwitchPort... portIDs) { return new ToPorts(portIDs); }
  public static Action Drop() { return drop; }
}
