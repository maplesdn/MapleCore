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
public class LearningSwitchPort1to4 extends MapleFunction {
  private Map<Long, SwitchPort> MacTable;

  public LearningSwitchPort1to4() {
    MacTable = new HashMap<Long, SwitchPort>();
  }

  @Override
  public Route onPacket(Packet p) {
	  
    boolean isPort1 = p.ingressPortIs(new SwitchPort((long) 0, 1));
    if (isPort1) {
      boolean isARP = p.ethTypeIs(Ethernet.TYPE_ARP);
      if (isARP)
        return Route.toPorts(new SwitchPort((long) 0, 4));
    }
    
    // learningswitch
    long src_mac = p.ethSrc();
    MacTable.put(src_mac,p.ingressPort());
    long dst_mac = p.ethDst();
    if(MacTable.containsKey(dst_mac)==false) {
      return Route.toPorts(ports(p.ingressPort().getSwitch()));
    } else {
      SwitchPort dst_portID = MacTable.get(dst_mac);
      return Route.toPorts(dst_portID);
    }
  }
}