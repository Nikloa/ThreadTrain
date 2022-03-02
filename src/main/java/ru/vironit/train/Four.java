package ru.vironit.train;

public class Four {

    static EggVoice thread;

    public static void main(String[] args) {
        thread = new EggVoice();
        System.out.println("Discussion started");
        thread.start();

        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Chicken");
        }

        if (thread.isAlive()) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Egg win!");
        } else {
            System.out.println("Chicken win!");
        }
        System.out.println("Discussion stopped");
    }
}

class EggVoice extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("egg");
        }
    }
}