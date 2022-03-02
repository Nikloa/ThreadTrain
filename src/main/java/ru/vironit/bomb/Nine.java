package ru.vironit.bomb;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Scanner;

public class Nine {

    static Thread input;
    static Thread output;

    static volatile int position = 6;
    static Integer str0;
    static Integer str1;
    static Integer str2;
    static Integer str3;
    static Integer str4;

    static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {

        output = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (str4 == null || str4 != position) {
                        draw();
                    } else {
                        System.out.println("--- Game Over ---");
                        System.exit(1);
                    }

                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    clear();
                    try {
                        Thread.currentThread().sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        input = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String string = scanner.next();
                    System.out.println("---" + string + "---");
                    if (string.equals("e")) {
                        System.exit(1);
                    }
                    if (string.equals("a")) {
                        decrement();
                    }
                    if (string.equals("d")) {
                        increment();
                    }
                }
            }
        });

        output.start();
        input.setDaemon(true);
        input.start();
    }

    public static void draw() {

        int temp = random.nextInt(30);
        if (temp < 13) str0 = temp;
        else str0 = null;

        str4 = str3; str3 = str2; str2 = str1; str1 = str0;

        drawString(str1);
        drawString(str2);
        drawString(str3);
        drawString(str4);

        System.out.print("||");
        for (int i = 0; i < position; i++) {
            System.out.print(" ");
        }
        System.out.print("O");
        for (int i = 0; i < 12-position; i++) {
            System.out.print(" ");
        }
        System.out.println("||");
    }

    public static void clear() {
        Robot bot = null;
        try {
            bot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        bot.keyPress(KeyEvent.VK_CONTROL);
        bot.keyPress(KeyEvent.VK_ALT);
        bot.keyPress(KeyEvent.VK_SHIFT);
        bot.keyPress(KeyEvent.VK_O);
        bot.keyRelease(KeyEvent.VK_CONTROL);
        bot.keyRelease(KeyEvent.VK_ALT);
        bot.keyRelease(KeyEvent.VK_SHIFT);
        bot.keyRelease(KeyEvent.VK_O);
    }

    public static void decrement() {
        if (position > 0) {
            position--;
        }
    }

    public static void increment() {
        if (position < 12) {
            position++;
        }
    }

    public static void drawString(Integer str) {
        if (str == null) {
            System.out.println("||             ||");
        } else {
            System.out.print("||");
            for (int i = 0; i < str; i++) {
                System.out.print(" ");
            }
            System.out.print("U");
            for (int i = 0; i < 12-str; i++) {
                System.out.print(" ");
            }
            System.out.println("||");
        }
    }
}
