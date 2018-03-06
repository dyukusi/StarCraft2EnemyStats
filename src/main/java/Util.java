import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.*;
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

    static String httpGet(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            return new InputStreamReader((InputStream) request.getContent()).toString();
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

    public static String httpPost(String strPostUrl, String strContentType, String formParam){
        HttpURLConnection con = null;
        StringBuffer result = new StringBuffer();

        try {

            URL url = new URL(strPostUrl);
            con = (HttpURLConnection) url.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", strContentType);

            String parameterString = new String(formParam);
            PrintWriter printWriter = new PrintWriter(con.getOutputStream());
            printWriter.print(parameterString);
            printWriter.close();

            con.connect();

            // HTTPレスポンスコード
            final int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                // 通信に成功した
                // テキストを取得する
                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if(null == encoding){
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                String line = null;
                // 1行ずつテキストを読み込む
                while((line = bufReader.readLine()) != null) {
                    result.append(line);
                }
                bufReader.close();
                inReader.close();
                in.close();
            }else{
                System.out.println(status);
            }

        }catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (con != null) {
                // コネクションを切断
                con.disconnect();
            }
        }

        return result.toString();
    }
}
