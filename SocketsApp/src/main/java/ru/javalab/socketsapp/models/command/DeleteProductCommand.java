package ru.javalab.socketsapp.models.command;

public class DeleteProductCommand {
    private int id;

    public DeleteProductCommand(int id) {
        this.id = id;
    }

    public DeleteProductCommand() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
