package dictionary.splay;

import dictionary.bst.BSTNode;
import dictionary.bst.BSTree;
import dictionary.exceptions.KeyAlreadyExistsException;
import dictionary.exceptions.KeyNotFoundException;

import java.util.Optional;

public class SplayTree<K extends Comparable<K>, T> extends BSTree<K,T> {

    //Basic rotations
    private BSTNode<K,T> RR(BSTNode<K,T> subRoot) {
        BSTNode<K,T> p = subRoot;
        subRoot = subRoot.getRight();
        p.setRight(subRoot.getLeft());
        subRoot.setLeft(p);
        return subRoot;
    }

    private BSTNode<K,T> LL(BSTNode<K,T> subRoot) {
        BSTNode<K,T> p = subRoot;
        subRoot = subRoot.getLeft();
        p.setLeft(subRoot.getRight());
        subRoot.setRight(p);
        return subRoot;
    }

    private BSTNode<K,T> LR(BSTNode<K,T> subRoot) {
        BSTNode<K,T> x = subRoot.getLeft();
        BSTNode<K,T> p = subRoot;
        subRoot = x.getRight();
        p.setLeft(subRoot.getRight());
        x.setRight(subRoot.getLeft());
        subRoot.setLeft(x);
        subRoot.setRight(p);
        return subRoot;
    }

    private BSTNode<K,T> RL(BSTNode<K,T> subRoot) {
        BSTNode<K,T> x = subRoot.getRight();
        BSTNode<K,T> p = subRoot;
        subRoot = x.getLeft();
        p.setRight(subRoot.getLeft());
        x.setLeft(subRoot.getRight());
        subRoot.setRight(x);
        subRoot.setLeft(p);
        return subRoot;
    }

    //Zigzag rotations
    private BSTNode<K,T> LZig(BSTNode<K,T> subRoot) {
        return LL(subRoot);
    }

    private BSTNode<K,T> LZigZig(BSTNode<K,T> subRoot) {
        return LL(LL(subRoot));
    }

    private BSTNode<K,T> LZigZag(BSTNode<K,T> subRoot) {
        return LR(subRoot);
    }

    private BSTNode<K,T> RZig(BSTNode<K,T> subRoot) {
        return RR(subRoot);
    }

    private BSTNode<K,T> RZigZig(BSTNode<K,T> subRoot) {
        return RR(RR(subRoot));
    }

    private BSTNode<K,T> RZigZag(BSTNode<K,T> subRoot) {
        return RL(subRoot);
    }

    private BSTNode<K,T> SplayR(BSTNode<K,T> subRoot, K key) {
        switch (subRoot.getKey().compareTo(key)) {
            case 1 -> {
                var p = subRoot.getLeft();
                if (p != null) {
                    switch (p.getKey().compareTo(key)) {
                        case 1 -> {
                            if(p.getLeft() != null) {
                                p.setLeft(SplayR(p.getLeft(), key));
                                subRoot = LZigZig(subRoot);
                            } else subRoot = LZig(subRoot);
                        }
                        case -1 -> {
                            if(p.getRight() != null) {
                                p.setRight(SplayR(p.getRight(), key));
                                subRoot = LZigZag(subRoot);
                            } else subRoot = LZig(subRoot);
                        }
                        default -> subRoot = LZig(subRoot);
                    }
                }
            }
            case -1 -> {
                var p = subRoot.getRight();
                if (p != null) {
                    switch (p.getKey().compareTo(key)) {
                        case 1 -> {
                            if(p.getLeft() != null) {
                                p.setLeft(SplayR(p.getLeft(), key));
                                subRoot = RZigZag(subRoot);
                            } else subRoot = RZig(subRoot);
                        }
                        case -1 -> {
                            if(p.getRight() != null) {
                                p.setRight(SplayR(p.getRight(), key));
                                subRoot = RZigZig(subRoot);
                            } else subRoot = RZig(subRoot);
                        }
                        default -> subRoot = RZig(subRoot);
                    }
                }
            }
        }
        return subRoot;
    }

    @Override
    public void Insert(K key, T item) throws KeyAlreadyExistsException {
        BSTNode<K, T> newNode = new BSTNode<>(key, item, null, null);
        if (root != null) {
            root = SplayR(root, key);
            switch (root.getKey().compareTo(key)) {
                case 0 -> throw new KeyAlreadyExistsException();
                case 1 -> {
                    newNode.setLeft(root.getLeft());
                    newNode.setRight(root);
                    root.setLeft(null);
                }
                case -1 -> {
                    newNode.setRight(root.getRight());
                    newNode.setLeft(root);
                    root.setRight(null);
                }
            }
        }
        root = newNode;
        count++;
    }

    @Override
    public void Delete(K key) throws KeyNotFoundException {
        if (root == null) throw new KeyNotFoundException();
        root = SplayR(root, key);
        if (root.getKey().compareTo(key) != 0) throw new KeyNotFoundException();
        if (root.getLeft() == null) root = root.getRight();
        else {
            root.setLeft(SplayR(root.getLeft(), key));
            root.getLeft().setRight(root.getRight());
            root = root.getLeft();
        }
        count--;
    }

    @Override
    public Optional<T> Get(K key) {
        if (root == null) return Optional.empty();
        root = SplayR(root, key);
        return root.getKey().compareTo(key) == 0 ? Optional.of(root.getValue()) : Optional.empty();
    }
}
