/**
 * Created by zhushigang on 2/21/15.
 */

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
   * @param data  the actual packet being send out
   * @param inSwitch the switch where the system received this packet
   * @param inPort the port number on the ingress switch
   * @param ports a list of egress ports to send the packet to
   */
  public void sendPacket(byte[] data, int inSwitch, int inPort, int... ports);

  /**
   * installRules will be called when the Maple system compiles/issues openflow Rules
   * @param rules
   * @param outSwitches
   */
  public void installRules(LinkedList<Rule> rules, int... outSwitches);
}
