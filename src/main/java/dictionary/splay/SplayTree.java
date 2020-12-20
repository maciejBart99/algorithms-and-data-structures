package dictionary.splay;

import dictionary.bst.BSTNode;
import dictionary.bst.BSTTree;

public class SplayTree<K extends Comparable<K>, T> extends BSTTree<K,T> {

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
                                subRoot = LZigZig(SplayR(subRoot, key));
                            } else subRoot = LZig(subRoot);
                        }
                        case -1 -> {
                            if(p.getRight() != null) {
                                subRoot = LZigZag(SplayR(subRoot, key));
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
                                subRoot = RZigZag(SplayR(subRoot, key));
                            } else subRoot = RZig(subRoot);
                        }
                        case -1 -> {
                            if(p.getRight() != null) {
                                subRoot = RZigZig(SplayR(subRoot, key));
                            } else subRoot = RZig(subRoot);
                        }
                        default -> subRoot = RZig(subRoot);
                    }
                }
            }
        }
        return subRoot;
    }
}
