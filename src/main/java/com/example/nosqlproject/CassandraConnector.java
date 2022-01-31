package com.example.nosqlproject;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.text.SimpleDateFormat;
import java.util.*;

public class CassandraConnector {

    private Cluster cluster;

    private Session session;

    public void connect(String node, Integer port) {
        Cluster.Builder b = Cluster.builder().addContactPoint(node);
        if (port != null) {
            b.withPort(port);
        }
        cluster = b.build();
        session = cluster.connect();
        session.execute("USE Alibaba;");
    }

    public List<Flight> getAllFlights(){
        String query = "SELECT * FROM Flight ALLOW FILTERING;";
        return getFlightsByQuery(query);
    }

    //1
    public List<Flight> getFlightsBySpecificDate(Date date, String classType, OrderBy orderBy, int limit){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormatted = formatter.format(date);
        StringBuilder query = new StringBuilder("SELECT * FROM Flight WHERE starttime >= '" + dateFormatted + " 00:00:00' and starttime <= '" + dateFormatted + " 23:59:59"  + "'");
        if(classType != null){
            query.append(" and classType = '").append(classType).append("'");
        }
        if(limit != -1){
            query.append(" limit ").append(limit);
        }
        query.append(" ALLOW FILTERING; ");
        List<Flight> flights = getFlightsByQuery(query.toString());
        if(orderBy != null){
            orderFlights(flights,orderBy);
//            query.append(" order by ").append(orderBy.getOrderBy()).append(" ").append(orderBy.getType());
        }
        return flights;
    }

    //2
    public List<Flight> getFlightsInPriceRange(double minPrice, double maxPrice, String classType, OrderBy orderBy, int limit){
        StringBuilder query = new StringBuilder("SELECT * FROM Flight WHERE price >= " + minPrice + " and price <= " + maxPrice);
        if(classType != null){
            query.append(" and classType = '").append(classType).append("'");
        }
        if(limit != -1){
            query.append(" limit ").append(limit);
        }
        query.append(" ALLOW FILTERING; ");
        List<Flight> flights = getFlightsByQuery(query.toString());
        if(orderBy != null){
            orderFlights(flights,orderBy);
        }
        return flights;
    }

    //3
    public String getMinMaxPrice(String origin, String destination, String classType){
        StringBuilder query = new StringBuilder("SELECT min(price) as minPrice ,max(price) as maxPrice FROM Flight WHERE origin = '" + origin + "' and destination = '" + destination + "'");
        if(classType != null){
            query.append(" and classType = '").append(classType).append("'");
        }
        query.append(" ALLOW FILTERING; ");
        Row result = session.execute(query.toString()).one();
        return "Minimum Price : " + result.getDouble("minPrice") + "\n" + "Maximum Price : " + result.getDouble("maxPrice");
    }

    //4
    public String getAvgSumPrice(String origin, String destination, String classType){
        StringBuilder query = new StringBuilder("SELECT avg(price) as avgPrice ,sum(price) as sumPrice FROM Flight WHERE origin = '" + origin + "' and destination = '" + destination + "'");
        if(classType != null){
            query.append(" and classType = '").append(classType).append("'");
        }
        query.append(" ALLOW FILTERING; ");
        Row result = session.execute(query.toString()).one();
        return "Average Price : " + result.getDouble("avgPrice") + "\n" + "Sum Price : " + result.getDouble("sumPrice");
    }

