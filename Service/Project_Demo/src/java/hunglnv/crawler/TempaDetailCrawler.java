/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.crawler;

import hunglnv.dao.TempaStateDAO;

/**
 *
 * @author hungj
 */
public class TempaDetailCrawler extends BaseCrawler implements Runnable{
    private String name;
    private String season;
    private float temparature;
    public TempaDetailCrawler() {
    }

    public TempaDetailCrawler(String name, String season, float temparature) {
        this.name = name;
        this.season = season;
        this.temparature = temparature;
    }
    

    @Override
    public void run() {
        TempaStateDAO dao = new TempaStateDAO();
        try {
            dao.createTempaState(name, temparature, season);
        } catch (Exception e) {
        }
    }
    
   
}
