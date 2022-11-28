package com.example.demo16;

import javafx.scene.layout.StackPane;

public class MainUI extends StackPane {

    public MainUI() {

        TrainerModel model = new TrainerModel();
        TrainerController controller = new TrainerController();
        TrainerView view = new TrainerView();
        InteractionModel iModel = new InteractionModel();

        controller.setModel(model);
        view.setModel(model);
        controller.setIModel(iModel);
        view.setIModel(iModel);
        model.addSubscriber(view);
        iModel.addSubscriber(view);

        view.setController(controller);

        this.getChildren().add(view);
    }
}
