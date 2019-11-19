package com.dawei.dao;

import com.dawei.ArticleApplication;
import com.dawei.model.Article;
import com.dawei.utils.DBUtilsHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

public class ActicleDbDaoImpl implements ArticleDao{

    private static Logger logger = Logger.getLogger(ActicleDbDaoImpl.class);

    private DBUtilsHelper dbh;
    private QueryRunner runner;

    static class ActicleDbDaoImplHolder {
       public static ActicleDbDaoImpl acticleDbDaoImpl = new ActicleDbDaoImpl();
    }

   private ActicleDbDaoImpl()  {
        try {
            dbh = new DBUtilsHelper();
            runner = dbh.getRunner();
            String sql = "create sequence T_Article_ID_SEQ MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 81 CACHE 20 NOORDER NOCYCLE";
            runner.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ActicleDbDaoImpl getInstance() {
        return ActicleDbDaoImplHolder.acticleDbDaoImpl;
    }

    @Override
    public int insertBatch(List<Article> articles) {
        return 0;
    }

    @Override
    public int insert(Article article) {
        int res = 0;
        Field[] fields = article.getClass().getDeclaredFields();
        Object[] parmas = new Object[fields.length];
        for (int i=0; i<fields.length; i++) {
           try {
               fields[i].setAccessible(true);
               if (fields[i].getName().equals("createTime") || fields[i].getName().equals("updateTime")) {
                   String time = fields[i].get(article).toString();
                   if (time.startsWith("发布时间：")) {
                       time = time.substring(time.indexOf("发布时间：") + "发布时间：".length());
                       fields[i].set(article,time);
                   }
               }
               parmas[i] = fields[i].get(article);
           } catch (IllegalAccessException e) {
                e.printStackTrace();
                logger.error(e);
           }

        }

       String sql = "insert into t_articlemanage (TOPICID,title,coverpicture,abstract,content,createtime, updatetime,managetype,platform,ispass) " +
               "values( T_Article_ID_SEQ.NEXTVAL,?,?,?,?,to_date(?,'YYYY-MM-DD'),to_date(?,'YYYY-MM-DD'),?,?,?)";

        try {
            int updateNum = runner.update(sql, parmas);
            logger.error("更新数据：" + updateNum);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                dbh.close();
            } catch (SQLException e) {
                logger.error("连接池关闭异常！");
                e.printStackTrace();
            }
        }

        return res;
    }
}
