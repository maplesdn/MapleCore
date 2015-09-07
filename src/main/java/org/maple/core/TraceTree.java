package org.maple.core;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

public class TraceTree {

  public Node root = null;
  public int priority = 0;
  public LinkedList<Rule> rules;

  public void augment(List<TraceItem> trace, Route route) {
    int[] portsArray = new int[route.ports.size()];
    for (int i = 0; i < route.ports.size(); i++) {
      portsArray[i] = route.ports.get(i);
    }
    
    if(root==null) {
      root = trace2tree(trace, portsArray);
    } else {
      root.augment(trace, portsArray);
    }
  }

  public int[] evaluate(int inPort, Ethernet frame) {
    if (null==root) {
      return null;
    } else {
      return root.evaluate(inPort, frame);
    }
  }

  public LinkedList<Rule> compile() {
    rules = new LinkedList<Rule>();
    priority = 0;
    build(root, Match.matchAny());
    return rules;
  }

  //TODO: This is a very naive version of generating rules from TT.
  //      The first step towards optimizations is described in Maple paper Section 4.3.
  private void build(Node t,Match match) {
    if (t == null) {  // empty child of T nodes
      Rule rule = new Rule(priority, match, Action.Punt());
      rules.add(rule);
      priority += 1;
    }
    else if (t instanceof L) {
      L leaf = (L) t;
      Action action = new ToPorts(leaf.outcome);
      Rule rule = new Rule(priority, match, action);
      rules.add(rule);
      priority += 1;
    } 
    else if (t instanceof V) {
      Set<Long> keys = ((V) t).subtree.keySet();
      Iterator<Long> iterator = keys.iterator();
      int i=0;
      while(iterator.hasNext()) {
        TraceItemV item = new TraceItemV();
        item.field = ((V) t).field;
        item.value = iterator.next();
        Match m = Match.matchAny();
        for(TraceItem item2 : match.fieldValues) {
          m.add(item2);
        }
        m.add(item);
        build(((V) t).getChild(item.value),m);
      }
    } 
    else if (t instanceof T){
      Match m_neg = Match.matchAny(); 
      for (TraceItem item2 : match.fieldValues) {
        m_neg.add(item2);
      }
      build(((T) t).subtree[T.NEG_BRANCH],m_neg);
      
      TraceItemT item = new TraceItemT();
      item.field = ((T) t).field;
      item.value = ((T) t).value;
      item.Tvalue = true;
      Match m_barr = Match.matchAny(); 
      for (TraceItem item2 : match.fieldValues) {
        m_barr.add(item2);
      }
      m_barr.add(item);
      Rule rule = new Rule(priority, m_barr, Action.Punt()); // barrier rule
      rules.add(rule);
      
      Match m_pos = Match.matchAny(); 
      for (TraceItem item2 : match.fieldValues) {
        m_pos.add(item2);
      }
      m_pos.add(item);
      priority += 1;
      build(((T) t).subtree[T.POS_BRANCH],m_pos);
    }
    else {
      // We should never reach here.
      //FIXME:TODO: I hate this. Replace println with a uniform LOG module.
      System.out.println("Error in building rules: Unknown type of node in TT."); 
      //Rule rule = new Rule(priority, Match.matchAny(), Action.Punt());
      //rules.add(rule);
    }
  }

  private Node trace2tree(List<TraceItem> trace, int... ports) {
    if (trace.isEmpty()) {
      return new L(ports);
    }
    if ( trace.get(0) instanceof TraceItemT) {
      T treeRoot = new T();
      treeRoot.augment(trace,ports);
      return treeRoot;
    }
    else {
      V treeRoot = new V();
      treeRoot.augment(trace,ports);
      return treeRoot;
    }
  }
}
