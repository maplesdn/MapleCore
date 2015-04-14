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
  
}
