package com.example.demo16;

public class Blob {
    double x,y;
    double r;

    double start_x, start_y;
    double start_radius, end_radius;
    int number=0;

    public Blob(double nx, double ny, int Number) {
        x = nx;
        y = ny;
        r = 50;
        number = Number;
        start_radius = 50;
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


    public Blob duplicate() {

        Blob duplicate_blob = new Blob(this.x, this.y, this.number);
        duplicate_blob.r = this.r;
        duplicate_blob.start_radius = this.start_radius;
        duplicate_blob.end_radius = this.end_radius;
        duplicate_blob.number = this.number;
        duplicate_blob.x = this.x;
        duplicate_blob.y = this.y;
        duplicate_blob.start_x = this.start_x;
        duplicate_blob.start_y = this.start_y;

        return duplicate_blob;
    }
}
