
import hunglnv.crawler.FlowerCategoriesCrawler;
import hunglnv.utils.AppConstant;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hungj
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        FlowerCategoriesCrawler cr = new FlowerCategoriesCrawler();
        
        java.util.Map<String,String> x = cr.getCategories(AppConstant.URL_COLLECTION);
        try {
            BufferedReader rd =  getBufferedReaderForURL("https://vivaled.vn/bong-den-led/");
            if (rd!=null) {
                System.out.println("xx");
            }
        } catch (Exception e) {
        }
    }
        protected static BufferedReader getBufferedReaderForURL(String urlString) throws MalformedURLException,UnsupportedEncodingException, IOException{
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        return reader;
    }
}
