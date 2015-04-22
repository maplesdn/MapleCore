package org.maple.core;

/**
 * The user defined algorithm policies should extend this class
 */
public abstract class MapleFunction {
  /**
   * onPacket will be called when a packet is received in Maple system
   * @param p   the parsed packet p will to be processed by the user defined algorithm policies
   * @return    a set of egress port numbers as the output of the user defined algorithm policies
   */
  public abstract int onPacket(Packet p);
}
