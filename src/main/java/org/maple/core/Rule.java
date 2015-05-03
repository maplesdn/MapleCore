package org.maple.core;

import java.util.LinkedList;

public class Rule {

  public int priority;
  public Match match;
  public LinkedList<Action> actions;
  
  public Rule(int p, Match m, LinkedList<Action> actions) {
    this.priority = p;
    this.match = m;
    this.actions = actions;    
  }

  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    Rule other2 = (Rule) other;
    return
        priority==other2.priority &&
        match.equals(other2.match) &&
        actions.equals(other2.actions);
  }

  @Override
  public String toString() {
    String str = "Rule (Priority: ";
    str+= priority;
    str+=", ";
    str+=match.toString();
    str+=", ";
    for(Action action : actions)
      str+=action.toString();
    str+=")";
    return str;
  }

  public static LinkedList<Action> punt() {
    LinkedList<Action> as = new LinkedList<Action>();
    as.add(Action.Punt());
    return as;
  }

}
