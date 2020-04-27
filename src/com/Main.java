package com;

import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;

class Main {
    public static void main(String[] args) throws AWTException, NativeHookException {
        JFrame frame = new MouseRecordingGUI("Mouse recording");
        frame.setResizable(false);
        frame.setVisible(true);

    }
}
