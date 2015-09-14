package org.maple.core;

/* to execute the test codes, copy junit.jar and hamcrest.jar
 * to IntelliJ repository/lib repo
 */

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class MapleTestTest {

  // Java does not support byte literals; therefore, we need to convert
  // an array of ints to array of bytes.
  byte[] makeFrameBytes(int[] data) {
    byte[] frame = new byte[data.length];
    for (int i = 0; i < frame.length; i++) {
      frame[i] = (byte) data[i];
    }
    return frame;
  }
  
    @Test
    public void test_handlePacket() {
        System.out.println("Test if handlePacket method can successfully send packet");

        Controller c = new Controller() {
            public void sendPacket(byte[] data, Switch inSwitch, SwitchPort inPort, SwitchPort... ports) {
              System.out.println("In sendPacket");
            }
            public void installRules(HashSet<Rule> rules, Switch... outSwitches) {
                System.out.println("In installRules");
            }
            public void deleteRules(HashSet<Rule> rules, Switch... outSwitches) {
                System.out.println("In deleteRules");
            }
          };

        MapleSystem mapleSystem = new MapleSystem(c);

        // Sample Ethernet frame, taken from some ping traffic.
        int[] data = {0,0,0,0,0,2,0,0,0,0,0,4,8,0,69,0,0,84,73,114,0,0,64,1,
                      29,50,10,0,0,4,10,0,0,2,0,0,241,88,120,214,0,1,250,39,16,
                      85,0,0,0,0,192,127,12,0,0,0,0,0,16,17,18,19,20,21,22,23,
                      24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,
                      43,44,45,46,47,48,49,50,51,52,53,54,55};
        byte[] frameBytes = makeFrameBytes(data);

        int ingressPortID = 1;
        long switchID = 42;
        SwitchPort ingressPort = new SwitchPort(switchID,1);
        Switch sw = new Switch(switchID);
        SwitchPort sp23 = new SwitchPort(switchID ,23);
        SwitchPort sp24 = new SwitchPort(switchID, 24);
        SwitchPort sp25 = new SwitchPort(switchID, 25);
        mapleSystem.portUp(sp23);
        mapleSystem.portUp(sp24);
        mapleSystem.portUp(sp25);
        mapleSystem.handlePacket(frameBytes, sw, ingressPort);
        
        
        // Sample ARP packet. Who has 192.168.1.254?  Tell 192.168.1.103
        int[] data2 = {0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0x8c, 0x70, 0x5a, 0x35, 0x6d, 0x1c, 0x08, 0x06, 0x00, 0x01,
        		      0x08, 0x00, 0x06, 0x04, 0x00, 0x01, 0x8c, 0x70, 0x5a, 0x35, 0x6d, 0x1c, 0xc0, 0xa8, 0x01, 0x67,
        		      0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xc0, 0xa8, 0x01, 0xfe};
        byte[] frameBytes2 = makeFrameBytes(data2);

        ingressPort = new SwitchPort(switchID,10);
        switchID = 40;
        Switch sw40 = new Switch(switchID);


       mapleSystem.handlePacket(frameBytes2, sw40, ingressPort);
        // assertTrue(c.sendPacket(data, out));
    }




}
