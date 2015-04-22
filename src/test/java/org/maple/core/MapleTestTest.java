package org.maple.core;

/* to execute the test codes, copy junit.jar and hamcrest.jar
 * to IntelliJ repository/lib repo
 */

import org.junit.Test;

import static org.junit.Assert.*;

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
            public void sendPacket(byte[] data, int inSwitch, int inPort, int... ports) {
              System.out.println("In sendPacket");
            }
            public void installRules(LinkedList<Rule> rules, int... outSwitches) {
                System.out.println("In installRules");
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

        int ingressPort = 23;
        int switchID = 42;

        mapleSystem.handlePacket(frameBytes, switchID, ingressPort);


        // assertTrue(c.sendPacket(data, out));
    }




}
