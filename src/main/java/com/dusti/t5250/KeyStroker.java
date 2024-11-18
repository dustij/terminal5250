package com.dusti.t5250;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyStroker implements KeyListener{
    private final ScreenBuffer screenBuffer;
    private final Cursor cursor;

    public KeyStroker(ScreenBuffer screenBuffer, Cursor cursor) {
        this.screenBuffer = screenBuffer;
        this.cursor = cursor;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        switch (keyCode) {
            case KeyEvent.VK_UP:
                cursor.moveBy(-1, 0);
                break;
            case KeyEvent.VK_DOWN:
                cursor.moveBy(1, 0);
                break;
            case KeyEvent.VK_LEFT:
                cursor.moveBy(0, -1);
                break;
            case KeyEvent.VK_RIGHT:
                cursor.moveBy(0, 1);
                break;
            default:
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    
}
