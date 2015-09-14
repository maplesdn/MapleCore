package org.maple.core;

import java.util.List;

public class L extends Node {

  SwitchPort[] outcome;

  public L(SwitchPort[] outcome) {
    this.outcome = outcome;
  }
  
  public void augment(List<TraceItem> trace, SwitchPort... ports) {
    
  }

  public SwitchPort[] evaluate(SwitchPort inPort, Ethernet frame) {
    return outcome;
  }
  
}
