package dictionary.bst;


import dictionary.exceptions.KeyAlreadyExistsException;
import dictionary.PrintStrategy;
import dictionary.SimpleDictionary;
import dictionary.exceptions.KeyNotFoundException;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;

public class BSTTree<K extends Comparable<K>,T> implements SimpleDictionary<K, T> {
    private BSTNode<K, T> root = null;
    private final Random branchRandomSelector = new Random();
    private int count = 0;

    private BSTNode<K, T> FindSmallestR(BSTNode<K, T> currentRoot) {
        if (currentRoot.getLeft() != null) return FindSmallestR(currentRoot.getLeft());
        else return currentRoot;
    }

    private BSTNode<K, T> FindBiggestR(BSTNode<K, T> currentRoot) {
        if (currentRoot.getRight() != null) return FindSmallestR(currentRoot.getRight());
        else return currentRoot;
    }

    private void InsertR(BSTNode<K, T> currentRoot, BSTNode<K, T> element) throws KeyAlreadyExistsException {
        switch (currentRoot.getKey().compareTo(element.getKey())) {
            case 0 -> throw new KeyAlreadyExistsException();
            case -1 -> {
                if (currentRoot.getRight() == null) currentRoot.setRight(element);
                else InsertR(currentRoot.getRight(), element);
            }
            case 1 -> {
                if (currentRoot.getLeft() == null) currentRoot.setLeft(element);
                else InsertR(currentRoot.getLeft(), element);
            }
        }
    }

    private Optional<T> SearchR(BSTNode<K, T> currentRoot, K key)  {
        if (currentRoot == null) return Optional.empty();
        return switch (currentRoot.getKey().compareTo(key)) {
            case 0 -> Optional.of(currentRoot.getValue());
            case -1 -> SearchR(currentRoot.getRight(), key);
            case 1 -> SearchR(currentRoot.getLeft(), key);
            default -> Optional.empty();
        };
    }

    private void DFSPrintR(BSTNode<K, T> currentRoot) {
        if (currentRoot == null) return;
        System.out.printf("(%s, %s) ", currentRoot.getKey().toString(), currentRoot.getValue().toString());
        DFSPrintR(currentRoot.getLeft());
        DFSPrintR(currentRoot.getRight());
    }

    private void BFSPrint(BSTNode<K, T> root) {
        var queue = new LinkedList<BSTNode<K, T>>();
        queue.addLast(root);
        while(!queue.isEmpty()) {
            var current = queue.peekFirst();
            System.out.printf("(%s, %s) ", current.getKey().toString(), current.getValue().toString());
            if (current.getRight() != null) queue.addLast(current.getRight());
            if (current.getLeft() != null) queue.addLast(current.getLeft());
        }
    }

    @Override
    public void Insert(K key, T item) throws KeyAlreadyExistsException {
        var newNode = new BSTNode<K, T>(key, item, null, null);
        if (root == null) root = newNode;
        else InsertR(root, newNode);
        count++;
    }

    @Override
    public Optional<T> Get(K key) {
        return SearchR(root, key);
    }

    @Override
    public void Delete(K key) throws KeyNotFoundException {
        BSTNode<K, T> current = root, previous = null;
        while(current != null && current.getKey().compareTo(key) != 0) {
            previous = current;
            current = current.getKey().compareTo(key) > 0 ? current.getLeft() : current.getRight();
        }
        if (current == null) throw new KeyNotFoundException();
        if (current.getLeft() != null && current.getRight() != null) {
            var toReplace = branchRandomSelector.nextBoolean() ? FindBiggestR(current.getLeft()) : FindSmallestR(current.getRight());
            current.setValue(toReplace.getValue());
            current.setKey(toReplace.getKey());
            current = toReplace;
        }
        if (current.getRight() != null) {
            if (previous == null) root = current.getRight();
            else if (previous.getKey().compareTo(key) > 0) previous.setLeft(current.getRight());
            else previous.setRight(current.getRight());
        } else if (current.getLeft() != null) {
            if (previous == null) root = current.getLeft();
            else if (previous.getKey().compareTo(key) > 0) previous.setLeft(current.getLeft());
            else previous.setRight(current.getLeft());
        } else {
            if (previous == null) root = null;
            else if (previous.getKey().compareTo(key) > 0) previous.setLeft(null);
            else previous.setRight(null);
        }
        count--;
    }

    @Override
    public void Print(PrintStrategy strategy) {
        if (strategy == PrintStrategy.BFS) {
            BFSPrint(root);
        } else {
            DFSPrintR(root);
        }
        System.out.println("");
    }

    @Override
    public int Count() {
        return count;
    }
}
