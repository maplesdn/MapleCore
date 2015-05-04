package org.maple.core;

/**
 * Created by zhushigang on 2015/5/1.
 */

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.HashSet;

public class RuleDiffTest {

    @Test
    public void TestEquals1() {
        Rule rule1 = new Rule(1,Match.matchAny(),Action.Drop());
        Rule rule2 = new Rule(1,Match.matchAny(),Action.Drop());
        assertTrue(rule1.equals(rule2));

        Rule rule3 = new Rule(1,Match.matchAny(),Action.Punt());
        assertTrue(!rule1.equals(rule3));

        HashSet<TraceItem> fieldValues = new HashSet<TraceItem>();
        fieldValues.add(TraceItem.inPort(10));
        Match match1 = new Match(fieldValues);

        Rule rule4 = new Rule(1,match1,Action.Drop());
        assertTrue(!rule1.equals(rule4));
    }

  @Test
  public void TestDiffEquals() {
    Diff diff1 = new Diff(new LinkedList<Rule>(), new LinkedList<Rule>());
    Diff diff2 = new Diff(new LinkedList<Rule>(), new LinkedList<Rule>());
    assertTrue(diff1 != null);
    assertTrue(diff2 != null);    
    assertEquals(diff1, diff2);
  }
  
    public void TestDiff1() {
        LinkedList<Rule> oldRules = new LinkedList<Rule>();

        Rule rule1 = new Rule(1,Match.matchAny(),Action.Drop());
        oldRules.add(rule1);
        Rule rule2 = new Rule(1,Match.matchAny(),Action.ToPorts(1));
        oldRules.add(rule2);

        LinkedList<Rule> newRules = new LinkedList<Rule>();
        Rule rule3 = new Rule(1,Match.matchAny(),Action.Punt());
        newRules.add(rule3);
        HashSet<TraceItem> fieldValues = new HashSet<TraceItem>();
        fieldValues.add(TraceItem.inPort(10));
        Match match1 = new Match(fieldValues);
        Rule rule4 = new Rule(1,match1,Action.Drop());
        newRules.add(rule4);

        Diff expectedDiff = new Diff(oldRules,newRules);

        Controller c = new Controller() {
            public void sendPacket(byte[] data, int inSwitch, int inPort, int... ports) {
                System.out.println("In sendPacket");
            }
            public void installRules(LinkedList<Rule> rules, int... outSwitches) {
                System.out.println("In installRules");
            }
            public void deleteRules(LinkedList<Rule> rules, int... outSwitches) {
                System.out.println("In deleteRules");
            }
        };

        MapleSystem mapleSystem = new MapleSystem(c);

        Diff diff = mapleSystem.diff(oldRules,newRules);

        assertTrue(expectedDiff.added.equals(diff.added));
        assertTrue(expectedDiff.removed.equals(diff.removed));
    }
}
