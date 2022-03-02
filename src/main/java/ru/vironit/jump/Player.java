package ru.vironit.jump;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Player {

    public final int jump;
    public final double speed;
    public final int attack;

    public final String left;
    public final String leftAttack;
    public final String leftDefence;
    public final String right;
    public final String rightAttack;
    public final String rightDefence;
    public final Color color;

    public Sprite hero;
    public double health;
    public boolean opposite;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean attackPressed = false;

    private double x;
    private double y;

    private double attackLost;
    private double jumpUp;
    private double jumpDown;

    public Player(boolean opposite, int attack,
            int jump, double speed, double x, double y,
            String left, String leftAttack, String leftDefence,
            String right, String rightAttack, String rightDefence
    ) {
        this.attack = attack;
        this.jump = jump;
        this.speed = speed;
        this.left = left;
        this.leftAttack = leftAttack;
        this.leftDefence = leftDefence;
        this.right = right;
        this.rightAttack = rightAttack;
        this.rightDefence = rightDefence;
        jumpUp = jump;
        jumpDown = jump;
        attackLost = attack;
        this.opposite = opposite;
        if (opposite) {
            this.x = x / 8 * 5;
            hero = getSprite(left);
            color = Color.red;
        } else {
            this.x = x / 8;
            hero = getSprite(right);
            color = Color.blue;
        }
        health = hero.getWidth();
        this.y = y / 2;
    }

    public Sprite getSprite(String path) {
        BufferedImage sourceImage = null;

        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            assert url != null;
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert sourceImage != null;
        return new Sprite(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
    }

    public void draw(Graphics graphics) {
        hero.draw(graphics, (int) x, (int) y);
        graphics.setColor(Color.black);
        graphics.fillRect((int) x - 2, 23, hero.getWidth() + 4, 19);
        graphics.setColor(color);
        graphics.fillRect((int) x, 25, (int) health, 15);
    }

    public void update() {
        if (x < 0) x = 0;
        if (x > Game.WIDTH - hero.getWidth()) x = Game.WIDTH - hero.getWidth();

        if (leftPressed) {
            if (downPressed) {
                x = x - speed / 2;
            } else {
                x = x - speed;
            }
        }
        if (rightPressed) {
            if (downPressed) {
                x = x + speed / 2;
            } else {
                x = x + speed;
            }
        }
        if (upPressed) {
            if (jumpUp > 0) {
                y = y - speed;
                jumpUp = jumpUp - speed;
            } else {
                y = y + speed;
                jumpDown = jumpDown - speed;
            }
            if (jumpDown < 0) {
                upPressed = false;
                jumpUp = jump;
                jumpDown = jump;
            }
        }
        if (attackPressed) {
            if (attackLost > 0) {
                attackLost = attackLost - speed;
            } else {
                attackPressed = false;
                attackLost = attack;
                if (downPressed) {
                    if (opposite) {
                        hero = getSprite(leftDefence);
                    } else {
                        hero = getSprite(rightDefence);
                    }
                } else {
                    if (opposite) {
                        hero = getSprite(left);
                    } else {
                        hero = getSprite(right);
                    }
                }
            }
        }
    }

    public void leftPressed() {
        leftPressed = true;
        opposite = true;
        if (downPressed) {
            hero = getSprite(leftDefence);
        } else {
            hero = getSprite(left);
        }
    }

    public void rightPressed() {
        rightPressed = true;
        opposite = false;
        if (downPressed) {
            hero = getSprite(rightDefence);
        } else {
            hero = getSprite(right);
        }
    }

    public void upPressed() {
        upPressed = true;
    }

    public void downPressed() {
        downPressed = true;
        if (opposite) {
            hero = getSprite(leftDefence);
        } else {
            hero = getSprite(rightDefence);
        }
    }

    public void attackPressed() {
        if (!downPressed) {
            attackPressed = true;
            if (opposite) {
                hero = getSprite(leftAttack);
            } else {
                hero = getSprite(rightAttack);
            }
        }
    }

    public void leftReleased() {
        leftPressed = false;
        if (downPressed) {
            hero = getSprite(leftDefence);
        } else {
            hero = getSprite(left);
        }
    }

    public void rightReleased() {
        rightPressed = false;
        if (downPressed) {
            hero = getSprite(rightDefence);
        } else {
            hero = getSprite(right);
        }
    }

    public void downReleased() {
        downPressed = false;
        if (opposite) {
            hero = getSprite(left);
        } else {
            hero = getSprite(right);
        }
    }

    public boolean isAttackPressed() {
        return  attackPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Sprite getHero() {
        return hero;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return health;
    }
}
