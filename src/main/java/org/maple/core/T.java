package org.maple.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class T extends Node {
  public TraceItemT.Field field = null;
  public long value;
  public final static int POS_BRANCH = 1;   // (val == exp);
  public final static int NEG_BRANCH = 0;   // (val != exp);
  public Node[] subtree = new Node[2];

  public Node getChild(boolean value) {
    if (value)
      return subtree[POS_BRANCH];
    else
      return subtree[NEG_BRANCH];
  }

  public void augment(List<TraceItem> trace, int... ports) {
    if (trace.isEmpty()) {
      return;
    }
    TraceItem next = trace.remove(0);
    System.out.println("trace2tree2" + next);
    field = next.field;
    value = next.value;
    if (trace.size()==0) {  // Add leaf node. 
      if (next.Tvalue == true)
        subtree[POS_BRANCH] = new L(ports); 
      else {
        subtree[NEG_BRANCH] = new L(ports);	
      }
    } else { // Augment trace recursively.
    	if(getChild(next.Tvalue)!= null)  
    	  getChild(next.Tvalue).augment(trace, ports);
        else { // Child is empty
          if ( trace.get(0) instanceof TraceItemT ) {
            T child = new T();
            subtree[next.Tvalue? POS_BRANCH : NEG_BRANCH] = child;
            child.augment(trace, ports);
          }
          else if ( trace.get(0) instanceof TraceItemV ) {
            V child = new V();
            subtree[next.Tvalue? POS_BRANCH : NEG_BRANCH] = child;
            child.augment(trace, ports);
          }
          else {
            System.out.println("Error in T.augment: Unknown type of TraceItem."); //FIXME:TODO: I hate this. Replace println with a uniform LOG module.
          }
        } // End of child is empty
    }
  }
  
  public int[] evaluate(int inPort, Ethernet frame) {
    Long value2 = extractField(field, inPort, frame);
    assert value2 != null;
    Node n = subtree[(value == value2) ? POS_BRANCH : NEG_BRANCH];
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
