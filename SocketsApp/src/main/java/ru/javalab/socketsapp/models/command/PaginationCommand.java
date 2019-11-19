package ru.javalab.socketsapp.models.command;

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
