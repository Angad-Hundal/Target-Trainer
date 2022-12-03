package com.example.demo16;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.lang.Math;

import java.util.List;

public class TrainerController {
    TrainerModel model;
    InteractionModel iModel;
    double prevX, prevY;
    double dX, dY;
    String key_pressed=" ";



    enum State {READY, PREPARE_CREATE, DRAGGING}

    State currentState = State.READY;

    public TrainerController() {

    }

    public void setModel(TrainerModel newModel) {
        model = newModel;
    }

    public void setIModel(InteractionModel newIModel) {
        iModel = newIModel;
    }

    public void handleMoved(MouseEvent event) {
        iModel.setCursor(event.getX(), event.getY());
    }

    public void handlePressed(MouseEvent event) {

        iModel.released = false;
        switch (currentState) {


            case READY -> {

                // blobs coming under the area
                List<Blob> hitList = model.areaHit(event.getX(), event.getY(), iModel.getCursorRadius());

                if (hitList.size() > 0) {


                    if (event.isControlDown()) {
                        iModel.select(hitList);
                    }


                    else {
                        if (!iModel.allSelected(hitList)) {
                            iModel.clearSelection();
                            iModel.select(hitList);
                        }
                    }
                    prevX = event.getX();
                    prevY = event.getY();
                    currentState = State.DRAGGING;
                }
                // else MAKE a BLOB



                else {

                    if (key_pressed.equals("Shift")){
                        currentState = State.PREPARE_CREATE;
                    }

                    else{
                        iModel.selection.clear();
                    }
                }
            }
        }


        iModel.points.clear();
        iModel.released = false;
        iModel.pathComplete = false;
        iModel.points.add(new Point2D(event.getX(), event.getY()));


        // RECTANGLE SELECTION
        iModel.rx = event.getX();
        iModel.ry = event.getY();

    }

    public void handleDragged(MouseEvent event) {
        iModel.released = false;

        iModel.setCursor(event.getX(), event.getY());
        switch (currentState) {


            case PREPARE_CREATE -> {
                currentState = State.READY;
            }

            case DRAGGING -> {

                if (key_pressed.equals("Shift")){

                    dX = event.getX() - prevX;
                    prevX = event.getX();

                    iModel.selection.forEach(b -> {

                        if (Math.abs(dX+b.r) < 5){
                            b.r = b.r;
                        }

                        else{
                            b.r = Math.abs(dX + b.r);
                        }

                    });


                }

                else{

                    dX = event.getX() - prevX;
                    dY = event.getY() - prevY;
                    prevX = event.getX();
                    prevY = event.getY();
                    model.moveBlobs(iModel.getSelection(), dX, dY);
                }

            }


        }

        if (iModel.pathComplete && iModel.released){
            iModel.points.clear();
        }

        if (!iModel.pathComplete){
            //iModel.points.add(new Point2D(event.getX(), event.getY()));
        }
        iModel.points.add(new Point2D(event.getX(), event.getY()));


        if (!iModel.released){
            iModel.cur_rect_x = event.getX();
            iModel.cur_rect_y = event.getY();
        }

        else{
            //iModel.released = true;
            iModel.rx = 0;
            iModel.ry = 0;
            iModel.cur_rect_x = 0;
            iModel.cur_rect_y = 0;
            iModel.points.clear();
        }

    }

    public void handleReleased(MouseEvent event) {

        switch (currentState) {

            case PREPARE_CREATE -> {
                model.addBlob(event.getX(), event.getY());
                currentState = State.READY;
            }

            case DRAGGING -> {
                //iModel.unselect();
                currentState = State.READY;
            }
        }

        if ((iModel.points.get(0).getX() <= iModel.points.get(iModel.points.size()-1).getX() + 50.0 &&  iModel.points.get(0).getX() >= iModel.points.get(iModel.points.size()-1).getX() - 50.0) && (iModel.points.get(0).getY() <= iModel.points.get(iModel.points.size()-1).getY() + 50.0 && iModel.points.get(0).getY() >= iModel.points.get(iModel.points.size()-1).getY() - 50.0)){
            iModel.pathComplete = true;
        }
        else {
            iModel.pathComplete = false;
            //iModel.released = true;
            iModel.points.clear();
        }

        iModel.released = true;
//        iModel.rx = 0;
//        iModel.ry = 0;
//        iModel.cur_rect_x = 0;
//        iModel.cur_rect_y = 0;

    }


    public void setInteractionModel(InteractionModel newModel) {
        iModel = newModel;
    }


    public void handleKeyPressed(KeyEvent keyEvent) {

        switch (keyEvent.getCode()) {

            case G -> {
                System.out.println("G pressed");
            }

            case SHIFT -> {
                System.out.println("Shift pressed");

                if (keyEvent.isControlDown()) {
                    System.out.println("shift");
                }

                key_pressed = "Shift";
            }

            case DELETE -> {
                key_pressed = "Delete";

                if (iModel.selection.size()>0){

                    iModel.selection.forEach(b -> {
                        // for each of the selected blob
                        model.getBlobs().remove(b);
                        // remove them from blobs
                    });
                    iModel.selection.clear();
                }
            }
        }
    }

    public void handleKeyReleased(KeyEvent keyEvent){
        key_pressed = " ";
    }
}
