package com.example.demo16;

public class ResizeCommand implements TargetCommand{


    TrainerModel model;
    double blob_x, blob_y;
    Blob blob;
    int blob_number;
    double radius_now;


    public ResizeCommand(TrainerModel model1, double x, double y, int blob_number, Blob b) {
        this.model = model1;
        this.blob = b;
        this.blob_x = x;
        this.blob_y = y;
        this.blob_number = blob_number;
        this.radius_now = b.r;
    }


    public void undo(){
        model.resizeBlob(blob, blob.start_radius);
    }

    public void redo(){
        model.resizeBlob(blob, radius_now);
    }
}
