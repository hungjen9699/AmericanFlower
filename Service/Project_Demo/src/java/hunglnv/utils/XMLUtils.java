/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hungj
 */
public class XMLUtils {

    public static String fixString(String document) {
        return document.replace("&frac12", "")
                .replace("&nbsp", "")
                .replace("&mdash", "")
                .replace("&ntilde", "");
    }

    public static int extractNumFromString(String str) {
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            String result = matcher.group();
            try {
                int num = Integer.parseInt(result);
                return num;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
         public static int extractNumFromStringForBulb(String str) {
         String result="";
         for (int i = 0; i < str.length(); i++) {
             boolean check=Character.isDigit(str.charAt(i));
             if (check) {
                 result+=str.charAt(i);
             }
         }
    return Integer.parseInt(result);
     }
}
