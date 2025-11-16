package core.basesyntax;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {
    private static final int ARRAY_CAPACITY = 10;
    private T[] array;
    private int size;

    public ArrayList() {
        this.array = (T[]) new Object[ARRAY_CAPACITY];
        this.size = 0;
    }

    @Override
    public void add(T value) {
        grow();

        array[size] = value;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        grow();

        for (int i = size - 1; i >= index; i--) {
            array[i + 1] = array[i];
        }

        array[index] = value;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        ensureCapacity(list);

        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndexForAccess(index);

        return array[index];
    }

    @Override
    public void set(T value, int index) {
        checkIndexForAccess(index);

        array[index] = value;
    }

    @Override
    public T remove(int index) {
        checkIndexForAccess(index);

        final T tempValue = get(index);

        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }

        size--;
        array[size] = null;
        return tempValue;
    }

    @Override
    public T remove(T element) {
        T found = null;
        boolean isFound = false;

        for (int i = 0; i < size; i++) {
            if (element == null && array[i] == null) {
                found = array[i];
                remove(i);
                isFound = true;
                break;
            } else if (array[i] != null && array[i].equals(element)) {
                found = array[i];
                remove(i);
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            throw new NoSuchElementException("Element not found");
        }
        return found;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void grow() {
        if (size == array.length) {
            int oldCapacity = array.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            array = Arrays.copyOf(array, newCapacity);
        }
    }

    private void ensureCapacity(List<T> list) {
        if (array.length < size + list.size()) {
            int newCapacity = Math.max(array.length * 3 / 2 + 1, size + list.size());
            array = Arrays.copyOf(array, newCapacity);
        }
    }

    private void checkIndexForAccess(int index) {
        if (index >= size || index < 0) {
            throw new ArrayListIndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new ArrayListIndexOutOfBoundsException("Invalid index " + index);
        }
    }
}
