/**
 * Created by zhushigang on 2/21/15.
 */

package org.maple.core;

public class MapleSystem {
  Controller C;
  MapleFunction userFunction;

  public void init(Controller c) {
    this.C = c;

<<<<<<< HEAD
        // TODO: pass userFunction as argument into init, rather than
        // hard-coding to be SampleMapleFunction.
        this.userFunction = new SampleMapleFunction();
        this.userFunction = new LearningSwitch();
    }

    public void handlePacket(byte[] data){
        //TODO: convert data in to Packet data type
        System.out.println("Maple received the packet. Thanks!"+data.length);
        Packet p = null;
        int out = userFunction.onPacket(p,data.length,data.length);
        System.out.println("User's MapleFunction returned: " + out);
=======
    // TODO: pass userFunction as argument into init, rather than
    // hard-coding to be SampleMapleFunction.
    this.userFunction = new SampleMapleFunction(); 
  }

  public void handlePacket(byte[] data) {
    System.out.println("Maple received the packet. Thanks!!!!!!!");

    int out = userFunction.onPacket(data.length);
    System.out.println("User's MapleFunction returned: " + out);
>>>>>>> b99bbbd28d07f5a661e7634afcf62d4a5c707b73

    if (C != null)
      C.sendPacket(data);
  }
}
