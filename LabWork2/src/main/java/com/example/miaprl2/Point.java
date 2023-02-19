package com.example.miaprl2;

public class Point {
    double X;
    double Y;
    int pointClass;
    boolean isKernell;
    public Point(double x, double y, int pc) {
        this.X = x;
        this.Y = y;
        this.pointClass = pc;
        this.isKernell = false;
    }
    public Point(double x, double y) {
        this.X = x;
        this.Y = y;
        this.pointClass = 0;
        this.isKernell = false;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public boolean isKernell(){
        return isKernell;
    }
}
