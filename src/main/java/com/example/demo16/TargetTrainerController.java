package com.example.demo16;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class TargetTrainerController {

    TrainerModel model;
    InteractionModel iModel;

    String key_pressed=" ";


    public TargetTrainerController(){

    }

    public void setModel(TrainerModel newModel) {
        model = newModel;
    }

    public void setIModel(InteractionModel newIModel) {
        iModel = newIModel;
    }


    public void handlePressed(MouseEvent event){


    }

    public void handleDragged(MouseEvent event){

    }

    public void handleReleased(MouseEvent event){

    }

    public void handleMoved(MouseEvent mouseEvent) {

    }


    public void handleKeyPressed(KeyEvent keyEvent){

        switch (keyEvent.getCode()) {


            // TARGET TRAINER

            case T -> {

                if (keyEvent.isControlDown()){
                    System.out.println("TARGET VIEW");
                    iModel.current_view_state = InteractionModel.AppMode.TEST;

                    TrialRecord new_trial_record = new TrialRecord();
                    iModel.trial_record.add(new_trial_record);
                }
            }


            case E -> {

                if (keyEvent.isControlDown()){
                    System.out.println("EDITOR VIEW");
                    iModel.current_view_state = InteractionModel.AppMode.EDIT;
                }
            }
        }

    }

    public void handleKeyReleased(KeyEvent keyEvent){
        key_pressed = " ";
    }


    public void handleClicked(MouseEvent mouseEvent) {


        // blob we are currently on
        Blob b = model.getBlobs().get(iModel.currentBlob);

        if ((mouseEvent.getX() <= b.x + b.r) & (mouseEvent.getX() >= b.x - b.r) & (mouseEvent.getY() >= b.y - b.r) & (mouseEvent.getY() <= b.y + b.r)){

            // if selected move to next blob
            iModel.currentBlob++;
        }

        if (iModel.currentBlob == model.getBlobs().size()){

            iModel.current_view_state = InteractionModel.AppMode.REPORT;
            iModel.trial_record.get(iModel.currentBlob).end_time = (int) System.currentTimeMillis();
            iModel.trial_record.get(iModel.currentBlob).elapsed_time = iModel.trial_record.get(iModel.currentBlob).end_time - iModel.trial_record.get(iModel.currentBlob).start_time;

        }
    }
}
