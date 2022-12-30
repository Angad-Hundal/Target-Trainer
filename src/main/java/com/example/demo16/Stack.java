package com.example.demo16;

import java.util.ArrayList;

public class Stack {

    public ArrayList<TargetCommand> command_stack;


    public Stack(){
        command_stack = new ArrayList<>();
    }


    public void add_stack(TargetCommand command){
        command_stack.add(command);
    }


    public int stack_size(){
        return command_stack.size();
    }


    public TargetCommand remove_stack(){

        TargetCommand a = command_stack.remove(this.stack_size()-1);
        return a;
    }

    public void clear(){
        command_stack.clear();
    }

}
