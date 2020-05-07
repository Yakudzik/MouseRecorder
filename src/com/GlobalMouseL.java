package com;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class GlobalMouseL implements NativeMouseInputListener, MouseWheelListener, NativeMouseWheelListener {
    //the default timing in Windows is 500 ms (half a second).
    private int x=0;
    private int y=0;
    private int clk=0;
    private int button=0;
    private int ringRotated=0;

    public void setRingRotated(int ringRotated) {
        this.ringRotated = ringRotated;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public void setClk(int clk) {
        this.clk = clk;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getClk() {
        return clk;
    }
    public int getXcoordinate() {
        return x;
    }
    public int getYcoordinate() {
        return y;
    }

    public int getRingRotated() {
        return ringRotated;
    }

    public int getButton() {
        return button;
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
       setClk(e.getClickCount());

       // System.out.println("Mouse Clicked: " + e.getClickCount());
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        setButton(e.getButton());
      //  System.out.println("Mouse Pressed: " + e.getButton());
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
      //  System.out.println("Mouse Released: " + e.getButton());
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        setX(e.getX());
        setY(e.getY());
      //  System.out.println(" Бабах на клетке "+getXX());
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        //System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
      //  System.out.println("Mosue : " + e.getScrollType());
    }

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        setRingRotated(e.getWheelRotation());
      //  System.out.println("Mosue Wheel Moved: " + e.getWheelRotation());
    }
}
