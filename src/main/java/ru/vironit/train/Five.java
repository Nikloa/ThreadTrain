package ru.vironit.train;

public class Five {

    public static int value = 0;
    static Increment increment;

    public static void main(String[] args) {
        increment = new Increment();
        System.out.print("Value = ");
        increment.start();

        for (int i = 1; i <= 3; i++) {
            try {
                Thread.sleep(i * 2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            increment.changeAction();
        }
        increment.finish();
    }
}

class Increment extends Thread {
    private volatile boolean increment = true;
    private volatile boolean finish = false;

    public void changeAction() {
        increment = !increment;
    }

    public void finish() {
        finish = true;
    }

    @Override
    public void run() {
        do {
            if (!finish) {
                if (increment) {
                    Five.value++;
                } else {
                    Five.value--;
                }
                System.out.print(Five.value + " ");
            } else return;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }
}
