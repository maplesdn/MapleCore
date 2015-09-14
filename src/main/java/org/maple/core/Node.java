package org.maple.core;

import java.util.List;

public abstract class Node {

  public abstract void augment(List<TraceItem> trace, SwitchPort... ports);
  public abstract SwitchPort[] evaluate(SwitchPort inPort, Ethernet frame);
}
