package ru.vironit.jump;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    private boolean running = false;
    private boolean round = true;

    private final int wait = 300;
    private int waitLost = wait;

    public static int WIDTH = 400;
    public static int HEIGHT = 300;
    public static String NAME = "JUMP";
    public static int jump = 100;
    public static double speed = 0.1;
    public static int attack = 50;

    private Player playerOne;
    private Player playerTwo;
    private Player winner;

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();
        long delta;

        init();

        while (running) {
            delta = System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
            if (round) {
                update(delta);
            }  else {
                waitLost--;
            }
            render();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JFrame frame = new JFrame(Game.NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        game.start();
    }

    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void init() {
        playerOne = new Player(false, attack,
                jump, speed, WIDTH, HEIGHT,
                "left-blue.png", "left-blue-attack.png", "left-blue-defence.png",
                "right-blue.png", "right-blue-attack.png", "right-blue-defence.png"
        );
        playerTwo = new Player(true, attack,
                jump, speed, WIDTH, HEIGHT,
                "left-red.png", "left-red-attack.png", "left-red-defence.png",
                "right-red.png", "right-red-attack.png", "right-red-defence.png"
        );
        addKeyListener(new KeyInputHandler());
    }

    public void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(2);
            requestFocus();
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        int floor = HEIGHT/2 + playerOne.getHero().getHeight();
        graphics.setColor(Color.cyan);
        graphics.fillRect(0, 0, getWidth(),floor);
        graphics.setColor(Color.green);
        graphics.fillRect(0, floor, getWidth(), floor + HEIGHT / 20);
        graphics.setColor(Color.darkGray);
        graphics.fillRect(0, floor + HEIGHT / 20, getWidth(),getHeight());

        playerOne.draw(graphics);
        playerTwo.draw(graphics);

        Sprite sprite;
        if (winner == playerOne) {
            sprite = wait("blue");
        } else {
            sprite = wait("red");
        }
        if (sprite != null) {
            sprite.draw(graphics, 0, 0);
        }

        graphics.dispose();
        bufferStrategy.show();
    }

    public void update(long delta) {
        if (playerOne.isAttackPressed() && !playerTwo.isDownPressed()) {
            attack(playerOne, playerTwo);
        }
        if (playerTwo.isAttackPressed() && !playerOne.isDownPressed()) {
            attack(playerTwo, playerOne);
        }

        playerOne.update();
        playerTwo.update();
    }

    public void attack(Player attack, Player defence) {
        double xOne = attack.getX(), xTwo = defence.getX();
        int widthOne = attack.getHero().getWidth() + (int) xOne;
        int widthTwo = defence.getHero().getWidth() + (int) xTwo;
        if (xOne > xTwo && xOne < widthTwo) {
            defence.setHealth(defence.getHealth() - (widthTwo - xOne) * speed * speed * speed);
        }
        if (widthOne > xTwo && widthOne < widthTwo) {
            defence.setHealth(defence.getHealth() - (widthOne - xTwo) * speed * speed * speed);
        }
        if (defence.getHealth() < 0) {
            round = false;
            winner = attack;
        }
    }

    public Sprite wait(String color) {
        if (waitLost < wait) {
            if (waitLost < wait / 3 * 2) {
                if (waitLost < wait / 3) {
                    if (waitLost < 0) {
                        waitLost = wait;
                        round = true;
                        init();
                    } else {
                        return playerOne.getSprite(color + "-1.png");
                    }
                } else {
                    return playerOne.getSprite(color + "-2.png");
                }
            } else {
                return playerOne.getSprite(color + "-3.png");
            }
        }
        return null;
    }

    private class KeyInputHandler extends KeyAdapter {
        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                playerTwo.leftPressed();
            }
            if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                playerTwo.rightPressed();
            }
            if (event.getKeyCode() == KeyEvent.VK_UP) {
                playerTwo.upPressed();
            }
            if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                playerTwo.downPressed();
            }
            if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                playerTwo.attackPressed();
            }
            if (event.getKeyCode() == KeyEvent.VK_A) {
                playerOne.leftPressed();
            }
            if (event.getKeyCode() == KeyEvent.VK_D) {
                playerOne.rightPressed();
            }
            if (event.getKeyCode() == KeyEvent.VK_W) {
                playerOne.upPressed();
            }
            if (event.getKeyCode() == KeyEvent.VK_S) {
                playerOne.downPressed();
            }
            if (event.getKeyCode() == KeyEvent.VK_CONTROL) {
                playerOne.attackPressed();
            }
            if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(1);
            }
        }

        public void keyReleased(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                playerTwo.leftReleased();
            }
            if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                playerTwo.rightReleased();
            }
            if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                playerTwo.downReleased();
            }
            if (event.getKeyCode() == KeyEvent.VK_A) {
                playerOne.leftReleased();
            }
            if (event.getKeyCode() == KeyEvent.VK_D) {
                playerOne.rightReleased();
            }
            if (event.getKeyCode() == KeyEvent.VK_S) {
                playerOne.downReleased();
            }
        }
    }
}
