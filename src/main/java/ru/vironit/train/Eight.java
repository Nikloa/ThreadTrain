package ru.vironit.train;

public class Eight {

    private static volatile boolean wait = true;
    private static int count = 0;

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                wait = false;
            }
        });

        thread.start();
        while (wait) {
            count++;
        }

        System.out.println(count);
    }
}
