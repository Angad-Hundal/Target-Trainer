package com.example.demo16;

import java.util.ArrayList;
import java.util.List;

public class MoveCommand implements TargetCommand{

    TrainerModel model;
    double blob_x, blob_y;
    Blob blob;
    int blob_number;


    public MoveCommand(TrainerModel model1, double x, double y, int blob_number, Blob b) {
        this.model = model1;
        this.blob = b;
        this.blob_x = x;
        this.blob_y = y;
        this.blob_number = blob_number;
    }


    public void redo() {

        System.out.println(blob.start_x);
        System.out.println(blob.start_y);
        model.moveBlob(blob, (blob.start_x - blob_x) * -1, (blob.start_y - blob_y) * -1);
    }
    public void undo() {

        System.out.println(blob.start_x);
        System.out.println(blob.start_y);
        model.moveBlob(blob, (blob.start_x - blob_x) * 1, (blob.start_y - blob_y) * 1);
    }

}
