package com.example.demo16;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {
    List<IModelListener> subscribers;
//    Blob selected; // selection used instead to cover multiple selections
    List<Blob> selection;
    double areaRadius;
    double cursorX, cursorY;

    public InteractionModel() {
        subscribers = new ArrayList<>();
        selection = new ArrayList<>();
        areaRadius = 75;
    }

    public void addSubscriber(IModelListener sub) {
        subscribers.add(sub);
    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.iModelChanged());
    }

    // part 2: add method
    public void select(List<Blob> hitList) {
        hitList.forEach(this::addSubtract);
        notifySubscribers();
    }

//    // part 1: add method
//    public void select(Blob b) {
//        addSubtract(b);
//        notifySubscribers();
//    }

    // part 1: add method
    private void addSubtract(Blob b) {
        if (selection.contains(b)) {
            selection.remove(b);
        } else {
            selection.add(b);
        }
    }

//    // part 1: this method now superseded by select()
//    // we can remove this method
//    public void setSelected(Blob b) {
//        selected = b;
//        notifySubscribers();
//    }

    // part 1: add method
    public void clearSelection() {
        selection.clear();
        notifySubscribers();
    }

//    // part 1: this method is now superseded by clearSelection
//    // (we can remove this method)
//    public void unselect() {
//        selected = null;
//        notifySubscribers();
//    }

    // part 1: add method
    public boolean isSelected(Blob b) {
        return selection.contains(b);
    }

    // part 1: add method
    public List<Blob> getSelection() {
        return selection;
    }

//    // part 1: this method is now superseded by getSelection
//    // and by isSelected
//    // (we can remove this method)
//    public Blob getSelected() {
//        return selected;
//    }

    // part 2: add method
    public double getCursorRadius() {
        return areaRadius;
    }
    
    // part 2: add method
    public boolean allSelected(List<Blob> hitList) {
        return selection.containsAll(hitList);
    }

    // part 2: add method
    public void setCursor(double x, double y) {
        cursorX = x;
        cursorY = y;
        notifySubscribers();
    }

    // part 2: add method
    public double getCursorX() {
        return cursorX;
    }

    // part 2: add method
    public double getCursorY() {
        return cursorY;
    }
}

