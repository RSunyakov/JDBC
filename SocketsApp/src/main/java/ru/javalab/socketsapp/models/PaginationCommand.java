package ru.javalab.socketsapp.models;

public class PaginationCommand {
    private int page;

    public PaginationCommand() {
    }

    public PaginationCommand(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
