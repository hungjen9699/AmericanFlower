/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.crawler;

import hunglnv.dao.BulbDAO;
import hunglnv.entity.Bulb;
import hunglnv.utils.XMLUtils;
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
public class BulbCrawler extends BaseCrawler implements Runnable {

    private Bulb bulbDTO;

    public BulbCrawler() {
    }

    public BulbCrawler(Bulb bulbDTO) {
        this.bulbDTO = bulbDTO;
    }

    public Bulb getBulbDTO() {
        return bulbDTO;
    }

    public void setBulbDTO(Bulb bulbDTO) {
        this.bulbDTO = bulbDTO;
    }

    public Map<String, String> getBulbPage(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedreaderFromURL(url);
            String line = "";
            String document = "<root>";
            boolean isStart = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("onerror=\"this.src=''\">")) {
                    line = line.replace("</a>", "</img></a>");
                }
                if (isStart) {
                    document += line.trim();
                }
                if (isStart && line.contains("<div class=\"clearfix\">")) {
                    break;
                }
                if (line.contains("<div class=\"product-gr4\">")) {
                    isStart = true;
                }
            }
            document = document.replaceAll("imgsrc", "img src");
            document = document.replaceAll("ahref", "a href");
            document = document.replaceAll("onerror", " onerror");
            document += "</root>";
            return stAXParserForBulb(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> getBulbList(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedreaderFromURL(url);
            String line = "";
            String document = "<root>";
            boolean isStart = false;
            boolean isFound = false;

            while ((line = reader.readLine()) != null) {
                if (isStart && line.contains("<div class='content'>")) {
                    break;
                }
                if (line.contains("<div class='group'>")) {
                    isStart = true;
                }
                if (isStart) {
                    document += line.trim();
                }

            }
            document += "</div>";
            document += "</div>";
            document += "</root>";
            Map<String, String> tmp = stAXParserForBulb(document);
            return tmp;
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

    public Map<String, String> stAXParserForBulb(String document) throws UnsupportedEncodingException, XMLStreamException {
        try {
            document = document.trim();
            XMLEventReader eventReader = parseStringToXMlEventReader(document);
            Map<String, String> listState = new HashMap<String, String>();
            try {
                while (eventReader.hasNext()) {
                    XMLEvent event = (XMLEvent) eventReader.next();
                    if (event.isStartElement()) {
                        StartElement startElement = event.asStartElement();
                        String tagName = startElement.getName().getLocalPart();
                        if ("a".equals(tagName)) {
                            Attribute linkHref = startElement.getAttributeByName(new QName("href"));
                            event = (XMLEvent) eventReader.next();
                            if (event.isStartElement()) {
                                startElement = event.asStartElement();
                                Attribute imgHref = startElement.getAttributeByName(new QName("src"));
                                listState.put(linkHref.getValue(), imgHref.getValue());
                            }
                        }
                    }
                }
                return listState;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Bulb getBulbDetail(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedreaderFromURL(url);
            String line = "";
            String document = "<root>";
            boolean isStart = false;
            boolean isFound = false;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<tbody style=\"box-sizing: border-box;\">")) {
                    isStart = true;
                    isFound = true;
                }
                if (line.contains("<div class=\"price-le\">")) {
                    isFound = true;
                }
                if (isFound && isStart && line.contains("<!-- <a class=\"compare")) {
                    break;
                }
                if (isStart && isFound) {
                    document += line.trim();
                }
                if (isStart && line.contains("</tbody>")) {
                    isFound = false;
                }
            }
            document = document.replaceAll("&nbsp;", "");
            document += "</root>";

            return stAXParserForBulbDetail(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bulb stAXParserForBulbDetail(String document) throws UnsupportedEncodingException, XMLStreamException {
        try {
            document = document.trim();
            Bulb bulb = new Bulb();
            XMLEventReader eventReader = parseStringToXMlEventReader(document);
            String x = document;
            String name = "";
            int wattage = 0;
            int luminous = 0;
            int price = 0;
            eventReader.next();
            while (eventReader.hasNext()) {
                XMLEvent event = (XMLEvent) eventReader.next();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String tagName = startElement.getName().getLocalPart();
                    if ("tr".equals(tagName)) {
                        eventReader.next();
                        event = (XMLEvent) eventReader.next();
                        Characters character = event.asCharacters();
                        String key = character.getData();

                        eventReader.next();
                        eventReader.next();
                        event = (XMLEvent) eventReader.next();

                        character = event.asCharacters();
                        String value = character.getData();
                        if (key.contains("Mã sản")) {
                            name = value;
                            bulb.setBulbName(name);
                        } else if (key.contains("Công suất")) {
                            wattage = XMLUtils.extractNumFromString(value);
                            bulb.setWattage(wattage);
                        } else if (key.contains("Quang thông")) {
                            luminous = XMLUtils.extractNumFromString(value);
                            bulb.setLuminous(luminous);
                        }
                    } else if ("span".equals(tagName)) {
                        event = (XMLEvent) eventReader.next();
                        Characters character = event.asCharacters();
                        price = XMLUtils.extractNumFromStringForBulb(character.getData());
                        bulb.setPrice(price);
                    }
                }
            }
            return bulb;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

   
    public Map<String, String> getBulbPageLedMart(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedreaderFromURL(url);
            String line = "";
            String document = "<root>";
            boolean isStart = false;
            while ((line = reader.readLine()) != null) {
                if (isStart && line.contains("<div class=\"rightblockpaging\">")) {
                    break;
                }
                if (line.contains("<div class=\"rightproblockct\">")) {
                    isStart = true;
                }
                if (isStart) {
                    document += line.trim();
                }
            }
            document += "</root>";
            return stAXParserForLedMart(document);
        } catch (Exception e) {
        }
        return null;
    }
     public Map<String, String> stAXParserForLedMart(String document) throws UnsupportedEncodingException, XMLStreamException {
              Map<String, String> listState = new HashMap<String, String>();
        try {
              document = document.trim();
        XMLEventReader eventReader = parseStringToXMlEventReader(document);
        boolean check = false;
            while (eventReader.hasNext()) {
                XMLEvent event = (XMLEvent) eventReader.next();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String tagName = startElement.getName().getLocalPart();
                    if ("a".equals(tagName)) {
                        Attribute linkHref = startElement.getAttributeByName(new QName("href"));
                        if (check && linkHref.getValue().contains("Bong-den")) {
                            event = (XMLEvent) eventReader.next();
                            if (event.isStartElement()) {
                                startElement = event.asStartElement();
                                Attribute imgHref = startElement.getAttributeByName(new QName("src"));
                                String link = "https://thegioidien.com/sanpham/"+linkHref.getValue().trim().replace("../", "");
                                String img = "https://thegioidien.com/sanpham/"+imgHref.getValue().trim().replace("../", "");
                                listState.put(link, img);
                            }
                        }
                        check = false;
                    } else if ("div".equals(tagName)) {
                        Attribute linkHref = startElement.getAttributeByName(new QName("class"));
                        if (linkHref != null) {
                            if (linkHref.getValue().equals("productitemimgcontent")) {
                                check = true;
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listState;
    }


    public Bulb getBulbPageLedMartDetail(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedreaderFromURL(url);
            String line = "";
            String document = "<root>";
            boolean isStart = false;
            boolean isFound = false;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (isStart && line.contains("<div class=\"sanphamrelate\">")) {
                    break;
                }
                if (line.contains("<div class=\"productdetailswrp\">")) {
                    isStart = true;
                }
                if (isStart) {
                    document += line.trim();
                }

            }
            document += "</div>";
            document += "</root>";
            document = document.trim().replace("&", "");
            return stAXParserForLedMartDetail(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bulb stAXParserForLedMartDetail(String document) throws UnsupportedEncodingException, XMLStreamException {
        Bulb bulb = new Bulb();
        try {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMlEventReader(document);
        String name = "";
        int price = 1;
        int wattage = 1;
        int luminous = 1;
        String imgLink = "";
        int count = 0;
            while (eventReader.hasNext()) {
                XMLEvent event = (XMLEvent) eventReader.next();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String tagName = startElement.getName().getLocalPart();
                    if ("span".equals(tagName)) {
                        Attribute nameAtt = startElement.getAttributeByName(new QName("id"));
                        if (nameAtt != null) {
                            if (nameAtt.getValue().equals("ctl10_spMasp")) {
                                event = (XMLEvent) eventReader.next();
                                Characters character = event.asCharacters();
                                name = character.getData();
                                bulb.setBulbName(name);
                            } else if (nameAtt.getValue().equals("ctl10_spGiaban")) {
                                event = (XMLEvent) eventReader.next();
                                Characters character = event.asCharacters();
                                price = XMLUtils.extractNumFromStringForBulb(character.getData());
                                bulb.setPrice(price);
                            }
                        }
                    } else if ("a".equals(tagName)) {
                        Attribute nameAtt = startElement.getAttributeByName(new QName("id"));
                        if (nameAtt != null) {
                            if (nameAtt.getValue().equals("ctl10_rptGallery_ctl00_lktoimage")) {
                                Attribute imgAtt = startElement.getAttributeByName(new QName("href"));
                                imgLink = "https://thegioidien.com/" + imgAtt.getValue().replace("../", "");
                                bulb.setImgLink(imgLink);
                            }
                        }
                    } else if ("tr".equals(tagName)) {
                        count++;
                        if (count > 1) {
                            eventReader.next();
                            event = (XMLEvent) eventReader.next();
                            Characters character = event.asCharacters();
                            String key = character.getData();
                            eventReader.next();
                            eventReader.next();
                            event = (XMLEvent) eventReader.next();
                            character = event.asCharacters();
                            String value = character.getData();
                            if (key.contains("ng suất")) {
                                wattage = XMLUtils.extractNumFromString(value);
                                bulb.setWattage(wattage);
                            } else if (key.contains("Quang th")) {
                                luminous = XMLUtils.extractNumFromString(value);
                                bulb.setLuminous(luminous);
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bulb;
    }

    @Override
    public void run() {
        BulbDAO bulbDAO = new BulbDAO();
        try {
            if (bulbDTO != null) {
                Boolean check = bulbDAO.createBulb(bulbDTO.getBulbName(), bulbDTO.getImgLink(), bulbDTO.getLink(), bulbDTO.getPrice(), bulbDTO.getWattage(), bulbDTO.getLuminous());
            }
        } catch (Exception e) {
        }
    }
}
