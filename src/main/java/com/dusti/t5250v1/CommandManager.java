package com.dusti.t5250v1;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandManager {
    private static final Logger LOGGER = Logger.getLogger(CommandManager.class.getName());

    private Map<String, CommandAction> act2cmd = new HashMap<>();

    public void setCommand(String act, CommandAction cmd) {
        if (act == null) {
            throw new IllegalArgumentException("The action command can't be null");
        }

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine(act + " -> " + cmd);
        }

        act2cmd.put(act, cmd);
    }

    public CommandAction getCommand(String act) {
        return act2cmd.get(act);
    }

    public void dispatchCommand(String act) {
        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("dispatchCommand " + act);
        }

        CommandAction cmd = getCommand(act);
        if (cmd != null) {
            cmd.exec();
        }
    }
}