    //6
    public List<Flight> getCheapestFlight(String origin, String destination, double minPrice, double maxPrice, Date firstDate, Date secondDate, String classType, OrderBy orderBy, int limit){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String firstDateFormatted = "";
        String secondDateFormatted = "";
        StringBuilder query = new StringBuilder("SELECT min(price) as minPrice FROM Flight WHERE origin = '" + origin + "' and destination = '" + destination + "' and price >= " + minPrice + " and price <= " + maxPrice);
        if(firstDate != null && secondDate != null){
            firstDateFormatted = formatter.format(firstDate);
            secondDateFormatted = formatter.format(secondDate);
            query.append(" and startTime >= '").append(firstDateFormatted).append(" 00:00:00' and finishTime <= '").append(secondDateFormatted).append(" 23:59:59"  + "'");
        }
        if(classType != null){
            query.append(" and classType = '").append(classType).append("'");
        }
        query.append(" ALLOW FILTERING; ");
        Row result = session.execute(query.toString()).one();
        double minFlightPrice = result.getDouble("minPrice");
        query = new StringBuilder("SELECT * FROM Flight WHERE price = " + minFlightPrice + " and origin = '" + origin + "' and destination = '" + destination + "'");
        if(firstDate != null && secondDate != null){
            query.append(" and startTime >= '").append(firstDateFormatted).append(" 00:00:00' and finishTime <= '").append(secondDateFormatted).append(" 23:59:59"  + "'");
        }
        if(classType != null){
            query.append(" and classType = '").append(classType).append("'");
        }
        query.append(" ALLOW FILTERING; ");
        Row result2 = session.execute(query.toString()).one();
        Flight flight = parseQuery(result2);
        query = new StringBuilder("SELECT * FROM Flight WHERE origin = '" + origin + "' and destination = '" + destination + "'");
        if(firstDate != null && secondDate != null){
            query.append(" and startTime >= '").append(firstDateFormatted).append(" 00:00:00' and finishTime <= '").append(secondDateFormatted).append(" 23:59:59"  + "'");
        }
        if(classType != null){
            query.append(" and classType = '").append(classType).append("'");
        }
        if(limit != -1){
            query.append(" limit ").append(limit);
        }
        query.append(" ALLOW FILTERING; ");
        List<Flight> flights = getFlightsByQuery(query.toString());
        if(orderBy != null){
            orderFlights(flights,orderBy);
        }
        for (Flight f : flights) {
            if(f.getFlightId() == flight.getFlightId()){
                f.setFlag(true);
            }
        }
        return flights;
    }

    //7
    public List<Flight> getFlightsByOriginDestCap(String originAirport, String DestinationAirport, int FlightCapacity, Date firstDate, Date secondDate, String classType, OrderBy orderBy, int limit){
        StringBuilder query = new StringBuilder("SELECT * FROM Flight WHERE Origin = '" + originAirport +"' and  destination = '" + DestinationAirport + "' and capacity = " + FlightCapacity);
        if(firstDate != null && secondDate != null){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String firstDateFormatted = formatter.format(firstDate);
            String secondDateFormatted = formatter.format(secondDate);
            query.append(" and startTime >= '").append(firstDateFormatted).append(" 00:00:00' and finishTime <= '").append(secondDateFormatted).append(" 23:59:59"  + "'");
        }
        if(classType != null){
            query.append(" and classType = '").append(classType).append("'");
        }
        if(limit != -1){
            query.append(" limit ").append(limit);
        }
        query.append(" ALLOW FILTERING; ");
        List<Flight> flights = getFlightsByQuery(query.toString());
        if(orderBy != null){
            orderFlights(flights,orderBy);
        }
        return flights;
    }

    //9
    public List<String> getAirlineCompany(Date date, String originAirport, String destinationAirport, OrderBy orderBy, int limit){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormatted = formatter.format(date);
        StringBuilder query = new StringBuilder("SELECT * FROM Flight WHERE Origin = '" + originAirport + "' and Destination = '" + destinationAirport + "' and  startTime >= '" + dateFormatted + " 00:00:00' and startTime <= '" + dateFormatted + " 23:59:59'");
        query.append(" ALLOW FILTERING; ");
        List<Flight> flights = getFlightsByQuery(query.toString());
        if(orderBy != null){
            orderFlights(flights,orderBy);
        }
        if(limit != -1){
            query.append(" limit ").append(limit);
        }
        HashSet<String> airlines = new HashSet<>();
        for (Flight flight: flights) {
            List<String> flightAirlines = flight.getAirlineCompany();
            airlines.addAll(flightAirlines);
        }
        return new LinkedList<>(airlines);
    }

