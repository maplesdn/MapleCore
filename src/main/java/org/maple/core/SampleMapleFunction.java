package org.maple.core;

public class SampleMapleFunction extends MapleFunction {

  @Override
  public int onPacket(Packet p) {
    return p.ingressPort() + 1;
  }

}
