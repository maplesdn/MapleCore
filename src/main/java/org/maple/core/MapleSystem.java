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
  LinkedList<Rule> currentRules;
  HashSet<Integer> ports;

  /**
   * Constructor to initiated the Maple System
   * User should assign this.userFunction to their algorithm policy
   * @param c   the controller class which should be implemented on the controller side
   */
  public MapleSystem(Controller c) {

    traceTree = new TraceTree();
    currentRules = new LinkedList<Rule>();
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
    // System.out.println("MapleSystem.portUp(" + port +")");
    ports.add(port);
    userFunction.ports.add(port);
  };

  /**
   * The controller should call this function when a port in the network went down
   * @param port the reference to the port went down
   */
  public void portDown(int port) {
    // System.out.println("MapleSystem.portDown(" + port +")");    
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
    /* System.out.println("Maple received a packet with inPort: " +
       inPort + " and frame len: " + data.length); */

    Ethernet frame = new Ethernet();
    frame.deserialize(data, 0, data.length);
    // System.out.println("handlePacket.frame: " + frame);

    Packet p = new Packet(frame, inPort);

    Route out = userFunction.onPacket(p);
    
    // System.out.println("User's MapleFunction returned: " + out + " with trace: " + traceString(p.trace));

    traceTree.augment(p.trace, out);

    controller.sendPacket(data, inSwitch, inPort, listToArray(out.ports));

    // Inform controller of updated rule sets.
    LinkedList<Rule> oldRules = currentRules;
    currentRules = traceTree.compile();
    Diff diff = diff(oldRules, currentRules);
    controller.deleteRules(diff.removed, inSwitch);
    controller.installRules(diff.added, inSwitch); 
  }


  int[] listToArray(LinkedList<Integer> input) {
    int[] output = new int[input.size()];
    for (int i = 0; i < output.length; i++) {
      output[i] = input.get(i);
    }
    return output;
  }
  
  public Diff diff(LinkedList<Rule> oldRules, LinkedList<Rule> newRules) {
    LinkedList<Rule> removed = new LinkedList<Rule>();
    for(Rule oldRule : oldRules) {
      boolean inNewRules = false;
      for(Rule newRule : newRules) {
        if (oldRule.equals(newRule))
          inNewRules = true;
      }
      if(!inNewRules)
        removed.add(oldRule);
    }

    LinkedList<Rule> added = new LinkedList<Rule>();
    for(Rule newRule : newRules) {
      boolean inOldRules = false;
      for(Rule oldRule : oldRules) {
        if (newRule.equals(oldRule))
          inOldRules = true;
      }
      if(!inOldRules)
        added.add(newRule);
    }

    return new Diff(removed, added);
  }

  String traceString(LinkedList<TraceItem> trace) {
    StringBuilder builder = new StringBuilder();
    for (TraceItem item : trace) {
      builder.append(item.toString());
    }
    return builder.toString();
  }

  
}
