package org.maple.core;

public class SampleMapleFunction extends MapleFunction {

  @Override public int onPacket(Packet p, int switchID, int portID) {
    return switchID + 1;
  }

}
