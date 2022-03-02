package ru.vironit.train;

public class Three {

    static AffableThread thread;

    public static void main(String[] args) {
        thread = new AffableThread();
        thread.start();
        while (true) {
            System.out.println("First");
        }
    }
}

class AffableThread extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println("Second");
        }
    }
}
