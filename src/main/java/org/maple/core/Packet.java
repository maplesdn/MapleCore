/**
 * Created by zhushigang on 2/28/15.
 */

package org.maple.core;

import java.util.LinkedList;

public class Packet {

  Ethernet frame;
  int ingressPort;
  LinkedList<TraceItem> trace;
  
  public Packet(Ethernet frame, int ingressPort) {
    this.frame = frame;
    this.ingressPort = ingressPort;
    this.trace = new LinkedList<TraceItem>();
  }

  public final long ethSrc() {
    long addr = Ethernet.toLong(frame.getSourceMACAddress());
    trace.add(TraceItem.ethSrcItem(addr));
    return addr;
  }

  public final long ethDst() {
    long addr = Ethernet.toLong(frame.getDestinationMACAddress());
    trace.add(TraceItem.ethDstItem(addr));
    return addr;
  }

  public final int ingressPort() {
    trace.add(TraceItem.inPortItem(ingressPort));
    return ingressPort;
  }

  @Override
  public String toString() {
    return "Packet [ingressPort=" + ingressPort + ", frame=" + frame + "]";
  }

}
