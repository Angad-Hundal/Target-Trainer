package com.example.demo16;

import javafx.scene.layout.StackPane;

public class MainUI extends StackPane implements AppModeListener{

    public MainUI() {

//        TrainerModel model = new TrainerModel();
//        TrainerController controller = new TrainerController();
//        TrainerView view = new TrainerView();
//
//
//        TargetTrainerView view2 = new TargetTrainerView(); // TARGET TRAINER
//
//        InteractionModel iModel = new InteractionModel();
//
//        //model.imodel = iModel;
//        model.setInteractionModel(iModel);
//        model.imodel= iModel;
//        controller.setModel(model);
//        view.setModel(model);
//        controller.setIModel(iModel);
//        view.setIModel(iModel);
//        model.addSubscriber(view);
//        iModel.addSubscriber(view);
//
//        iModel.addAppSubscribers(view2);    // TARGET TRAINER
//
//        view.setController(controller);
//
//
//        if (iModel.current_view_state.equals(InteractionModel.AppMode.EDIT)){
//            this.getChildren().add(view);
//        }
//
//        else {
//            this.getChildren().add(view2);
//        }
//
//        //this.getChildren().add(view);
    }

    public void ui(){


        TrainerModel model = new TrainerModel();
        TrainerController controller = new TrainerController();
        TrainerView view = new TrainerView();


        TargetTrainerView view2 = new TargetTrainerView(); // TARGET TRAINER

        InteractionModel iModel = new InteractionModel();

        //model.imodel = iModel;
        model.setInteractionModel(iModel);
        model.imodel= iModel;
        controller.setModel(model);
        view.setModel(model);
        controller.setIModel(iModel);
        view.setIModel(iModel);
        model.addSubscriber(view);
        iModel.addSubscriber(view);

        iModel.addAppSubscribers(view2);    // TARGET TRAINER
        iModel.addAppSubscribers(this);

        view.setController(controller);


        if (iModel.current_view_state.equals(InteractionModel.AppMode.EDIT)){
            this.getChildren().add(view);
        }

        else {
            this.getChildren().add(view2);
        }

        //this.getChildren().add(view);
    }

    public void appModelChanged(){
        ui();
        System.out.println("CHANGED");
    }
}
