/**
 * Created by zhushigang on 2/21/15.
 */

package org.maple.core;

import java.util.LinkedList;

public interface Controller {
  public void sendPacket(byte[] data, int inSwitch, int inPort, int... ports);
  public void installRules(LinkedList<Rule> rules, int... outSwitches);
}
