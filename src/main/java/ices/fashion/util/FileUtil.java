package ices.fashion.util;

import com.qiniu.util.Auth;
import ices.fashion.constant.QiniuCloudConst;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.*;

public class FileUtil {

    public static void download(String targetUrl) {
        String downloadUrl = getDownloadUrl(targetUrl);

        String filePath = System.getProperty("user.dir") + "\\img\\";
        System.out.println(filePath);
        downloadFile(downloadUrl, filePath);
    }

    public static void downloadFile(String url, String filePath) {
        OkHttpClient client = new OkHttpClient();
        System.out.println(url);
        Request req = new Request.Builder().url(url).build();
        Response resp = null;
        try {
            resp = client.newCall(req).execute();
            System.out.println(resp.isSuccessful());
            if (resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream is = body.byteStream();
                byte[] data = readInputStream(is);
                File img = new File(filePath + "test.png");
                FileOutputStream fops = new FileOutputStream(img);
                fops.write(data);
                fops.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unexpected code" + resp);
        }
    }

    public static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        byte[] buff  = new byte[1024 * 2];
        int len = 0;
        try {
            while ((len = is.read(buff)) != -1) {
                writer.write(buff, 0, len);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toByteArray();
    }



    public static String getDownloadUrl(String targetUrl) {
        Auth auth = Auth.create(QiniuCloudConst.ACCESS_KEY, QiniuCloudConst.SECRET_KEY);
        return auth.privateDownloadUrl(targetUrl);
    }
}
