package com;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MouseRecordingGUI extends JFrame {
    private JPanel mainGUIpanel;
    private JButton buttonStartRecord;
    private JButton buttonRepeatRecord;
    private JButton buttonStopRecord;
    private JTextField textBoxRepeat;
    private JTextField textBoxDelay;
    private JLabel DelayLable;
    private JLabel LabelXCoordinate;
    private JLabel LabelYCoordinate;
    private JLabel LableTest;
    private JLabel RepeatLable;
    private JProgressBar progressRepeatBar;

    Timer updateTime;
    int DELAY = 150; //time what i get update xy coordinates
    Robot gundam = new Robot();//robot obj. He repeat mouse clicks and movement
    GlobalMouseL clicker = new GlobalMouseL(); //this obj get mouse clicks and xy coordinats
    Random rnd = new Random();

    int rndForRepeatMotionDelay;
    int appRepeat = 1, appDelay = 30000; // save repeat and delay values from jtextField
    String textBoxValue;
    int clkCounter; //click counter


    public MouseRecordingGUI(String title) throws AWTException, NativeHookException {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainGUIpanel);
        this.pack();

        textBoxDelay.setText("30"); //set standard repeats and delay to text Box
        textBoxRepeat.setText("1");

        ArrayList<Integer[]> clicksAndCo = new ArrayList<>(); // list where i save xy in int array int[x][x] when app catch mouse click
        progressRepeatBar.setMinimum(0); // set minimum to progress bar
        progressRepeatBar.setStringPainted(true); // get visiable percentage on progress bar


        buttonStartRecord.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                clkCounter = 0; //if start record again, bring all counters to 0
                clicksAndCo.clear();
                clicker.setClk(0);
                LableTest.setText("0");

                try {
                    GlobalScreen.registerNativeHook(); //Connect RNH get the coordinates from all desktop
                } catch (NativeHookException nativeHookException) {
                    nativeHookException.printStackTrace();
                }

                GlobalScreen.addNativeMouseListener(clicker); // Connect NativeMouseListener - get clicks from all desktop
                GlobalScreen.addNativeMouseMotionListener(clicker); // Connect NativeMouseMotionListener - get xy coordinats from all desktop

                updateTime = new Timer(DELAY, new ActionListener() { //timer update Delay/action (coordinate update)

                    @Override
                    public void actionPerformed(ActionEvent d) {
                        LabelXCoordinate.setText("X - " + String.valueOf(clicker.getXcoordinate())); //get x coordinate
                        LabelYCoordinate.setText("Y - " + String.valueOf(clicker.getYcoordinate()));//get y coordinate
                        LableTest.setText(String.valueOf(clicker.getClk()));//get clicks

                        if (clicker.getClk() > clkCounter) { // if click counter more than last time - add xy to int array AND add this array to List
                            clicksAndCo.add(new Integer[]{clicker.getXcoordinate(), clicker.getYcoordinate()});
                            clkCounter = clicker.getClk(); // get new click counter
                            for (int i = 0; i < clicksAndCo.size(); i++) {
                                System.out.println(Arrays.toString(clicksAndCo.get(i))); // print coordinate to console - check
                            }
                        }
                    }
                });

                updateTime.start(); //start updates

            }
        });

        buttonRepeatRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int r = 0; r < appRepeat; r++) { // loop to repeat app few times

                    int gundamXY[] = new int[2];

                    for (int i = 0; i < clicksAndCo.size(); i++) {
                        gundamXY[0] = clicksAndCo.get(i)[0]; // loop to get xy coordinats from List
                        gundamXY[1] = clicksAndCo.get(i)[1];

                        gundam.mouseMove(gundamXY[0], gundamXY[1]); // move mouse to xy coordinate
                        gundam.mousePress(InputEvent.BUTTON1_DOWN_MASK); //press mouse
                        gundam.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // release mouse

                        rndForRepeatMotionDelay = rnd.nextInt(6); //set random delay for repeat mouse clicks
                        rndForRepeatMotionDelay = rndForRepeatMotionDelay * 100;

                        try {
                            Thread.sleep(1900 + rndForRepeatMotionDelay);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }

                    progressRepeatBar.setValue(r + 1); // get value to progress bar
                    progressRepeatBar.getPercentComplete(); // get percentage to progress bar
                    progressRepeatBar.update(progressRepeatBar.getGraphics()); // upgrade progress bar

                    if (r == appRepeat - 1) //in last time we don't need sleep. If it lats time out from loop
                        break;

                    try {
                        Thread.sleep(appDelay);//default app delay 30seconds
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }
        });

        buttonStopRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime.stop(); //stop timer updating

                try {
                    textBoxValue = textBoxRepeat.getText(); //get value from jtextField
                    appRepeat = Integer.parseInt(textBoxValue);
                    textBoxValue = textBoxDelay.getText();
                    appDelay = Integer.parseInt(textBoxValue) * 1000;

                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(new JFrame(), "Wrong Repeat or Delay format try again");
                }

                try {
                    GlobalScreen.unregisterNativeHook(); //stop receiving coordinates
                } catch (NativeHookException nativeHookException) {
                    nativeHookException.printStackTrace();
                }
                progressRepeatBar.setMaximum(appRepeat); //set maximum value from textbox to progress bar
            }
        });

        buttonStartRecord.setMnemonic('S'); //hotkeys
        buttonRepeatRecord.setMnemonic('R');
        buttonStopRecord.setMnemonic('E');
    }
}