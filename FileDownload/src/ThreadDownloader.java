import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class ThreadDownloader extends Thread {
    private String urlStr;

    public ThreadDownloader(String url) {
        this.urlStr = url;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(urlStr);
            try (BufferedInputStream bis = new BufferedInputStream(url.openStream());
                 FileOutputStream fis = new FileOutputStream(new File(this.setFileName()))) {
                byte[] buffer = new byte[1000];
                int count = 0;
                while ((count = bis.read(buffer, 0, 1000)) != -1) {
                    fis.write(buffer, 0, count);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + "Smth went wrong");
        }
    }

    private String setFileName() {
        String fileName = this.urlStr.substring(urlStr.lastIndexOf('/') + 1);
        if (fileName.isEmpty()) fileName = this.urlStr.substring(8, urlStr.lastIndexOf("."));
        if (new File(fileName).exists()) {
            String fileNameWithoutType = fileName.substring(0, fileName.lastIndexOf('.') - 1);
            fileName = fileNameWithoutType + "--" + new Random().nextInt() + fileName.substring(fileName.lastIndexOf('.'));
        } return fileName;
    }
}
