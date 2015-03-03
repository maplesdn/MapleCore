/**
 * Created by zhushigang on 2/21/15.
 */

  package org.maple.core;

public class MapleSystem {

  private Controller C;
  private MapleFunction userFunction;

  public void init(Controller c) {
    this.C = c;

    // TODO: pass userFunction as an argument into init,
    // rather than hard-coding it.
    // this.userFunction = new SampleMapleFunction();
    this.userFunction = new LearningSwitch();
  }

  public void handlePacket(byte[] data) {
    System.out.println("Maple received the packet. Data len: " + data.length);

    // TODO: convert data in to Packet data type.
    Packet p = null;
    int out = userFunction.onPacket(p, data.length, data.length);
    System.out.println("User's MapleFunction returned: " + out);

    if (C != null)
      C.sendPacket(data);
  }
}
