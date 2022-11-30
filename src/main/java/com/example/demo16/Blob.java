package com.example.demo16;

public class Blob {
    double x,y;
    double r;
    int number=0;

    public Blob(double nx, double ny, int Number) {
        x = nx;
        y = ny;
        r = 50;
        number = Number;
    }

    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    // part 3: add method
    public boolean contains(double cx, double cy, double cr) {
        return dist(cx,cy,x,y) <= (r + cr);
    }

    public boolean contains(double cx, double cy) {
        return dist(cx,cy,x,y) <= r;
    }

    private double dist(double x1,double y1,double x2, double y2) {
        return Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
    }
}
