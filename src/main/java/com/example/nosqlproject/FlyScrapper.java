package com.example.nosqlproject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FlyScrapper {

    String url;

    public FlyScrapper(String url) {
        this.url = url;
    }

    public List<SMFFlight> getResult(){
        List<SMFFlight> flights = new LinkedList<>();
        try {
            final Document document = Jsoup.connect(url).get();
            List<Element> row = document.select("tbody tr");
            for(Element element : row){
                String info = element.select("div").text();
                String u = element.select(".tooltip").get(0).attributes().get("title").split(",")[0];
                String []words = info.split(" ");
                SMFFlight smfFlights = new SMFFlight();
                smfFlights.setFlightId(Integer.parseInt(words[0]));
                String img = element.select("div img").get(0).attributes().get("alt");
                LinkedList<String> airlineCompany = new LinkedList<>();
                if(!img.isEmpty()){
                    airlineCompany.add(img);
                }
                else{
                    airlineCompany.add("Southwest");
                }
                smfFlights.setAirlineCompany(airlineCompany);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mmaa");
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String d = dateFormat2.format(date) +" "+ words[3];
                smfFlights.setArrivalDate(dateFormat.parse(d));
                smfFlights.setOrigin(u);
                smfFlights.setOriginCity(u);
                smfFlights.setOriginCountry("USA");
                flights.add(smfFlights);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return flights;
    }
}