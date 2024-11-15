package com.dusti;

import javax.swing.SwingUtilities;
import com.dusti.t5250.T5250Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        try{
            SwingUtilities.invokeAndWait(() -> {
                T5250Frame frame = new T5250Frame("tn5250");
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        System.exit(0);
                    }
                });
                frame.setVisible(true);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
