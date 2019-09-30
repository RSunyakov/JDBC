import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            FileOutputStream fis = new FileOutputStream(new File(String.valueOf(new Random().nextInt())));
            byte[] buffer = new byte[1000];
            int count = 0;
            while ((count = bis.read(buffer,0,1000)) != -1) {
                fis.write(buffer,0,count);
            }
            fis.close();
            bis.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
