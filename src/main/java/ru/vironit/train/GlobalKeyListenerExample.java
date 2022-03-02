package ru.vironit.train;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import ru.vironit.wave.Seven;


public class GlobalKeyListenerExample implements NativeMouseListener {

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
        if (nativeMouseEvent.getButton() == NativeMouseEvent.BUTTON1) {
            Seven.decrementPosition();
        }
        if (nativeMouseEvent.getButton() == NativeMouseEvent.BUTTON2) {
            Seven.incrementPosition();
        }
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {

    }
}
