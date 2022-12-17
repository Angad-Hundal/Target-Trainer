package com.example.demo16;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    StackPane root;
    InteractionModel iModel;


    public void start(Stage stage) throws IOException {

        TrainerView view = new TrainerView();

        TargetTrainerView view2 = new TargetTrainerView(); // TARGET TRAINER
        //ReportView view3 = new ReportView();

        TrainerController controller = new TrainerController();
        TargetTrainerController controller2 = new TargetTrainerController();

        TrainerModel model = new TrainerModel();
        //InteractionModel iModel = new InteractionModel();
        iModel = new InteractionModel();

        view.setModel(model);
        view.setController(controller);
        view.setInteractionModel(iModel);
        controller.setModel(model);
        controller.setInteractionModel(iModel);


        // TARGET TRAINER
        controller2.setModel(model);
        controller2.setIModel(iModel);
        view2.setIModel(iModel);
        view2.setModel(model);
        view2.setController(controller2);


        model.addSubscriber(view);
        iModel.addSubscriber(view);

        iModel.addAppSubscribers(view2);    // TARGET TRAINER
        //iModel.addAppSubscribers(this);     // THIS ONE ALSO


        InteractionModel.AppMode a = iModel.current_view_state;

        if (a.equals(InteractionModel.AppMode.EDIT)){
            root = new StackPane(view);
        }

        else if (a.equals(InteractionModel.AppMode.TEST)){
            root = new StackPane(view2);
        }


        //root = new StackPane(view);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Target Trainer");
        stage.show();

        scene.setOnKeyPressed(controller::handleKeyPressed);
        scene.setOnKeyReleased(controller::handleKeyReleased);

    }

//    public void appModelChanged(){
//
//        current_app_model();
//    }

//    public InteractionModel.AppMode current_app_model(){
//
//        return iModel.current_view_state;
//    }

    public static void main(String[] args) {
        launch();
    }
}