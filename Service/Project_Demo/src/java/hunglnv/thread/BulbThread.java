/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.thread;

import hunglnv.crawler.BulbCrawler;
import hunglnv.entity.Bulb;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hungj
 */
public class BulbThread extends BaseThread implements Runnable {

    public BulbThread() {
    }

    @Override
    public void run() {
        try {
            BulbCrawler crawler = new BulbCrawler();
            List<Thread> threads = new ArrayList<>();
            Map<String, String> listBulb = crawler.getBulbPageLedMart("https://thegioidien.com/sanpham/4/936/Bong-den-LED-bulb.aspx");
            for (Map.Entry<String, String> entry : listBulb.entrySet()) {
                Bulb bulb = crawler.getBulbPageLedMartDetail(entry.getKey());
                bulb.setLink(entry.getKey());
                crawler.setBulbDTO(bulb);
                Thread thread = new Thread(crawler);
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
