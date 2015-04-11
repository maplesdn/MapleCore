package org.maple.core;

/* to execute the test codes, copy junit.jar and hamcrest.jar
 * to IntelliJ repository/lib repo
 */

import org.junit.Test;

import static org.junit.Assert.*;
import java.util.LinkedList;

public class TraceTreeTest {

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
    }

  
    @Test
    public void testAugment2() {

      TraceTree tree = new TraceTree();

      assertTrue(tree.root == null);

      LinkedList<TraceItem> trace = new LinkedList<TraceItem>();
      trace.add(TraceItem.ethSrcItem(1234L));
      int[] outcome = {1,2,3};
      tree.augment(trace, outcome);

      assertTrue(tree.root != null);
      assertTrue(tree.root instanceof V);

      V node = (V) tree.root;

      assertTrue(null == node.getChild(9000L));
      assertTrue(null != node.getChild(1234L));

      assertTrue(node.getChild(1234L) instanceof L);
      L leaf = (L) node.getChild(1234L);
      assertEquals(outcome, leaf.outcome);

    }

}
