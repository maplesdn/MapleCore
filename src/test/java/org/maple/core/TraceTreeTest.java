package org.maple.core;

/* to execute the test codes, copy junit.jar and hamcrest.jar
 * to IntelliJ repository/lib repo
 */

import org.junit.Test;

import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.HashSet;

public class TraceTreeTest {

  int[] data = {0,0,0,0,0,2,0,0,0,0,0,4,8,0,69,0,0,84,73,114,0,0,64,1,
                29,50,10,0,0,4,10,0,0,2,0,0,241,88,120,214,0,1,250,39,16,
                85,0,0,0,0,192,127,12,0,0,0,0,0,16,17,18,19,20,21,22,23,
                24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,
                43,44,45,46,47,48,49,50,51,52,53,54,55};
  int PORT = 10;

  @Test
  public void testAugment1() {

    TraceTree tree = new TraceTree();

    assertTrue(tree.root == null);
    
    LinkedList<TraceItem> emptyTrace = new LinkedList<TraceItem>();
    int[] outcome = {1,2,3};
    tree.augment(emptyTrace, outcome);

    assertTrue(tree.root != null);
    assertTrue(tree.root instanceof L);
    assertEquals(outcome, ((L) tree.root).outcome);

    byte[] frameBytes = makeFrameBytes(data);
    Ethernet frame = new Ethernet();
    frame.deserialize(frameBytes, 0, frameBytes.length);
    assertNotNull(tree.evaluate(PORT, frame));
    assertEquals(outcome, tree.evaluate(PORT, frame));
  }

  
  @Test
  public void testAugment2() {

    TraceTree tree = new TraceTree();

    assertTrue(tree.root == null);

    
    LinkedList<TraceItem> trace = new LinkedList<TraceItem>();
    trace.add(TraceItem.inPortItem(PORT));
    int[] outcome = {1,2,3};
    tree.augment(trace, outcome);

    assertTrue(tree.root != null);
    assertTrue(tree.root instanceof V);

    V node = (V) tree.root;

    assertTrue(null == node.getChild(PORT+1));
    assertTrue(null != node.getChild(PORT));
    assertTrue(node.getChild(PORT) instanceof L);
    L leaf = (L) node.getChild(PORT);
    assertEquals(outcome, leaf.outcome);

    byte[] frameBytes = makeFrameBytes(data);
    Ethernet frame = new Ethernet();
    frame.deserialize(frameBytes, 0, frameBytes.length);
    assertNull(tree.evaluate(PORT + 1, frame));    
    assertNotNull(tree.evaluate(PORT, frame));
    assertEquals(outcome, tree.evaluate(PORT, frame));
    
  }

  @Test
  public void testItemEq() {
    assertEquals(TraceItem.ethSrcItem(1),  TraceItem.ethSrcItem(1));
    assertNotEquals(TraceItem.ethSrcItem(1),  TraceItem.ethSrcItem(2));    
  }

  @Test
  public void testCompile1() {

    TraceTree tree;
    LinkedList<Rule> rulesExpected;
    LinkedList<Action> actions;
    LinkedList<TraceItem> trace;

    tree = new TraceTree();
    rulesExpected = new LinkedList<Rule>();
    rulesExpected.add(new Rule(0, Match.matchAny(), Rule.punt())); 
    assertNotNull(tree.compile());
    assertEquals(rulesExpected, tree.compile());

    rulesExpected = new LinkedList<Rule>();
    actions = new LinkedList<Action>();
    actions.add(Action.ToPort(1));
    actions.add(Action.ToPort(2));
    actions.add(Action.ToPort(3));    
    rulesExpected.add(new Rule(0, Match.matchAny(), actions));
    LinkedList<TraceItem> emptyTrace = new LinkedList<TraceItem>();
    int[] outcome = {1,2,3};
    tree.augment(emptyTrace, outcome);
    assertEquals(rulesExpected, tree.compile());

    tree = new TraceTree();
    trace = new LinkedList<TraceItem>();
    trace.add(TraceItem.inPortItem(PORT));
    tree.augment(trace, outcome);
    rulesExpected = new LinkedList<Rule>();
    rulesExpected.add(new Rule(0,
                               Match.matchAny().add(TraceItem.inPortItem(PORT)),
                               actions));
    assertEquals(rulesExpected, tree.compile());
  }
  
  // Java does not support byte literals; therefore, we need to convert
  // an array of ints to array of bytes.
  byte[] makeFrameBytes(int[] data) {
    byte[] frame = new byte[data.length];
    for (int i = 0; i < frame.length; i++) {
      frame[i] = (byte) data[i];
    }
    return frame;
  }
}
