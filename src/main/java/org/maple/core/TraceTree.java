package org.maple.core;

import java.util.List;

public class TraceTree {

  public Node root = null;

  public void augment(List<TraceItem> trace, int... ports) {
    if(root==null) {
        root = trace2tree(trace, ports);
    }
    // TODO: add case where root!=null
  }

  public int[] evaluate(int inPort, Ethernet frame) {
    return null;
  }

  private Node trace2tree(List<TraceItem> trace, int... ports) {
    if (trace.isEmpty()) {
      return new L(ports);
    }
    V treeRoot = new V();
    treeRoot.augment(trace,ports);
    return treeRoot;
  }
  
}
