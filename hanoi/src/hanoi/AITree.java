package hanoi;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple tree. Not binary.
 */
public class AITree<T> {

    private Node<T> root; //The root of the tree.

    /**
     * Constructor.
     * @param rootData the data that will go into the root of the tree.
     */
    public AITree(T rootData) {
        root = new Node<T>();
        root.data = rootData;
        root.children = new ArrayList<Node<T>>();
    }

    /**
     * The Node class.
     * @param <T>
     */
    public static class Node<T> {
        private T data; //Data of the node.
        private Node<T> parent; //The node's parent.
        private List<Node<T>> children; //A list of the node's children nodes.

        /**
         * Adds a child to the Node given data for the child.
         * @param childData the data the child will have.
         * @return the child's Node
         */
        public Node<T> addChild(T childData){
            Node<T> child = new Node<T>();
            child.data = childData;
            child.parent = this;
            child.children = new ArrayList<Node<T>>();
            this.children.add(child);
            return child;
        }

        /**
         * Getter for data.
         * @return data
         */
        public T getData() {
            return data;
        }

        /**
         * Getter for parent.
         * @return parent
         */
        public Node<T> getParent() {
            return parent;
        }

        /**
         * Getter for children.
         * @return children
         */
        public List<Node<T>> getChildren() {
            return children;
        }
    }

    /**
     * Getter for root.
     * @return root
     */
    public Node<T> getRoot() {
        return root;
    }
}