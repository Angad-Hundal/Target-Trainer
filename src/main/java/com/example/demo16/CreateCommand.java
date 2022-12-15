package com.example.demo16;

public class CreateCommand implements TargetCommand{


    TrainerModel model;
    double blob_x, blob_y;
    Blob blob;
    int blob_number;

    public CreateCommand( TrainerModel model1, double x, double y, int blob_number, Blob b){

        this.model = model1;
        this.blob_x = x;
        this.blob_y = y;
        this.blob_number = blob_number;
        this.blob = b;

    }


    public void redo(){

        model.addBlob(blob_x, blob_y);
    }

    public void undo(){

        System.out.println("UNDO" + blob_number);
        model.deleteBlob(blob);
    }
}
