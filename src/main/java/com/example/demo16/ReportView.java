package com.example.demo16;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

public class ReportView extends StackPane {


    InteractionModel iModel;
    TrainerModel model;

    NumberAxis xAxis;
    NumberAxis yAxis;
    ScatterChart<Number, Number> scatter_chart;


    public ReportView(){

        StackPane sp = new StackPane();

        xAxis = new NumberAxis();
        xAxis.setLabel("ID");

        yAxis = new NumberAxis();
        yAxis.setLabel("MT");


        scatter_chart = new ScatterChart<>(xAxis, yAxis);


        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        series.setName("Targeting Performance");

        series.getData().add(new XYChart.Data<>(1, 2));
        series.getData().add(new XYChart.Data<>(3, 4));
        series.getData().add(new XYChart.Data<>(5, 6));



        iModel.trial_record.forEach(r -> {

            int current = iModel.currentBlob;
            int prev = iModel.currentBlob - 1;

            Blob current_blob = model.getBlobs().get(current);
            Blob prev_blob = model.getBlobs().get(prev);

            double dist = Math.sqrt ((current_blob.x - prev_blob.x)*(current_blob.x - prev_blob.x) + (current_blob.y - prev_blob.y)*(current_blob.y - prev_blob.y));
            double id = Math.log(dist/2*(current_blob.r) + 1 );


            series.getData().add(new XYChart.Data<>(r.trialID, r.elapsed_time));

        });

        scatter_chart.getData().add(series);

        sp.getChildren().add(scatter_chart);
        this.getChildren().add(sp);
    }

    public void setiModel(InteractionModel imodel){
        this.iModel = imodel;
    }

}
