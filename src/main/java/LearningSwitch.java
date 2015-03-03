/**
 * Created by zhushigang on 2/28/15.
 */

package org.maple.core;

import java.util.HashMap;
import java.util.Map;

public class LearningSwitch extends MapleFunction {

  private Map<String, Integer> MacTable;

  public LearningSwitch() {
    //TODO: initiate the Hashmap for port-address
    MacTable = new HashMap<String, Integer>();
  }

  @Override
  public int onPacket(Packet p, int switchID, int portID) {
    //TODO: implement the learning algorithm using Hashmap
    String src_mac = "1"; //TODO: replace with source mac from p
    MacTable.put(src_mac,portID);
    String dst_mac = "2"; // TODO; replace with destination mac from p
    if(MacTable.containsKey(dst_mac)==false) {
      //TODO: return broadcast
    }else {
      int dst_portID = MacTable.get(dst_mac);
      //TODO: forward to dst_portID
    }
    return 0;
  }
}
