package com.example.demo16;

public class DeleteCommand implements TargetCommand{

    TrainerModel model;
    double blob_x, blob_y;
    Blob blob;
    int blob_number;

    public DeleteCommand(TrainerModel model1, double x, double y, int blob_number, Blob b){

        this.model = model1;
        this.blob_x = x;
        this.blob_y = y;
        this.blob = b;
        this.blob_number = blob_number;
    }

    public void redo(){
        int blob_index = model.getBlobs().size()-1;
        Blob temp_blob = model.getBlobs().get(blob_index);
        model.getBlobs().remove(temp_blob);
    }

    public void undo(){
        model.addBlob(blob_x, blob_y);
    }
}
