package org.maple.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class V extends Node {
  public TraceItem.Field field = null;
  public Map<Long,Node> subtree = new HashMap<Long, Node>();

  public Node getChild(long value) {
    return subtree.get(value);
  }

  public void augment(List<TraceItem> trace, int... ports) {
    if (trace.isEmpty()) {
      return;
    }
    TraceItem next = trace.remove(0);
    field = next.field;
    if (trace.size()==0) {
      subtree.put(next.value,new L(ports));
    } else {
      V child = new V();
      subtree.put(next.value,child);
      child.augment(trace, ports);
    }
  }
  
  public int[] evaluate(int inPort, Ethernet frame) {
    Long value = extractField(field, inPort, frame);
    assert value != null;
    Node n = subtree.get(value);
    if (null == n) {
      return null;
    } else {
      return n.evaluate(inPort, frame);
    }
  }

  Long extractField(TraceItem.Field fld, int inPort, Ethernet frame) {
    switch (fld) {
      case IN_PORT: return (long) inPort;
      case ETH_SRC: return Ethernet.toLong(frame.getSourceMACAddress());
      case ETH_DST: return Ethernet.toLong(frame.getDestinationMACAddress());
      case ETH_TYPE: return (long) frame.getEtherType();
      default: return null;
    }
  }

}
