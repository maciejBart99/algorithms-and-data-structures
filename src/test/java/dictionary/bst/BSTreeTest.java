package dictionary.bst;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dictionary.SimpleDictionary;
import dictionary.exceptions.KeyAlreadyExistsException;
import dictionary.exceptions.KeyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class BSTreeTest {

    private SimpleDictionary<Integer, Integer> dict;

    @BeforeEach
    public void Setup() {
        this.dict = new BSTree<>();
    }

    @Test
    void ShouldHave0CountAtStart() {
        assertEquals(dict.Count(), 0);
    }

    @Test
    void ShouldIncreaseCountWhenInsertElements() throws KeyAlreadyExistsException {
        dict.Insert(10, 20);
        dict.Insert(1, 30);
        dict.Insert(3, 20);
        dict.Insert(2, 40);
        assertEquals(dict.Count(), 4);
    }

    @Test
    void ShouldBeAbleToGetInsertedElements() throws KeyAlreadyExistsException {
        dict.Insert(10, 20);
        dict.Insert(1, 30);
        dict.Insert(3, 20);
        dict.Insert(2, 40);
        assertEquals(dict.Get(2), Optional.of(40));
    }

    @Test()
    void ShouldThrowOnDoubleInsertion() {
        assertThrows(KeyAlreadyExistsException.class, () -> {
            dict.Insert(2, 40);
            dict.Insert(2, 40);
        });
    }

    @Test
    void ShouldDecreaseCountWhenDeleting() throws KeyAlreadyExistsException, KeyNotFoundException {
        dict.Insert(10, 20);
        dict.Insert(1, 30);
        dict.Insert(3, 20);
        dict.Insert(2, 40);
        dict.Delete(10);
        assertEquals(dict.Count(), 3);
    }

    @Test
    void ShouldDeleteElement() throws KeyAlreadyExistsException, KeyNotFoundException {
        dict.Insert(1, 30);
        dict.Insert(10, 20);
        dict.Insert(3, 20);
        dict.Insert(2, 40);
        dict.Delete(10);
        assertEquals(dict.Get(10), Optional.empty());
    }

    @Test()
    void ShouldThrowOnDeletingNonExisting() {
        assertThrows(KeyNotFoundException.class, () -> {
            dict.Insert(10, 20);
            dict.Insert(1, 30);
            dict.Insert(3, 20);
            dict.Insert(2, 40);
            dict.Delete(1000);
        });
    }
}