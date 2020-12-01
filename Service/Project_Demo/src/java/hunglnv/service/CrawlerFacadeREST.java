/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.service;

import hunglnv.crawler.BulbCrawler;
import hunglnv.crawler.FlowerCategoriesCrawler;
import hunglnv.crawler.FlowerCrawler;
import hunglnv.crawler.TempaCrawler;
import hunglnv.crawler.TempaDetailCrawler;
import hunglnv.entity.Bulb;
import hunglnv.thread.BulbThread;
import hunglnv.thread.FlowerThread;
import hunglnv.thread.TempaThread;
import hunglnv.utils.AppConstant;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author hungj
 */
@Path("crawler")
public class CrawlerFacadeREST {

    public CrawlerFacadeREST() {
    }

    @GET
    @Path("flowercrawler")
    @Produces(MediaType.TEXT_PLAIN)
    public String crawlFlower() {
        String x = "";
        try {
            FlowerCategoriesCrawler crawler = new FlowerCategoriesCrawler();
            Map<String, String> crawlerResult = crawler.getCategories(AppConstant.URL_COLLECTION);
            Map<String, String>  crawlerResult2 = crawler.getSecondCategories(AppConstant.URL_COLLECTION);
            crawlerResult2.entrySet().forEach((entry) -> {
                crawlerResult.put(entry.getKey(), entry.getValue());
            });
            for (Map.Entry<String, String> entry : crawlerResult.entrySet()) {
                   Thread thread = new Thread(new FlowerCrawler(entry.getKey(), entry.getValue()));
                    thread.start();
                    thread.join();
                    x = entry.getKey();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Ngu";
        }
        return x;
    }

    @GET
    @Path("bulbCrawler")
    @Produces(MediaType.TEXT_PLAIN)
    public String crawlBulb() {
        try {
            BulbCrawler crawler = new BulbCrawler();
            String tmp="";
            String name="";
            int price=0;
            int luminous=0;
            int wattage=0;
           Map<String, String> listBulb = crawler.getBulbPage("http://denledanhduong.com/den-led-bulb.htm");
           for (Map.Entry<String, String> entry : listBulb.entrySet()) {
               Bulb bulb = crawler.getBulbDetail(entry.getKey());
               bulb.setImgLink(entry.getValue());
               bulb.setLink(entry.getKey());
               crawler.setBulbDTO(bulb);
               
               crawler.run();
           }
           return tmp;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ngu";
        }
    }
      
    @GET
    @Path("bulbCrawler2")
    @Produces(MediaType.TEXT_PLAIN)
    public String crawlBulb2() {
        try {
            BulbCrawler crawler = new BulbCrawler();
            String tmp="";
            String name="";
            int price=0;
            int luminous=0;
            int wattage=0;
           Map<String, String> listBulb = crawler.getBulbPageLedMart("https://thegioidien.com/sanpham/4/936/Bong-den-LED-bulb.aspx");
           for (Map.Entry<String, String> entry : listBulb.entrySet()) {
               Bulb bulb = crawler.getBulbPageLedMartDetail(entry.getKey());
               bulb.setLink(entry.getKey());
               crawler.setBulbDTO(bulb);
               Thread thread = new Thread(crawler);
                    thread.start();
                    thread.join();
           }
           return tmp;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ngu";
        }
    }
    
    @GET
    @Path("tempaStateCrawler")
    @Produces(MediaType.TEXT_PLAIN)
    public String crawlState() {
        try {
            crawlerBySeason(AppConstant.URL_SPRING_WEATHER, "Spring");
            crawlerBySeason(AppConstant.URL_SUMMER_WEATHER, "Summer");
            crawlerBySeason(AppConstant.URL_FALL_WEATHER, "Fall");
            crawlerBySeason(AppConstant.URL_WINTER_WEATHER, "Winter");
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "Ngu";
        }
    }
       @GET
    @Path("crawlAll")
    @Produces(MediaType.TEXT_PLAIN)
    public String crawlAll() {
        try {
         crawlFlower();
         crawlState();
         crawlBulb2();
        } catch (Exception e) {
            e.printStackTrace();
            return "Ngu";
        }
        return "okok";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String crawlREST() {
        try {
            FlowerThread flowerThread = new FlowerThread();
            BulbThread bulbThread = new BulbThread();
            TempaThread tempaThread = new TempaThread();
            tempaThread.start();
            flowerThread.start();
            bulbThread.start();
            tempaThread.join();
            flowerThread.join();
            bulbThread.join();
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf("ngu");
        }
        return String.valueOf("okok");
    }
    
    public void crawlerBySeason(String src, String season) throws InterruptedException {
        TempaCrawler crawler = new TempaCrawler();
        Map<String, Float> listState = crawler.getStates(src);
        for (Map.Entry<String, Float> entry : listState.entrySet()) {
            Thread thread = new Thread(new TempaDetailCrawler(entry.getKey(),season, entry.getValue()));
                    thread.start();
                    thread.join();
        }
    }
}
