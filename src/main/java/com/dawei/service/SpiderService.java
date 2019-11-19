package com.dawei.service;

import com.dawei.utils.DateUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SpiderService implements PageProcessor {

    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        if (page.getUrl().regex("http://www\\.cnipa\\.gov\\.cn/zscqgz/[0-9]{7}\\.htm").match()) {
//        if (page.getUrl().regex("http://www\\.cnipa\\.gov\\.cn\\twzb/dsljshzscqgjltyc/").match()) {
            page.putField("title", page.getHtml().xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/text()").toString()); // 标题
            page.putField("coverpicture",page.getHtml().xpath("//*[@id=\"printContent\"]/table/tbody/tr/td/p[1]/img/@src").toString()); // 标题图片
            page.putField("abst", page.getHtml().xpath("/html/body/div/div/div/div[3]/div[2]/div[2]/text()").toString()); // 摘要
            page.putField("content", page.getHtml().xpath("//*[@id=\"printContent\"]").all().toString()); // 文章内容
            page.putField("createTime", page.getHtml().xpath("/html/body/div/div/div/div[3]/div[2]/span[1]/text()").toString()); // 发布时间
        }
        // 列表页
        else {
            // 文章url
            try {
                List<String> spiderList = new ArrayList<>();
                List<String> list = page.getHtml().xpath("div[@class='index_articl_list']/ul/li/a/@href").all();
                List<String> publishTimes = page.getHtml().xpath("div[@class='index_articl_list']/ul/li/span/text()").all();
                if (publishTimes.size()>0) {
                    List<String> oldPublishTimes = new ArrayList<>();
                    oldPublishTimes.addAll(publishTimes);
                    Collections.sort(publishTimes);
                    Date beforeDay = DateUtil.getBeforeDay();
                    String maxDate = publishTimes.get(publishTimes.size() - 1);
//                    String curMaxDate = publishTimes.get(publishTimes.size() - 1);
//                     抓取前一天数据
                    if (beforeDay.equals(DateUtil.getDate(maxDate))) {
                        // 主要为了是爬取后面页面，将之后分页的日期与最大值进行比较
//                        if ("".equals(maxDate) || DateUtil.getDate(curMaxDate).compareTo(DateUtil.getDate(maxDate))>=0) {
//                        if ("".equals(maxDate) || curMaxDate.equals(DateUtil.getDate(maxDate))) {
//                            maxDate = curMaxDate;
//                        }
                        for (int i = 0; i < oldPublishTimes.size(); i++) {
                            if (oldPublishTimes.get(i).equals(maxDate)) {
                                spiderList.add(list.get(i));
                            }
                        }
                        page.addTargetRequests(spiderList);
                    }
                    //测试
//                    page.addTargetRequests(list);

                    // 翻页url
                    page.addTargetRequests(page.getHtml().xpath("a[@class='huilan14']/@href").all());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
