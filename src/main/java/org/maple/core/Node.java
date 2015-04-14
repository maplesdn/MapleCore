package org.maple.core;

import java.util.List;

public abstract class Node {

  public abstract void augment(List<TraceItem> trace, int... ports);
  public abstract int[] evaluate(int inPort, Ethernet frame);
}
