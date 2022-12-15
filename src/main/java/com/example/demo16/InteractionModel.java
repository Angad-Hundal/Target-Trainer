package com.example.demo16;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {
    List<IModelListener> subscribers;
    List<Blob> selection;
    double areaRadius;
    double cursorX, cursorY;

    double mx, my; // store mouse coordinates
    //PixelReader reader; // for checking the offscreen bitmap's colours
    List<Point2D> points; // points for the user path
    boolean pathComplete;
    boolean released = false;



    // RECTANGLE SELECTION

    double rx, ry;
    double cur_rect_x, cur_rect_y;


    // UNDO/REDO COMMANDS
    Stack undo_stack, redo_stack;


    // CUT COPY AND PASTE
    TargetClipboard clipboard;



    public InteractionModel() {
        subscribers = new ArrayList<>();
        selection = new ArrayList<>();
        areaRadius = 75;
        clipboard = new TargetClipboard();


        // lasso
        //setupOffscreen();
        points = new ArrayList<>();
        pathComplete = false;
        undo_stack = new Stack();
        redo_stack = new Stack();

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



    // CUT COPY AND PASTE
    public void copyToClipboard() {

        clipboard.copyClipboard(selection);
        notifySubscribers();
    }

    public List<Blob> getClipboard() {
        return clipboard.getClipboard();
    }


}
