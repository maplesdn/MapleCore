/**
 * Created by zhushigang on 2/28/15.
 */

package org.maple.core;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

/**
 * Example user defined algorithm policy which simple simulates the behaviour of a learing switch
 */
public class LearningSwitch extends MapleFunction {
  private Map<Long, Integer> MacTable;

  public LearningSwitch() {
    MacTable = new HashMap<Long, Integer>();
  }

  @Override
  public Route onPacket(Packet p) {
    long src_mac = p.ethSrc();
    MacTable.put(src_mac,p.ingressPort());
    long dst_mac = p.ethDst();
    if(MacTable.containsKey(dst_mac)==false) {
      return Route.toPorts(Integer.MAX_VALUE);
    }else {
      int dst_portID = MacTable.get(dst_mac);
      return Route.toPorts(dst_portID);
    }
  }
}
