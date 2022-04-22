import java.util.Random;

public class HomeworkSixth {
    public static void main(String[] args) {
        //Input
        int n = 20;

        //Output
        for (int i = 0; i < n; i++) {
            Tree<Integer> tree = new Tree<>();
            fillTree(tree);
            tree.display();
            System.out.print("\n\n");
        }
    }

    private static void fillTree(Tree<Integer> tree) {
        while (tree.getMaxLevel() < 4) {
            tree.insert(new Random().nextInt(51) - 25);
            tree.setMaxLevel(tree.getRoot(), 1);
        }
    }
}

class Node <T extends Comparable<T>> {
    private T value;
    private Node<T> leftChild;
    private Node<T> rightChild;
    int repeatCount;

    public Node(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public boolean isLeftChild(T value) {
        return value.compareTo(getValue()) > 0;
    }

    public boolean isLeaf() {
        return getLeftChild() == null && getRightChild() == null;
    }

    public boolean hasSingleChild() {
        return getRightChild() != null ^ getLeftChild() != null;
    }

    public void print() {
        print("", this, false);
    }

    public void print(String prefix, Node<T> node, boolean isLeft) {
        if (node != null) {
            System.out.println (prefix + (isLeft ? "├── " : "\\── ") + node.getValue());
            print(prefix + (isLeft ? "│   " : "    "), node.getLeftChild(), true);
            print(prefix + (isLeft ? "│   " : "    "), node.getRightChild(), false);
        }
    }
}

class Tree <T extends Comparable<T>> {
    private Node<T> root;
    private int size;
    private int maxLevel;

    public Tree() {
        this.size = 0;
        this.maxLevel = 0;
    }

    public Node<T> getRoot() { return root; }

    public int getMaxLevel() { return maxLevel; }

    public Node<T> find(T value) {
        Node<T> current = root;

        while (current.getValue() != value) {
            if (current.isLeftChild(value))
                current = current.getRightChild();
            else
                current = current.getLeftChild();
            if (current == null)
             return null;
        }
        return current;
    }

    public boolean contains(T value) {
        return find(value) != null;
    }

    public boolean insert(T value) {
        if (!isEmpty() && this.contains(value))
            return false;
        Node<T> newNode = new Node<T>(value);
        if (isEmpty()) {
            root = newNode;
            size++;
            return true;
        }
        Node<T> current = root;
        Node<T> parent;
        while (true) {
            parent = current;
            if (current.isLeftChild(value)) {
                current = current.getRightChild();
                if (current == null) {
                    parent.setRightChild(newNode);
                    size++;
                    return true;
                }
            } else {
                current = current.getLeftChild();
                if (current == null) {
                    parent.setLeftChild(newNode);
                    size++;
                    return true;
                }
            }
        }
    }

    public boolean remove(T value) {
        if (!this.contains(value))
            return false;
        Node<T> removable = root;
        Node<T> parent = root;
        boolean isLeftChild = false;

        //find() doesn't work here because we need parent Node too
        while (removable.getValue() != value) {
            parent = removable;
            if (removable.isLeftChild(value)) {
                removable = removable.getRightChild();
                isLeftChild = false;
            } else {
                removable = removable.getLeftChild();
                isLeftChild = true;
            }
        }

        if (removable.isLeaf()) {
            if (removable == root) {
                this.root = null;
                this.size = 0;
                return true;
            } else if (isLeftChild)
                parent.setLeftChild(null);
            else
                parent.setRightChild(null);
            this.size--;
            return true;
        }

        if (removable.hasSingleChild()) {
            if (removable.getLeftChild() == null) {
                if (removable == root)
                    root = removable.getRightChild();
                else if (isLeftChild)
                    parent.setLeftChild(removable.getRightChild());
                else
                    parent.setRightChild(removable.getRightChild());
            }
            if (removable.getRightChild() == null) {
                if (removable == root)
                    root = removable.getLeftChild();
                else if (isLeftChild)
                    parent.setLeftChild(removable.getLeftChild());
                else
                    parent.setRightChild(removable.getLeftChild());
            }
        } else {
            Node<T> successor = getSuccessor(removable);
            if (removable == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.setLeftChild(successor);
            } else {
                parent.setRightChild(successor);
            }
            successor.setLeftChild(removable.getLeftChild());
        }
        this.size--;
        return true;
    }

    public Node<T> getSuccessor(Node<T> liege) {
        Node<T> parent = liege;
        Node<T> successor = liege;
        Node<T> current = liege.getRightChild();

        while (current != null) {
            parent = successor;
            successor = current;
            current = current.getLeftChild();
        }

        if (successor != liege.getRightChild()) {
            parent.setLeftChild(successor.getRightChild());
            successor.setRightChild(liege.getRightChild());
        }

        return successor;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void setMaxLevel(Node<T> current, int level) {
        if (current == null) {
            return;
        }
        if (this.maxLevel < level)
            this.maxLevel = level;
        setMaxLevel(current.getLeftChild(), level + 1);
        setMaxLevel(current.getRightChild(), level + 1);
    }

    public void display() {
        this.root.print();
        if (isBalanced())
            System.out.println("Balanced");
        else
            System.out.println("Unbalanced");
    }

    public boolean isBalanced() {
        int levelLeft = 0;
        int levelRight = 0;
        if (root.getLeftChild() != null) {
            this.maxLevel = 0;
            setMaxLevel(root.getLeftChild(), 1);
            levelLeft = maxLevel;
        }
        if (root.getRightChild() != null) {
            this.maxLevel = 0;
            setMaxLevel(root.getRightChild(), 1);
            levelRight = maxLevel;
        }
        this.maxLevel = 0;
        setMaxLevel(root, 1);
        return Math.abs(levelRight - levelLeft) <= 1;
    }
}
