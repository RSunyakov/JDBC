package com.javalab.projectusefiledownloader;

public class Main {
    public static void main(String[] args) {
        FileDownloader downloader = new FileDownloader(args);
        downloader.download();
    }
}
