package com.example.demo16;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class TrainerView extends StackPane implements TrainerModelListener, IModelListener {
    GraphicsContext gc;
    Canvas myCanvas;
    TrainerModel model;
    InteractionModel iModel;

    public TrainerView() {
        myCanvas = new Canvas(800, 800);
        gc = myCanvas.getGraphicsContext2D();

        setStyle("-fx-background-color: skyblue;");

        this.getChildren().add(myCanvas);
    }

    private void draw() {

        gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());

        model.getBlobs().forEach(b -> {

            if (iModel.isSelected(b)) {
                gc.setFill(Color.PURPLE);


            } else {
                gc.setFill(Color.STEELBLUE);
            }
            gc.fillOval(b.x - b.r, b.y - b.r, b.r * 2, b.r * 2);


            // write the blob number
            gc.setStroke(Color.BLACK);
            int blob_number = model.getBlobs().indexOf(b) + 1 ;
            String a = String.valueOf(Integer.valueOf(blob_number));
            gc.strokeText(a, b.x, b.y);
        });

        gc.setStroke(Color.GRAY);

        gc.strokeOval(iModel.getCursorX() - iModel.getCursorRadius(),
                iModel.getCursorY() - iModel.getCursorRadius(),
                iModel.getCursorRadius() * 2, iModel.getCursorRadius()*2);

    }

    public void setModel(TrainerModel newModel) {
        model = newModel;
    }

    public void setIModel(InteractionModel newIModel) {
        iModel = newIModel;
    }

    @Override
    public void modelChanged() {
        draw();

    }

    @Override
    public void iModelChanged() {
        draw();
    }

    public void setInteractionModel(InteractionModel newIModel) {
        iModel = newIModel;
    }


    public void setController(TrainerController controller) {


        myCanvas.setOnMousePressed(controller::handlePressed);
        myCanvas.setOnMouseDragged(controller::handleDragged);
        myCanvas.setOnMouseReleased(controller::handleReleased);
        myCanvas.setOnMouseMoved(controller::handleMoved);


    }
}
