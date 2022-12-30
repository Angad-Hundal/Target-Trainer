package com.example.demo16;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TrainerView extends StackPane implements TrainerModelListener, IModelListener {
    GraphicsContext gc;
    Canvas myCanvas;
    TrainerModel model;
    InteractionModel iModel;
    PixelReader reader; // for checking the offscreen bitmap's colours

    public TrainerView() {
        myCanvas = new Canvas(800, 800);
        gc = myCanvas.getGraphicsContext2D();

        setStyle("-fx-background-color: skyblue;");

        this.getChildren().add(myCanvas);
        //setupOffscreen();  // for lasso
    }

    private void draw() {

        setupOffscreen();

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




        // LASSO DRAWING
        if (reader.getColor((int) iModel.mx, (int) iModel.my).equals(Color.RED)) {

            // mouse is on polygon
            gc.setFill(Color.GREEN);
        } else {

            // mouse is outside polygon
            gc.setFill(Color.PURPLE);
        }


        // draw user path (points during creation, filled path when finished)
        // path not completed and not released
        if (!iModel.pathComplete && !iModel.released) {
            gc.setFill(Color.GRAY);
            iModel.points.forEach(p -> gc.fillOval(p.getX()-3,p.getY()-3,6,6));
        }

        // path not completed but released
        else if (!iModel.pathComplete && iModel.released){
            //System.out.println("PATH NOT COMPLETED BUT MOUSE RELEASED");
        }

        else {

            gc.setFill(Color.RED);
            gc.beginPath();

            gc.moveTo(iModel.points.get(0).getX() , iModel.points.get(0).getY());
            ArrayList<Double> initial = new ArrayList<>();
            double initialx = iModel.points.get(0).getX();
            double initialy = iModel.points.get(0).getY();

            initial.add(0, 0.0);
            initial.add(1, 0.0);
            initial.set(0,initialx);
            initial.set(1,initialy);

            gc.setLineWidth(2);

            iModel.points.forEach(p -> {
                gc.lineTo(p.getX(),p.getY());
                gc.strokeLine(initial.get(0),initial.get(1), p.getX(), p.getY());

                initial.set(0, p.getX());
                initial.set(1, p.getY());
            });

            gc.closePath();
        }


        // RECTANGLE DEALING
        if (!iModel.released){

            // green to red
            gc.setStroke(Color.RED);
            gc.strokeLine(iModel.rx, iModel.ry, iModel.rx, iModel.cur_rect_y);
            gc.strokeLine(iModel.rx, iModel.cur_rect_y, iModel.cur_rect_x, iModel.cur_rect_y);
            gc.strokeLine(iModel.cur_rect_x, iModel.cur_rect_y, iModel.cur_rect_x, iModel.ry);
            gc.strokeLine(iModel.cur_rect_x, iModel.ry, iModel.rx, iModel.ry);


            // green to red
            gc.setFill(Color.GREEN);

        }


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



    private void setupOffscreen() {

        // offscreen bitmap for checking 'contains' on an oddly-shaped polygon
        Canvas checkCanvas = new Canvas(800, 800);
        GraphicsContext checkGC = checkCanvas.getGraphicsContext2D();
        checkGC.clearRect(0, 0, checkCanvas.getWidth(), checkCanvas.getHeight());



        model.getBlobs().forEach(b -> {

            if (iModel.isSelected(b)) {
                checkGC.setFill(Color.PURPLE);


            } else {
                checkGC.setFill(Color.STEELBLUE);
            }
            checkGC.fillOval(b.x - b.r, b.y - b.r, b.r * 2, b.r * 2);


            // write the blob number
            checkGC.setStroke(Color.BLACK);
            int blob_number = model.getBlobs().indexOf(b) + 1 ;
            String a = String.valueOf(Integer.valueOf(blob_number));
            checkGC.strokeText(a, b.x, b.y);
        });

        checkGC.setStroke(Color.GRAY);

        checkGC.strokeOval(iModel.getCursorX() - iModel.getCursorRadius(),
                iModel.getCursorY() - iModel.getCursorRadius(),
                iModel.getCursorRadius() * 2, iModel.getCursorRadius()*2);




        // path completed and released
        if (iModel.pathComplete && iModel.released){


            //System.out.println("path completed and released ");
            checkGC.setFill(Color.RED);
            checkGC.beginPath();

            checkGC.moveTo(iModel.points.get(0).getX() , iModel.points.get(0).getY());
            ArrayList<Double> initial = new ArrayList<>();
            double initialx = iModel.points.get(0).getX();
            double initialy = iModel.points.get(0).getY();

            initial.add(0, 0.0);
            initial.add(1, 0.0);
            initial.set(0,initialx);
            initial.set(1,initialy);

            checkGC.setLineWidth(2);

            iModel.points.forEach(p -> {
                checkGC.lineTo(p.getX(),p.getY());
                checkGC.strokeLine(initial.get(0),initial.get(1), p.getX(), p.getY());

                initial.set(0, p.getX());
                initial.set(1, p.getY());
            });

            checkGC.closePath();
            checkGC.fill();


        }


        WritableImage buffer = checkCanvas.snapshot(null, null);
        reader = buffer.getPixelReader();
        ArrayList<Double> a = new ArrayList<>();
        a.add(0,0.0);


        model.getBlobs().forEach(b -> {

            a.set(0,reader.getColor((int) b.x, (int) b.y).getRed());

            if (a.get(0) == 1.0){
                iModel.selection.add(b);
            }
        });

    }
}
