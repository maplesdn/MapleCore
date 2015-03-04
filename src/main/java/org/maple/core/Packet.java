/**
 * Created by zhushigang on 2/28/15.
 */

package org.maple.core;

public class Packet {

  Ethernet frame;
  int ingressPort;

  public Packet(Ethernet frame, int ingressPort) {
    this.frame = frame;
    this.ingressPort = ingressPort;
  }

  public final long ethSrc() {
    return Ethernet.toLong(frame.getSourceMACAddress());
  }

  public final long ethDst() {
    return Ethernet.toLong(frame.getDestinationMACAddress());
  }

  public final int ingressPort() { 
    return ingressPort;
  }

  @Override
  public String toString() {
    return "Packet [ingressPort=" + ingressPort + ", frame=" + frame + "]";
  }

}
