/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.crawler;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author hungj
 */
public class TempaCrawler extends BaseCrawler{

    public TempaCrawler() {
    }
     public Map<String,Float> getStates(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedreaderFromURL(url);
            if (reader == null) {
                return null;
            }
            String line = "";
            String document = "<root>";
            boolean isStart = false;
            boolean isFound = false;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (isStart && line.contains("<div class='content'>")) {
                    break;
                }
                if (count > 1) {
                    break;
                }
                if (isStart && line.contains("</tbody>")) {
                    count++;
                    isFound = false;
                }
                if (isStart && isFound) {
                    document += line.trim();
                }
                if (line.contains("<tbody>")) {
                    isStart = true;
                    isFound = true;
                }

            }
            document += "</root>";
            return stAXParserForState(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
        
        }
        return null;
    }

    public Map<String, Float> stAXParserForState(String document) throws UnsupportedEncodingException, XMLStreamException {
       
        try { document = document.trim();
        XMLEventReader eventReader = parseStringToXMlEventReader(document);
        Map<String, Float> listState = new HashMap<String, Float>();
            
            while (eventReader.hasNext()) {
                XMLEvent event = (XMLEvent) eventReader.next();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String tagName = startElement.getName().getLocalPart();
                    if ("tr".equals(tagName)) {
                        eventReader.next();
                        event = (XMLEvent) eventReader.next();

                        // Get state
                        Characters character = event.asCharacters();
                        String state = character.getData();

                        eventReader.next();
                        eventReader.next();
                        eventReader.next();
                        eventReader.next();
                        eventReader.next();
                        event = (XMLEvent) eventReader.next();
                        //Get temparature
                        Characters character2 = event.asCharacters();
                        float temparature = Float.parseFloat(character2.getData());
                        
                        listState.put(state, temparature);
                    }

                }
            }
            return listState;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
