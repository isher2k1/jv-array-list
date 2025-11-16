package core.basesyntax;

import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {
    private static final int ARRAY_CAPACITY = 10;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final float MULTIPLIER = (float) THREE / TWO + ONE;
    private T[] array;
    private int size;

    public ArrayList() {
        this.array = (T[]) new Object[ARRAY_CAPACITY];
        this.size = ZERO;
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

        System.arraycopy(array, index, array, index + ONE, size - index);

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

        System.arraycopy(array, index + ONE, array, index, size - index - ONE);

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
        return size == ZERO;
    }

    private void grow() {
        if (size == array.length) {
            int oldCapacity = array.length;
            int newCapacity = oldCapacity + (oldCapacity >> ONE);
            T[] tempArray = (T[]) new Object[newCapacity];
            System.arraycopy(array, ZERO, tempArray, ZERO, oldCapacity);
            array = tempArray;
        }
    }

    private void ensureCapacity(List<T> list) {
        if (array.length < size + list.size()) {
            int newCapacity = max((int) (array.length * MULTIPLIER), size + list.size());
            T[] tempArray = (T[]) new Object[newCapacity];
            System.arraycopy(array, ZERO, tempArray, ZERO, array.length);
            array = tempArray;
        }
    }

    private void checkIndexForAccess(int index) {
        if (index >= size || index < ZERO) {
            throw new ArrayListIndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < ZERO) {
            throw new ArrayListIndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private int max(int firstValue, int secondValue) {
        return firstValue >= secondValue ? firstValue : secondValue;
    }
}
