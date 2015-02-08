package com.example.eddie.hackatbrown2;

/**
 * Created by eddie on 2/8/15.
 */
import java.net.*;
import java.io.*;
import java.util.*;

public class URLReader {
    String[] _content;

    public URLReader() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] getInfo() {
        try {
            URL url = new URL("https://safe-dawn-3761.herokuapp.com/comments");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String[] messageArray;
            String inputLine;
            String message = "";
            while ((inputLine = in.readLine()) != null) {
                message = message.concat(inputLine);
            }
            in.close();

            messageArray = message.split("\\},");

////            for (int i = 0; i < messageArray.length; i++) {
////                System.out.println(messageArray[i]);
////            }
            return messageArray;

        }
        catch(Exception e) {
            return new String[]{"fcked up"};
        }

    }
}


//        try {
//            URLConnection urlConnection = _url.openConnection();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
////            String inputLine;
//
////            while ((inputLine = reader.readLine()) != null) {
////                _content.append(inputLine);
////            }
//            String WholeLine = reader.readLine();
//            reader.close();
//
//            WholeLine = WholeLine.substring(1, -1);
//            String[] wholeLineArray = WholeLine.split(",\\{");
//
////            for (int i = 1; i <= wholeLineArray.length-1; i++) {
////                wholeLineArray[i] = "\\{".concat(wholeLineArray[i]);
////            }
//
//            return wholeLineArray;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new String[]{};
//        }
        //return _content;
