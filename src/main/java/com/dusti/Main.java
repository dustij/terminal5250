package com.dusti;

import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import com.dusti.core.LoggerFactory;
import com.dusti.core.MainFrame;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class.getName());

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame("Terminal5250");
            frame.setVisible(true);
            logger.info("Application started successfully.");
        });
    }
}
