package rougelike.screen;

import rougelike.ApplicationMain;
import rougelike.asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public abstract class RestartScreen implements Screen {

    @Override
    public abstract void displayOutput(AsciiPanel terminal);

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                Screen s = new PlayScreen();
                ApplicationMain.getInstance().setScreen(s);
                return s;
            default:
                return this;
        }
    }

}
