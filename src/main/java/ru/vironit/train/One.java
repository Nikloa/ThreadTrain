package ru.vironit.train;

public class One {

    static Rnbl rnbl;

    public static void main(String[] args) {
        rnbl = new Rnbl();
        Thread thread = new Thread(rnbl);
        thread.start();
        while (true) {
            System.out.println("First");
        }
    }
}

class Rnbl implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("Second");
        }
    }
}
