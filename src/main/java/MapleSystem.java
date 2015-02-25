/**
 * Created by zhushigang on 2/21/15.
 */
package org.maple.core;
public class MapleSystem{
    Controller C;
    void init(Controller c){
        this.C = c;

    }

    void handlePacket(byte[] data){
        System.out.println("Maple received the packet. Thanks!");
        if (C != null)
            C.sendPacket(data);
    }
}
