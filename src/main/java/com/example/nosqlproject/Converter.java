package com.example.nosqlproject;

import java.text.SimpleDateFormat;
import java.util.*;

public class Converter {

    public List<Flight> FlyConverter(List<SMFFlight> smfFlights){
        List<Flight> flights = new LinkedList<>();
        for (SMFFlight smfFlight : smfFlights) {
            Flight flight = new Flight();
            flight.setFlightId(smfFlight.getFlightId());
            flight.setOrigin(smfFlight.getOrigin());
            flight.setOriginCity(smfFlight.getOriginCity());
            flight.setOriginCountry(smfFlight.getOriginCountry());
            flight.setDestination("Sacramento International");
            flight.setDestinationCity("California");
            flight.setDestinationCountry("US");
            flight.setFlag(false);
            flight.setClassType("economy");
            flight.setFinishTime(smfFlight.getArrivalDate());
            Calendar cal = Calendar.getInstance();
            cal.setTime(smfFlight.getArrivalDate());
            int randomTime = new Random().nextInt(4) - 6;
            cal.add(Calendar.HOUR, randomTime);
            flight.setStartTime(cal.getTime());
            flight.setCapacity((new Random().nextInt(4) * 50) + 50);
            flight.setDuration(getDuration(flight.getStartTime(),flight.getFinishTime()));
            flight.setPrice((new Random().nextInt(20) * 50) + 100);
            flight.setAirlineCompany(Collections.singletonList(smfFlight.getAirlineCompany().get(0)));
            flight.setStops(new LinkedList<>());
            flights.add(flight);
        }
        return flights;
    }

    private double getDuration(Date start, Date finish){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh mm");
        String dateString1 = simpleDateFormat.format(start);
        int hour1 = Integer.parseInt(dateString1.split(" ")[0]);
        int minute1 = Integer.parseInt(dateString1.split(" ")[1]);
        String dateString2 = simpleDateFormat.format(finish);
        int hour2 = Integer.parseInt(dateString2.split(" ")[0]);
        int minute2 = Integer.parseInt(dateString2.split(" ")[1]);
        int finalHour = hour2 - hour1;
        if(finalHour < 0){
            finalHour += 24;
        }
        int finalMinute = minute2 - minute1;
        if(finalMinute < 0){
            finalMinute += 60;
            finalHour--;
        }
        String doubleString = finalHour + "." + finalMinute;
        return Double.parseDouble(doubleString);
    }
}
