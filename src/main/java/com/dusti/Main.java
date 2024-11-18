package com.dusti;

import javax.swing.SwingUtilities;
import com.dusti.t5250.T5250Frame;
import com.dusti.t5250.MyLogger;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        final Logger LOGGER = MyLogger.getLogger(Main.class.getName()); 
        try{
            SwingUtilities.invokeAndWait(() -> {
                T5250Frame frame = new T5250Frame("Terminal5250");
                frame.setVisible(true);
                LOGGER.info("App started");;
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
            LOGGER.severe("Could not start app ==> " + e.getMessage());
        }
    }
}
