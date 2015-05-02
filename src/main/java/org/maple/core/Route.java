package org.maple.core;

import java.util.LinkedList;
import java.util.Collection;

public class Route {
  LinkedList<Integer> ports;

  Route() {
    this.ports = new LinkedList<Integer>();
  }
  Route(LinkedList<Integer> ports) {
    this.ports = ports;
  }
  
  public static Route drop() {
    return new Route();
  }
  public static Route toPorts(Collection<Integer> ports) {
    return new Route(new LinkedList<Integer>(ports));
  }
  public static Route toPorts(int... ports) {
    LinkedList<Integer> output = new LinkedList<Integer>();
    for (int i = 0; i < ports.length; i++) {
      output.add(ports[i]);
    }
    return new Route(output);
  }

  
  // TODO: This is quite inefficient; it would be better to use
  // StringBuilder here.
  @Override
  public String toString() {

    String start = "Route: [";
    String end = "]";
    String middle = "";

    for (int i = 0; i < ports.size(); i++) {
      middle += ports.get(i);
      if (i < ports.size() - 1) { middle += ", "; }
    }
    
    return start + middle + end;
  }

  @Override
  public boolean equals(Object other) {
    if (null == other) { return false; }
    Route other2 = (Route) other;
    return ports.equals(other2.ports);
  }

  @Override
  public int hashCode() {
    final int prime = 5557;
    int result = 1;
    for (int p : ports) {
      result = prime * result + p;
    }
    return result;
  }
}
