/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.crawler;

import hunglnv.dao.FlowerDAO;
import hunglnv.entity.Flower;
import hunglnv.entity.FlowerCategory;
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
public class FlowerDetailCrawler extends BaseCrawler implements Runnable {

    private String url;
    private FlowerCategory flowerCategory;

    public FlowerDetailCrawler(String url, FlowerCategory flowerCategory) {
        this.url = url;
        this.flowerCategory = flowerCategory;
    }

        @Override
    public void run() {
        try {
            Map<String, String> listFlowerDetail = getProduct(url);
            for (Map.Entry<String, String> entry : listFlowerDetail.entrySet()) {
                String doc = getSeedDetail(entry.getKey());
                Flower flower = stAXParserForSeedDetail(doc, entry.getValue(), entry.getKey());
                FlowerDAO flowerDAO = new FlowerDAO();
                flowerDAO.saveFlowerToDB(flower, flowerCategory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getProduct(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedreaderFromURL(url);
            String line = "";
            String document = "<root>";
            boolean isStart = false;
            boolean isFound = false;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (isStart && line.contains("<div class=\"wrapper-footer\" style=\"margin-top:50px\">")) {
                    break;
                }
                if (isStart && line.contains("<h3 itemprop=\"name\">")) {
                    isFound = true;
                }
                if (isStart && line.contains("<a href") && isStart && line.contains("</a>") && isFound) {
                    document += line.trim();
                }
                if (line.contains("</h3>")) {
                    isFound = false;
                }
                if (line.contains("<div class=\"thumb\">")) {
                    isStart = true;
                }
            }
            document += "</root>";
            return stAXParserForProduct(document, url);
        } catch (Exception e) {
            System.out.println("Exception at getProduct" + url);

        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Exception at getProduct" + url);

            }
        }
        return null;
    }

    public Map<String, String> stAXParserForProduct(String document, String url) throws UnsupportedEncodingException, XMLStreamException {
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
                        categories.put(link, character.getData());
                    }
                }
            }
            return categories;
        } catch (Exception e) {
            System.out.println("Exception at staXParserForProduct" + url);

        }
        return null;
    }

    public String getSeedDetail(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedreaderFromURL(url);
            String line = "";
            String document = "<root>";
            boolean isStart = false;
            while ((line = reader.readLine()) != null) {
                if (isStart && line.contains("</table>")) {
                    break;
                }
                if (isStart && line.contains("<div class=\"zoom\">")) {
                    isStart = false;
                    document += "</a>";
                }

                if (isStart) {
                    document += "\n" + line.trim();
                }

                if (line.contains("<table class=\"table table-striped\">") || line.contains("<div class=\"main-image text-center\">")) {
                    isStart = true;
                }
            }
            document += "</root>";
            return document;
        } catch (Exception e) {
            System.out.println("Exception at getSeedDetail" + url);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Exception at getSeedDetail" + url);

            }
        }
        return null;
    }

    public Flower stAXParserForSeedDetail(String document, String name, String flowerLink) throws UnsupportedEncodingException, XMLStreamException {
        Flower result = new Flower();
        result.setFlowerCategory(flowerCategory);
        result.setLink(flowerLink);
        result.setFlowerName(name);
        try {
            document = document.trim();
            XMLEventReader eventReader = parseStringToXMlEventReader(document);
            Map<String, String> categories = new HashMap<String, String>();
            boolean isStart = false;
            boolean isFound = false;
            int i = 0;
            while (eventReader.hasNext()) {
                XMLEvent event = (XMLEvent) eventReader.next();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String tagName = startElement.getName().getLocalPart();
                    if ("a".equals(tagName)) {
                        Attribute attHref = startElement.getAttributeByName(new QName("href"));
                        String link = "https://www.theseedcollection.com.au"+attHref.getValue();

                        //Set link
                        result.setImgLink(link);

                    } else if ("b".equals(tagName)) {
                        isStart = true;
                        event = (XMLEvent) eventReader.next();
                        Characters characters = event.asCharacters();
                        if (characters.getData().contains("When:") || characters.getData().contains("Germination:") || characters.getData().contains("Days to Maturity/Flowering:")) {
                            isFound = true;
                        }
                    } else if (isStart && isFound && "td".equals(tagName)) {
                        event = (XMLEvent) eventReader.next();
                        Characters characters = event.asCharacters();
                        isFound = false;
                        i++;
                        switch (i) {
                            case 1:
                                //Set season
                                result.setSeason(characters.getData());
                                break;
                            case 2:
                                String[] part = characters.getData().split(" days @ ");
                                String[] temparatureRange = part[1].split("-");
                                int temparature = 0;
                                if (temparatureRange.length > 1) {
                                    temparature = XMLUtils.extractNumFromString(temparatureRange[1]);
                                } else {
                                    temparature = XMLUtils.extractNumFromString(temparatureRange[0]);
                                }
                                float temFloat = (float) temparature;
                                //Set temparature
                                result.setTemparature(temFloat);

                                String[] dayRange = part[0].split("-");
                                int germinationDay = 0;
                                if (dayRange.length > 1) {
                                    germinationDay = XMLUtils.extractNumFromString(dayRange[1]);
                                } else {
                                    germinationDay = XMLUtils.extractNumFromString(dayRange[0]);
                                }

                                //Set germination
                                result.setGermination(germinationDay);
                                break;
                            case 3:
                                String[] part1 = characters.getData().split("-");
                                int maturingDay = 0;
                                if (part1.length > 1) {
                                    maturingDay = XMLUtils.extractNumFromString(part1[1]);
                                } else {
                                    maturingDay = XMLUtils.extractNumFromString(part1[0]);
                                }
                                //Set marturing
                                result.setMaturity(maturingDay);
                                break;
                            default:
                                break;
                        }
                    } else {
                        isStart = false;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}
