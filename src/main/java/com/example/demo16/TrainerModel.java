package com.example.demo16;

import java.util.*;
import java.util.stream.Collectors;

public class TrainerModel {
    private List<TrainerModelListener> subscribers;
    private List<Blob> blobs;

    public TrainerModel() {
        subscribers = new ArrayList<>();
        blobs = new ArrayList<>();
    }

    public void addBlob(double x, double y) {
        blobs.add(new Blob(x,y));
        notifySubscribers();
    }

    public void moveBlob(Blob b, double dx, double dy) {
        b.move(dx,dy);
        notifySubscribers();
    }

    public void addSubscriber(TrainerModelListener sub) {
        subscribers.add(sub);
    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.modelChanged());
    }

    public List<Blob> getBlobs() {
        return blobs;
    }

    public boolean hitBlob(double x, double y) {
        for (Blob b : blobs) {
            if (b.contains(x,y)) return true;
        }
        return false;
    }

    public Blob whichHit(double x, double y) {
        for (Blob b : blobs) {
            if (b.contains(x,y)) return b;
        }
        return null;
    }

    // part 1: add method
    public void moveBlobs(List<Blob> selection, double dX, double dY) {
        selection.forEach(b -> b.move(dX,dY));
        notifySubscribers();
    }

    // part 2: add method (note there are two versions that do the same thing
    // the first uses streams, the second is more traditional)
    public List<Blob> areaHit(double x, double y, double cursorRadius) {
        return blobs.stream().filter(b -> b.contains(x,y,cursorRadius)).collect(Collectors.toList());
    }

    // part 2: alternate method that does not use streams
    public List<Blob> areaHit2(double x, double y, double cursorRadius) {
        List<Blob> hitList = new ArrayList<>();
        blobs.forEach(b -> {
            if (b.contains(x,y,cursorRadius)) hitList.add(b);
        });
        return hitList;
    }

}
