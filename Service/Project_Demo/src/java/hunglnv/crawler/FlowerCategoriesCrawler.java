/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author hungj
 */
public class FlowerCategoriesCrawler extends BaseCrawler {

    public FlowerCategoriesCrawler() {
    }

    public Map<String, String> getCategories(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedreaderFromURL(url);
            String line = "";
            String document = "<root>";
            boolean isStart = false;
            boolean isFound = false;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (isStart && line.contains("</ul>")) {
                    break;
                }
                if (isStart) {
                    document += line.trim();
                }
                if (line.contains("<ul class=\"nav navbar-nav hidden-xs\">")) {
                    isFound = true;
                    count++;
                }
                if (isFound && line.contains("<li class=\"dropdown dropdown-hover\">")) {
                    count++;
                }
                if (count == 4 && line.contains("<ul class=\"dropdown-menu columns\">")) {
                    isStart = true;
                }
            }
            document += "</root>";
            return stAXParserForCategories(document);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
      public Map<String, String> getSecondCategories(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedreaderFromURL(url);
            String line = "";
            String document = "<root>";
            boolean isStart = false;
            boolean isFound = false;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (isStart && line.contains("</ul>")) {
                    break;
                }
                if (isStart) {
                    document += line.trim();
                }
                if (line.contains("<ul class=\"nav navbar-nav hidden-xs\">")) {
                    isFound = true;
                    count++;
                }
                if (isFound && line.contains("<li class=\"dropdown dropdown-hover\">")) {
                    count++;
                }
                if (count == 5 && line.contains("<ul class=\"dropdown-menu columns\">")) {
                    isStart = true;
                }
            }
            document += "</root>";
            return stAXParserForCategories(document);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Map<String, String> stAXParserForCategories(String document) throws UnsupportedEncodingException, XMLStreamException {
        try {
            document = document.trim();
            XMLEventReader eventReader = parseStringToXMlEventReader(document);
            Map<String, String> categories = new HashMap<String, String>();

            while (eventReader.hasNext()) {
                XMLEvent event = (XMLEvent) eventReader.next();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String tagName = startElement.getName().getLocalPart();
                    if ("a".equals(tagName)) {
                        Attribute attHref = startElement.getAttributeByName(new QName("href"));
                        String link = attHref.getValue();
                        event = (XMLEvent) eventReader.next();
                        Characters character = event.asCharacters();
                        if (!link.contains("other-flowers")) {
                            categories.put(link, character.getData());
                        }
                    }
                }
            }
            return categories;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
         public Map<String,String> getBulbPage(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedreaderFromURL("https://thegioichieusang.com/make/den-led-bup-2/");
            String line = "";
            String document = "<root>";
            boolean isStart = false;
            boolean isFound = false;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (isStart && line.contains("<div class=\"pagination\">")) {
                    System.out.println("oss");
                    break;
                }
                if (line.contains("<div class=\"newcardeals\">")) {
                    isStart = true;
                }
                if (line.contains("<div class=\"newcardeals\">")) {
                    isFound = true;
                }
                if (isFound && isStart) {
                    document += line.trim();
                }
            }
            document += "</root>";
            return stAXParserForBulbPage(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Map<String, String> stAXParserForBulbPage(String document) throws UnsupportedEncodingException, XMLStreamException {
        try {
            document = document.trim();
            XMLEventReader eventReader = parseStringToXMlEventReader(document);
            Map<String, String> listBulb = new HashMap<String, String>();
            int count = 0;
            String img = "";
            while (eventReader.hasNext()) {
                XMLEvent event = (XMLEvent) eventReader.next();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String tagName = startElement.getName().getLocalPart();
                    if ("a".equals(tagName)) {
                        Attribute attHref = startElement.getAttributeByName(new QName("href"));
                        Attribute attClass = startElement.getAttributeByName(new QName("class"));
                        if (attClass == null) {
                            String link = attHref.getValue();
                            event = (XMLEvent) eventReader.next();
                            // Lay link + Ten
                            Characters character = event.asCharacters();
                            String name = character.getData();
                           // Bulb bulb = getBulbDetail(link, img, name);
                            listBulb.put(link, name);
                            count++;
                        }
                    } // Lay img
                    else if ("img".equals(tagName)) {
                        Attribute attHref = startElement.getAttributeByName(new QName("src"));
                        img = attHref.getValue();
                    }
                }
            }
            return listBulb;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
