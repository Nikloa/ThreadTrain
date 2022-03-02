package ru.vironit.snake;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;

public enum Sprite {

    BODY("circle"), FOOD("square");

    private Texture texture;

    private Sprite(String texturename) {
        try {
            this.texture = TextureLoader.getTexture("PNG", new FileInputStream(new File("../../../../resources" + texturename + ".png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Texture getTexture() {
        return this.texture;
    }
}
