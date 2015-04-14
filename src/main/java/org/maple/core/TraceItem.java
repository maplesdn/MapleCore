package org.maple.core;

public class TraceItem {

  public enum Field {
    IN_PORT,
    ETH_SRC, 
    ETH_DST,
    ETH_TYPE
  }
  
  public Field field;
  public long value;

  // TODO add hashCode()

  @Override
  public String toString() {
    return "TraceItem [field=" + field + ", value=" + value + "]";
  }

  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    TraceItem other2 = (TraceItem) other;
    return field==other2.field && value==other2.value;
  }

  public static TraceItem ethSrcItem(long addr) {
    TraceItem item = new TraceItem();
    item.field = TraceItem.Field.ETH_SRC;
    item.value = addr;
    return item;
  }

  public static TraceItem ethDstItem(long addr) {
    TraceItem item = new TraceItem();
    item.field = TraceItem.Field.ETH_DST;
    item.value = addr;
    return item;
  }

  public static TraceItem ethTypeItem(int typ) {
    TraceItem item = new TraceItem();
    item.field = TraceItem.Field.ETH_TYPE;
    item.value = typ;
    return item;
  }

  public static TraceItem inPortItem(int port) {
    TraceItem item = new TraceItem();
    item.field = TraceItem.Field.IN_PORT;
    item.value = port;
    return item;
  }

}
