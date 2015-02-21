package com.winter.see.algorithms.breadthfirstsearch;

import com.winter.see.algorithms.breadthfirstsearch.BreadthFirstSearch.Node;

import java.util.Stack;

/**
 * Created by winter on 2/20/15.
 */
public class Path {
    private Node last;

    public Path(Node n) {
        this.last = n;
    }

    public Node[] listPath() {
        Stack<Node> stack = new Stack<Node>();
        Node curr = last;
        while (curr.parent != null) {
            stack.push(curr);
            curr = curr.parent;
        }
        stack.push(curr);
        return stack.toArray(new Node[stack.size()]);
    }
}
