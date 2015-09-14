/**
 * Created by zhushigang on 2/21/15.
 */

package org.maple.core;

import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Maple System is the runtime system which should be initiated on the SDN controller side
 */
public class MapleSystem {

  private Controller controller;
  private MapleFunction userFunction;
  TraceTree traceTree;
  HashSet<Rule> currentRules;
  Set<SwitchPort> ports;

  /**
   * Constructor to initiated the Maple System
   * User should assign this.userFunction to their algorithm policy
   * @param c   the controller class which should be implemented on the controller side
   */
  public MapleSystem(Controller c) {

    traceTree = new TraceTree();
    currentRules = new HashSet<Rule>();
    ports = new HashSet<SwitchPort>();
    
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
  public void portUp(SwitchPort port) {
    // System.out.println("MapleSystem.portUp(" + port +")");
    ports.add(port);
    // userFunction.ports.add(port);
  };

  /**
   * The controller should call this function when a port in the network went down
   * @param port the reference to the port went down
   */
  public void portDown(SwitchPort port) {
    // System.out.println("MapleSystem.portDown(" + port +")");    
    ports.remove(port);
    // userFunction.ports.remove(port);
  };

  /**
   * The controller should call this function when a packet is received
   * @param data actual packet received
   * @param inSwitch  reference to the ingress switch
   * @param inPort reference to the ingress port
   */
  public void handlePacket(byte[] data, Switch inSwitch, SwitchPort inPort) {
    /* System.out.println("Maple received a packet with inPort: " +
       inPort + " and frame len: " + data.length); */

    Ethernet frame = new Ethernet();
    frame.deserialize(data, 0, data.length);
    // System.out.println("handlePacket.frame: " + frame);

    Packet p = new Packet(frame, inPort);

    Route out = userFunction.onPacket(p);
    
    //System.out.println("User's MapleFunction returned: "  + "Trace: " + traceString(p.trace) + out);

    traceTree.augment(p.trace, out);

    controller.sendPacket(data, inSwitch, inPort, out.getEndPoints().toArray(new SwitchPort[0]));

    // Inform controller of updated rule sets.
    HashSet<Rule> oldRules = currentRules;
    currentRules = traceTree.compile();
    
    // Print out all of the rules for testing.
    //ListIterator<Rule> ruleIterator = currentRules.listIterator();
    //System.out.println("==== Rule Generated ====");
    //while (ruleIterator.hasNext()) {
    //	System.out.println(ruleIterator.next());
    //}
    //System.out.println("====      ====");
    
    Diff diff = diff(oldRules, currentRules);
    controller.deleteRules(diff.removed, inSwitch);
    controller.installRules(diff.added, inSwitch); 
  }


  
  public Diff diff(HashSet<Rule> oldRules, HashSet<Rule> newRules) {
    HashSet<Rule> oldRulesCopy = new HashSet<Rule>(oldRules);
    oldRulesCopy.removeAll(newRules);
    HashSet<Rule> removed = oldRulesCopy;

    HashSet<Rule> newRulesCopy = new HashSet<Rule>(newRules);
    newRulesCopy.removeAll(oldRules);
    HashSet<Rule> added = newRulesCopy;



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
