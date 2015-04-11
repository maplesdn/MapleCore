/**
 * Created by zhushigang on 2/21/15.
 */

package org.maple.core;

import java.util.HashSet;
import java.util.LinkedList;


public class MapleSystem {

  private Controller controller;
  private MapleFunction userFunction;
  TraceTree traceTree;

  HashSet<Integer> ports;

  public MapleSystem(Controller c) {

    traceTree = new TraceTree();
    ports = new HashSet<Integer>();
    
    if (c == null)
      throw new IllegalArgumentException("controller passed to " +
                                         "MapleSystem must be non-null");

    this.controller = c;

    // TODO: pass userFunction as an argument into init,
    // rather than hard-coding it.
    // this.userFunction = new SampleMapleFunction();
    this.userFunction = new LearningSwitch();
  }

  public void portUp(int port) {
    System.out.println("MapleSystem.portUp(" + port +")");
    ports.add(port);
  };
  public void portDown(int port) {
    System.out.println("MapleSystem.portDown(" + port +")");    
    ports.remove(port);
  };
  
  public void handlePacket(byte[] data, int inSwitch, int inPort) {
    System.out.println("Maple received a packet with inPort: " +
                       inPort + " and frame len: " + data.length);

    Ethernet frame = new Ethernet();
    frame.deserialize(data, 0, data.length);
    System.out.println("handlePacket.frame: " + frame);

    Packet p = new Packet(frame, inPort);

    int out = userFunction.onPacket(p);
    
    System.out.println("User's MapleFunction returned: " + out + " with trace: " + traceString(p.trace));

    traceTree.augment(p.trace, out);

    controller.sendPacket(data, inSwitch, inPort, out);
  }

  String traceString(LinkedList<TraceItem> trace) {
    StringBuilder builder = new StringBuilder();
    for (TraceItem item : trace) {
      builder.append(item.toString());
    }
    return builder.toString();
  }

  
}
