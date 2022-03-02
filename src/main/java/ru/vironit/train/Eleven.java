package ru.vironit.train;

public class Eleven {

    public static int value = 0;
    static IncrementInterrupt increment;

    public static void main(String[] args) {
        increment = new IncrementInterrupt();
        System.out.print("Value = ");
        increment.start();

        for (int i = 1; i <= 3; i++) {
            try {
                Thread.sleep(i*2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            increment.changeAction();
        }

        increment.interrupt();
    }
}

class IncrementInterrupt extends Thread {

    private volatile boolean increment = true;

    public void changeAction() {
        increment = !increment;
    }

    @Override
    public void run() {
//        boolean flag = Thread.currentThread().isInterrupted();
//        System.out.println(flag);
//        boolean flag2 = Thread.interrupted();
//        System.out.println(flag2);
//        boolean flag3 = Thread.currentThread().isInterrupted();
//        System.out.println(flag);

        do {
            if (!Thread.interrupted()) {
                if (increment) Eleven.value++;
                else Eleven.value--;
                System.out.print(Eleven.value + " ");
            } else return;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }

        } while (true);
    }
}
