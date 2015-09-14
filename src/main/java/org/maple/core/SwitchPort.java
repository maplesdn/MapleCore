package org.maple.core;

/**
 * Created by shigangzhu on 9/12/15.
 */
public class SwitchPort implements Comparable<SwitchPort> {
    
    private long SwitchID;
    private int portID;

    public SwitchPort(long sw, int portID) {
        this.SwitchID = sw;
        this.portID = portID;
    }
    public int compareTo(SwitchPort other) {
        return this.hashCode() - other.hashCode();
    }

    public int hashCode() {
        final int prime = 5557;
        int result = 1;
        result = prime * result + (int) this.SwitchID;
        result = prime * result + this.portID;
        return result;
    }

    public boolean equals(SwitchPort other) {
        return this.SwitchID == other.getSwitch() && this.portID == other.getPort();
    }

    public int getPort() {
        return this.portID;
    }

    public long getSwitch() {
        return this.SwitchID;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SwitchPort: ");
        sb.append(this.portID);
        sb.append("; on Switch: ");
        sb.append(this.SwitchID);
        return sb.toString();
    }

}
