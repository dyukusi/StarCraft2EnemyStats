import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;

public class Util {
    static JsonElement getJsonByURL(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            return jp.parse(new InputStreamReader((InputStream) request.getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    static void writeText(String path, String text, boolean shouldOverwrite) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(path, !shouldOverwrite)));
            pw.println(text);
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static long calcHoursBefore(long timestamp) {
        long currentTimeStampSec = System.currentTimeMillis() / 1000;
        return ((currentTimeStampSec - timestamp) / 60) / 60;
    }

    static void downloadImage(String url, String path) {
        try {
            URL urlObject = new URL(url);
            URLConnection conn = urlObject.openConnection();
            InputStream in = conn.getInputStream();

            File file = new File(path);
            FileOutputStream out = new FileOutputStream(file, false);
            int b;
            while((b = in.read()) != -1){
                out.write(b);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyImage(String fromPath, String toPath) {
        try {
            File fileIn = new File(fromPath);
            File fileOut = new File(toPath);

            FileChannel inCh = new FileInputStream(fileIn).getChannel();
            FileChannel outCh = new FileOutputStream(fileOut).getChannel();
            inCh.transferTo(0, inCh.size(), outCh);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
