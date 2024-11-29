package com.dusti.core;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.dusti.controllers.CursorController;
import com.dusti.views.ScreenView;

public class MouseHandler {
    private CursorController cursorController;

    public MouseHandler(CursorController cursorController) {
        this.cursorController = cursorController;
    }

    public void addListener(ScreenView screenView) {
        screenView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent evt) {
                cursorController.moveToRel(evt.getX(), evt.getY());
            }
        });
    }
}
