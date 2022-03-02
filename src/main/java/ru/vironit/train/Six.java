package ru.vironit.train;

public class Six {

    public static void main(String[] args) {
        Thread quoter = new Thread(() -> {
            while (true) {
                System.out.println("1/4");
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread half = new Thread(() -> {
            while (true) {
                System.out.println("1/2");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread second = new Thread(() -> {
            while (true) {
                System.out.println("1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        quoter.start();
        half.start();
        second.start();
    }
}
