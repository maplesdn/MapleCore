package org.maple.core;

/* to execute the test codes, copy junit.jar and hamcrest.jar
 * to IntelliJ repository/lib repo
 */

import org.junit.Test;

import static org.junit.Assert.*;

public class MapleTestTest {

    @Test
    public void test_handlePacket() {
        System.out.println("Test if handlePacket method can successfully send packet");

        Controller c = null;

        MapleSystem mapleSystem = new MapleSystem(c);
        byte[] data = {2,3};
        int ingressPort = 23;

        mapleSystem.handlePacket(data, ingressPort);


        // assertTrue(c.sendPacket(data, out));
    }




}
