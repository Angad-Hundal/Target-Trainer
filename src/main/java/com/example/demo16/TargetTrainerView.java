package com.example.demo16;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class TargetTrainerView extends StackPane implements AppModeListener, IModelListener{

    GraphicsContext gc;
    Canvas myCanvas;
    TrainerModel model;
    InteractionModel iModel;

    //int currentBlob;

    public TargetTrainerView() {


        myCanvas = new Canvas(800, 800);
        gc = myCanvas.getGraphicsContext2D();

        setStyle("-fx-background-color: lightgreen;");
        System.out.println("REACHING TTV");

        this.getChildren().add(myCanvas);

    }


    public void draw(){

        gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());


        if (iModel.currentBlob >= model.getBlobs().size()) {
            iModel.currentBlob = 0;

        }

        else {


            // select the particular blob that we are on
            Blob b = model.getBlobs().get(iModel.currentBlob);
            gc.setFill(Color.STEELBLUE);
            gc.fillOval(b.x - b.r, b.y - b.r, b.r * 2, b.r * 2);


            // Write the number of the blob on screen
            gc.setStroke(Color.BLACK);
            int blob_number = model.getBlobs().indexOf(b) + 1 ;
            String a = String.valueOf(Integer.valueOf(blob_number));
            gc.strokeText(a, b.x, b.y);
        }


    }


    public void appModelChanged(){
        draw();
    }

    @Override
    public void iModelChanged() {
        draw();
    }


    public void setController(TargetTrainerController controller) {

        myCanvas.setOnMousePressed(controller::handlePressed);
        myCanvas.setOnMouseDragged(controller::handleDragged);
        myCanvas.setOnMouseReleased(controller::handleReleased);
        myCanvas.setOnMouseMoved(controller::handleMoved);
        myCanvas.setOnMouseClicked(controller::handleClicked);
    }



    public void setModel(TrainerModel newModel) {
        model = newModel;
    }

    public void setIModel(InteractionModel newIModel) {
        iModel = newIModel;
    }


    public void modelChanged() {
        draw();
    }



}
