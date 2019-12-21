package ru.javalab.socketsapp.models.command;

public class AddProductCommand {
    private String price;
    private String name;

    public AddProductCommand(String price, String name) {
        this.price = price;
        this.name = name;
    }

    public AddProductCommand() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
