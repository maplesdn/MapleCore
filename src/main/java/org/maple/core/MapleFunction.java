package org.maple.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * The user defined algorithm policies should extend this class
 */
public abstract class MapleFunction {

  HashSet<SwitchPort> ports = new HashSet<SwitchPort>();
  HashMap<Long, Switch> switches = new HashMap<Long, Switch>();

  final public HashSet<SwitchPort> ports(Long switchID) {
    // make a copy:
    return new HashSet<SwitchPort>(switches.get(switchID).getPorts());
  }

  
  /**
   * onPacket will be called when a packet is received in Maple system
   * @param p   the parsed packet p will to be processed by the user defined algorithm policies
   * @return    a set of egress port numbers as the output of the user defined algorithm policies
   */
  public abstract Route onPacket(Packet p);
}
