package org.maple.core;

public class SampleMapleFunction extends MapleFunction {

  @Override public int onPacket(int p) {
    return p + 1;
  }

}
