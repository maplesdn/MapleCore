package org.maple.core;

import org.maple.core.TraceItem.Field;

public class TraceItemV extends TraceItem {

  @Override
  public String toString() {
    return "TraceItemV [field=" + field + ", value=" + value + "] --> ";
  }

  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    TraceItem other2 = (TraceItem) other;
    return field==other2.field && value==other2.value;
  }

  @Override
  public int hashCode() {
    final int prime = 5557;
    int result = 1;
    result = prime * result + (int) field.ordinal();
    result = prime * result + (int) value;
    return result;
  }

  public static TraceItemV ethSrc(long addr) {
    TraceItemV item = new TraceItemV();
    item.field = TraceItemV.Field.ETH_SRC;
    item.value = addr;
    return item;
  }

  public static TraceItemV ethDst(long addr) {
    TraceItemV item = new TraceItemV();
    item.field = TraceItemV.Field.ETH_DST;
    item.value = addr;
    return item;
  }

  public static TraceItemV ethType(int typ) {
    TraceItemV item = new TraceItemV();
    item.field = TraceItemV.Field.ETH_TYPE;
    item.value = typ;
    return item;
  }

  public static TraceItemV inPort(int port) {
    TraceItemV item = new TraceItemV();
    item.field = TraceItemV.Field.IN_PORT;
    item.value = port;
    return item;
  }

}
