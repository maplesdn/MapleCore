package org.maple.core;

import java.util.List;

public class L extends Node {

  int[] outcome;

  public L(int[] outcome) {
    this.outcome = outcome;
  }
  
  public void augment(List<TraceItem> trace, int... ports) {
    
  }

  public int[] evaluate(int inPort, Ethernet frame) {
    return outcome;
  }
  
}
