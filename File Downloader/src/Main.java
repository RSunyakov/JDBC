public class Main {
    public static void main(String[] args) {
        String[] s1 = new String[1];
        s1[0] = "https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html";
        FileDownloader downloader = new FileDownloader(s1);
        downloader.download();
    }
}
