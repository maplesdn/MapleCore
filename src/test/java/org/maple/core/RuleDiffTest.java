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

        //Rule rule3 = new Rule(1,Match.matchAny(),Action.Punt());
        //assertTrue(!rule1.equals(rule3));

        HashSet<TraceItem> fieldValues = new HashSet<TraceItem>();
        fieldValues.add(TraceItemV.inPort(new SwitchPort((long) 0, 10)));
        Match match1 = new Match(fieldValues);

        Rule rule4 = new Rule(1,match1,Action.Drop());
        assertTrue(!rule1.equals(rule4));
    }

  @Test
  public void TestDiffEquals() {
    Diff diff1 = new Diff(new HashSet<Rule>(), new HashSet<Rule>());
    Diff diff2 = new Diff(new HashSet<Rule>(), new HashSet<Rule>());
    assertTrue(diff1 != null);
    assertTrue(diff2 != null);
    assertEquals(diff1, diff2);
  }
  
    public void TestDiff1() {
        HashSet<Rule> oldRules = new HashSet<Rule>();

        Rule rule1 = new Rule(1,Match.matchAny(),Action.Drop());
        oldRules.add(rule1);
        Rule rule2 = new Rule(1,Match.matchAny(),Action.ToPorts(new SwitchPort((long) 0, 1)));
        oldRules.add(rule2);

        HashSet<Rule> newRules = new HashSet<Rule>();
        Rule rule3 = new Rule(1,Match.matchAny(),Action.Punt());
        newRules.add(rule3);
        HashSet<TraceItem> fieldValues = new HashSet<TraceItem>();
        fieldValues.add(TraceItemV.inPort(new SwitchPort((long) 0, 10)));
        Match match1 = new Match(fieldValues);
        Rule rule4 = new Rule(1,match1,Action.Drop());
        newRules.add(rule4);

        Diff expectedDiff = new Diff(oldRules,newRules);

        Controller c = new Controller() {
            public void sendPacket(byte[] data, Switch inSwitch, SwitchPort inPort, SwitchPort... ports) {
                System.out.println("In sendPacket");
            }
            public void installRules(HashSet<Rule> rules, Switch... outSwitches) {
                System.out.println("In installRules");
            }
            public void deleteRules(HashSet<Rule> rules, Switch... outSwitches) {
                System.out.println("In deleteRules");
            }
        };

        MapleSystem mapleSystem = new MapleSystem(c);

        Diff diff = mapleSystem.diff(oldRules,newRules);

        assertTrue(expectedDiff.added.equals(diff.added));
        assertTrue(expectedDiff.removed.equals(diff.removed));
    }
}
