package KD3;

public class TreeNodes {
    private TreeNodes left;
    private TreeNodes right;
    private Nd node;

    public TreeNodes(Nd node) {
        this.left = null;
        this.right = null;
        this.node = node;
    }

    public TreeNodes getLeft() {
        return left;
    }

    public void setLeft(TreeNodes left) {
        this.left = left;
    }

    public TreeNodes getRight() {
        return right;
    }

    public void setRight(TreeNodes right) {
        this.right = right;
    }

    public Nd getNode() {
        return node;
    }

    public void setNode(Nd node) {
        this.node = node;
    }


}
