package org.maple.core;
import java.util.LinkedList;
public class Diff {
  public LinkedList<Rule> removed;  // = old - new
  public LinkedList<Rule> added;    // = new - old

  public Diff (LinkedList<Rule> removed, LinkedList<Rule> added) {
    this.removed = removed;
    this.added   = added;
  }

  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    if (other instanceof Diff) {
      Diff other2 = (Diff) other;
      return
          removed.equals(other2.removed) &&
          added.equals(other2.added);
    } else {
      return false;
    }
  }

  
}
