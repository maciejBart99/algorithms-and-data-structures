package dictionary.bst;

public class BSTNode<K extends Comparable<K>, T> {

    public BSTNode(K key, T value, BSTNode<K, T> left, BSTNode<K, T> right) {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public BSTNode<K, T> getLeft() {
        return left;
    }

    public void setLeft(BSTNode<K, T> left) {
        this.left = left;
    }

    public BSTNode<K, T> getRight() {
        return right;
    }

    public void setRight(BSTNode<K, T> right) {
        this.right = right;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public K getKey() {
        return key;
    }

    private K key;
    private T value;
    private BSTNode<K, T> left, right;
}
