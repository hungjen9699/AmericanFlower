/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.thread;

import hunglnv.crawler.BaseCrawler;
import hunglnv.crawler.FlowerCategoriesCrawler;
import hunglnv.crawler.FlowerCrawler;
import hunglnv.utils.AppConstant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hungj
 */
public class FlowerThread  extends BaseThread implements Runnable{

    public FlowerThread() {
    }
    
    @Override
    public void run() {
         try {
            FlowerCategoriesCrawler crawler = new FlowerCategoriesCrawler();
            Map<String, String> crawlerResult = crawler.getCategories(AppConstant.URL_COLLECTION);
            Map<String, String>  crawlerResult2 = crawler.getSecondCategories(AppConstant.URL_COLLECTION);
            List<Thread> threads = new ArrayList<>();
            
            crawlerResult2.entrySet().forEach((entry) -> {
                crawlerResult.put(entry.getKey(), entry.getValue());
            });
            
            for (Map.Entry<String, String> entry : crawlerResult.entrySet()) {
                   Thread thread = new Thread(new FlowerCrawler(entry.getKey(), entry.getValue()));
                   threads.add(thread);
                   thread.start();
            }
             for (Thread thread : threads) {
                  thread.join();
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
