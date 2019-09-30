public class FileDownloader {
    private String[] url;

    public FileDownloader(String[] url) {
        this.url = url;
    }

    public void download() {
        int countOfUrls = url.length;
        for (int i = 0; i < countOfUrls; i++) {
            new ThreadDownloader(url[i]).start();
        }
    }
}
