package com.example.demo16;

import javafx.geometry.Point2D;
import javafx.scene.image.PixelReader;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {
    List<IModelListener> subscribers;
    List<Blob> selection;
    double areaRadius;
    double cursorX, cursorY;


    // LASSO SELECTION

    double mx, my; // store mouse coordinates
    //PixelReader reader; // for checking the offscreen bitmap's colours
    List<Point2D> points; // points for the user path
    boolean pathComplete;
    boolean released = false;



    // RECTANGLE SELECTION

    double rx, ry;
    double cur_rect_x, cur_rect_y;



    public InteractionModel() {
        subscribers = new ArrayList<>();
        selection = new ArrayList<>();
        areaRadius = 75;


        // lasso
        //setupOffscreen();
        points = new ArrayList<>();
        pathComplete = false;
    }


    public void addSubscriber(IModelListener sub) {
        subscribers.add(sub);
    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.iModelChanged());
    }

    public void select(List<Blob> hitList) {
        hitList.forEach(this::addSubtract);
        notifySubscribers();
    }


    private void addSubtract(Blob b) {
        if (selection.contains(b)) {
            selection.remove(b);
        } else {
            selection.add(b);
        }
    }

    public void clearSelection() {
        selection.clear();
        notifySubscribers();
    }


    public boolean isSelected(Blob b) {
        return selection.contains(b);
    }

    // part 1: add method
    public List<Blob> getSelection() {
        return selection;
    }


    public double getCursorRadius() {
        return areaRadius;
    }
    

    public boolean allSelected(List<Blob> hitList) {
        return selection.containsAll(hitList);
    }


    public void setCursor(double x, double y) {
        cursorX = x;
        cursorY = y;
        notifySubscribers();
    }

    public double getCursorX() {
        return cursorX;
    }


    public double getCursorY() {
        return cursorY;
    }
}

