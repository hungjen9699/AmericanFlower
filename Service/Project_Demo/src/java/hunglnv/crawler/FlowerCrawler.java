/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.crawler;

import hunglnv.dao.FlowerCategoriesDAO;
import hunglnv.dao.FlowerDAO;
import hunglnv.entity.FlowerCategory;


/**
 *
 * @author hungj
 */
public class FlowerCrawler extends BaseCrawler implements Runnable {

    private String url;
    private String categoryName;
    protected FlowerCategory flowerCategory = null;

    public FlowerCrawler(String url, String categoryName) {
        this.url = url;
        this.categoryName = categoryName;
    }

    @Override
    public void run() {
        FlowerCategoriesDAO catDAO = new FlowerCategoriesDAO();
        flowerCategory = catDAO.createCategory(categoryName, url);
        if (flowerCategory == null) {
            return;
        }
        FlowerDAO flowerDAO = new FlowerDAO();
        try  {
            String pageURL = url;
              Thread thread = new Thread(new FlowerDetailCrawler(pageURL, flowerCategory));
                    thread.start();
                    thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        for (Map.Entry<String, String> entry : flowerByCate.entrySet()) {
//            Flower flower = flowerDAO.createFlower(flowerCategory, entry.getKey(), entry.getValue());
//        }
    }
 


}
