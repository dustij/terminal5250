package com.dusti.t5250v1;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class T5250Frame extends JFrame {
    public static final String EXIT_CMD = "EXIT_CMD";
    private T5250Controller t5250Ctrl;
    private CharacterTerminal terminal;
    private CharacterTerminalBuffer buffer;

    public T5250Frame(String title) {
        super(title);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        t5250Ctrl = createController();
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        getCommandMgr().setCommand(EXIT_CMD, this::processExitCmd);

        buffer = new CharacterTerminalBuffer(80, 24);
        terminal = new CharacterTerminal(buffer);
        setLayout(new BorderLayout());
        add(terminal, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);

        displayMenu();
    }

    private void displayMenu() {
        buffer.clear();
        String[] menuOptions = {
            "1. View Budget",
            "2. Add Expense",
            "3. Add Income",
            "4. Exit"
        };

        for (int i = 0; i < menuOptions.length; i++) {
            buffer.writeString(0, i + 1, menuOptions[i]);
        }

        // Add input field at bottom
        int bottom = buffer.getRows() - 2;
        buffer.writeString(0, bottom, "===> " + "_".repeat(buffer.getColumns() - 6));

        terminal.repaint();
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

    protected T5250Controller createController() {
        return new T5250Controller();
    }    

    public final CommandManager getCommandMgr() {
        return t5250Ctrl.getCommandMgr();
    }
}
