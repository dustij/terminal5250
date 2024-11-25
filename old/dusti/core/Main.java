package com.dusti.core;

import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import com.dusti.ui.T5250Frame;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class.getName());

        SwingUtilities.invokeLater(() -> {
            T5250Frame frame = new T5250Frame("Terminal5250");
            frame.setVisible(true);
            logger.info("Application started successfully.");
        });
    }
}
