package com.dawei;

import com.dawei.service.ArticlePipeline;
import com.dawei.service.SpiderService;
import com.dawei.utils.CronJobFactory;

import com.dawei.utils.DateUtil;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Spider;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;



public class ArticleApplication {

    private static Logger logger = Logger.getLogger(ArticleApplication.class);

    public static void main( String[] args ) {

        // 凌晨2点启动定时器，抓取前一天数据
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1, new CronJobFactory());
//        long initialDelay = 0;
//        long delay = 1;
////        System.out.println("11111111111");
////        long oneDay = 24 * 60 * 60 * 1000;
////        long initDelay = getTimeMillis("02:00:00") - System.currentTimeMillis();
////        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
//
//        try {
//            ScheduledFuture future = scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("ThreadName: " + Thread.currentThread().getName() + ", date: " + new Date());
//                    Spider.create(new SpiderService())
//                            .addUrl("http://www.cnipa.gov.cn/zscqgz/index.htm")
//                            .addPipeline(new ArticlePipeline())
//                            .thread(1)
//                            .run();
////                }
////            }, initialDelay, delay, TimeUnit.MINUTES);
//                }
//            }, initialDelay, delay, TimeUnit.MINUTES);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            logger.error("threadname:" + Thread.currentThread().getName());
            logger.error(" ArticleApplication start: " + DateUtil.parseDate(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Spider.create(new SpiderService())
                            .addUrl("http://www.cnipa.gov.cn/zscqgz/index.htm")
                            .addPipeline(new ArticlePipeline())
                            .thread(1)
                            .run();
    }
}
