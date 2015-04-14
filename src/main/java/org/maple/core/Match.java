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

  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    Match other2 = (Match) other;
    return fieldValues.equals(other2.fieldValues);
  }
}
