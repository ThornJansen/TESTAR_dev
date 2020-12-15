package nl.ou.testar.ReinforcementLearning.RewardFunctions;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fruit.alayer.Roles;
import org.fruit.alayer.Tags;
import org.fruit.alayer.Widget;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class TreeDistHelper {

    private static final Logger logger = LogManager.getLogger(TreeDistHelper.class);

    private final static int DELETE = 1;
    private final static int INSERT = 1;
    private final static int RELABLE = 1;

    void treeDist(final Widget keyRoot1, final Widget keyRoot2, final MultiKeyMap forestDist, final MultiKeyMap treeDist) {
        forestDist.put(null, null, 0);

        final Deque<Widget> keyRootPathTree1 = getLeftMostArray(keyRoot1);
        final Deque<Widget> keyRootPathTree2 = getLeftMostArray(keyRoot2);

        for (final Widget node : keyRootPathTree1) {
            forestDist.put(node, null, getForestDist(forestDist, node, null) + DELETE);
        }

        for (final Widget node : keyRootPathTree2) {
            forestDist.put(null, node, getForestDist(forestDist, null, node) + INSERT);
        }

        for (final Widget nodeTree1 : keyRootPathTree1) {
            for (final Widget nodeTree2: keyRootPathTree2) {
            	
                System.out.println("**** Node Tree1 Widget Title : " + nodeTree1.get(Tags.Title, "NOtitleOne"));
                System.out.println("**** Node Tree1 Widget Role : " + nodeTree1.get(Tags.Role, Roles.Widget));
                System.out.println("**** Node Tree2 Widget Title : " + nodeTree2.get(Tags.Title, "NOtitleTwo"));
                System.out.println("**** Node Tree2 Widget Role : " + nodeTree2.get(Tags.Role, Roles.Widget));
                
                System.out.println("--------------------------------------------------------------------------------------------------");
                System.out.println("getLeftMostArray(nodeTree1).getFirst() TITLE: " + getLeftMostArray(nodeTree1).getFirst().get(Tags.Title, "NO TITLE"));
                System.out.println("getLeftMostArray(nodeTree1).getFirst() ROLE: " + getLeftMostArray(nodeTree1).getFirst().get(Tags.Role, Roles.Widget));
                System.out.println("getLeftMostArray(nodeTree1).getFirst() AbstractIDCustom: " + getLeftMostArray(nodeTree1).getFirst().get(Tags.AbstractIDCustom, "NO AbstractIDCustom"));
                System.out.println("keyRootPathTree1.getFirst() TITLE: " + keyRootPathTree1.getFirst().get(Tags.Title, "NO TITLE"));
                System.out.println("keyRootPathTree1.getFirst() ROLE: " + keyRootPathTree1.getFirst().get(Tags.Role, Roles.Widget));
                System.out.println("keyRootPathTree1.getFirst() AbstractIDCustom: " + keyRootPathTree1.getFirst().get(Tags.AbstractIDCustom, "NO AbstractIDCustom"));
                
                System.out.println("--------------------------------------------------------------------------------------------------");
                
                System.out.println("getLeftMostArray(nodeTree2).getFirst() TITLE: " + getLeftMostArray(nodeTree2).getFirst().get(Tags.Title, "NO TITLE"));
                System.out.println("getLeftMostArray(nodeTree2).getFirst() ROLE: " + getLeftMostArray(nodeTree2).getFirst().get(Tags.Role, Roles.Widget));
                System.out.println("getLeftMostArray(nodeTree2).getFirst() AbstractIDCustom: " + getLeftMostArray(nodeTree2).getFirst().get(Tags.AbstractIDCustom, "NO AbstractIDCustom"));	
                System.out.println("keyRootPathTree2.getFirst() TITLE: " + keyRootPathTree2.getFirst().get(Tags.Title, "NO TITLE"));
                System.out.println("keyRootPathTree2.getFirst() ROLE: " + keyRootPathTree2.getFirst().get(Tags.Role, Roles.Widget));
                System.out.println("keyRootPathTree2.getFirst() AbstractIDCustom: " + keyRootPathTree2.getFirst().get(Tags.AbstractIDCustom, "NO AbstractIDCustom"));
                
                
                /*if (getLeftMostArray(nodeTree1).getFirst().equals(keyRootPathTree1.getFirst())
                        && getLeftMostArray(nodeTree2).getFirst().equals(keyRootPathTree2.getFirst())) {*/
            	
                
                if (getLeftMostArray(nodeTree1).getFirst().get(Tags.AbstractIDCustom, "one").equals(keyRootPathTree1.getFirst().get(Tags.AbstractIDCustom,"two"))
                        && getLeftMostArray(nodeTree2).getFirst().get(Tags.AbstractIDCustom, "one").equals(keyRootPathTree2.getFirst().get(Tags.AbstractIDCustom,"two"))) {

                	
                    System.out.println(" INSIDEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE :D");
                	
                	
                    final Widget earlierNode1 = getEarlierNode(nodeTree1, keyRootPathTree1);
                    final Widget earlierNode2 = getEarlierNode(nodeTree2, keyRootPathTree1);

                    final int i = getForestDist(forestDist, nodeTree1, nodeTree2) + DELETE;
                    final int j = getForestDist(forestDist, nodeTree1, nodeTree2) + INSERT;
                    final boolean nodesAreEqual = areNodesEqual(earlierNode1, earlierNode2);
                    final int k = getForestDist(forestDist, earlierNode1, earlierNode2) + (nodesAreEqual ? 0 : RELABLE);

                    final int min = NumberUtils.min(i, j, k);
                    forestDist.put(nodeTree1, nodeTree2, min);
                    treeDist.put(nodeTree1, nodeTree2, min);
                } else {
                    final Widget earlierNode1 = getEarlierNode(nodeTree1, keyRootPathTree1);
                    final Widget earlierNode2 = getEarlierNode(nodeTree2, keyRootPathTree1);

                    final int i = getForestDist(forestDist, nodeTree1, nodeTree2) + DELETE;
                    final int j = getForestDist(forestDist, nodeTree1, nodeTree2) + INSERT;
                    final int k = getForestDist(forestDist, earlierNode1, earlierNode2) + getTreeDist(treeDist,nodeTree1, nodeTree2);

                    forestDist.put(nodeTree1, nodeTree2, NumberUtils.min(i,j,k));
                }
            }
        }

    }

    private boolean areNodesEqual(final Widget node1, final Widget node2) {
        if (node1 == null || node2 == null) {
            return false;
        }
        
        //logger.debug("Comparing nodes: '{}' and '{}'", node1.toString(), node2.toString());
        
        logger.debug("Comparing AbstractIDCustom properties of nodes: '{}' and '{}'", node1.toString(), node2.toString());
        
        return StringUtils.equals(node1.getAbstractRepresentation(), node2.getAbstractRepresentation());
    }

    private Widget getEarlierNode(final Widget node, final Deque<Widget> deque) {
        final List<Widget> dequeList = new ArrayList<>(deque);
        final int position = dequeList.indexOf(node); // TODO: test if this works
        
        
        System.out.println("getEarlierNode -> " + node.get(Tags.Role, Roles.Widget));
        System.out.println("getEarlierNode left position -> " + String.valueOf(position - 1));

        if (position - 1 < 0) {
            return null;
        }

        return dequeList.get(position - 1);
    }

    /** Creates a collection of the path from the most left item to the root element
     * The first element is the most left
     */
    private Deque<Widget> getLeftMostArray(final Widget widget) {
        final Deque<Widget> result = new ArrayDeque<>();
        result.add(widget);

        addLeftNode(widget, result);

        return result;
    }

    private void addLeftNode(final Widget widget, final Deque<Widget> result) {
        final List<Widget> sortedChildNodes = LRKeyrootsHelper.getSortedChildList(widget);
        if (sortedChildNodes.isEmpty()) {
            return;
        }

        result.add(sortedChildNodes.get(0));
        // call recursively
        addLeftNode(sortedChildNodes.get(0), result);
    }

    private Integer getForestDist(final MultiKeyMap forestDist, final Widget node1, final Widget node2) {
        if (node1 == null && node2 == null) {
            return 0;
        }

        if (!forestDist.containsKey(node1, node2)) {
            return 0;
        }

        return (Integer) forestDist.get(node1, node2);
    }

    private Integer getTreeDist(final MultiKeyMap treeDist, final Widget node1, final Widget node2) {
        if (node1 == null && node2 == null) {
            return 0;
        }

        if (!treeDist.containsKey(node1, node2)) {
            return 0;
        }

        return (Integer) treeDist.get(node1, node2);
    }
}
