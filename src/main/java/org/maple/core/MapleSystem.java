/**
 * Created by zhushigang on 2/21/15.
 */

package org.maple.core;

public class MapleSystem {

  private Controller C;
  private MapleFunction userFunction;

  public MapleSystem(Controller c) {
    this.C = c;

    // TODO: pass userFunction as an argument into init,
    // rather than hard-coding it.
    // this.userFunction = new SampleMapleFunction();
    this.userFunction = new LearningSwitch();
  }

  public void handlePacket(byte[] data, int ingressPort) {
    System.out.println("Maple received a packet with ingressPort: " + 
                       ingressPort + " and frame len: " + data.length);

    Ethernet frame = new Ethernet();;
    frame.deserialize(data, 0, data.length);
    System.out.println("handlePacket.frame: " + frame);

    Packet p = new Packet(frame, ingressPort); 

    int out = userFunction.onPacket(p);
    System.out.println("User's MapleFunction returned: " + out);

    if (C != null)
      C.sendPacket(data, out);
  }
}
