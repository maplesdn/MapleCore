package org.maple.core;
import java.util.Collection;
import java.util.HashSet;
/**
 * Created by shigangzhu on 9/11/15.
 */
public class Switch implements Comparable<Switch>{


    private long SwitchID;
    private HashSet<SwitchPort> SwitchPorts = new HashSet<SwitchPort>();



    public Switch() {
        this.SwitchID=0;
        this.SwitchPorts=null;
    }

    public Switch(long SwitchID) {
        this.SwitchID=SwitchID;
    }

    public Switch(long SwitchID, HashSet<SwitchPort> SwitchPorts) {
        this.SwitchID=SwitchID;
        this.SwitchPorts = SwitchPorts;
    }

    public int compareTo(Switch other) {
        return this.hashCode() - other.hashCode();
    }

    public boolean equals(Switch other) {
        return this.SwitchID==other.getSwitch();
    }

    public long getSwitch() {
        return this.SwitchID;
    }

    public void addPorts(SwitchPort port) {
        this.SwitchPorts.add(port);
    }

    public void addPorts(Collection<SwitchPort> ports) {
        this.SwitchPorts.addAll(ports);
    }

    public Collection<SwitchPort> getPorts() {
        return this.SwitchPorts;
    }

    public int hashCode() {
        final int prime = 5557;
        int result = 1;
        result = prime * result + (int) SwitchID;
        return result;

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Switch: ");
        sb.append(this.SwitchID);
        sb.append(";");
        sb.append("Ports: ");
        sb.append(this.SwitchPorts);
        return sb.toString();

    }
}
