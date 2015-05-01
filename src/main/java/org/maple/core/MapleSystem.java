/**
 * Created by zhushigang on 2/21/15.
 */

package org.maple.core;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Maple System is the runtime system which should be initiated on the SDN controller side
 */
public class MapleSystem {

  private Controller controller;
  private MapleFunction userFunction;
  TraceTree traceTree;

  HashSet<Integer> ports;

  /**
   * Constructor to initiated the Maple System
   * User should assign this.userFunction to their algorithm policy
   * @param c   the controller class which should be implemented on the controller side
   */
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

  /**
   * The controller should call this function when a port in the network went up
   * @param port the reference to the port went up
   */
  public void portUp(int port) {
    System.out.println("MapleSystem.portUp(" + port +")");
    ports.add(port);
    userFunction.ports.add(port);
  };

  /**
   * The controller should call this function when a port in the network went down
   * @param port the reference to the port went down
   */
  public void portDown(int port) {
    System.out.println("MapleSystem.portDown(" + port +")");    
    ports.remove(port);
    userFunction.ports.remove(port);    
  };

  /**
   * The controller should call this function when a packet is received
   * @param data actual packet received
   * @param inSwitch  reference to the ingress switch
   * @param inPort refernce to the ingress port
   */
  public void handlePacket(byte[] data, int inSwitch, int inPort) {
    System.out.println("Maple received a packet with inPort: " +
                       inPort + " and frame len: " + data.length);

    Ethernet frame = new Ethernet();
    frame.deserialize(data, 0, data.length);
    System.out.println("handlePacket.frame: " + frame);

    Packet p = new Packet(frame, inPort);

    Route out = userFunction.onPacket(p);
    
    System.out.println("User's MapleFunction returned: " + out + " with trace: " + traceString(p.trace));

    traceTree.augment(p.trace, out);

    // TODO: make this a single send to multiple ports.
    for (Integer port : out.ports) {
      controller.sendPacket(data, inSwitch, inPort, port);
    }
    controller.installRules(traceTree.compile(),inSwitch);

    //TODO
    // oldRules = currentRules (defined currentRules in this object).
    // currentRules = traceTree.compile();
    // Diff diff = diff(oldRules, currentRules);
    // deleteRules(diff.removed);
    // installRules(diff.added); 
  }

  
  public Diff diff(LinkedList<Rule> oldRules, LinkedList<Rule> newRules) {
    return new Diff(new LinkedList<Rule>(), new LinkedList<Rule>());
  }

  String traceString(LinkedList<TraceItem> trace) {
    StringBuilder builder = new StringBuilder();
    for (TraceItem item : trace) {
      builder.append(item.toString());
    }
    return builder.toString();
  }

  
}
