package org.maple.core;

import java.util.LinkedList;

public class SampleMapleFunction extends MapleFunction {

  @Override
  public Route onPacket(Packet p) {
    return Route.drop();
  }

}
