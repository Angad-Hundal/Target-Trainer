package com.example.demo16;

import java.util.*;
import java.util.stream.Collectors;

public class TrainerModel {
    private List<TrainerModelListener> subscribers;
    private List<Blob> blobs;

    public InteractionModel imodel;


    public TrainerModel() {
        subscribers = new ArrayList<>();
        blobs = new ArrayList<>();
        imodel = new InteractionModel();
    }

    public void setInteractionModel(InteractionModel interactionModel){
        imodel = interactionModel;
    }

    public void addBlob(double x, double y) {

        blobs.add(new Blob(x,y, blobs.size()));
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


    public void moveBlobs(List<Blob> selection, double dX, double dY) {
        selection.forEach(b -> b.move(dX,dY));
        notifySubscribers();
    }


    public List<Blob> areaHit(double x, double y, double cursorRadius) {
        return blobs.stream().filter(b -> b.contains(x,y,cursorRadius)).collect(Collectors.toList());
    }



    public void resizeBlob(Blob b, double new_r){

        b.r = new_r;

        blobs.forEach(blob -> {

            if (blob.number == b.number){
                blob.r = new_r;
            }
        });

        notifySubscribers();
    }


    public List<Blob> areaHit2(double x, double y, double cursorRadius) {
        List<Blob> hitList = new ArrayList<>();
        blobs.forEach(b -> {
            if (b.contains(x,y,cursorRadius)) hitList.add(b);
        });
        return hitList;
    }

    public boolean rectContains(double ptx, double pty, double rectx, double recty, double rectlength, double rectwidth ){

        if (ptx <= rectx + rectlength & ptx >= rectx & pty >= recty & pty <=recty + rectwidth){
            System.out.println("TRUE");
            return true;
        }
        else {
            System.out.println("False");
            return false;
        }

    }

    public void deleteBlob(Blob b){
        blobs.remove(b);

        imodel.selection.remove(b);
        notifySubscribers();
    }

}
