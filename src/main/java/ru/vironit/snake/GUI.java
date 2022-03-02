package ru.vironit.snake;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;
import static ru.vironit.snake.Constants.*;

public class GUI {

    private static Cell[][] cells;

    private static void initializeOpenGL() {
        try {
            Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
            Display.setTitle(SCREEN_NAME);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glClearColor(1, 1, 1, 1);
    }

    public static void draw() {
        glClear(GL_COLOR_BUFFER_BIT);

        for (Cell[] line : cells) {
            for (Cell cell : line) {
                drawElement(cell);
            }
        }
    }

    private static void drawElement(Cell cell) {
        if (cell.getSprite() == null) return;

        cell.getSprite().getTexture().bind();

        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(cell.getX(), cell.getY() + cell.getHeight());
        glTexCoord2f(1, 0);
        glVertex2f(cell.getX() + cell.getWidth(), cell.getY() + cell.getHeight());
        glTexCoord2f(1, 1);
        glVertex2f(cell.getX() + cell.getWidth(), cell.getY());
        glTexCoord2f(0,1);
        glVertex2f(cell.getX(), cell.getY());
        glEnd();
    }

    public static void update(boolean have_to_decrease) {
        updateOpenGL();

        for (Cell[] line : cells) {
            for (Cell cell : line)  {
                cell.update(have_to_decrease);
            }
        }
    }

    private static void updateOpenGL() {
        Display.update();
        Display.sync(FPS);
    }

    public static int getState(int x, int y) {
        return cells[x][y].getState();
    }

    public static void setState(int x, int y, int state) {
        cells[x][y].setState(state);
    }

    public static void init() {
        initializeOpenGL();

        cells = new Cell[CELL_COUNT_X][CELL_COUNT_Y];

        Random random = new Random();

        for (int i = 0; i < CELL_COUNT_X; i++) {
            for (int j = 0; j < CELL_COUNT_Y; j++) {
                cells[i][j] = new Cell(i*CELL_SIZE, j*CELL_SIZE, random.nextInt(100) < INITIAL_SPAWN_CHANCE ? - 1 : 0);
            }
        }
    }
}
