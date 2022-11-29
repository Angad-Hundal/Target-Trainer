package com.example.demo16;

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
        switch (currentState) {


            case READY -> {
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
                        System.out.println("Press shift to make a blob");
                        iModel.selection.clear();
                    }
                }
            }
        }
    }

    public void handleDragged(MouseEvent event) {

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
                System.out.println("Delete pressed");
            }
        }

    }

    public void handleKeyReleased(KeyEvent keyEvent){
        key_pressed = " ";
    }
}
