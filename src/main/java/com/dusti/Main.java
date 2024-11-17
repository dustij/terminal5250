package com.dusti;

import javax.swing.SwingUtilities;
import com.dusti.t5250.T5250Frame;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        try{
            SwingUtilities.invokeAndWait(() -> {
                T5250Frame frame = new T5250Frame("Terminal5250");
                frame.setVisible(true);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
