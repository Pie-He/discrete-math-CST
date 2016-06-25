import parse.Parser;
import parse.Result;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by admin on 2016/6/24.
 */
public class Tree {
    List<Node> list = new LinkedList<Node>();
    private int index = 0;
    private final static Parser PARSER = new Parser();

    public Tree() {

    }

    public Tree(Node root) {
        list.add(root);
    }

    public Tree(String str) {
        list.add(new Node(str, false));
    }

    public void addNode(Node node) {
        list.add(node);
    }


    public static void constructTree(List<String> strs) {
        String rootStr = strs.get(0);
        //int preCount = Integer.parseInt(strs.get(1));
        Node root = new Node(rootStr, false);
        Tree tree = new Tree();
        Queue<Node> reduceQueue = new LinkedList<Node>();
        Queue<Node> premises = new LinkedList<Node>();
        for (int i = 2; i < strs.size(); i++) {
            premises.add(new Node(strs.get(i), true));
        }
        reduceQueue.add(root);
        Node current = null;
        while ((current = reduceQueue.poll()) != null) {
            tree.addNode(current);
            tree.reduceNode(current, reduceQueue);
        }
        tree.list.stream().forEach(i -> System.out.println(i.toText()));
    }

    public void reduceNode(Node node, Queue<Node> reduceQueue) {
        node.reduced();
        Result rs = PARSER.split(node.entry);

        //将自己复制一遍
        Node head = new Node(node.entry, node.truth);

        switch (rs.operator) {
            case "\\not": {
                Node child = new Node(head.entry, !head.truth);
                head.setLeft(child);
                break;
            }
            case "\\and": {
                Node child1 = new Node(rs.operand1, head.truth);
                Node child2 = new Node(rs.operand2, head.truth);
                head.setLeft(child1);
                if (head.truth)
                    child1.setLeft(child2);
                else
                    head.setRight(child2);
                break;
            }
            case "\\or": {
                Node child1 = new Node(rs.operand1, head.truth);
                Node child2 = new Node(rs.operand2, head.truth);
                head.setLeft(child1);
                if (head.truth)
                    head.setRight(child2);
                else
                    child1.setLeft(child2);
                break;
            }
            case "\\imply": {
                Node child1 = new Node(rs.operand1, !head.truth);
                Node child2 = new Node(rs.operand2, head.truth);
                head.setLeft(child1);
                if (head.truth)
                    head.setRight(child2);
                else
                    child1.setLeft(child2);
                break;
            }
            case "\\eq": {
                Node child1 = new Node(rs.operand1, !head.truth);
                Node child2 = new Node(rs.operand1, head.truth);
                Node child3 = new Node(rs.operand2, head.truth);
                Node child4 = new Node(rs.operand2, !head.truth);
                child1.setLeft(child3);
                child2.setLeft(child4);
                head.setLeft(child1);
                head.setRight(child2);
                break;
            }
        }
        addAtomic(node, head, reduceQueue);
    }

    private void addAtomic(Node node, Node head, Queue<Node> reduceQueue) {
        if (node.getLeft() == null) {
            Node n = head.clone();
            reduceQueue.add(n);
            node.setLeft(n);
            return;
        }

        //递归向左右儿子添加head的拷贝
        if (node.getLeft() != null)
            addAtomic(node.getLeft(), head, reduceQueue);

        if (node.getRight() != null)
            addAtomic(node.getRight(), head, reduceQueue);

        return;
    }
}

class Node {
    private Node parent;
    private Node left;
    private Node right;
    boolean reduced = false;//是否规约
    String entry;
    boolean truth;//真值
    boolean contradictory = false;

    Node(String message, boolean truth) {
        this.entry = message;
        this.truth = truth;
        if (Tool.isLetter(message))
            reduced = true;
    }

    Node(String message, boolean reduced, boolean truth) {
        this(message, truth);
        this.reduced = reduced;
    }

    public String toString() {
        return entry;
    }

    public boolean setParent(Node parent) {
        this.parent = parent;
        Node node = parent;
        while (node != null) {
            if (node.entry.equals(this.entry) && node.truth != this.truth) {
                this.setContradictory(true);
                return false;
            }
            node = node.parent;
        }
        return true;
    }

    public boolean isTruth() {
        return truth;
    }

    public void setTruth(boolean truth) {
        this.truth = truth;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
        left.setParent(this);
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
        right.setParent(this);
    }

    public boolean isContradictory() {
        return contradictory;
    }

    public void setContradictory(boolean contradictory) {
        this.contradictory = contradictory;
    }


    public boolean isReduced() {
        return reduced;
    }

    public void reduced() {
        this.reduced = true;
    }

    @Override
    protected Node clone() {
        Node node = new Node(this.entry, reduced, truth);
        node.setContradictory(this.contradictory);
        if (this.left != null)
            node.setLeft(this.left.clone());
        if (this.right != null)
            node.setRight(this.right.clone());
        return node;
    }

    public String toText() {
        return this.truth ? "T" : "F" + " " + this.entry;
    }

}
