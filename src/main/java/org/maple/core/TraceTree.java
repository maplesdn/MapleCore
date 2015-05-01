package org.maple.core;

import sun.awt.image.ImageWatched;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;

public class TraceTree {

  public Node root = null;
  public int priority = 0;
  public LinkedList<Rule> rules;

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
    rules = new LinkedList<Rule>();
    priority = 0;
    build(root, Match.matchAny());
    return rules;
  }

  private void build(Node t,Match match) {
    if (t instanceof L) {
      L leaf = (L) t;
      LinkedList<Action> actions = new LinkedList<Action>();
      actions.add(new ToPorts(leaf.outcome));
      Rule rule = new Rule(priority, match, actions);
      rules.add(rule);
    }
    else if (t instanceof V){
      Set<Long> keys = ((V) t).subtree.keySet();
      Iterator<Long> iterator = keys.iterator();
      int i=0;
      while(iterator.hasNext()) {
        TraceItem item = new TraceItem();
        item.field = ((V) t).field;
        item.value = iterator.next();
        Match m = Match.matchAny();
        for(TraceItem item2 : match.fieldValues) {
          m.add(item2);
        }
        m.add(item);
        build(((V) t).getChild(item.value),m);
      }
    } else {
      LinkedList<Action> actions = new LinkedList<Action>();
      actions.add(Action.Punt());
      Rule rule = new Rule(priority,Match.matchAny(), actions);
      rules.add(rule);
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
