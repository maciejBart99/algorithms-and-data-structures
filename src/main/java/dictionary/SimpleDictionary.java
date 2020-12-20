package dictionary;

import dictionary.exceptions.KeyAlreadyExistsException;
import dictionary.exceptions.KeyNotFoundException;

import java.util.Optional;

public interface SimpleDictionary<K extends Comparable<K>, T> {
    void Insert(K key, T item) throws KeyAlreadyExistsException;
    Optional<T> Get(K key);
    void Delete(K key) throws KeyNotFoundException;
    void Print(PrintStrategy strategy);
    int Count();
}
