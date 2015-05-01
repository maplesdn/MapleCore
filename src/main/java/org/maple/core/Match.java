package org.maple.core;

import java.util.HashSet;

public class Match {
  public HashSet<TraceItem> fieldValues;
  public Match() {}
  public Match(HashSet<TraceItem> fieldValues) {
    this.fieldValues = fieldValues;
  }

  public static Match matchAny() {
    return new Match(new HashSet<TraceItem>());
  }

  public Match add(TraceItem item) {
    fieldValues.add(item);
    return this;
  }

  @Override
  public String toString() {
    String str = "Matches: ";
    for (TraceItem item : fieldValues) {
      str+=item.toString();
      str+="; ";
    }
    return str.substring(0,str.length()-2);
  }

  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    Match other2 = (Match) other;
    return fieldValues.equals(other2.fieldValues);
  }
}
