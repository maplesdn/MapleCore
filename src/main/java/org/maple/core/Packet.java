/**
 * Created by zhushigang on 2/28/15.
 */

package org.maple.core;

import java.util.LinkedList;

public class Packet {

  Ethernet frame;
  SwitchPort ingressPort;
  LinkedList<TraceItem> trace;
  
  public Packet(Ethernet frame, SwitchPort ingressPort) {
    this.frame = frame;
    this.ingressPort = ingressPort;
    this.trace = new LinkedList<TraceItem>();
  }

  public final long ethSrc() {
    long addr = Ethernet.toLong(frame.getSourceMACAddress());
    trace.add(TraceItemV.ethSrc(addr));
    return addr;
  }

  public final long ethDst() {
    long addr = Ethernet.toLong(frame.getDestinationMACAddress());
    trace.add(TraceItemV.ethDst(addr));
    return addr;
  }

  public final int ethType() {
    trace.add(TraceItemV.ethType(frame.getEtherType()));
    return frame.getEtherType();
  }

  public final SwitchPort ingressPort() {
    trace.add(TraceItemV.inPort(ingressPort));
    return ingressPort;
  }
  
  public final boolean ethSrcIs(long exp) {
    long addr = Ethernet.toLong(frame.getSourceMACAddress());
    trace.add(TraceItemT.ethSrcIs(addr, exp));
    return (addr==exp);
  }

  public final boolean ethDstIs(long exp) {
    long addr = Ethernet.toLong(frame.getDestinationMACAddress());
    trace.add(TraceItemT.ethDstIs(addr, exp));
    return (addr==exp);
  }

  public final boolean ethTypeIs(int exp) {
    trace.add(TraceItemT.ethTypeIs(frame.getEtherType(), exp));
    return (frame.getEtherType() == exp);
  }

  public final boolean ingressPortIs(SwitchPort exp) {
    trace.add(TraceItemT.inPortIs(ingressPort, exp));
    return (ingressPort.equals(exp));
  }

  @Override
  public String toString() {
    return "Packet [ingressPort=" + ingressPort + ", frame=" + frame + "]";
  }

}
