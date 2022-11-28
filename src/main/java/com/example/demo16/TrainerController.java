package com.example.demo16;

import javafx.scene.input.MouseEvent;

import java.util.List;

public class TrainerController {
    TrainerModel model;
    InteractionModel iModel;
    double prevX, prevY;
    double dX, dY;

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
                //if (model.hitBlob(event.getX(),event.getY())) { // part 2
                //Blob b = model.whichHit(event.getX(),event.getY()); // part 2


                // select all the blobs that come inside the cursor
                List<Blob> hitList = model.areaHit(event.getX(), event.getY(), iModel.getCursorRadius()); // part 2


                if (hitList.size() > 0) { // part 2 //  if something comes under the selected part

                    if (event.isControlDown()) { // part 1
                        //iModel.setSelected(b); // part 1
                        //iModel.select(b); // part 1
                        iModel.select(hitList); // part 2
                    }
                    else { // part 1
                        //if (!iModel.isSelected(b)) { // part 1: only clear if click is on an unselected blob
                        if (!iModel.allSelected(hitList)) { // part 2: only clear if the cursor is not on a selected
                            iModel.clearSelection(); // part 1
                            // iModel.select(b); // part 1
                            iModel.select(hitList); // part 2
                        }
                    }
                    prevX = event.getX();
                    prevY = event.getY();
                    currentState = State.DRAGGING;
                }
                // else MAKE a BLOB
                else {
                    currentState = State.PREPARE_CREATE;
                }
            }
        }
    }

    public void handleDragged(MouseEvent event) {

        iModel.setCursor(event.getX(), event.getY()); // part 2
        switch (currentState) {
            case PREPARE_CREATE -> {
                currentState = State.READY;
            }
            case DRAGGING -> {
                dX = event.getX() - prevX;
                dY = event.getY() - prevY;
                prevX = event.getX();
                prevY = event.getY();
                // model.moveBlob(iModel.getSelected(), dX,dY); // part 1
                model.moveBlobs(iModel.getSelection(), dX, dY); // part 1 (handle moving multiple)
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
                //iModel.unselect(); // part 1 - remove this so selection is persistent
                currentState = State.READY;
            }
        }
    }
}
