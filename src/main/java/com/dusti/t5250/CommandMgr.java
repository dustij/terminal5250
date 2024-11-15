package com.dusti.t5250;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandMgr {
    private static final Logger LOGGER = Logger.getLogger(CommandMgr.class.getName());

    private Map<String, Command> act2cmd = new HashMap<>();

    public void setCommand(String act, Command cmd) {
        if (act == null) {
            throw new IllegalArgumentException("The action command can't be null");
        }

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine(act + " -> " + cmd);
        }

        act2cmd.put(act, cmd);
    }

    public Command getCommand(String act) {
        return act2cmd.get(act);
    }

    public void dispatchCommand(String act) {
        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("dispatchCommand " + act);
        }

        Command cmd = getCommand(act);
        if (cmd != null) {
            cmd.exec();
        }
    }
}
