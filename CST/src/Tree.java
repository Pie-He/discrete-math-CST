import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 2016/6/24.
 */
public class Tree {
    List<Node> list = new LinkedList<Node>();
    private int index = 0;
    private final static Parser PARSER = new Parser();

    public Tree(Node root) {
        list.add(root);
    }

    public Tree(String str) {
        list.add(new Node(str));
    }

    public static void construct(String str) {
        Node root = new Node(str);
        Tree tree = new Tree(root);
        Result rs = PARSER.split(str);
    }
}

class Node {
    Node left;
    Node right;
    String message;

    Node(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }
}
