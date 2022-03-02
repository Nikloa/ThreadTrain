package ru.vironit.train;

public class Two {

    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("First");
                }
            }
        });
        thread.start();
        while (true) {
            System.out.println("Second");
        }
    }
}
