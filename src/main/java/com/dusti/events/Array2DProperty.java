package com.dusti.events;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import com.dusti.interfaces.BufferChangeListener;

public class Array2DProperty<T> {
    private final List<BufferChangeListener<T>> listeners = new ArrayList<>();
    private final T[][] array2D;

    public Array2DProperty(int rows, int cols, Supplier<T[][]> arraySupplier) {
        this.array2D = arraySupplier.get();
        if (array2D.length != rows || array2D[0].length != cols) {
            throw new IllegalArgumentException("Array dimensions must match rows and cols");
        }
    }

    public void dispatchUpdateEvent() {
        for (BufferChangeListener<T> listener : listeners) {
            listener.onBufferChange(new BufferEvent<>(null, null, null));
        }
    }

    public void addListener(BufferChangeListener<T> listener) {
        listeners.add(listener);
    }

    public void removeListener(BufferChangeListener<T> listener) {
        listeners.remove(listener);
    }

    public T getValueAt(int row, int col) {
        return array2D[row][col];
    }

    public void setValueAt(int row, int col, T value) {
        var oldValue = array2D[row][col];
        array2D[row][col] = value;
        notifyListeners(new BufferEvent<>(new BufferEventSource(row, col), oldValue, value));
    }

    private void notifyListeners(BufferEvent<T> event) {
        for (BufferChangeListener<T> listener : listeners) {
            listener.onBufferChange(event);
        }
    }

    public int getRowCount() {
        return array2D.length;
    }

    public int getColCount() {
        return array2D[0].length;
    }

    public List<BufferChangeListener<T>> getListeners() {
        return listeners;
    }
}
