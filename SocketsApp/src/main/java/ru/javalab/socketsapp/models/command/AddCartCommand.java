package ru.javalab.socketsapp.models.command;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddCartCommand {
    @JsonProperty("product_id")
    private int productId;

    public AddCartCommand(int productId) {
        this.productId = productId;
    }

    public AddCartCommand() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}