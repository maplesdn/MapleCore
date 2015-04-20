package org.maple.core;

import sun.awt.image.ImageWatched;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;

public class TraceTree {

  public Node root = null;
  public int priority = 0;

  public void augment(List<TraceItem> trace, int... ports) {
    if(root==null) {
      root = trace2tree(trace, ports);
    } else {
      root.augment(trace, ports);
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
    LinkedList<Rule> rules = new LinkedList<Rule>();
    priority = 0;
    build(root, Match.matchAny(),rules);
    return rules;
  }

  private void build(Node t, Match m, LinkedList<Rule> rules) {
    if (t instanceof L) {
      LinkedList<Action> actions = new LinkedList<Action>();
      for (int i=0; i<((L) t).outcome.length; i++) {
        ToPort toPort = new ToPort(((L) t).outcome[i]);
        actions.add(toPort);
      }
      Rule rule = new Rule(priority, m, actions);
      rules.add(rule);
    }
    else {
      assert t instanceof V;
      Set<Long> keys = ((V) t).subtree.keySet();
      Iterator<Long> iterator = keys.iterator();
      while(iterator.hasNext()) {
        TraceItem item = new TraceItem();
        item.field = ((V) t).field;
        item.value = iterator.next();
        m.add(item);
        build(((V) t).getChild(item.value), m, rules);
      }
    }
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
