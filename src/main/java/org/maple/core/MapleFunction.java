package org.maple.core;

public abstract class MapleFunction {
  public abstract int onPacket(Packet p, int switchID, int portID);
}
