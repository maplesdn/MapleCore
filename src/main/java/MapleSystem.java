/**
 * Created by zhushigang on 2/21/15.
 */
package org.maple.core;
public class MapleSystem{
    Controller C;
  MapleFunction userFunction;

    public void init(Controller c){
        this.C = c;

        // TODO: pass userFunction as argument into init, rather than
        // hard-coding to be SampleMapleFunction.
        this.userFunction = new SampleMapleFunction(); 
    }

    public void handlePacket(byte[] data){
        System.out.println("Maple received the packet. Thanks!");

        int out = userFunction.onPacket(data.length);
        System.out.println("User's MapleFunction returned: " + out);

        if (C != null)
            C.sendPacket(data);
    }
}
