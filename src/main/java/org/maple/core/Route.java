package org.maple.core;

import java.util.LinkedList;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class Route {
  private HashSet<SwitchPort> ports;
  public static final Route nullRoute = new Route();

  Route() {
    this.ports = new HashSet<SwitchPort>();
  }
  Route(HashSet<SwitchPort> ports) {
    this.ports = ports;
  }
  
  public static Route drop() {
    return nullRoute;
  }
  public static Route toPorts(Collection<SwitchPort> ports) {
    return new Route(new HashSet<SwitchPort>(ports));
  }
  public static Route toPorts(SwitchPort... ports) {
    HashSet<SwitchPort> output = new HashSet<SwitchPort>();
    for (int i = 0; i < ports.length; i++) {
      output.add(ports[i]);
    }
    return new Route(output);
  }

  public Collection<SwitchPort> getEndPoints() {
    return this.ports;
  }


  public boolean equals(Route other) {
    if (null == other) { return false; }
    return ports.equals(other.ports);
  }

  @Override
  public int hashCode() {
    final int prime = 5557;
    int result = 1;
    Iterator<SwitchPort> e = ports.iterator();
    while (e.hasNext()) {
      result = prime * result + e.next().hashCode();
    }
    return result;
  }
}
