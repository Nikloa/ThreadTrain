package ru.vironit.snake;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.Random;

import static ru.vironit.snake.Constants.CELL_COUNT_X;
import static ru.vironit.snake.Constants.CELL_COUNT_Y;

public class Main {

    private static boolean isExitRequested = false;
    private static int x = -1, y = 0, direction = 1, length = 3;
    private static boolean have_to_decrease = true;


    public static void main(String[] args) {

        GUI.init();
        generateNewObject();

        while (!isExitRequested) {
            input();

            move();

            GUI.draw();
            GUI.update(have_to_decrease);
        }
    }

    private static void move() {
        have_to_decrease = true;

        switch (direction) {
            case 0:
                y++; break;
            case 1:
                x++; break;
            case 2:
                y--; break;
            case 3:
                x--; break;
        }

        if (x < 0 || x >= CELL_COUNT_X || y < 0 || y >= CELL_COUNT_Y) {
            System.exit(1);
        }

        int nextCellState = GUI.getState(x, y);

        if (nextCellState > 0) {
            System.exit(1);
        } else {
            if (nextCellState < 0) {
                length++;
                generateNewObject();
                have_to_decrease = false;
            }
            GUI.setState(x, y, length);
        }
    }

    private static void generateNewObject() {
        int point = new Random().nextInt(CELL_COUNT_X * CELL_COUNT_Y - length);

        for (int i = 0; i < CELL_COUNT_X; i++) {
            for (int j = 0; j < CELL_COUNT_Y; j++) {
                if (GUI.getState(i, j) <= 0) {
                    GUI.setState(i, j, -1);
                    return;
                } else {
                    point--;
                }
            }
        }
    }

    private static void input() {
        int newDirection = direction;

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                switch (Keyboard.getEventKey()) {
                    case Keyboard.KEY_ESCAPE:
                        isExitRequested = true;
                        break;
                    case Keyboard.KEY_UP:
                        if (direction != 2) newDirection = 0;
                        break;
                    case Keyboard.KEY_RIGHT:
                            if (direction != 3) newDirection = 1;
                            break;
                    case Keyboard.KEY_DOWN:
                        if (direction != 0) newDirection = 2;
                        break;
                    case Keyboard.KEY_LEFT:
                        if (direction != 1) newDirection = 3;
                        break;
                }
            }
        }

        direction = newDirection;
        isExitRequested = isExitRequested || Display.isCloseRequested();
    }
}
