package ru.kpfu.itis.javalabmessagequeue.consumer;

public interface Consumer<T> {
    void accept(T t);
}
