package com.dusti.t5250;

import java.awt.AWTEvent;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class T5250Frame extends JFrame {
    public static final String EXIT_CMD = "EXIT_CMD";

    private T5250Ctrl t5250Ctrl;

    public T5250Frame(String title) {
        super(title);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        t5250Ctrl = createController();
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        getCommandMgr().setCommand(EXIT_CMD, this::processExitCmd);
    }

    protected void processExitCmd() {
        setVisible(false);
        dispose();
    }

    protected void processWindowEvent(WindowEvent e) {
        switch (e.getID()) {
            case WindowEvent.WINDOW_CLOSING:
                getCommandMgr().dispatchCommand(EXIT_CMD);
                break;
            case WindowEvent.WINDOW_CLOSED:
                break;
        }
        super.processWindowEvent(e);
    }

    protected T5250Ctrl createController() {
        return new T5250Ctrl();
    }    

    public final CommandMgr getCommandMgr() {
        return t5250Ctrl.getCommandMgr();
    }
}
