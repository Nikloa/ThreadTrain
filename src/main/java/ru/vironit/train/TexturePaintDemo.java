package ru.vironit.train;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TexturePaintDemo extends JPanel {
    public void init() {
        setBackground(Color.white);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        BufferedImage bi = new BufferedImage(5, 5,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D big = bi.createGraphics();
        big.setColor(Color.blue);
        big.fillRect(0, 0, 5, 5);
        big.setColor(Color.lightGray);
        big.fillOval(0, 0, 5, 5);
        Rectangle r = new Rectangle(0, 0, 5, 5);
        g2.setPaint(new TexturePaint(bi, r));

        Rectangle rect = new Rectangle(5,5,200,200);

        g2.fill(rect);
    }

    public static void main(String s[]) {
        JFrame f = new JFrame();
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        TexturePaintDemo p = new TexturePaintDemo();
        f.getContentPane().add("Center", p);
        p.init();
        f.pack();
        f.setSize(new Dimension(250, 250));
        f.show();
    }

}
