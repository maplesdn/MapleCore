package org.maple.core;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Example user defined algorithm policy which simple simulates the
 behaviour of a learing switch with some security rules.
 */
public class ACLLearningSwitch extends MapleFunction {
  
  Map<Long, Integer> macTable;

  // TODO: make blacklist editable via runtime administrative API.
  HashSet<Long> blacklist;

  public ACLLearningSwitch() {
    macTable = new HashMap<Long, Integer>();
    blacklist = new HashSet<Long>();
    blacklist.add(2L);
  }

  @Override
  public Route onPacket(Packet p) {
    long src_mac = p.ethSrc();

    if (blacklist.contains(src_mac)) { return Route.drop(); }
    
    macTable.put(src_mac,p.ingressPort());
    long dst_mac = p.ethDst();
    if(macTable.containsKey(dst_mac)==false) {
      return Route.toPorts(ports());
    }else {
      int dst_portID = macTable.get(dst_mac);
      return Route.toPorts(dst_portID);
    }
  }
}
