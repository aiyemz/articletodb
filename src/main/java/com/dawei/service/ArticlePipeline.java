package com.dawei.service;

import com.dawei.ArticleApplication;
import com.dawei.dao.ActicleDbDaoImpl;
import com.dawei.dao.ArticleDao;
import com.dawei.model.Article;
import com.dawei.utils.ImgUtil;
import com.dawei.utils.PropertiesUtil;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public class ArticlePipeline implements Pipeline {

    private static Logger logger = Logger.getLogger(ArticlePipeline.class);

    @Override
    public void process(ResultItems resultItems, Task task) {

        Map<String, Object> mapResults = resultItems.getAll();
        Iterator<Map.Entry<String, Object>> iter = mapResults.entrySet().iterator();
        Map.Entry<String, Object> entry;
        Article article = new Article();
        while (iter.hasNext()) {
            entry = iter.next();
            try {
                Field field = article.getClass().getDeclaredField(entry.getKey());
                field.setAccessible(true);
                field.set(article, entry.getValue());
                if (entry.getKey().equals("createTime")) {
                    article.setUpdateTime(entry.getValue().toString());
                }
                article.setPlatform("S");
                article.setManagetype(0);
                article.setIsPass("0");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        if (mapResults.size()>0) {
            ArticleDao articleDao = ActicleDbDaoImpl.getInstance();

            String url = article.getCoverpicture();
            if (url != null) {
                if (url.startsWith("../")) {
                    String path = url.substring(url.lastIndexOf("/")+1);
                    url = "http://www.cnipa.gov.cn/" +url.substring(url.indexOf("../")+"../".length());
//                    article.setCoverpicture(getImgPath(url, "e:\\download\\"+path));

                    String imgDir = PropertiesUtil.loadPropertyFile("systemenv.properties").get("imgDir").toString();
                    File file = new File(imgDir);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    article.setCoverpicture(getImgPath(url, imgDir+path));
                } else {
                    logger.error("记录url:" + url);
                }
            }
            logger.error("入库article:" + article);
            articleDao.insert(article);
        }
    }

    private String getImgPath(String url,String path) {
        String res = "";
        // 下载
        if (ImgUtil.downLoadImg(url, path)) {
            // 上传
            res = ImgUtil.uploadImg(path);
        }
        return res;
    }

}
