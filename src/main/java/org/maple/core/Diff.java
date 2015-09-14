package org.maple.core;
import java.util.HashSet;
import java.util.LinkedList;
public class Diff {
  public HashSet<Rule> removed;  // = old - new
  public HashSet<Rule> added;    // = new - old

  public Diff (HashSet<Rule> removed, HashSet<Rule> added) {
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
