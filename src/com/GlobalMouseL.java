package com;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;


public class GlobalMouseL implements NativeMouseInputListener {

    private int x=0;
    private int y=0;
    private int clk=0;

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

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
       setClk(e.getClickCount());

       // System.out.println("Mouse Clicked: " + e.getClickCount());
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
       // System.out.println("Mouse Pressed: " + e.getButton());
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

}
