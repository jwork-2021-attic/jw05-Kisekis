package rougelike;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.JFrame;

import rougelike.asciiPanel.AsciiFont;
import rougelike.asciiPanel.AsciiPanel;
import rougelike.screen.Screen;
import rougelike.screen.StartScreen;

public class ApplicationMain extends JFrame implements KeyListener {

    private AsciiPanel terminal;
    private Screen screen;
    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    private static final ApplicationMain instance = new ApplicationMain();

    private ApplicationMain() {
        super();
        terminal = new AsciiPanel(52, 25, AsciiFont.TEST);
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        new Thread(()->{
            while(true) {

                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public void repaint() {
        terminal.clear();

        screen.displayOutput(terminal);
        super.repaint();
    }

    /**
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        screen.respondToUserInput(e);
    }
    /**
     *
     * @param e
     */
    public void keyReleased(KeyEvent e) {
    }
    /**
     *
     * @param e
     */
    public void keyTyped(KeyEvent e) {
    }

    public static ApplicationMain getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        ApplicationMain app = ApplicationMain.getInstance();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}


