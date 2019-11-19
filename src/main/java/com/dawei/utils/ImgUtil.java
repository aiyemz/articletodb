package com.dawei.utils;

import com.dawei.ArticleApplication;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

public class ImgUtil {

    private static Logger logger = Logger.getLogger(ImgUtil.class);

    private static CloseableHttpClient httpClient = HttpClients.createDefault();
    private static HttpPost httpPost;
    private static final String SUBSTR = "<script type=\"text/javascript\">window.parent.OnUploadCompleted(0, '";

    static {
        Properties properties = PropertiesUtil.loadPropertyFile("systemenv.properties");
        httpPost = new HttpPost(properties.getProperty("url"));//建立HttpPost对象,改成自己的地址
    }

    private ImgUtil() {

    }

    public static String uploadImg(String imgPath) {

        String resPath = "";
        File file = new File(imgPath);
        if(!file.exists()){
            logger.error("文件不存在:" + imgPath);
            return resPath;
        }
        FileBody bin = new FileBody(file, ContentType.create("image/png", Consts.UTF_8));//创建图片提交主体信息
        HttpEntity entity = MultipartEntityBuilder
                .create()
                .setCharset(Charset.forName("utf-8"))
                .addPart("file", bin)//添加到请求
                .build();
        httpPost.setEntity(entity);
        HttpResponse response= null;//发送Post,并返回一个HttpResponse对象
        try {
            response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode()==200) {//如果状态码为200,就是正常返回
                String result = EntityUtils.toString(response.getEntity());
                if (result.indexOf(SUBSTR)>=0) {
                    result = result.substring(result.indexOf(SUBSTR)+SUBSTR.length());
                    if (result.indexOf("',")>=0) {
                        result = result.substring(0, result.indexOf("',"));
                        resPath = "<p><img src=\""+result+"\" style=\"width:200px\" /></p>";
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
        logger.error("resPath:" + resPath);
        return resPath;
    }

    public static boolean downLoadImg(String url,String path ) {
        boolean flag = true;
        FileOutputStream fos = null;
        DataInputStream dis = null;
        ByteArrayOutputStream bos = null;
        try {
            URL _url = new URL(url);
            dis = new DataInputStream(_url.openStream());
            bos = new ByteArrayOutputStream();
            fos = new FileOutputStream(path);

            byte[] buffer = new byte[1024];
            int length;

            while ((length=dis.read(buffer))>0) {
                bos.write(buffer,0, length);
            }
            fos.write(bos.toByteArray());

        } catch (MalformedURLException e) {
            flag = false;
            e.printStackTrace();
        } catch (IOException e) {
            flag =false;
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
}
