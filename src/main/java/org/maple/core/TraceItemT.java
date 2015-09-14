package org.maple.core;

import org.maple.core.TraceItem.Field;

public class TraceItemT extends TraceItem {
	
  @Override
  public String toString() {
    return "TraceItemT [field=" + field + ", value=" + value + ", Tvalue=" + Boolean.toString(Tvalue) + "] --> ";
  }

  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    TraceItemT other2 = (TraceItemT) other;
    return field==other2.field && value==other2.value && Tvalue==other2.Tvalue;
  }

  @Override
  public int hashCode() {
    final int prime = 5557;
    int result = 1;
    result = prime * result + (int) field.ordinal();
    result = prime * result + (int) value;
    if (Tvalue)
      result += 1;
    return result;
  }

  public static TraceItemT ethSrcIs(long addr, long exp) {
    TraceItemT item = new TraceItemT();
    item.field = TraceItemT.Field.ETH_SRC;
    item.value = exp;
    item.Tvalue = (addr == exp);
    return item;
  }

  public static TraceItemT ethDstIs(long addr, long exp) {
    TraceItemT item = new TraceItemT();
    item.field = TraceItemT.Field.ETH_DST;
    item.value = exp;
    item.Tvalue = (addr == exp);
    return item;
  }

  public static TraceItemT ethTypeIs(int typ, int exp) {
    TraceItemT item = new TraceItemT();
    item.field = TraceItemT.Field.ETH_TYPE;
    item.value = exp;
    item.Tvalue = (typ == exp);
    return item;
  }

  public static TraceItemT inPortIs(SwitchPort port, SwitchPort exp) {
    TraceItemT item = new TraceItemT();
    item.field = TraceItemT.Field.IN_PORT;
    item.value = exp.hashCode();
    item.Tvalue = (port.equals(exp));
    return item;
  }
}
