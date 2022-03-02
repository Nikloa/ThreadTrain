package ru.vironit.wave;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import ru.vironit.train.GlobalKeyListenerExample;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Seven {

    private static int position = 0;

    public static void main(String[] args) throws IOException, InterruptedException {

        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeMouseListener(new GlobalKeyListenerExample());

        while (true) {
            System.out.print("||");
            for (int i = 0; i < position; i++) {
                System.out.print(" ");
            }
            System.out.print("O");
            for (int i = 0; i < 12-position; i++) {
                System.out.print(" ");
            }
            System.out.println("||");
            Thread.sleep(100);
        }
    }

    public static void incrementPosition() {
        if (position < 12) {
            position++;
        }
    }

    public static void decrementPosition() {
        if (position > 0) {
            position--;
        }
    }
}