    //10
    public void deleteByDateName(Date date, String airlineName) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormatted = formatter.format(date);
        ResultSet result =  session.execute("SELECT FlightID FROM Flight WHERE airlineCompany CONTAINS '" + airlineName + "' and  startTime >= '" + dateFormatted + " 00:00:00' and startTime <= '" + dateFormatted + " 23:59:59" + "' ALLOW FILTERING;");
        for (Row row : result) {
            int id = row.getInt("FlightID");
            session.execute("DELETE FROM Flight WHERE FlightID = " + id + ";");
        }
    }

    //11
    public void changeCapacityById(int flightId, int capacity){
        session.execute("UPDATE Flight SET capacity = " + capacity + "  WHERE flightId = " + flightId);
    }

    //12
    public void changeCapacityByDateOriginDest(String airline, int capacity, Date date, String originAirport, String destinationAirport){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormatted = formatter.format(date);
        String s = "SELECT FlightID FROM Flight WHERE airlineCompany CONTAINS '" + airline + "' and Origin = '" + originAirport + "' and Destination = '" + destinationAirport + "' and startTime >= '" + dateFormatted + " 00:00:00' and startTime <= '" + dateFormatted + " 23:59:59' ALLOW FILTERING;";
        ResultSet result = session.execute(s);
        for (Row row : result) {
            int id = row.getInt("FlightID");
            session.execute("UPDATE Flight SET Capacity = " + capacity + " WHERE FlightID = " + id + ";");
        }
    }

    //13
    public String airportsName(Date date, String originCountry, String destinationCountry){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormatted = formatter.format(date);
        ResultSet resultSet = session.execute("SELECT origin,destination FROM Flight WHERE originCountry = '" + originCountry + "' and destinationCountry = '" + destinationCountry + "' and startTime >= '" + dateFormatted + " 00:00:00' and startTime <= '" + dateFormatted + " 23:59:59' ALLOW FILTERING;");
        StringBuilder stringBuilder = new StringBuilder();
        for (Row row : resultSet) {
            stringBuilder.append(row.getString("origin")).append("\n").append(row.getString("destination"));
        }
        return stringBuilder.toString();
    }

    private List<Flight> getFlightsByQuery(String query){
        List<Flight> flights = new LinkedList<>();
        ResultSet resultSet = session.execute(query);
        for (Row row : resultSet) {
            Flight flight = parseQuery(row);
            flights.add(flight);
        }
        return flights;
    }

    private void orderFlights(List<Flight> initialList, OrderBy orderBy){
        if(orderBy.getOrderBy().equals("Price")){
            initialList.sort(Comparator.comparing(Flight::getPrice));
        }
        else {
            initialList.sort(Comparator.comparing(Flight::getStartTime));
        }
        if(orderBy.getType().equals("DESC")){
            initialList.sort(Collections.reverseOrder());
        }
    }

    private Flight parseQuery(Row row){
        Flight flight = new Flight();
        flight.setFlightId(row.getInt("flightId"));
        flight.setAirlineCompany(row.getList("airlineCompany",String.class));
        flight.setCapacity(row.getInt("capacity"));
        flight.setClassType(row.getString("classType"));
        flight.setDestination(row.getString("destination"));
        flight.setDestinationCity(row.getString("destinationCity"));
        flight.setDestinationCountry(row.getString("destinationCountry"));
        flight.setFinishTime(row.getTimestamp("finishTime"));
        flight.setDuration(row.getDouble("flightDuration"));
        flight.setOrigin(row.getString("origin"));
        flight.setOriginCity(row.getString("originCity"));
        flight.setOriginCountry(row.getString("originCountry"));
        flight.setPrice(row.getDouble("price"));
        flight.setStartTime(row.getTimestamp("startTime"));
        flight.setStops(row.getList("stops",String.class));
        return flight;
    }

    public void insertFlight(Flight flight){
        StringBuilder query = new StringBuilder("INSERT INTO FLIGHT (flightid,airlinecompany,capacity,classType,destination,destinationCity,destinationCountry,finishtime,flightduration,origin,origincity,origincountry,price,starttime,stops,crawldate) values (");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        query.append(flight.getFlightId()).append(",");
        List<String> airlines = new LinkedList<>();
        for (String s : flight.getAirlineCompany()) {
            airlines.add("'" + s + "'");
        }
        query.append("[").append(String.join(",",airlines)).append("],");
        query.append(flight.getCapacity()).append(",");
        query.append("'").append(flight.getClassType()).append("',");
        query.append("'").append(flight.getDestination()).append("',");
        query.append("'").append(flight.getDestinationCity()).append("',");
        query.append("'").append(flight.getDestinationCountry()).append("',");
        query.append("'").append(simpleDateFormat.format(flight.getFinishTime())).append("',");
        query.append(flight.getDuration()).append(",");
        query.append("'").append(flight.getOrigin()).append("',");
        query.append("'").append(flight.getOriginCity()).append("',");
        query.append("'").append(flight.getOriginCountry()).append("',");
        query.append(flight.getPrice()).append(",");
        query.append("'").append(simpleDateFormat.format(flight.getStartTime())).append("',");
        query.append("[").append(String.join(",",flight.getStops())).append("],");
        query.append("'").append(simpleDateFormat.format(new Date())).append("');");
        session.execute(query.toString());
    }

    public Session getSession() {
        return this.session;
    }

    public void close() {
        session.close();
        cluster.close();
    }
}
