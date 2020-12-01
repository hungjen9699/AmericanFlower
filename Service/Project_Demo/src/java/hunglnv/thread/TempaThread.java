/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.thread;

import hunglnv.crawler.TempaCrawler;
import hunglnv.crawler.TempaDetailCrawler;
import hunglnv.utils.AppConstant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hungj
 */
public class TempaThread  extends BaseThread implements Runnable{
    List<Thread> threads = new ArrayList<>();
    public TempaThread() {
    }
    
    @Override
    public void run() {
        try {
            crawlerBySeason(AppConstant.URL_SPRING_WEATHER, "Spring");
            crawlerBySeason(AppConstant.URL_SUMMER_WEATHER, "Summer");
            crawlerBySeason(AppConstant.URL_FALL_WEATHER, "Fall");
            crawlerBySeason(AppConstant.URL_WINTER_WEATHER, "Winter");
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public void crawlerBySeason(String src, String season) throws InterruptedException {
        TempaCrawler crawler = new TempaCrawler();
        Map<String, Float> listState = crawler.getStates(src);
        for (Map.Entry<String, Float> entry : listState.entrySet()) {
            Thread thread = new Thread(new TempaDetailCrawler(entry.getKey(),season, entry.getValue()));
                    threads.add(thread);
                    thread.start();
        }
    }
    
}
