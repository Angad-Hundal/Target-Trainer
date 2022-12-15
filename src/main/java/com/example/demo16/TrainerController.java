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

    boolean undone;



    enum State {READY, PREPARE_CREATE, DRAGGING}

    State currentState = State.READY;

    public TrainerController() {
        undone = false;
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

        if (undone){
            iModel.redo_stack.clear();
            undone = false;
            System.out.println("SUCCESS");
        }

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


                model.getBlobs().forEach(blob -> {

                    blob.start_x = blob.x;
                    blob.start_y = blob.y;
                });
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
                            //iModel.undo_stack.add_stack(new ResizeCommand(model, b.x, b.y, b.number, b));
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

                Blob b = model.getBlobs().get(model.getBlobs().size()-1);
                b.start_x = event.getX();
                b.start_y = event.getY();

                System.out.println("B Number" + b.number);
                iModel.undo_stack.add_stack(new CreateCommand(model, event.getX(), event.getY(), b.number, b));
                currentState = State.READY;
            }

            case DRAGGING -> {
                //iModel.unselect();
                currentState = State.READY;

                iModel.selection.forEach(each_blob -> {
                    iModel.undo_stack.add_stack(new MoveCommand(model, each_blob.x, each_blob.y, each_blob.number, each_blob));

                    if (each_blob.start_radius != each_blob.r){

                        iModel.undo_stack.add_stack(new ResizeCommand(model, each_blob.x, each_blob.y, each_blob.number, each_blob));
                    }
                });

            }
        }

        if ((iModel.points.get(0).getX() <= iModel.points.get(iModel.points.size()-1).getX() + 50.0 &&  iModel.points.get(0).getX() >= iModel.points.get(iModel.points.size()-1).getX() - 50.0) && (iModel.points.get(0).getY() <= iModel.points.get(iModel.points.size()-1).getY() + 50.0 && iModel.points.get(0).getY() >= iModel.points.get(iModel.points.size()-1).getY() - 50.0)){
            iModel.pathComplete = true;
            System.out.println("LASSO CASE");
        }
        else {
            iModel.pathComplete = false;
            //iModel.released = true;
            iModel.points.clear();
            System.out.println("Rectangle case");

            model.getBlobs().forEach(b -> {

                if (model.rectContains(b.x, b.y, iModel.rx, iModel.ry, Math.abs(iModel.rx- iModel.cur_rect_x), Math.abs(iModel.ry - iModel.cur_rect_y))){
                    System.out.println("BLOB INSIDE RECT");
                    iModel.selection.add(b);

                }
            });
        }

        iModel.released = true;

    }


    public void setInteractionModel(InteractionModel newModel) {
        iModel = newModel;
    }


    public void handleKeyPressed(KeyEvent keyEvent) {

        switch (keyEvent.getCode()) {

            case G -> {
                System.out.println("G pressed");
            }


            case Z -> {
                System.out.println("Z pressed");

                if (keyEvent.isControlDown()){

                    // UNDO CASE

                    System.out.println("Control Z UNDO");
                    key_pressed = "CTRLZ";

                    if (iModel.undo_stack.stack_size() != 0){

                        TargetCommand command = iModel.undo_stack.remove_stack();
                        String command_name = command.getClass().getSimpleName();
                        undone = true;

                        switch (command_name){

                            case "CreateCommand" -> {
                                System.out.println("Create Command");
                                // new model, x, y, blob number
                                // number blob
                                iModel.redo_stack.add_stack(command);
                                command.undo();
                            }

                            case "DeleteCommand" -> {
                                System.out.println("Delete Command");
                                iModel.redo_stack.add_stack(command);
                                command.undo();
                            }

                            case "MoveCommand" -> {
                                System.out.println("Move Command");
                                iModel.redo_stack.add_stack(command);
                                command.undo();
                            }

                            case "ResizeCommand" -> {
                                System.out.println("Resize Command");
                                iModel.redo_stack.add_stack(command);
                                command.undo();
                            }
                        }
                    }
                    else{
                        System.out.println("UNDO STACK EMPTY");
                    }
                }

                else {
                    key_pressed = "Z";
                }
            }

            case R -> {


                System.out.println("R pressed");

                if (keyEvent.isControlDown()){

                    // REDO CASE

                    System.out.println("Control R REDO");
                    key_pressed = "CTRLR";

                    if (iModel.redo_stack.stack_size() != 0){

                        TargetCommand command = iModel.redo_stack.remove_stack();
                        String command_name = command.getClass().getSimpleName();

                        switch (command_name){

                            case "CreateCommand" -> {
                                System.out.println("Create Command");
                                System.out.println("CREATE REDO");
                                command.redo();
                            }

                            case "DeleteCommand" -> {
                                System.out.println("Delete Command");
                                System.out.println("DELETE REDO");
                                command.redo();
                            }

                            case "MoveCommand" -> {
                                System.out.println("Move Command");
                                System.out.println("MOVE REDO");
                                command.redo();
                            }

                            case "ResizeCommand" -> {
                                System.out.println("Resize Command");
                                System.out.println("RESIZE REDO");
                                command.redo();
                            }
                        }
                    }
                }

                else {
                    key_pressed = "R";
                }
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

                        iModel.undo_stack.add_stack(new DeleteCommand(model, b.x, b.y, b.number, b));
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