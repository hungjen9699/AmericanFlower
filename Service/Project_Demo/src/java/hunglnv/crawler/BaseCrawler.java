/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.crawler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;

/**
 *
 * @author hungj
 */
public class BaseCrawler {

    public BaseCrawler() {
    }
    
    protected BufferedReader getBufferedreaderFromURL(String urlString) throws Exception {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36");
        InputStream is = conn.getInputStream();
        BufferedReader bf = new  BufferedReader(new InputStreamReader(is, "UTF-8"));
        return bf;
    }
    
    protected XMLEventReader parseStringToXMlEventReader(String xmlSection) throws Exception {
        byte[] byteArray = xmlSection.getBytes("UTF-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = inputFactory.createXMLEventReader(inputStream);
        return reader;
    }
}
