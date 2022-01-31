package com.example.nosqlproject;

import java.util.Date;
import java.util.List;

public class SMFFlight {
    private int flightId;
    private List<String> airlineCompany;
    private String origin;
    private String originCity;
    private String originCountry;
    private String destination;
    private String destinationCity;
    private String destinationCountry;
    private Date arrivalDate;
    private Date departureTime;
    private String status;

    public SMFFlight() {
    }

    public SMFFlight(int flightId, List<String> airlineCompany, String origin, String originCity, String originCountry, String destination, String destinationCity, String destinationCountry, Date arrivalDate, Date departureTime) {
        this.flightId = flightId;
        this.airlineCompany = airlineCompany;
        this.origin = origin;
        this.originCity = originCity;
        this.originCountry = originCountry;
        this.destination = destination;
        this.destinationCity = destinationCity;
        this.destinationCountry = destinationCountry;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public List<String> getAirlineCompany() {
        return airlineCompany;
    }

    public void setAirlineCompany(List<String> airlineCompany) {
        this.airlineCompany = airlineCompany;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
