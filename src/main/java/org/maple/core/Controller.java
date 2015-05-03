
package org.maple.core;

import java.util.LinkedList;

/**
 *  For integrating Maple System, the SDN controller needs to implement a controller class using this interface
 *  which abstracts the functions a SDN controller should provide to use Maple.
 *  The controller can therefore respond when the methods sendPacket or installRules are called
 */
public interface Controller {
  /**
   * sendPacket will be called when a packet is received by controller and passed to the Maple System
   * @param data the actual packet being sent out
   * @param inSwitch the switch where the system received this packet
   * @param inPort the port number on the ingress switch
   * @param ports a list of egress ports to send the packet to
   */
  public void sendPacket(byte[] data, int inSwitch, int inPort, int... ports);

  /**
   * installRules will be called when the Maple system compiles/issues OpenFlow Rules
   * @param rules a list of rules to install
   * @param outSwitches a list of switches in which to install the rules
   */
  public void installRules(LinkedList<Rule> rules, int... outSwitches);

  /**
   * deleteRules will be called when the Maple system removes OpenFlow rules
   * @param rules a list of rules to delete
   * @param outSwitches a list of switches in which to delete the rules
   */
  public void deleteRules(LinkedList<Rule> rules, int... outSwitches);
}
