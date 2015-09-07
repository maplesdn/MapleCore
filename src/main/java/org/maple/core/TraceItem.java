package org.maple.core;

public abstract class TraceItem {

  public enum Field {
    IN_PORT,
    ETH_SRC, 
    ETH_DST,
    ETH_TYPE
  }
  
  public Field field;
  public long value;
  public boolean Tvalue;

  public abstract String toString();
  public abstract boolean equals(Object other);
  public abstract int hashCode();

}
