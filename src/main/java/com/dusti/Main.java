package com.dusti;

import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import com.dusti.core.LoggerFactory;
import com.dusti.core.MainFrame;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class.getName());

        SwingUtilities.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame("Terminal5250");
                frame.setVisible(true);
                logger.info("Application started successfully.");
            } catch (Exception e) {
                String execption = e.getClass().getSimpleName();
                logger.severe("Failed to start application because " + execption + " was thrown.");
                logger.severe("--| " + execption + ": " + e.getMessage());
                var stackTrace = e.getStackTrace();
                for (int i = 0; i < stackTrace.length; i++) {
                    String msg = String.format("%s.%s:%d", stackTrace[i].getClassName(),
                            stackTrace[i].getMethodName(), stackTrace[i].getLineNumber());
                    logger.severe("  |--> " + msg);
                }
                throw new RuntimeException(e);
            }
        });
    }
}
