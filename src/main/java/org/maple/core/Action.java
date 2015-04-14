package org.maple.core;

import java.util.HashSet;

public class Action {
  public static Action Punt() { return new Punt(); }
  public static Action ToPort(int portID) { return new ToPort(portID); }
}
