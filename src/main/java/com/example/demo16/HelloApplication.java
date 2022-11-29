package com.example.demo16;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    public void start(Stage stage) throws IOException {


        TrainerView view = new TrainerView();
        TrainerController controller = new TrainerController();
        TrainerModel model = new TrainerModel();
        InteractionModel iModel = new InteractionModel();

        view.setModel(model);
        view.setController(controller);
        view.setInteractionModel(iModel);
        controller.setModel(model);
        controller.setInteractionModel(iModel);
        model.addSubscriber(view);
        iModel.addSubscriber(view);

        StackPane root = new StackPane(view);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Target Trainer");
        stage.show();

        scene.setOnKeyPressed(controller::handleKeyPressed);
        scene.setOnKeyReleased(controller::handleKeyReleased);

    }

    public static void main(String[] args) {
        launch();
    }
}