package ices.fashion.util;

import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import ices.fashion.constant.QiniuCloudConst;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;

@Slf4j
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static File download(String targetUrl, String fileName) {
        String downloadUrl = getDownloadUrl(targetUrl);
        return downloadFile(downloadUrl, fileName);
    }

    public static File downloadFile(String url, String fileName) {
        File file = null;
        OkHttpClient client = new OkHttpClient();
        System.out.println(url);
        Request req = new Request.Builder().url(url).build();
        Response resp = null;
        try {
            resp = client.newCall(req).execute();
//            System.out.println(resp.isSuccessful());
            if (resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream is = body.byteStream();
                byte[] data = readInputStream(is);
                String filePath = System.getProperty("user.dir") + "\\img\\";
                file = new File(filePath + fileName);
                FileOutputStream fops = new FileOutputStream(file);
                fops.write(data);
                fops.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unexpected code" + resp);
        }
        return file;
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

    public static String concatUrl(String fileName) throws UnsupportedEncodingException {
        String encodeFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        return String.format("%s/%s", QiniuCloudConst.DOMAIN_BUCKET, encodeFileName);
    }

    /**
     * 向 url 发送图像
     * @param multipartFile
     * @param url
     * @return
     */
    public static String uploadFile(MultipartFile multipartFile, String url, String name) {
        if (multipartFile == null && StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            return null;
        }
        // 转成 FileSystemResource 格式
        File excelFile = transfer2file(multipartFile);
        FileSystemResource fileSystemResource = new FileSystemResource(excelFile);
        log.info("post filename: " + fileSystemResource.getFilename());
        // 构造 Post 请求
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> map= new LinkedMultiValueMap<>();
        map.add(name, fileSystemResource);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
        // 发起 Post 请求
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.postForEntity(url, request, String.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        System.out.println(response.getBody());
        //程序结束时，删除临时文件
//		deleteFile(excelFile);
        return response.getBody();
    }

    private static File transfer2file(MultipartFile multfile) {
        // 获取文件名
        String fileName = multfile.getOriginalFilename();
        log.info("original filename: " + fileName);
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 生成临时文件
        File file = createTmpFile(multfile.getOriginalFilename());
        // MultipartFile to File
        try {
//			excelFile = File.createTempFile(fileName, prefix);
            multfile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
//		log.info("filename: " + excelFile);
        return file;
    }

    private static File createTmpFile(String filename) {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "tmp";
        File dir = new File(path);
        if(!dir.exists()){//不存在则创建路径
            dir.mkdirs();
        }
        String filepath = path + "/" + filename;
        File file = new File(filepath);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("filepath: " + filepath);
        return file;
    }

    //将byte数组写入文件
    public static String createFile(String filename, byte[] content) {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "tmp/" + filename;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(content);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public static MultipartFile fileToMultipartFile(File file) throws IOException {
        FileInputStream input = new FileInputStream(file);
        System.out.println(file.getName());
        String contentType = "image/jpeg";
        int len = file.getName().length();
        if (file.getName().startsWith("png", len - 3)) {
            contentType = "image/png";
        }

        return new MockMultipartFile(file.getName(), file.getName(), contentType, input);
    }
}
